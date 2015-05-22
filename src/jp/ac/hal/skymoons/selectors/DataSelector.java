package jp.ac.hal.skymoons.selectors;

import jp.ac.hal.skymoons.data.*;
import java.util.HashMap;

public class DataSelector {
	private HashMap<String, MysqlData> URI_DATA_MAPPING;
	
	public DataSelector(){
		MysqlData testData = new TestData();
		
		URI_DATA_MAPPING = new HashMap<String, MysqlData>();
		//sample
		URI_DATA_MAPPING.put("/nn/test",testData);
	}
	public MysqlData choice(String uri){
		MysqlData mData = URI_DATA_MAPPING.get(uri);
		return mData;
	}
}
