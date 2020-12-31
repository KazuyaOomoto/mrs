package jp.co.mgnc.business.reserve.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.mgnc.business.common.exception.DaoException;
import jp.co.mgnc.business.reserve.dao.ReserveDAO;
import jp.co.mgnc.business.reserve.dto.ReserveDetail;
import jp.co.mgnc.business.reserve.flowbean.ReserveCurrentListFlowBean;


@WebServlet("/admin/reserve-current-list")
public class ReserveCurrentListOpenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public ReserveCurrentListOpenServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
				//セッションを取得する
				HttpSession session = request.getSession(false);
				
				//ログインしている従業員の本日以降の予約リストをDAOから取得する
				ArrayList<ReserveDetail> DetailList = new ReserveDAO().selectReserveDetailByEmpAfterDay((String)session.getAttribute("empNo"), LocalDate.now());
				int size = DetailList.size();
				ReserveCurrentListFlowBean[] flowbeans = new ReserveCurrentListFlowBean[size];
				
				//予約リストが見つかった
				if(!DetailList.isEmpty()) {
					
					//ReserveCurrentListFlowBeanに予約リストの情報を設定
					for(int i=0; i<size; i++) {
						ReserveDetail detail = DetailList.get(i);
						flowbeans[i] = new ReserveCurrentListFlowBean();
						flowbeans[i].setPurpose(detail.getPurpose());
						flowbeans[i].setReserveDate(detail.getReserveDate());
						flowbeans[i].setTel(detail.getTel());
						flowbeans[i].setTimeCd(detail.getTimeCd());
						flowbeans[i].setTimeName(detail.getTimeName());
						flowbeans[i].setRoomCd(detail.getRoomCd());
						flowbeans[i].setRoomName(detail.getRoomName());
					}
					//セッションに登録
					session.setAttribute("ReserveCurrentListFlowBeanList", flowbeans);
					
					//削除確認から戻るボタンを押した場合、予約削除対象が残るため、ここで削除
					if(session.getAttribute("ReserveCurrentListFlowBean") != null) {
						session.removeAttribute("ReserveCurrentListFlowBean");
					}
				}else {
					if(session.getAttribute("ReserveCurrentListFlowBean") != null) {
						session.removeAttribute("ReserveCurrentListFlowBean");
					}
				}
	
			} catch (DaoException e) {
				e.printStackTrace();
				//エラー画面（/error）に遷移する。
				response.sendRedirect(request.getContextPath() +"/error"); 
				return; 
			}

		//WEB-INF/jsp/reserve/search.jspに遷移する。
		request.getRequestDispatcher("/WEB-INF/jsp/reserve/reserve-current-list.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
