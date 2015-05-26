package jp.ac.hal.skymoons.controllers;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全てのビジネスロジックを表す抽象クラス
 * @author IH12B-Touma
 *
 */
public abstract class AbstractModel {
	/**
	 * 全てのビジネスロジックを表す抽象クラスです
	 * 各モデルクラスは必ずこのクラスを継承してください
	 */

	/**
	 * 業務ロジックを実行します
	 * @return　表示するJSP名
	 */
	abstract public String doService(
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception;
}
