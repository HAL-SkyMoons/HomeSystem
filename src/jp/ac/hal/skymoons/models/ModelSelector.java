package jp.ac.hal.skymoons.models;

import java.sql.SQLException;
import java.util.HashMap;
import jp.ac.hal.skymoons.core.Core;

public class ModelSelector extends Core {
	@Override
	public void executeSQL() throws SQLException{
		return;
	}
	private static final HashMap<String, Core> URI_MODEL_MAPPING;
	static{
		//URI�ƃ��f���̃}�b�s���O
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
