package jp.ac.hal.skymoons.models;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.core.Core;
import jp.ac.hal.skymoons.models.*;

public class ModelSelector extends Core {
	@Override
	public void executeSQL() throws SQLException{
		return;
	}
	private static final HashMap<String, Core> URI_MODEL_MAPPING;
	static{
		//URIとモデルのマッピング
		URI_MODEL_MAPPING = new HashMap<String, Core>();
		URI_MODEL_MAPPING.put("/nn/nn",new SelectModel("SELECT * FROM TEST"));
	}
	public static Core choice(String uri){
		Core target = URI_MODEL_MAPPING.get(uri);
		if(target == null){
			target = new EmptyModel();
		}else{
			target.setUri(uri);
		}
		return target;
	}
}
