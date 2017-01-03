import com.App;
import com.models.OutputPrettyViewer;
import com.models.Params;
import com.models.TestingParams;
import org.junit.Before;

/**
 * Created by Guillaume on 03/01/2017.
 */
public abstract class AbstractTestUnitaires {

    OutputPrettyViewer outputPrettyViewerBuilder;

    @Before
    public void prepareTest(String path) {
        TestingParams testingParams = new TestingParams();
        testingParams.put(Params.DATE, true);
        testingParams.put(Params.NETWORK, true);
        testingParams.put(Params.FILE, true);
        testingParams.put(Params.ANNOTATIONS, true);
        App app = new App(path, testingParams, false);
        app.start();

        outputPrettyViewerBuilder = app.getOutputPrettyViewerBuilder();
    }
}
