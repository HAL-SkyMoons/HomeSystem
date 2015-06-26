package jp.ac.hal.skymoons.controllers;

import java.util.HashMap;

import jp.ac.hal.skymoons.models.ContentsAdditionModel;
import jp.ac.hal.skymoons.models.ContentsDeleteModel;
import jp.ac.hal.skymoons.models.ContentsDetailModel;
import jp.ac.hal.skymoons.models.ContentsEditModel;
import jp.ac.hal.skymoons.models.ContentsListModel;
import jp.ac.hal.skymoons.models.ContentsRegistModel;
import jp.ac.hal.skymoons.models.ContentsSearchModel;
import jp.ac.hal.skymoons.models.ContentsUpdateModel;
import jp.ac.hal.skymoons.models.EmployeePageModel;
import jp.ac.hal.skymoons.models.EmployeeSearchModel;
import jp.ac.hal.skymoons.models.Home;
import jp.ac.hal.skymoons.models.PlanCalendar;
import jp.ac.hal.skymoons.models.PlanConfirmation;
import jp.ac.hal.skymoons.models.PlanDetail;
import jp.ac.hal.skymoons.models.PlanEdit;
import jp.ac.hal.skymoons.models.PlanList;
import jp.ac.hal.skymoons.models.PlanRegister;
import jp.ac.hal.skymoons.models.TestModel;
import jp.ac.hal.skymoons.models.customer.CreateAdd;
import jp.ac.hal.skymoons.models.customer.CreateEdit;
import jp.ac.hal.skymoons.models.customer.CreateList;
import jp.ac.hal.skymoons.models.customer.DeleteCustomer;
import jp.ac.hal.skymoons.models.customer.CreateDetail;
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

		mapping.put("/HomeSystem/fc/Employee", new TestModel());
		//社員出力機能
		mapping.put("/HomeSystem/fc/EmployeeList", new EmployeeSearchModel());
		mapping.put("/HomeSystem/fc/EmployeePage", new EmployeePageModel());

		mapping.put("/homesystem/fcon/test", new TestModel());
		mapping.put("/HomeSystem/fc/test", new TestModel());

		// コンテンツモデル
		mapping.put("/HomeSystem/fc/contents/addition", new ContentsAdditionModel());
		mapping.put("/HomeSystem/fc/contents/detail", new ContentsDetailModel());
		mapping.put("/HomeSystem/fc/contents/delete", new ContentsDeleteModel());
		mapping.put("/HomeSystem/fc/contents/edit", new ContentsEditModel());
		mapping.put("/HomeSystem/fc/contents/list", new ContentsListModel());
		mapping.put("/HomeSystem/fc/contents/regist", new ContentsRegistModel());
		mapping.put("/HomeSystem/fc/contents/search", new ContentsSearchModel());
		mapping.put("/HomeSystem/fc/contents/update", new ContentsUpdateModel());

		mapping.put("/homesystem/fc/test", new TestModel());
		mapping.put("/HomeSystem/fc/PlanList", new PlanList());
		mapping.put("/HomeSystem/fc/PlanRegister", new PlanRegister());
		mapping.put("/HomeSystem/fc/PlanConfirmation", new PlanConfirmation());
		mapping.put("/HomeSystem/fc/PlanDetail", new PlanDetail());
		mapping.put("/HomeSystem/fc/PlanEdit", new PlanEdit());
		mapping.put("/HomeSystem/fc/Home", new Home());
		mapping.put("/HomeSystem/fc/PlanCalendar", new PlanCalendar());


		// ログイン認証機能
		mapping.put("/HomeSystem/fc/login/administrator", new LoginAdministrator());
		mapping.put("/HomeSystem/fc/login/user", new LoginUser());
		// ランキング作成機能
		mapping.put("/HomeSystem/fc/ranking/topnum", new CreateRankingList());
		// 顧客情報関連機能
		mapping.put("/HomeSystem/fc/customer/list", new CreateList());
		mapping.put("/HomeSystem/fc/customer/add", new CreateAdd());
		mapping.put("/HomeSystem/fc/customer/detail", new CreateDetail());
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
