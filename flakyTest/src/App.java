import models.*;
import org.apache.commons.io.IOUtils;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;
import spoon.support.reflect.code.CtFieldReadImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.code.CtVariableReadImpl;
import spoon.support.reflect.declaration.CtFieldImpl;
import spoon.support.reflect.declaration.CtMethodImpl;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Sylvain on 16/11/2016.
 */
public class App {

    private String path;
    private final TestingParams testingParams;

    public App(String path, TestingParams testingParams) {
        this.path = path;
        this.testingParams = testingParams;
    }

    public void start() {
        if (!(new File(path)).exists()) {
            return;
        }

        if (path.charAt(path.length() - 1) != '/') {
            path += "/";
        }

        final SpoonAPI spoon = new Launcher();
        spoon.getEnvironment().setNoClasspath(true);
        spoon.addInputResource(path);
        final AnalyseDateInstances processor = new AnalyseDateInstances();
        spoon.addProcessor(processor);
        spoon.run();

        final Factory factory = spoon.getFactory();

        final CtModel model = factory.getModel();
        final List<CtClass> classes = model.getElements(element -> true);

        OutputPrettyViewer outputPrettyViewerBuilder = new OutputPrettyViewer(path);
        for (CtClass ctClass : classes) {
            ClassWarnings classWarnings = new ClassWarnings(ctClass.getQualifiedName(), ctClass.getPosition().getFile().getAbsolutePath());

            for (Map.Entry<Params, Boolean> e : testingParams.getParamsBooleanMap().entrySet()) {
                if (e.getKey().equals(Params.DATE) && e.getValue()) {
                    analyseDateInstance(ctClass, classWarnings);
                } else if (e.getKey().equals(Params.NETWORK) && e.getValue()) {
                    //TODO
                } else if (e.getKey().equals(Params.FILE) && e.getValue()) {
                    analyseFileOperations(ctClass, classWarnings);
                } else if (e.getKey().equals(Params.ANNOTATIONS) && e.getValue()) {
                    analyseTestAnnotations(ctClass, classWarnings);
                }
            }

            outputPrettyViewerBuilder.addClassWarning(classWarnings);
        }

        String html = outputPrettyViewerBuilder.GenerateHTMLView();

        try {
            File HTMLFile = new File("result.html");
            IOUtils.write(html, new FileOutputStream(HTMLFile), "UTF-8");
            Desktop.getDesktop().browse(HTMLFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void analyseDateInstance(CtClass ctClass, ClassWarnings classWarnings) {
        final List<String> varFieldBlackList = new ArrayList<>();
        final List<String> varLocalBlackList = new ArrayList<>();

        final List<CtConstructorCall> dateInstancelst = ctClass.getElements(element -> element.getType().getActualClass().equals(Date.class));
        for (CtConstructorCall ctConstructorCall : dateInstancelst) {
            if (ctConstructorCall.getParent() instanceof CtFieldImpl) {
                varFieldBlackList.add(((CtFieldImpl) ctConstructorCall.getParent()).getSimpleName());
            } else if (ctConstructorCall.getParent() instanceof CtLocalVariableImpl) {
                varLocalBlackList.add(((CtLocalVariableImpl) ctConstructorCall.getParent()).getSimpleName());
            }
        }

        ctClass.getMethods().stream().filter(ctMethod -> ctMethod instanceof CtMethodImpl).forEach(ctMethod -> {
            CtMethodImpl method = ((CtMethodImpl) ctMethod);
            method.getAnnotations().stream().filter(ctAnnotation -> "Test".equals(ctAnnotation.getType().getSimpleName())).forEach(ctAnnotation -> {
                final List<CtConstructorCall> lst = method.getElements(element -> element.getType().getActualClass().equals(Date.class));
                for (CtConstructorCall cc : lst) {
                    System.out.println("Date instanciation in test context : " + cc.getPosition());
                    classWarnings.addWarning(new Warning(Warning.Criticality.MEDIUM, Params.DATE, cc.getPosition().getLine()));
                }

                final List<CtFieldReadImpl> lstFieldImpl = method.getElements(element -> true);
                lstFieldImpl.stream().filter(ctFieldRead -> varFieldBlackList.contains(ctFieldRead.getVariable().getSimpleName())).forEach(ctFieldRead -> {
                    System.out.println("Date instanciation in test context : " + ctFieldRead.getPosition());
                    classWarnings.addWarning(new Warning(Warning.Criticality.MEDIUM, Params.DATE, ctFieldRead.getPosition().getLine()));
                });

                final List<CtVariableReadImpl> lstLocalImpl = method.getElements(element -> true);
                lstLocalImpl.stream().filter(ctVariableRead -> varLocalBlackList.contains(ctVariableRead.getVariable().getSimpleName())).forEach(ctVariableRead -> {
                    System.out.println("Date instanciation in test context : " + ctVariableRead.getPosition());
                    classWarnings.addWarning(new Warning(Warning.Criticality.MEDIUM, Params.DATE, ctVariableRead.getPosition().getLine()));
                });
            });
        });
    }

    private void analyseFileOperations(CtClass ctClass, ClassWarnings classWarnings) {
        //TODO
        final List<CtConstructorCall> lst = ctClass.getElements(element -> element.getType().getActualClass().equals(File.class));

        for (CtConstructorCall cc : lst) {
            classWarnings.addWarning(new Warning(Warning.Criticality.LOW, Params.FILE, cc.getPosition().getLine()));
            System.out.println("File operation in test context : " + cc.getPosition());
        }
    }

    private void analyseTestAnnotations(CtClass ctClass, ClassWarnings classWarnings) {
        ctClass.getMethods().stream().filter(ctMethod -> ctMethod instanceof CtMethodImpl).forEach(ctMethod -> {
            CtMethodImpl method = ((CtMethodImpl) ctMethod);
            if (method.getSimpleName().startsWith("test")) {
                boolean isAnnotationAdded = false;
                for (CtAnnotation<? extends Annotation> ctAnnotation : method.getAnnotations()) {
                    if ("Test".equals(ctAnnotation.getType().getSimpleName())) {
                        isAnnotationAdded = true;
                    }
                }
                if (!isAnnotationAdded) {
                    classWarnings.addWarning(new Warning(Warning.Criticality.LOW, Params.ANNOTATIONS, method.getPosition().getLine()));
                    System.out.println("Miss test annotation in test context : " + method.getPosition());
                }
            }
        });
    }

    public static void main(String args[]) {
        TestingParams testingParams = new TestingParams();
        testingParams.put(Params.DATE, true);
        testingParams.put(Params.FILE, true);
        App app = new App("../use-case/src/main/java/", testingParams);
        app.start();
    }
}
