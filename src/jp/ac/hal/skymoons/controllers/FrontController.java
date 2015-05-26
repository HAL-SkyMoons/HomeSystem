package jp.ac.hal.skymoons.controllers;

import jp.ac.hal.skymoons.models.ModelSelector;
import jp.ac.hal.skymoons.selectors.DataSelector;
import jp.ac.hal.skymoons.selectors.ForwardPageSelector;
import jp.ac.hal.skymoons.selectors.RequestNameSelector;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.core.Core;
import jp.ac.hal.skymoons.dao.MysqlDao;
import jp.ac.hal.skymoons.data.MysqlData;
import jp.ac.hal.skymoons.selectors.RequestColumsSelector;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/admin/exec/*")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字コード設定等
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//URI取得
		String uri = request.getRequestURI();
		
		//モデルの選択
		Core core = ModelSelector.choice(uri);
		
		//使用するデータの選択
		DataSelector ds = new DataSelector();
		MysqlData mData = ds.choice(uri);

		//リクエスト値をdataにセット
		RequestColumsSelector rgs = new RequestColumsSelector();
		rgs.setValues(request, mData);
		
		//coreにMysqlDataのセット
		core.setMysqlData(mData);
		
		//データベース処理
		try{
			//DAOの生成とセット
			MysqlDao mysqlDao = new MysqlDao();
			core.setMysqlDao(mysqlDao);
			try{
				//SQLの実行
				//SELECTであればmDataへ値がセットされる
				core.executeSQL();
				//処理確定
				core.getMysqlDao().commit();
			}catch(Exception e){
				//処理取り消し
				core.getMysqlDao().rollback();
				e.printStackTrace();
			}finally{
				core.getMysqlDao().close();
			}
		}catch(SQLException e){
			//エラー処理 SQL実行エラー?
			e.printStackTrace();
		}catch(NamingException e){
			//エラー処理 初期コンテキスト等
			e.printStackTrace();
		}catch(Exception e){
			//その他のエラー処理
			e.printStackTrace();
		}
		//リクエストに値を設定
		if(core.getList() != null && RequestNameSelector.getRequestName(uri) != null){
			request.setAttribute(RequestNameSelector.getRequestName(uri), core.getList());
		}
		
		//次ページ移動処理
		RequestDispatcher dispatcher;
		
		//移動先の取得
		String forward = ForwardPageSelector.choice(uri);
		dispatcher = request.getRequestDispatcher(forward);
		
		//移動処理
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}