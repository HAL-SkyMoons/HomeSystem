package jp.ac.hal.skymoons.models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;


public class EmployeePlofileEditModel extends AbstractModel{

	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		//表示画面用テキスト
		String returnURI="";
		String comment = request.getParameter("comment");
		System.out.println("comment is " +comment);
		//ボタン入力(登録or表示)判定
		if(request.getParameter("button") == null){
			System.out.println("Edit Button Off");
			//編集内容の受け取り
			returnURI="/Employee/EmployeePlofileEditPage.jsp";
		}else{
			System.out.println("Edit Button On");
			//編集画面の描画
			returnURI = "/Employee/EmployeePlofileEditPage.jsp";
		}
		return returnURI;
	}
}
