package jp.ac.hal.skymoons.selectors;


import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import jp.ac.hal.skymoons.data.MysqlData;

//リクエストを受け取るための名前を受け取るメソッド
public class RequestColumsSelector {
	private static final HashMap<String, String[]> URI_COLUM_MAPPING;
	static{
		URI_COLUM_MAPPING = new HashMap<String, String[]>();
		//リクエスト名配列の生成
		final String[] testColums = {"a_Id","b_Id","c_Id"};
		
		//URIと配列のマッピング
		URI_COLUM_MAPPING.put("/nn/test",testColums);
	}
	public ArrayList<String> getColums(String uri){
		ArrayList<String> columList = new ArrayList<>();
		for(String columName : URI_COLUM_MAPPING.get(uri)){
			columList.add(columName);
		}
		return columList;
	}
	public MysqlData setValues(HttpServletRequest request, MysqlData mData){
		String uri = request.getRequestURI();
		if(URI_COLUM_MAPPING.get(uri) != null){
			ArrayList<String> array = new ArrayList<>();
			for(String paramName : URI_COLUM_MAPPING.get(uri)){
				array.add(request.getParameter(paramName));
			}
			mData.setRequestValues(array);
		}
		return mData;
	}
}
