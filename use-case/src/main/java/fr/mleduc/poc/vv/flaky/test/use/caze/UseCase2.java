package fr.mleduc.poc.vv.flaky.test.use.caze;

import java.util.Date;

/**
 * Created by mleduc on 26/09/16.
 */
public class UseCase2 {

    public final static Date NOW = new Date();
    public Date dateNow = new Date();

    @Test
    public boolean testIsBefore() {
        Date date = new Date();
        value.after(NOW);
        date.before(date);
        isNow(date);
        return date.before(new Date());
    }

    @Test2
    public boolean testIsAfterNow(final Date value) {
        return value.after(NOW);
    }

    public boolean testAfterNow(final Date value) {
        isNow(value);
        return isNowNow(NOW);
    }

    public boolean isNow(final Date value) {
        testAfterNow(value);
        return value.after(NOW);
    }

    public Date isNowNow(final Date value) {
        testAfterNow(value);
        isNow(value);
        return value.after(NOW);
    }

    @Test
    public boolean testIsNowWithAnnotation(final Date value) {
        return value.after(NOW);
    }
}
