
package jp.ac.hal.skymoons.selectors;

import java.util.HashMap;

public class RequestNameSelector{
	private static final HashMap<String, String> URI_MODEL_MAPPING;
	static{
		URI_MODEL_MAPPING = new HashMap<String, String>();
		//sample
		URI_MODEL_MAPPING.put("/nn/test","aa");
	}
	public static String getRequestName(String uri){
		return URI_MODEL_MAPPING.get(uri);
	}
}