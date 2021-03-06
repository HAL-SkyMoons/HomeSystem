package jp.ac.hal.skymoons.controllers;

import java.util.HashMap;

import jp.ac.hal.skymoons.models.EmployeeMyPageModel;
import jp.ac.hal.skymoons.models.EmployeePageModel;
import jp.ac.hal.skymoons.models.EmployeePlofileEditModel;
import jp.ac.hal.skymoons.models.EmployeeSearchModel;
import jp.ac.hal.skymoons.models.Home;
import jp.ac.hal.skymoons.models.PlanCalendar;
import jp.ac.hal.skymoons.models.PlanDetail;
import jp.ac.hal.skymoons.models.PlanList;
import jp.ac.hal.skymoons.models.PlanRegister;
import jp.ac.hal.skymoons.models.TestModel;
import jp.ac.hal.skymoons.models.contents.ContentsDetailModel;
import jp.ac.hal.skymoons.models.contents.ContentsEditModel;
import jp.ac.hal.skymoons.models.contents.ContentsListModel;
import jp.ac.hal.skymoons.models.contents.ContentsRegistModel;
import jp.ac.hal.skymoons.models.contents.ContentsSearchModel;
import jp.ac.hal.skymoons.models.customer.CreateAdd;
import jp.ac.hal.skymoons.models.customer.CreateList;
import jp.ac.hal.skymoons.models.customer.InsertCustomer;
import jp.ac.hal.skymoons.models.login.Index;
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

		//indexよう
		mapping.put("/HomeSystem/fc/Index", new Index());
		//社員出力機能
		mapping.put("/HomeSystem/fc/EmployeeList", new EmployeeSearchModel());
		mapping.put("/HomeSystem/fc/EmployeePage", new EmployeePageModel());
		mapping.put("/HomeSystem/fc/EmployeeMyPage", new EmployeeMyPageModel());
		//社員プロフィール編集機能
		mapping.put("/HomeSystem/fc/EmployeeProfileEdit", new EmployeePlofileEditModel());

		mapping.put("/homesystem/fcon/test", new TestModel());
		mapping.put("/homesystem/fc/test", new TestModel());

		// コンテンツモデル
		mapping.put("/HomeSystem/fc/contents/search", new ContentsSearchModel());
		mapping.put("/HomeSystem/fc/contents/list", new ContentsListModel());
		mapping.put("/HomeSystem/fc/contents/detail", new ContentsDetailModel());
		mapping.put("/HomeSystem/fc/contents/regist", new ContentsRegistModel());
		mapping.put("/HomeSystem/fc/contents/edit", new ContentsEditModel());


		//
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
		mapping.put("/HomeSystem/fc/customer/add", new CreateAdd());
		mapping.put("/HomeSystem/fc/customer/insert", new InsertCustomer());
		// 社員情報管理機能
		mapping.put("/HomeSystem/fc/staff/list", new jp.ac.hal.skymoons.models.staff.CreateList());

		// ジャンル管理機能
		mapping.put("/HomeSystem/fc/genre", new jp.ac.hal.skymoons.models.genre.GenreTop());
		mapping.put("/HomeSystem/fc/genreList", new jp.ac.hal.skymoons.models.genre.GenreList());
		mapping.put("/HomeSystem/fc/genreRegister", new jp.ac.hal.skymoons.models.genre.GenreRegister());
		mapping.put("/HomeSystem/fc/genreChange", new jp.ac.hal.skymoons.models.genre.GenreChange());
		mapping.put("/HomeSystem/fc/bigGenreList", new jp.ac.hal.skymoons.models.genre.BigGenreList());
		mapping.put("/HomeSystem/fc/bigGenreRegister", new jp.ac.hal.skymoons.models.genre.BigGenreRegister());
		mapping.put("/HomeSystem/fc/bigGenreChange", new jp.ac.hal.skymoons.models.genre.BigGenreChange());

		//バッチ管理機能
		mapping.put("/HomeSystem/fc/batch", new jp.ac.hal.skymoons.models.batch.BatchList());
		mapping.put("/HomeSystem/fc/batchRegister", new jp.ac.hal.skymoons.models.batch.BatchRegister());
		mapping.put("/HomeSystem/fc/batchChange", new jp.ac.hal.skymoons.models.batch.BatchChange());

		//トロフィー管理機能
		mapping.put("/HomeSystem/fc/trophy", new jp.ac.hal.skymoons.models.trophy.TrophyList());
		mapping.put("/HomeSystem/fc/trophyRegister", new jp.ac.hal.skymoons.models.trophy.TrophyRegister());
		mapping.put("/HomeSystem/fc/trophyDetail", new jp.ac.hal.skymoons.models.trophy.TrophyDetail());
		mapping.put("/HomeSystem/fc/trophyChange", new jp.ac.hal.skymoons.models.trophy.TrophyChange());

		//社内資格管理機能
		mapping.put("/HomeSystem/fc/companyCapacity", new jp.ac.hal.skymoons.models.companycapacity.CompanyCapacityList());
		mapping.put("/HomeSystem/fc/companyCapacityRegister", new jp.ac.hal.skymoons.models.companycapacity.CompanyCapacityRegister());
		mapping.put("/HomeSystem/fc/companyCapacityDetail", new jp.ac.hal.skymoons.models.companycapacity.CompanyCapacityDetail());
		mapping.put("/HomeSystem/fc/companyCapacityChange", new jp.ac.hal.skymoons.models.companycapacity.CompanyCapacityChange());

		// 管理システム
		mapping.put("/HomeSystem/fc/SystemAdmin/login", new jp.ac.hal.skymoons.systemadmin.models.Login());
		mapping.put("/HomeSystem/fc/SystemAdmin/logout", new jp.ac.hal.skymoons.systemadmin.models.Logout());
		mapping.put("/HomeSystem/fc/SystemAdmin/menu", new jp.ac.hal.skymoons.systemadmin.models.Menu());
		mapping.put("/HomeSystem/fc/SystemAdmin/staff/list", new jp.ac.hal.skymoons.systemadmin.models.staff.List());
		mapping.put("/HomeSystem/fc/SystemAdmin/staff/detail", new jp.ac.hal.skymoons.systemadmin.models.staff.Detail());
		mapping.put("/HomeSystem/fc/SystemAdmin/staff/add", new jp.ac.hal.skymoons.systemadmin.models.staff.Add());
		mapping.put("/HomeSystem/fc/SystemAdmin/administrator/list", new jp.ac.hal.skymoons.systemadmin.models.administrator.List());
		mapping.put("/HomeSystem/fc/SystemAdmin/administrator/detail", new jp.ac.hal.skymoons.systemadmin.models.administrator.Detail());

		//ホメ閲覧機能
		mapping.put("/HomeSystem/fc/HomeView", new jp.ac.hal.skymoons.models.HomeView() );
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

