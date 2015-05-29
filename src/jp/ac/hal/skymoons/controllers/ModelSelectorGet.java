package jp.ac.hal.skymoons.controllers;

import java.util.HashMap;

import jp.ac.hal.skymoons.login.LoginAdministrator;
import jp.ac.hal.skymoons.login.LoginCustomer;
import jp.ac.hal.skymoons.login.LoginStaff;
import jp.ac.hal.skymoons.models.*;

public class ModelSelectorGet {
	private static final HashMap<String, AbstractModel> mapping;

	static{
		mapping = new HashMap<String, AbstractModel>();
		mapping.put("/homesystem/fcon/test", new TestModel());
		
		// ログイン認証機能
		mapping.put("/HomeSystem/fc/login/administrator", new LoginAdministrator());
		mapping.put("/HomeSystem/fc/login/customer", new LoginCustomer());
		mapping.put("/HomeSystem/fc/login/staff", new LoginStaff());
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

