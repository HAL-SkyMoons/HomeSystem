package jp.ac.hal.skymoons.controllers;

import java.util.HashMap;

import jp.ac.hal.skymoons.login.LoginAdministrator;
import jp.ac.hal.skymoons.login.LoginUser;
import jp.ac.hal.skymoons.models.EmployeePageModel;
import jp.ac.hal.skymoons.models.EmployeeSearchModel;
import jp.ac.hal.skymoons.models.Home;
import jp.ac.hal.skymoons.models.PlanConfirmation;
import jp.ac.hal.skymoons.models.PlanDetail;
import jp.ac.hal.skymoons.models.PlanEdit;
import jp.ac.hal.skymoons.models.PlanList;
import jp.ac.hal.skymoons.models.PlanRegister;
import jp.ac.hal.skymoons.models.TestModel;

/**
 * リクエストとモデル句タスのマッピングを管理するクラス
 * @author IH12B-Touma
 *
 */
public class ModelSelector {
	private static final HashMap<String, AbstractModel> mapping;

	static{
		mapping = new HashMap<String, AbstractModel>();

		mapping.put("/HomeSystem/fc/Employee", new TestModel());
		//社員出力機能
		mapping.put("/HomeSystem/fc/EmployeeList", new EmployeeSearchModel());
		mapping.put("/HomeSystem/fc/EmployeePage", new EmployeePageModel());

		mapping.put("/homesystem/fc/test", new TestModel());
		mapping.put("/HomeSystem/fc/PlanList", new PlanList());
		mapping.put("/HomeSystem/fc/PlanRegister", new PlanRegister());
		mapping.put("/HomeSystem/fc/PlanConfirmation", new PlanConfirmation());
		mapping.put("/HomeSystem/fc/PlanDetail", new PlanDetail());
		mapping.put("/HomeSystem/fc/PlanEdit", new PlanEdit());
		mapping.put("/HomeSystem/fc/Home", new Home());


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
