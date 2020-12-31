package jp.co.mgnc.business.reserve.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.mgnc.business.reserve.dao.ReserveDAO;
import jp.co.mgnc.business.reserve.dto.ReserveDetail;
import jp.co.mgnc.business.reserve.formbean.ReserveAllListFormBean;
import jp.co.mgnc.business.reserve.util.ReserveAllListUrlQueryFilter;

@WebServlet("/admin/reserve-all-list")
public class ReserveAllListOpenServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReserveAllListOpenServelet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// ReserveAllListGetParameterBeanで、URLのクエリ解析を実施する。
			ReserveAllListUrlQueryFilter bean = new ReserveAllListUrlQueryFilter();
			bean.validate(request);
			// 正常に終了
			// ----リクエストスコープ変数の設定---
			// ReserveAllListGetParameterBeanから年月を取得
			int year = bean.getYear();
			int month = bean.getMonth();
			updateRequestScopeKeys(request, year, month);

			// 「/WEB-INF/jsp/reserve/reserve-all-list.jsp」に遷移する
			request.getRequestDispatcher("/WEB-INF/jsp/reserve/reserve-all-list.jsp").forward(request, response);
			return;
		}
		/*
		 * URLに指定フォーマット"reserve-all-list&date=yyyy/MM"が見つからなかった
		 * ⇒NullPointerExceptionが発生 (例)/admin/reserve-all-list, /admin/reserve-all-list?
		 */
		catch (NullPointerException e1) {
			// 現在の日付をURLに付加してreserve-all-listにリダイレクト遷移
			response.sendRedirect(request.getContextPath() + "/admin/reserve-all-list?date="
					+ String.format("%04d", LocalDate.now().getYear()) + "/"
					+ String.format("%02d", LocalDate.now().getMonthValue()));
			return;
		}
		/*
		 * フォーマットの表記内容不足 ⇒ArrayIndexOutOfBoundsExceptionが発生
		 * (例)/admin/reserve-all-list?date=2019 2-4.フォーマット表記が不正
		 * ⇒IllegalArgumentExceptionが発生 (例)/admin/reserve-all-list?date=2OI9/09
		 */
		catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e1) {
			try {
				// エラーメッセージを作成
				ArrayList<String> errMsg = new ArrayList<>();
				errMsg.add("日付の入力内容に誤りがあります。表記はyyyy/mmです。");

				// 前回情報を設定して全予約一覧表示画面「/WEB-INF/jsp/reserve/reserve-all-list.jsp」に遷移する
				backToOldAllReserveList(request, errMsg);
				request.getRequestDispatcher("/WEB-INF/jsp/reserve/reserve-all-list.jsp").forward(request, response);
				return;
			}
			// 前回情報が設定できなかった
			catch (Exception e2) {
				//異常系エラーの場合は例外内容を出力する
				e2.printStackTrace();

				//エラー画面（/error）に遷移する。
				response.sendRedirect(request.getContextPath() + "/error");
				return;
			}
		}
		// その他の例外が発生した場合
		catch (Exception e1) {
			//異常系エラーの場合は例外内容を出力する
			e1.printStackTrace();

			//エラー画面（/error）に遷移する。
			response.sendRedirect(request.getContextPath() + "/error");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//予約フォームのバリデーションを実施
		ReserveAllListFormBean formbean = new ReserveAllListFormBean();
		ArrayList<String> errMsg = formbean.validate(request);

		//バリデーションエラーメッセージなし
		if (errMsg.isEmpty()) {
			try {
				
				//日付入力フォームからのPOST
				if(formbean.ismonth_input()) {
					//全予約情報リスト表示画面(/admin/reserve-all-list)へ画面へ遷移する
					response.sendRedirect(request.getContextPath() + "/admin/reserve-all-list?date="
							+ formbean.getDate().getYear() + "/" + formbean.getDate().getMonthValue());
					return;
				}
				//セッションから全予約情報リストを取得
				HttpSession session = request.getSession(false);

				//FormBeanから日にち(date)と時間帯コード(time)をフォームから取り出す
				ReserveDetail reserve = new ReserveDetail();
				reserve.setReserveDate(formbean.getDate());
				reserve.setTimeCd(formbean.getTime());
				reserve.setRoomCd(1);
				
				if(session.getAttribute("ReserveAllListFlowBean") != null) {
					session.removeAttribute("ReserveAllListFlowBean");
				}
				//全予約リストから指定した予約情報をセッションに"ReserveAllListFlowBean"として設定
				session.setAttribute("ReserveAllListFlowBean", reserve);
					

				//予約情報入力画面(/admin/reserve-regist-input)へ画面へ遷移する
				response.sendRedirect(request.getContextPath() + "/admin/reserve-regist-input");
				return;
			}
			//データベース処理例外発生
			catch (Exception e) {
				//異常系エラーの場合は例外内容を出力する
				e.printStackTrace();

				//エラー画面（/error）に遷移する。
				response.sendRedirect(request.getContextPath() + "/error");
				return;
			}
		} else {
			try {
				//前回情報を設定して全予約一覧表示画面「/WEB-INF/jsp/reserve/reserve-all-list.jsp」に遷移する
				backToOldAllReserveList(request, errMsg);
				request.getRequestDispatcher("/WEB-INF/jsp/reserve/reserve-all-list.jsp").forward(request, response);
				return;
			}
			//前回情報が作成できなかった
			catch (Exception e) {
				//異常系エラーの場合は例外内容を出力する
				e.printStackTrace();

				//エラー画面（/error）に遷移する。
				response.sendRedirect(request.getContextPath() + "/error");
				return;
			}
		}
	}

	private void updateRequestScopeKeys(HttpServletRequest request, int year, int month) throws Exception {
		/*
		 * リクエストスコープ変数の設定(毎回更新)開始
		 * リクエストスコープに年月日を分解して設定(/WEB-INF/jsp/reserve/reserve-all-list.
		 * jspだけで使用するため一時的とする)
		 */
		request.setAttribute("year", year);
		request.setAttribute("month", month);

		//1か月分の予約リストを取得する。
		Map<Integer, ArrayList<ReserveDetail>> flowbeans = new ReserveDAO().selectReserveDetailCanlender(year, month);
		request.setAttribute("ReserveAllListFlowBeanList", flowbeans);
	}

	private void backToOldAllReserveList(HttpServletRequest request, ArrayList<String> errMsg) throws Exception {

		//エラーがあれば、リクエストスコープ"ReserveAllListErr"にエラーメッセージを設定
		if (errMsg != null) {
			request.setAttribute("ReserveAllListErr", errMsg);
		}
		int year = ReserveAllListUrlQueryFilter.getOldyear();
		int month = ReserveAllListUrlQueryFilter.getOldmonth();
		if(year == 0 && month == 0) {
			year = LocalDate.now().getYear();
			month = LocalDate.now().getMonthValue();
		}
		updateRequestScopeKeys(request, year, month);
	}

}
