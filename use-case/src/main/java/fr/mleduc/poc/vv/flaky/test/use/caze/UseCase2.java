package fr.mleduc.poc.vv.flaky.test.use.caze;

import java.util.Date;

/**
 * Created by mleduc on 26/09/16.
 */
public class UseCase2 {

    public final static Date NOW = new Date();

	@Test
    public boolean testIsBeforeNow(final Date value) {
        return value.before(new Date());
    }

	@Test2
    public boolean testIsAfterNow(final Date value) {
        return value.after(NOW);
    }
	
	public boolean testIsNow(final Date value) {
        return value.after(NOW);
    }
}
