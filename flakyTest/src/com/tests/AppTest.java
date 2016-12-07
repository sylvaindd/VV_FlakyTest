package com.tests;

import com.App;
import com.models.*;

/**
 * Created by Sylvain on 07/12/2016.
 */
public class AppTest {
	@org.junit.Test
	public void start() throws Exception {
		TestingParams testingParams = new TestingParams();
		testingParams.put(Params.DATE, true);
		testingParams.put(Params.NETWORK, true);
		testingParams.put(Params.FILE, true);
		testingParams.put(Params.ANNOTATIONS, true);
		App app = new App("/Users/DUARTE/git/VV_FlakyTest/use-case", testingParams);
		app.start();

		OutputPrettyViewer outputPrettyViewerBuilder = app.getOutputPrettyViewerBuilder();

		int dateCounter = 0;
		if (outputPrettyViewerBuilder.getClassWarningsList() != null && outputPrettyViewerBuilder.getClassWarningsList().size() > 0) {

			for (ClassWarnings classWarnings : outputPrettyViewerBuilder.getClassWarningsList()) {
				for (Warning warning : classWarnings.getWarningList()) {
					if (warning.getParams().equals(Params.DATE))
						dateCounter++;
				}
			}
		}
		assert dateCounter == 8;
	}

}