package jp.ac.hal.skymoons.controllers;

import java.util.HashMap;

import jp.ac.hal.skymoons.models.EmployeeSearchModel;
import jp.ac.hal.skymoons.models.TestModel;

public class ModelSelectorGet {
	private static final HashMap<String, AbstractModel> mapping;

	static{
		mapping = new HashMap<String, AbstractModel>();
		mapping.put("/HomeSystem/fc/test", new TestModel());
		mapping.put("/HomeSystem/fc/EmployeeList", new EmployeeSearchModel());
	}

/**
 * URIに応じたモデルのインスタンスを返します
 * @param uri　リクエストURI
 * @return　実行すべきモデルインスタンス
 */
	public static AbstractModel select(String uri){
		System.out.println("リクエストURI→"+uri);
		AbstractModel model = mapping.get(uri);
		if(model == null){
			System.out.println("nullとび");
			model = new TestModel();
		}
		System.out.println("model:"+model);
		return model;
	}
}

