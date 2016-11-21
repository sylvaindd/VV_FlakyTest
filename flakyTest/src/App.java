import models.*;
import org.apache.commons.io.IOUtils;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Sylvain on 16/11/2016.
 */
public class App {

	private String				path;
	private final TestingParams	testingParams;

	public App(String path, TestingParams testingParams) {
		this.path = path;
		this.testingParams = testingParams;
	}

	public void start() {
		if (!(new File(path)).exists())
			return;
		if (path.charAt(path.length() - 1) != '/')
			path += "/";
		final SpoonAPI spoon = new Launcher();
		spoon.getEnvironment().setNoClasspath(true);
		spoon.addInputResource(path);
		final AnalyseDateInstances processor = new AnalyseDateInstances();
		spoon.addProcessor(processor);
		spoon.run();

		final Factory factory = spoon.getFactory();

		final CtModel model = factory.getModel();
		final List<CtClass> stateLessClasses = model.getElements(element -> element.getAnnotations().stream()
				.filter(ctAnnotation -> Objects.equals(ctAnnotation.getType().getSimpleName(), "Stateless")).findAny().map(ctAnnotation -> true).orElse(false));

		OutputPrettyViewer outputPrettyViewerBuilder = new OutputPrettyViewer(path);
		for (CtClass ctClass : stateLessClasses) {
			ClassWarnings classWarnings = new ClassWarnings(ctClass.getQualifiedName());

			for (Map.Entry<Params, Boolean> e : testingParams.getParamsBooleanMap().entrySet()) {
				if (e.getKey().equals(Params.DATE) && e.getValue()) {
					final List<CtConstructorCall> lst = ctClass.getElements(element -> element.getType().getActualClass().equals(Date.class));

					for (CtConstructorCall cc : lst) {
						System.out.println("Date instanciation in stateless context : " + cc.getPosition());
						classWarnings.addWarning(new Warning(Warning.Criticality.MEDIUM, "Date", "Date description", cc.getPosition().getLine()));
					}
				}
				else if (e.getKey().equals(Params.FILE) && e.getValue()) {
					final List<CtConstructorCall> lst = ctClass.getElements(element -> element.getType().getActualClass().equals(File.class));

					for (CtConstructorCall cc : lst) {
						classWarnings.addWarning(new Warning(Warning.Criticality.LOW, "File", "File description", cc.getPosition().getLine()));
						System.out.println("File instanciation in stateless context : " + cc.getPosition());
					}
				}
			}
			outputPrettyViewerBuilder.addClassWarning(classWarnings);
		}
		String html = outputPrettyViewerBuilder.GenerateHTMLView();

		try {
			File HTMLFile = new File("result.html");
			IOUtils.write(html, new FileOutputStream(HTMLFile), "UTF-8");
			Desktop.getDesktop().browse(HTMLFile.toURI());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		TestingParams testingParams = new TestingParams();
		testingParams.put(Params.DATE, true);
		testingParams.put(Params.FILE, true);
		App app = new App("../use-case/src/main/java/", testingParams);
		app.start();
	}
}
