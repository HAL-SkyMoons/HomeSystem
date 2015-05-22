package jp.ac.hal.skymoons.selectors;
import java.util.HashMap;

public class ForwardPageSelector{
	private static final HashMap<String, String> URI_MODEL_MAPPING;
	static{
		URI_MODEL_MAPPING = new HashMap<String, String>();
		//URIの関連付け
		URI_MODEL_MAPPING.put("/nn/ins","/nn/sel");
	}
	public static String choice(String uri){
		String target = URI_MODEL_MAPPING.get(uri);
		if(target == null){
			return "/error.jsp";
		}else{
			return target;
		}
	}
}
