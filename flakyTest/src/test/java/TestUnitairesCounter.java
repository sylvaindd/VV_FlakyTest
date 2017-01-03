import com.App;
import com.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by thoma on 03/01/2017.
 */
public class TestUnitairesCounter {
    OutputPrettyViewer outputPrettyViewerBuilder;
    @Before
    public void prepareTest (){
        TestingParams testingParams = new TestingParams();
        testingParams.put(Params.DATE, true);
        testingParams.put(Params.NETWORK, true);
        testingParams.put(Params.FILE, true);
        testingParams.put(Params.ANNOTATIONS, true);
        App app = new App("../tests_programs/FlakyTestUnitaire/", testingParams,false);
        app.start();

        outputPrettyViewerBuilder = app.getOutputPrettyViewerBuilder();
    }
    private int checkDate(){
        int dateCounter = 0;
        if (outputPrettyViewerBuilder.getClassWarningsList() != null && outputPrettyViewerBuilder.getClassWarningsList().size() > 0) {

            for (ClassWarnings classWarnings : outputPrettyViewerBuilder.getClassWarningsList()) {
                for (Warning warning : classWarnings.getWarningList()) {
                    if (warning.getParams().equals(Params.DATE))
                        dateCounter++;
                }
            }
        }
        return dateCounter;
    }
    private int checkFile(){
        int fileCounter = 0;
        if (outputPrettyViewerBuilder.getClassWarningsList() != null && outputPrettyViewerBuilder.getClassWarningsList().size() > 0) {

            for (ClassWarnings classWarnings : outputPrettyViewerBuilder.getClassWarningsList()) {
                for (Warning warning : classWarnings.getWarningList()) {
                    if (warning.getParams().equals(Params.FILE))
                        fileCounter++;
                }
            }
        }
        return fileCounter;
    }
    private int checkAnnotation(){
        int annotationCounter = 0;
        if (outputPrettyViewerBuilder.getClassWarningsList() != null && outputPrettyViewerBuilder.getClassWarningsList().size() > 0) {

            for (ClassWarnings classWarnings : outputPrettyViewerBuilder.getClassWarningsList()) {
                for (Warning warning : classWarnings.getWarningList()) {
                    if (warning.getParams().equals(Params.ANNOTATIONS))
                        annotationCounter++;
                }
            }
        }
        return annotationCounter;
    }
    private int checkWebService(){
        int webserviceCounter = 0;
        if (outputPrettyViewerBuilder.getClassWarningsList() != null && outputPrettyViewerBuilder.getClassWarningsList().size() > 0) {

            for (ClassWarnings classWarnings : outputPrettyViewerBuilder.getClassWarningsList()) {
                for (Warning warning : classWarnings.getWarningList()) {
                    if (warning.getParams().equals(Params.NETWORK))
                        webserviceCounter++;
                }
            }
        }
        return webserviceCounter;
    }
    @Test
    public void checkDateSup1() throws Exception {
        int dateCounter = checkDate();
        Assert.assertTrue("dateCounter "+dateCounter,checkDate()>=1);
    }
    @Test
    public void checkDateCount() throws Exception {
        int dateCounter = checkDate();
        Assert.assertEquals("dateCounter "+dateCounter,4,checkDate());

    }
    @Test
    public void checkFileSup1() throws Exception {
        int fileCounter = checkFile();
        Assert.assertTrue("fileCounter "+fileCounter,fileCounter>=1);

    }
    @Test
    public void checkFileCount() throws Exception {

        int fileCounter = checkFile();
        Assert.assertEquals("fileCounter "+fileCounter,4,fileCounter);

    }
    @Test
    public void checkAnnotationSup1() throws Exception {

        int annotationCounter = checkAnnotation();
        Assert.assertTrue("annotationCounter "+annotationCounter,annotationCounter>=1);
    }
    @Test
    public void checkAnnotationCount() throws Exception {

        int annotationCounter = checkAnnotation();
        Assert.assertEquals("annotationCounter "+annotationCounter,2,annotationCounter);
    }

    @Test
    public void checkWebServiceSup1() throws Exception {

        int webserviceCounter = checkWebService();
        Assert.assertTrue("Webservicecounter "+webserviceCounter,webserviceCounter>=1);
    }
    @Test
    public void checkWebServiceCount() throws Exception {
        int webserviceCounter = checkWebService();
        Assert.assertEquals("Webservicecounter "+webserviceCounter,5,webserviceCounter);
    }
    @Test public void checkWebServiceOff(){
        TestingParams testingParams = new TestingParams();
        testingParams.put(Params.DATE, true);
        testingParams.put(Params.NETWORK, false);
        testingParams.put(Params.FILE, true);
        testingParams.put(Params.ANNOTATIONS, true);
        App app = new App("../tests_programs/FlakyTestUnitaire/", testingParams,false);
        app.start();

        outputPrettyViewerBuilder = app.getOutputPrettyViewerBuilder();
        int webserviceCounter = checkWebService();
        Assert.assertEquals("webserviceCounter"+webserviceCounter,0,webserviceCounter);
    }
    @Test public void checkDateOff(){
        TestingParams testingParams = new TestingParams();
        testingParams.put(Params.DATE, false);
        testingParams.put(Params.NETWORK, true);
        testingParams.put(Params.FILE, true);
        testingParams.put(Params.ANNOTATIONS, true);
        App app = new App("../tests_programs/FlakyTestUnitaire/", testingParams,false);
        app.start();

        outputPrettyViewerBuilder = app.getOutputPrettyViewerBuilder();
        int checkDate = checkDate();
        Assert.assertEquals("checkDate"+checkDate,0,checkDate);
    }
    @Test public void checkFileOff(){
        TestingParams testingParams = new TestingParams();
        testingParams.put(Params.DATE, true);
        testingParams.put(Params.NETWORK, true);
        testingParams.put(Params.FILE, false);
        testingParams.put(Params.ANNOTATIONS, true);
        App app = new App("../tests_programs/FlakyTestUnitaire/", testingParams,false);
        app.start();

        outputPrettyViewerBuilder = app.getOutputPrettyViewerBuilder();
        int checkFile = checkFile();
        Assert.assertEquals("checkFile"+checkFile,0,checkFile);
    }
    @Test public void checkAnnotationOff(){
        TestingParams testingParams = new TestingParams();
        testingParams.put(Params.DATE, true);
        testingParams.put(Params.NETWORK, true);
        testingParams.put(Params.FILE, true);
        testingParams.put(Params.ANNOTATIONS, false);
        App app = new App("../tests_programs/FlakyTestUnitaire/", testingParams,false);
        app.start();

        outputPrettyViewerBuilder = app.getOutputPrettyViewerBuilder();
        int checkAnnotation = checkAnnotation();
        Assert.assertEquals("checkAnnotation"+checkAnnotation,0,checkAnnotation);
    }

}
