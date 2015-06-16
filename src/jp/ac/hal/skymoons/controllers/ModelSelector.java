package jp.ac.hal.skymoons.controllers;

import java.util.HashMap;

import jp.ac.hal.skymoons.login.LoginAdministrator;
import jp.ac.hal.skymoons.login.LoginUser;
import jp.ac.hal.skymoons.models.*;

/**
 * リクエストとモデル句タスのマッピングを管理するクラス
 * @author IH12B-Touma
 *
 */
public class ModelSelector {
	private static final HashMap<String, AbstractModel> mapping;

	static{
		mapping = new HashMap<String, AbstractModel>();
		mapping.put("/homesystem/fcon/test", new TestModel());
		mapping.put("/HomeSystem/fcon/contents/detail", new ContentsDetailModel());
		mapping.put("/HomeSystem/fc/test", new TestModel());

		// コンテンツモデル
		mapping.put("/HomeSystem/fc/contents/update", new ContentsUpdateModel());
		
		// ログイン認証機能
		mapping.put("/HomeSystem/fc/login/administrator", new LoginAdministrator());
		mapping.put("/HomeSystem/fc/login/user", new LoginUser());
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
