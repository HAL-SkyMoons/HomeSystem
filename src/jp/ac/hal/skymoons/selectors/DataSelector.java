package jp.ac.hal.skymoons.selectors;

import jp.ac.hal.skymoons.data.*;
import java.util.HashMap;

public class DataSelector {
	private HashMap<String, OracleData> URI_DATA_MAPPING;
	
	public DataSelector(){
		OracleData testData = new TestData();
		
		URI_DATA_MAPPING = new HashMap<String, OracleData>();
		//sample
		URI_DATA_MAPPING.put("/nn/test",testData);
	}
	public OracleData choice(String uri){
		OracleData oData = URI_DATA_MAPPING.get(uri);
		return oData;
	}
}
