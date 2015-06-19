package jp.ac.hal.skymoons.controllers;

import java.util.HashMap;

import jp.ac.hal.skymoons.models.*;
import jp.ac.hal.skymoons.models.customer.AddCustomer;
import jp.ac.hal.skymoons.models.customer.CreateEdit;
import jp.ac.hal.skymoons.models.customer.CreateList;
import jp.ac.hal.skymoons.models.customer.DeleteCustomer;
import jp.ac.hal.skymoons.models.customer.DetailCustomer;
import jp.ac.hal.skymoons.models.login.LoginAdministrator;
import jp.ac.hal.skymoons.models.login.LoginUser;
import jp.ac.hal.skymoons.models.ranking.CreateRankingList;

/**
 * リクエストとモデル句タスのマッピングを管理するクラス
 * @author IH12B-Touma
 *
 */
public class ModelSelector {
	private static final HashMap<String, AbstractModel> mapping;

	static{
		mapping = new HashMap<String, AbstractModel>();
		mapping.put("/HomeSystem/fc/test", new TestModel());
		
		// ログイン認証機能
		mapping.put("/HomeSystem/fc/login/administrator", new LoginAdministrator());
		mapping.put("/HomeSystem/fc/login/user", new LoginUser());
		// ランキング作成機能
		mapping.put("/HomeSystem/fc/ranking/topnum", new CreateRankingList());
		// 顧客情報関連機能
		mapping.put("/HomeSystem/fc/customer/list", new CreateList());
		mapping.put("/HomeSystem/fc/customer/add", new AddCustomer());
		mapping.put("/HomeSystem/fc/customer/detail", new DetailCustomer());
		mapping.put("/HomeSystem/fc/customer/edit", new CreateEdit());
		mapping.put("/HomeSystem/fc/customer/deletecustomer", new DeleteCustomer());
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
