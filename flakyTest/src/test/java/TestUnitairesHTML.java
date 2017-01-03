import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.tidy.Tidy;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by thoma on 03/01/2017.
 */
public class TestUnitairesHTML extends AbstractTestUnitaires {

	@Before
	public void prepareTest() {
		super.prepareTest("../tests_programs/FlakyTestUnitaire");
	}

	@Test
	public void HTMLisCorrect() {
		Tidy tidy = new Tidy();
		tidy.setQuiet(true);
		tidy.setErrout(null);
		InputStream stream = new ByteArrayInputStream(outputPrettyViewerBuilder.GenerateHTMLView().getBytes());
		tidy.parse(stream, System.out);
		Assert.assertEquals("Html error", 0, tidy.getParseErrors());
	}
}
