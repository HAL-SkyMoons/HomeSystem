package jp.ac.hal.skymoons.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/fc/*")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("fconGET!");

		request.setCharacterEncoding("UTF-8");

		// リクエストのあったURIを取得
		String requestURI = request.getRequestURI();

		// 対象となるモデルを取得
		AbstractModel targetModel = ModelSelectorGet.select(requestURI);
		System.out.println("targetModel:" + targetModel);

		// 業務ロジックを実行しJSP名を取得
		String jspName = "/jsp/error.jsp";
		try {
			jspName = targetModel.doService(request, response);
			System.out.println(jspName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// fowerd
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspName);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("fconPOST!");
		
		request.setCharacterEncoding("UTF-8");

		request.setCharacterEncoding("UTF-8");

		// リクエストのあったURIを取得
		String requestURI = request.getRequestURI();

		// 対象となるモデルを取得
		AbstractModel targetModel = ModelSelector.select(requestURI);
		System.out.println("targetModel:" + targetModel);

		// Daoを設定
		// targetModel.setLanguage(languageDao);

		// 業務ロジックを実行しJSP名を取得
		String jspName = "/jsp/error.jsp";
		try {
			jspName = targetModel.doService(request, response);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println(jspName);

		// fowerd
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspName);
		dispatcher.forward(request, response);
	}
	
}
