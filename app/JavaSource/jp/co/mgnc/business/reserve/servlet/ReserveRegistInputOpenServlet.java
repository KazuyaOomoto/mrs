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
import jp.co.mgnc.business.reserve.dto.ReserveDetail;
import jp.co.mgnc.business.reserve.flowbean.ReserveInputFlowBean;
import jp.co.mgnc.business.reserve.flowbean.ReserveSearchFlowBean;
import jp.co.mgnc.business.time.dao.TimeDAO;
import jp.co.mgnc.business.time.dto.Time;

@WebServlet("/admin/reserve-regist-input")
public class ReserveRegistInputOpenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReserveRegistInputOpenServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session =request.getSession(false);
		
		//全予約リスト画面から遷移してきた場合
		ReserveDetail InputBean = (ReserveDetail)session.getAttribute("ReserveAllListFlowBean");
		if(InputBean != null) {
			
			Time time;	//時間帯クラスオブジェクトの参照
			
			try {
				//時間帯コードの取得
				time = new TimeDAO().selectByTimeCd(InputBean.getTimeCd());
			} catch (DaoException e) {
				e.printStackTrace();
				//エラー画面（/error）に遷移する。
				response.sendRedirect(request.getContextPath() +"/error"); 
				return; 
			}

			ReserveInputFlowBean flowbean = new ReserveInputFlowBean(
								InputBean.getReserveDate(),
								InputBean.getTimeCd(),
								InputBean.getRoomCd(),
						 		time.getTimeName()
			 			);
			
			if(session.getAttribute("ReserveInputFlowBean") != null) {
				session.removeAttribute("ReserveInputFlowBean");
			}
			session.setAttribute("ReserveInputFlowBean", flowbean);
		}else {
			ArrayList<String> errMsg = new ArrayList<>();
			errMsg.add(ResourceBundle.getBundle("message").getString("SEARCH_ERR_EXIST_RESERVE"));
			request.setAttribute("ReserveRegistInputErr", errMsg);
		}
		request.getRequestDispatcher("/WEB-INF/jsp/reserve/reserve-regist-input.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションからReserveSearchFlowBeanを取得
		HttpSession session = request.getSession(false);
		ReserveSearchFlowBean searchbean = (ReserveSearchFlowBean)session.getAttribute("ReserveSearchFlowBean");

		try {
			// reserve-register-input画面仕様より、表示するのは時間帯名の為、TimeDAOから取得する
			Time time = new TimeDAO().selectByTimeCd(searchbean.getTimeCd());
			ReserveInputFlowBean flowbean = new ReserveInputFlowBean(
								searchbean.getLdate(),
								searchbean.getTimeCd(),
								searchbean.getRoomCd(),
						 		time.getTimeName()
			 			);
			session.removeAttribute("ReserveSearchFlowBean"); 
			session.setAttribute("ReserveInputFlowBean", flowbean); 
		} catch(DaoException e) {
			//エラー画面（/error）に遷移する。
			response.sendRedirect(request.getContextPath() +"/error"); 
			return; 
		}
		request.getRequestDispatcher("/WEB-INF/jsp/reserve/reserve-regist-input.jsp").forward(request, response);
	}

}
