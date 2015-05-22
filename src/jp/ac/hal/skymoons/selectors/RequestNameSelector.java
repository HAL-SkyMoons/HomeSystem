
package jp.ac.hal.skymoons.selectors;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestNameSelector{
	private static final HashMap<String, String[]> URI_MODEL_MAPPING;
	static{
		URI_MODEL_MAPPING = new HashMap<String, String[]>();
		String[] testNames = {"test1","test2"};
		//sample
		URI_MODEL_MAPPING.put("/nn/test",testNames);
	}
	public static String[] getRequestName(String uri){
		return URI_MODEL_MAPPING.get(uri);
	}
}