package com;

import com.models.*;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import org.apache.commons.io.IOUtils;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;
import spoon.support.reflect.code.CtFieldReadImpl;
import spoon.support.reflect.declaration.CtFieldImpl;
import spoon.support.reflect.declaration.CtMethodImpl;
import spoon.support.reflect.reference.CtExecutableReferenceImpl;
import sun.net.www.http.HttpClient;
import sun.net.www.protocol.http.HttpURLConnection;

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

	private String				path;
	private final TestingParams	testingParams;
	private OutputPrettyViewer	outputPrettyViewerBuilder;

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
		spoon.run();

		final Factory factory = spoon.getFactory();

		final CtModel model = factory.getModel();
		final List<CtClass> classes = model.getElements(element -> true);

		outputPrettyViewerBuilder = new OutputPrettyViewer(path);
		for (CtClass ctClass : classes) {
			ClassWarnings classWarnings = new ClassWarnings(ctClass.getQualifiedName(), ctClass.getPosition().getFile().getAbsolutePath());

			for (Map.Entry<Params, Boolean> e : testingParams.getParamsBooleanMap().entrySet()) {
				if (e.getKey().equals(Params.DATE) && e.getValue()) {
					analyseTypeInstance(ctClass, classWarnings, Date.class);
				}
				else if (e.getKey().equals(Params.NETWORK) && e.getValue()) {
					analyseTypeInstance(ctClass, classWarnings, HttpClient.class);
					analyseTypeInstance(ctClass, classWarnings, HttpURLConnection.class);
					analyseTypeInstance(ctClass, classWarnings, HttpResponse.class);
					analyseTypeInstance(ctClass, classWarnings, HttpRequest.class);
				}
				else if (e.getKey().equals(Params.FILE) && e.getValue()) {
					analyseTypeInstance(ctClass, classWarnings, File.class);
				}
				else if (e.getKey().equals(Params.ANNOTATIONS) && e.getValue()) {
					analyseTestAnnotations(ctClass, classWarnings);
				}
			}

			outputPrettyViewerBuilder.addClassWarning(classWarnings);
		}

		String html = outputPrettyViewerBuilder.GenerateHTMLView();

		try {
			File HTMLFile = new File("result.html");
            FileOutputStream fileOutputStream = new FileOutputStream(HTMLFile);
            IOUtils.write(html, fileOutputStream, "UTF-8");
            fileOutputStream.close();
			Desktop.getDesktop().browse(HTMLFile.toURI());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private <T> void analyseTypeInstance(CtClass ctClass, ClassWarnings classWarnings, Class<T> cl) {
		final List<String> varFieldBlackList = new ArrayList<>();

		final List<String> methodAlreadyCheck = new ArrayList<>();

		final List<CtConstructorCall<T>> typeInstancelst = ctClass.getElements(element -> element.getType().getQualifiedName().equals(cl.getCanonicalName()));
		for (CtConstructorCall<T> ctConstructorCall : typeInstancelst) {
			if (ctConstructorCall.getParent() instanceof CtFieldImpl) {
				varFieldBlackList.add(((CtFieldImpl) ctConstructorCall.getParent()).getSimpleName());
			}
		}

		ctClass.getMethods().stream().filter(ctMethod -> ctMethod instanceof CtMethodImpl).forEach(ctMethod -> {
			CtMethodImpl method = ((CtMethodImpl) ctMethod);
			method.getAnnotations().stream().filter(ctAnnotation -> "Test".equals(ctAnnotation.getType().getSimpleName())).forEach(ctAnnotation -> {
				addTypeInstanceWarnings(ctClass, classWarnings, varFieldBlackList, methodAlreadyCheck, method, cl);
			});
		});
	}

	private <T> void addTypeInstanceWarnings(CtClass ctClass, ClassWarnings classWarnings, List<String> varFieldBlackList, List<String> methodAlreadyCheck, CtMethodImpl method, Class<T> cl) {
		methodAlreadyCheck.add(method.getSimpleName());
		final List<CtConstructorCall> lst = method.getElements(element -> element.getType().getQualifiedName().equals(cl.getCanonicalName()));
		for (CtConstructorCall cc : lst) {
			System.out.println(cl + " instanciation in test context : " + cc.getPosition());
			classWarnings.addWarning(new Warning(Warning.Criticality.MEDIUM, Params.getParamsForClass(cl), cc.getPosition().getLine()));
		}

		final List<CtFieldReadImpl> lstFieldImpl = method.getElements(element -> true);
		lstFieldImpl.stream().filter(ctFieldRead -> varFieldBlackList.contains(ctFieldRead.getVariable().getSimpleName())).forEach(ctFieldRead -> {
			System.out.println(cl + " instanciation in test context : " + ctFieldRead.getPosition());
			classWarnings.addWarning(new Warning(Warning.Criticality.MEDIUM, Params.getParamsForClass(cl), ctFieldRead.getPosition().getLine()));
		});

		for (CtStatement ctStatement : method.getBody().getStatements()) {
			List<CtExecutableReferenceImpl> elementsList = ctStatement.getElements(element -> true);
			for (CtExecutableReferenceImpl ctExecutableReference : elementsList) {
				String classTypeName = ctExecutableReference.getDeclaringType().getSimpleName();
				String methodName = ctExecutableReference.getSimpleName();
				if (classTypeName.equals(ctClass.getSimpleName()) && !methodAlreadyCheck.contains(methodName)) {
					CtMethodImpl underMethod = getMethodFromName(ctClass, methodName);
					addTypeInstanceWarnings(ctClass, classWarnings, varFieldBlackList, methodAlreadyCheck, underMethod, cl);
				}
			}
		}
	}

	private CtMethodImpl getMethodFromName(CtClass ctClass, String methodName) {
		for (Object ctMethod : ctClass.getMethods()) {
			if (ctMethod instanceof CtMethodImpl) {
				if (((CtMethodImpl) ctMethod).getSimpleName().equals(methodName)) {
					return (CtMethodImpl) ctMethod;
				}
			}
		}
		return null;
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

	public OutputPrettyViewer getOutputPrettyViewerBuilder() {
		return outputPrettyViewerBuilder;
	}
}
