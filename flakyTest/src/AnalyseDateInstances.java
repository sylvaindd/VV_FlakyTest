import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;

/**
 * Created by Sylvain on 16/11/2016.
 */

public class AnalyseDateInstances extends AbstractProcessor<CtAssignment<Object, Object>> {
    public void process(CtAssignment<Object, Object> element) {
        System.out.println(element);
    }
}
