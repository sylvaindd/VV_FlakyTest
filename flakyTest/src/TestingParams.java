import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sylvain on 16/11/2016.
 */
public class TestingParams {

	private Map<Params, Boolean> paramsBooleanMap;

	public TestingParams() {
		paramsBooleanMap = new HashMap<>();
		for (Params p : Params.values()) {
			put(p, false);
		}
	}

	public Boolean get(Params p) {
		return paramsBooleanMap != null ? paramsBooleanMap.get(p) : null;
	}

	public void put(Params p, Boolean b) {
		if (paramsBooleanMap != null)
			paramsBooleanMap.put(p, b);
	}

	public Map<Params, Boolean> getParamsBooleanMap() {
		return paramsBooleanMap;
	}

}
