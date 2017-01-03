import com.models.ClassWarnings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by thoma on 03/01/2017.
 */
public class TestUnitairesCeption extends AbstractTestUnitaires {

	@Before
	public void prepareTest() {
		super.prepareTest("../flakyTest/src/test/");
	}

	@Test
	public void testNoFlakyTest() {
		int sum = 0;
		for (ClassWarnings classWarnings : outputPrettyViewerBuilder.getClassWarningsList()) {
			sum += classWarnings.getWarningList().size();
		}
		Assert.assertEquals("No flakkyTest", 0, sum);
	}
}
