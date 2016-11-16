import models.Params;
import models.TestingParams;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Sylvain on 16/11/2016.
 */
public class App {

	private final String		path;
	private final TestingParams	testingParams;

	public App(String path, TestingParams testingParams) {
		this.path = path;
		this.testingParams = testingParams;
	}

	public void start() {
		if (!(new File(path)).exists())
			return;
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

		OutputPrettyViewerBuilder outputPrettyViewerBuilder = new OutputPrettyViewerBuilder();

		for (CtClass ctClass : stateLessClasses) {

			for (Map.Entry<Params, Boolean> e : testingParams.getParamsBooleanMap().entrySet()) {
				if (e.getKey().equals(Params.DATE) && e.getValue()) {
					final List<CtConstructorCall> lst = ctClass.getElements(element -> element.getType().getActualClass().equals(Date.class));

					for (CtConstructorCall cc : lst) {
						System.out.println("Date instanciation in stateless context : " + cc.getPosition());
					}
				}
				else if (e.getKey().equals(Params.FILE) && e.getValue()) {
					final List<CtConstructorCall> lst = ctClass.getElements(element -> element.getType().getActualClass().equals(File.class));

					for (CtConstructorCall cc : lst) {
						System.out.println("File instanciation in stateless context : " + cc.getPosition());
					}
				}
			}
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
