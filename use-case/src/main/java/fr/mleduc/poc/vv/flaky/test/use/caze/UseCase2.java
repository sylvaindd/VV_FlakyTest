package fr.mleduc.poc.vv.flaky.test.use.caze;

import java.util.Date;

/**
 * Created by mleduc on 26/09/16.
 */
public class UseCase2 {

    public final static Date NOW = new Date();

    public boolean isBeforeNow(final Date value) {
        return value.before(new Date());
    }

    public boolean isAfterNow(final Date value) {
        return value.after(NOW);
    }
}
