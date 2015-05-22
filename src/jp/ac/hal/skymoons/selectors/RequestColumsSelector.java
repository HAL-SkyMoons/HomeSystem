package jp.ac.hal.skymoons.selectors;


import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import jp.ac.hal.skymoons.data.OracleData;

//���N�G�X�g���󂯎�邽�߂̖��O���󂯎�郁�\�b�h
public class RequestColumsSelector {
	private static final HashMap<String, String[]> URI_COLUM_MAPPING;
	static{
		URI_COLUM_MAPPING = new HashMap<String, String[]>();
		//���N�G�X�g���z��̐���
		final String[] testColums = {"a_Id","b_Id","c_Id"};
		
		//URI�Ɣz��̃}�b�s���O
		URI_COLUM_MAPPING.put("/nn/test",testColums);
	}
	public ArrayList<String> getColums(String uri){
		ArrayList<String> array = new ArrayList<>();
		for(String columName : URI_COLUM_MAPPING.get(uri)){
			array.add(columName);
		}
		return array;
	}
	public OracleData setValues(HttpServletRequest request, OracleData oData){
		String uri = request.getRequestURI();
		if(URI_COLUM_MAPPING.get(uri) != null){
			ArrayList<String> array = new ArrayList<>();
			for(String paramName : URI_COLUM_MAPPING.get(uri)){
				array.add(request.getParameter(paramName));
			}
			oData.setRequestValues(array);
		}
		return oData;
	}
}
