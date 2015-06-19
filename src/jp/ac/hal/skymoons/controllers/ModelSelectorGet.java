package jp.ac.hal.skymoons.controllers;

import java.util.HashMap;

import jp.ac.hal.skymoons.models.ContentsDetailModel;
import jp.ac.hal.skymoons.models.ContentsEditModel;
import jp.ac.hal.skymoons.models.ContentsListModel;
import jp.ac.hal.skymoons.models.ContentsRegistModel;
import jp.ac.hal.skymoons.models.ContentsSearchModel;
import jp.ac.hal.skymoons.models.EmployeePageModel;
import jp.ac.hal.skymoons.models.EmployeeSearchModel;
import jp.ac.hal.skymoons.models.Home;
import jp.ac.hal.skymoons.models.PlanDetail;
import jp.ac.hal.skymoons.models.PlanList;
import jp.ac.hal.skymoons.models.PlanRegister;
import jp.ac.hal.skymoons.models.TestModel;
import jp.ac.hal.skymoons.models.customer.AddCustomer;
import jp.ac.hal.skymoons.models.customer.CreateList;
import jp.ac.hal.skymoons.models.customer.InsertCustomer;
import jp.ac.hal.skymoons.models.login.LoginAdministrator;
import jp.ac.hal.skymoons.models.login.LoginUser;
import jp.ac.hal.skymoons.models.login.LogoutAdministrator;
import jp.ac.hal.skymoons.models.login.LogoutUser;
import jp.ac.hal.skymoons.models.ranking.CreateRankingList;

public class ModelSelectorGet {
	private static final HashMap<String, AbstractModel> mapping;

	static{
		mapping = new HashMap<String, AbstractModel>();
		mapping.put("/HomeSystem/fc/test", new TestModel());
		//社員出力機能
		mapping.put("/HomeSystem/fc/EmployeeList", new EmployeeSearchModel());
		mapping.put("/HomeSystem/fc/EmployeePage", new EmployeePageModel());
		mapping.put("/homesystem/fcon/test", new TestModel());
		mapping.put("/homesystem/fc/test", new TestModel());
		
		// コンテンツモデル
		mapping.put("/HomeSystem/fc/contents/search", new ContentsSearchModel());
		mapping.put("/HomeSystem/fc/contents/list", new ContentsListModel());
		mapping.put("/HomeSystem/fc/contents/detail", new ContentsDetailModel());
		mapping.put("/HomeSystem/fc/contents/regist", new ContentsRegistModel());
		mapping.put("/HomeSystem/fc/contents/edit", new ContentsEditModel());
		
		
		mapping.put("/HomeSystem/fc/PlanList", new PlanList());
		mapping.put("/HomeSystem/fc/PlanRegister", new PlanRegister());
		mapping.put("/HomeSystem/fc/PlanDetail", new PlanDetail());
		mapping.put("/HomeSystem/fc/Home", new Home());
		mapping.put("/HomeSystem/fc/PlanCalendar", new PlanCalendar());

		// ログイン認証機能
		mapping.put("/HomeSystem/fc/login/administrator", new LoginAdministrator());
		mapping.put("/HomeSystem/fc/login/user", new LoginUser());
		mapping.put("/HomeSystem/fc/logout/administrator", new LogoutAdministrator());
		mapping.put("/HomeSystem/fc/logout/user", new LogoutUser());
		// ランキング作成機能
		mapping.put("/HomeSystem/fc/ranking/topnum", new CreateRankingList());
		// 顧客情報関連機能
		mapping.put("/HomeSystem/fc/customer/list", new CreateList());
		mapping.put("/HomeSystem/fc/customer/add", new AddCustomer());
		mapping.put("/HomeSystem/fc/customer/insert", new InsertCustomer());
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

