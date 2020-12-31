package jp.co.mgnc.business.reserve.servlet;

import java.io.IOException;
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
import jp.co.mgnc.business.reserve.flowbean.ReserveInputFlowBean;

@WebServlet("/admin/reserve-regist-complete")
public class ReserveRegistCompleteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public ReserveRegistCompleteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//セッションからReserveInputFlowBeanを取得
		HttpSession session = request.getSession(false);
		ReserveInputFlowBean flowbean = (ReserveInputFlowBean)session.getAttribute("ReserveInputFlowBean");
		
		// ReserveDTOにReserveInputFlowBeanのメンバ、従業員番号の値を設定
		Reserve reserve = new Reserve();
		reserve.setReserveDate(flowbean.getLdate());
		reserve.setEmpNo((String)session.getAttribute("empNo"));
		reserve.setTimeCd(flowbean.getTimeCd());
		reserve.setRoomCd(flowbean.getRoomCd());
		reserve.setTel(flowbean.getTel());
		reserve.setPurpose(flowbean.getPurpose());
		
		//遷移先をreserve-regist-complete.jspに設定
		String path = "/WEB-INF/jsp/reserve/reserve-regist-complete.jsp";
		try {
			//予約があるかどうか調査する
			Reserve ret = new ReserveDAO().selectReserveByKey(
												reserve.getReserveDate(), 
												reserve.getTimeCd(), 
												reserve.getRoomCd());
			
			 //既に予約があった
			 if(ret != null) {
				 	ArrayList<String> errMsg = new ArrayList<>();
				 	errMsg.add(ResourceBundle.getBundle("message").getString("SEARCH_ERR_EXIST_RESERVE"));
					request.setAttribute("ReserveRegistInputErr", errMsg);
					path = "/WEB-INF/jsp/reserve/reserve-regist-confirm.jsp";
			 }else {
				//会議室予約情報に新規登録
				 new ReserveDAO().insert(reserve);
				 //セッションからReserveInputFlowBeanを削除
				 session.removeAttribute("ReserveInputFlowBean");
				 if(session.getAttribute("ReserveAllListFlowBean") != null)
					 session.removeAttribute("ReserveAllListFlowBean");
			 }
		} catch(DaoException e) {
		 	response.sendRedirect(request.getContextPath() + "/error");
			return; 
		}
		request.getRequestDispatcher(path).forward(request, response);
	}

}
