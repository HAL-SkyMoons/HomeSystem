package jp.ac.hal.skymoons.models;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.BigGenreBean;
import jp.ac.hal.skymoons.beans.EmployeeBatchBean;
import jp.ac.hal.skymoons.beans.EmployeeCapacityBean;
import jp.ac.hal.skymoons.beans.EmployeeCompanyCapacityBean;
import jp.ac.hal.skymoons.beans.EmployeeGenreBean;
import jp.ac.hal.skymoons.beans.EmployeeHomeLogBean;
import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.EmployeePlanBean;
import jp.ac.hal.skymoons.beans.EmployeePlanCommentBean;
import jp.ac.hal.skymoons.beans.EmployeeTrophyBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class EmployeePlofileEditModel extends AbstractModel{

	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("employeeProfielModel entered");
		SampleDao dao = new SampleDao();
		SessionController sessionController = new SessionController(request);
		System.out.println("sessionID is "+sessionController.getUserId());
		//表示画面用テキスト
		String returnURI="";
		//コメント欄初期値テキスト
		String defaultComment="";
    	//form情報用リスト
		List items;
		//更新用引数
		String comment = "";
		String password= "";
		String employeeId = (String)sessionController.getUserId();
		//DB更新確認
		boolean commentJud = false;
		boolean passwordJud = false;
		boolean imageJud = false;
		//form入力判定
		if(ServletFileUpload.isMultipartContent(request)){
			System.out.println("form item is seted");
			// ファクトリー生成
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			factory.setSizeThreshold(1024);
			upload.setSizeMax(-1);
			try {
				items = upload.parseRequest(request);
				// 全フィールドに対するループ
				for (Object val : items) {
					FileItem item = (FileItem) val;
					if (item.isFormField()) {
						//commentのDB更新処理
						if(item.getFieldName().equals("comment")){
							comment = item.getString("UTF-8");
							System.out.println("comment is "+comment);
							if(comment != null && !(comment.isEmpty())){
								commentJud = dao.setEmployeeDetailCommentUpdate(employeeId, comment);
								if(commentJud==false){
									System.out.println("DB update err...");
									dao.rollback();
								}else{
									System.out.println("DB update success!!");
									dao.commit();
								}
							}
						}else if(item.getFieldName().equals("password")){
							password = item.getString("UTF-8");
							System.out.println("password is "+password);
							if(password != null && !(password.isEmpty())){
								passwordJud = dao.setPasswordforChange(employeeId,password);
								if(passwordJud==false){
									System.out.println("DB update err...");
									dao.rollback();
								}else{
									System.out.println("DB update success!!");
									dao.commit();
								}
							}
						}
					} else {
						//画像のinput処理
						System.out.println("Image file got is "+item.getName());
						if(item.getName() !=null && !(item.getName().isEmpty())){
							if(item.getName().endsWith(".jpg")){
								//画像拡張子、名前の確認
								//画像パスの取得
								String path = request.getServletContext().getRealPath("/images/employees/");
								System.out.println("image folder is "+path);
								//画像名の取得
								String fileName = item.getName();
								fileName = (new File(fileName)).getName();
								System.out.println((new File(path + "/"+employeeId+".jpg")));
								//画像の保存
								item.write(new File(path + "/"+employeeId+".jpg"));
								imageJud = true;
							}else{
								//画像拡張子がjpg以外の場合
								System.out.println("Image update err");
								request.setAttribute("err", "画像の拡張子がjpgではありません");
								defaultComment = dao.getEmployeeDetailComment(employeeId);
						    	request.setAttribute("comment",defaultComment);
								returnURI = "/Employee/EmployeePlofileEditPage.jsp";
								break;
							}
						}
					}
				}
			} catch (FileUploadException e) {
				// エラー処理
				throw new ServletException(e);
			}
			//正常終了した場合マイページへ遷移
			if(imageJud == true || commentJud == true || passwordJud == true){
				//マイページ用引数
				ArrayList<EmployeePageBean> employeePageReturn = new ArrayList<EmployeePageBean>();
				ArrayList<EmployeeBatchBean> employeeBatchReturn = new ArrayList<EmployeeBatchBean>();
				ArrayList<EmployeeGenreBean> employeeGenreReturn = new ArrayList<EmployeeGenreBean>();
				ArrayList<EmployeePlanBean> employeePlanReturn = new ArrayList<EmployeePlanBean>();
				ArrayList<EmployeePlanCommentBean> employeePlanCommentReturn = new ArrayList<EmployeePlanCommentBean>();
				ArrayList<EmployeeHomeLogBean> employeeHomeLogReturn = new ArrayList<EmployeeHomeLogBean>();
				ArrayList<EmployeeBatchBean> employeeBatchMonthReturn = new ArrayList<EmployeeBatchBean>();
				ArrayList<EmployeeBatchBean> employeeBatchYearReturn = new ArrayList<EmployeeBatchBean>();
				ArrayList<BigGenreBean> bigGenreList = new ArrayList<BigGenreBean>();
				//マイページ追加項目
				ArrayList<EmployeeCapacityBean> employeeCapacityReturn = new ArrayList<EmployeeCapacityBean>();
				ArrayList<EmployeeCompanyCapacityBean> employeeCompanyCapacityReturn = new ArrayList<EmployeeCompanyCapacityBean>();
				ArrayList<EmployeeTrophyBean> employeeTrophyReturn = new ArrayList<EmployeeTrophyBean>();
				//チャート描画用変数
				String[] employeeChartBatchName = {};
				int[] employeeChartBatchCount = {};
				int batchKindCount=0;
				//月間
				int[] employeeChartBatchCountMonth = {};
				//年間
				int[] employeeChartBatchCountYear = {};
				//通算
				int[] employeeChartBatchCountTotal = {};
				//引数準備
				String loginUserId = (String)sessionController.getUserId();
				//日付取得(月,年単位)
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				Calendar cal = Calendar.getInstance();
				Date nowDate = new Date();
				cal.setTime(nowDate);
				cal.add(Calendar.MONTH, -1);
				String monthDate = df.format(cal.getTime());
				cal.setTime(nowDate);
				cal.add(Calendar.YEAR, -1);
				String yearDate = df.format(cal.getTime());

				//情報取得処理
				employeePageReturn = (ArrayList<EmployeePageBean>)dao.getEmployeeDetail(loginUserId);
				employeeBatchReturn = (ArrayList<EmployeeBatchBean>)dao.getEmployeeDetailOfBatch(loginUserId);
				employeeGenreReturn = (ArrayList<EmployeeGenreBean>)dao.getEmployeeDetailOfGenre(loginUserId);
				employeePlanReturn = (ArrayList<EmployeePlanBean>)dao.getEmployeeDetailOfPlan(loginUserId);
				employeePlanCommentReturn = (ArrayList<EmployeePlanCommentBean>)dao.getEmployeeDetailOfPlanComment(loginUserId);
				employeeHomeLogReturn = (ArrayList<EmployeeHomeLogBean>)dao.getEmployeeDetailOfHomeLog(loginUserId);
				employeeBatchMonthReturn = (ArrayList<EmployeeBatchBean>)dao.getEmployeeDetailOfBatchInLimited(loginUserId,monthDate);
				employeeBatchYearReturn = (ArrayList<EmployeeBatchBean>)dao.getEmployeeDetailOfBatchInLimited(loginUserId,yearDate);
				bigGenreList = (ArrayList<BigGenreBean>) dao.getAllBigGenre();

				//チャート描画用情報取得処理
				employeeChartBatchName = (String[])dao.getEmployeeDetailOfBadgeNameForChart(loginUserId);
				batchKindCount = employeeChartBatchName.length;
				employeeChartBatchCount = (int[])dao.getEmployeeDetailOfBadgeCountForChart(loginUserId,"total","total",batchKindCount);
				employeeChartBatchCountMonth =(int[])dao.getEmployeeDetailOfBadgeCountForChart(loginUserId,"month",monthDate,batchKindCount);
				employeeChartBatchCountYear =(int[])dao.getEmployeeDetailOfBadgeCountForChart(loginUserId,"year",yearDate,batchKindCount);
				employeeChartBatchCountTotal =(int[])dao.getEmployeeDetailOfBadgeCountForChart(loginUserId,"total","total",batchKindCount);
				//マイページ追加項目
				employeeCapacityReturn = (ArrayList<EmployeeCapacityBean>)dao.getMyEmployeeDetailOfCapacity(loginUserId);
				employeeCompanyCapacityReturn = (ArrayList<EmployeeCompanyCapacityBean>)dao.getMyEmployeeDetailOfCompanyCapacity(loginUserId);
				employeeTrophyReturn = (ArrayList<EmployeeTrophyBean>)dao.getMyEmployeeDetailOfTrophy(loginUserId);

				//出力ページ用の引数をsetAtribute
				request.setAttribute("employeeDetail", employeePageReturn);
				request.setAttribute("employeeGenreDetail",employeeGenreReturn);
				request.setAttribute("employeeBadgeDetail", employeeBatchReturn);
				request.setAttribute("employeePlanDetail", employeePlanReturn);
				request.setAttribute("employeePlanCommentDetail", employeePlanCommentReturn);
				request.setAttribute("employeeHomeLogDetail",employeeHomeLogReturn);
				request.setAttribute("sessionId", (String)sessionController.getUserId());
				request.setAttribute("employeeBadgeMonth", employeeBatchMonthReturn);
				request.setAttribute("employeeBadgeYear", employeeBatchYearReturn);
				request.setAttribute("bigGenreList", bigGenreList);

				//チャート用の引数をsetAttribute
				request.setAttribute("chartName", employeeChartBatchName);
				request.setAttribute("chartCount", employeeChartBatchCount);
				request.setAttribute("chartCountMonth", employeeChartBatchCountMonth);
				request.setAttribute("chartCountYear", employeeChartBatchCountYear);
				request.setAttribute("chartCountTotal", employeeChartBatchCountTotal);
				//マイページ追加項目
				request.setAttribute("employeeCapacity", employeeCapacityReturn);
				request.setAttribute("employeeCompanyCapacity", employeeCompanyCapacityReturn);
				request.setAttribute("employeeTrophy", employeeTrophyReturn);
				returnURI = "/Employee/EmployeeMyPage.jsp";

			}else{
				returnURI = "/Employee/EmployeePlofileEditPage.jsp";
			}
	    }else{
	    	System.out.println("form item is not seted");
	    	defaultComment = dao.getEmployeeDetailComment(employeeId);
	    	request.setAttribute("comment",defaultComment);
			returnURI = "/Employee/EmployeePlofileEditPage.jsp";
	    }
		dao.close();
		return returnURI;
	}
}