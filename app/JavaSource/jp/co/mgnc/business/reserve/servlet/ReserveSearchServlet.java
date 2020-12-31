package jp.co.mgnc.business.reserve.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.mgnc.business.common.exception.DaoException;
import jp.co.mgnc.business.reserve.dao.ReserveDAO;
import jp.co.mgnc.business.reserve.dto.Reserve;
import jp.co.mgnc.business.reserve.flowbean.ReserveSearchFlowBean;
import jp.co.mgnc.business.reserve.formbean.ReserveSerachFormBean;
import jp.co.mgnc.business.time.dao.TimeDAO;


@WebServlet("/admin/search")
public class ReserveSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public ReserveSearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*　reserve-regist-input, reserve-regist-completeから戻るボタンを押した際、セッションに設定されているFlowbeanを一旦削除	*/
		HttpSession session = request.getSession(false);
		if(session.getAttribute("ReserveSearchFlowBean") != null) {
			session.removeAttribute("ReserveSearchFlowBean");
		}
		if(session.getAttribute("ReserveInputFlowBean") != null ) {
			session.removeAttribute("ReserveInputFlowBean");
		}
		//WEB-INF/jsp/reserve/search.jspに遷移する。
		request.getRequestDispatcher("/WEB-INF/jsp/reserve/search.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ReserveSerachFormBean bean = new ReserveSerachFormBean();
		ArrayList<String> ReserveSearchErrMsg = bean.validate(request);
		
		HttpSession session = request.getSession(false);
		//エラーメッセージリストが空の場合
		if(ReserveSearchErrMsg.isEmpty()) {
			try {
				LocalDate date = bean.getDate();
				int timeCd = bean.getTimeCd();
				int roomCd = bean.getRoomCd();
				
				//バリデーションから更に厳密な時間に変換
				LocalTime timeLimit = new TimeDAO(). selectByTimeCd(timeCd).getTimeLimit();
				LocalDateTime reserve_datetime = LocalDateTime.of(date, timeLimit);
				boolean isBefore = reserve_datetime.isBefore(LocalDateTime.now());
				Reserve reserve = new ReserveDAO().selectReserveByKey(date, timeCd, roomCd);
				//予約情報が存在しない場合
				if(reserve == (Reserve)null && !isBefore) {
					//requestスコープにReserveSearchFlowBeanを格納する。
					ReserveSearchFlowBean flowbean = new ReserveSearchFlowBean(date, timeCd, roomCd);
					session.setAttribute("ReserveSearchFlowBean", flowbean);
				} else {
					ReserveSearchErrMsg.add(ResourceBundle.getBundle("message").getString("SEARCH_ERR_EXIST_RESERVE"));
					//エラーメッセージSEARCH_ERR_EXIST_RESERVEをエラーメッセージリストに追加し、requestスコープにセットする。
					request.setAttribute("ReserveSearchErrMsg", ReserveSearchErrMsg);
				}
			} catch (DaoException e) { //データベース例外（DaoException）の場合
				//エラー画面（/error）に遷移する。
				response.sendRedirect(request.getContextPath() + "/error");
				return;
			}
		} else {
			//エラーメッセージリストをrequestスコープにセットする。
			request.setAttribute("ReserveSearchErrMsg", ReserveSearchErrMsg);
		}
		
		//WEB-INF/jsp/reserve/search.jspに遷移する。
		request.getRequestDispatcher("/WEB-INF/jsp/reserve/search.jsp").forward(request, response);
	}

}
