import com.App;
import com.models.Params;
import com.models.TestingParams;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by thoma on 03/01/2017.
 */
public class TestUnitairesCeption extends AbstractTestUnitaires{
    @Before
    public void prepareTest() {
        super.prepareTest("../flakyTest/src/test/");
    }
    @Test
    public void testNoFlakyTest (){
        Assert.assertEquals("No flakkyTest",0,outputPrettyViewerBuilder.getClassWarningsList().size());
    }
}
