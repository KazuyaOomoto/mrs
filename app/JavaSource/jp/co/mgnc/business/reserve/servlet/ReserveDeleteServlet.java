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
import jp.co.mgnc.business.employee.dao.EmployeeDAO;
import jp.co.mgnc.business.employee.dto.Employee;
import jp.co.mgnc.business.reserve.dao.ReserveDAO;
import jp.co.mgnc.business.reserve.dto.Reserve;
import jp.co.mgnc.business.reserve.flowbean.ReserveCurrentListFlowBean;
import jp.co.mgnc.business.reserve.formbean.ReserveDeleteLoginCheckFormBean;


@WebServlet("/admin/reserve-delete")
public class ReserveDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReserveDeleteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//フォームのバリデーション
		ReserveDeleteLoginCheckFormBean formbean = new ReserveDeleteLoginCheckFormBean();
		ArrayList<String> errMsg = formbean.validate(request);

		//セッションを取得
		HttpSession session = request.getSession(false);

		String path = "/WEB-INF/jsp/reserve/reserve-delete-complete.jsp";
		
		//バリデーションのエラーなし
		if(errMsg.isEmpty()) {
			ReserveCurrentListFlowBean flowbean = (ReserveCurrentListFlowBean)session.getAttribute("ReserveCurrentListFlowBean");
			//入力した現在のパスワードをEmployeeDAOに渡し、従業員情報を取得する。
			try {
				Employee employee = new EmployeeDAO().selectByIdPass((String)session.getAttribute("empNo"), formbean.getPassword());
				
				// 従業員情報が存在しない場合
				if(employee == null) {
					//エラーメッセージLOGIN_ERRをエラーメッセージリストに追加する。
					errMsg.add(ResourceBundle.getBundle("message").getString("LOGIN_ERR"));
					request.setAttribute("DeleteReserveErr", errMsg);
					path = "/WEB-INF/jsp/reserve/reserve-delete-confirm.jsp";
				} else {
					//予約があるかどうか調査する
					Reserve reserve = new ReserveDAO().selectReserveByKey(
							flowbean.getReserveDate(),
							flowbean.getTimeCd(),
							flowbean.getRoomCd());
					
					//予約がある
					if(reserve != null) {
						//DAOから削除を実行し、セッションからReserveCurrentListFlowBeanキーを削除する
						new ReserveDAO().deleteReserveByKey(
									flowbean.getReserveDate(),
									flowbean.getTimeCd(),
									flowbean.getRoomCd()
								);
						session.removeAttribute("ReserveCurrentListFlowBean");
					}
					//予約がない
					else {
						//「指定した予約は見つかりませんでした」をエラーメッセージにセットし、reserve-delete-confirmに遷移
						ArrayList<String> deleteErr = new ArrayList<>();
						deleteErr.add("指定した予約は見つかりませんでした");
						request.setAttribute("DeleteReserveErr", deleteErr);
						path = "/WEB-INF/jsp/reserve/reserve-delete-confirm.jsp"; 
					}
				}
			} catch (DaoException e) {
				e.printStackTrace();
				//エラー画面（/error）に遷移する。
				response.sendRedirect(request.getContextPath() +"/error"); 
				return; 
			}
		}
		//エラーあり(入力フォームの不備あり)
		else {
			request.setAttribute("DeleteReserveErr", errMsg);
			path = "/WEB-INF/jsp/reserve/reserve-delete-confirm.jsp";
		}

		// 次画面「/WEB-INF/jsp/reserve/reserve-delete-confirm.jsp」に遷移する
		request.getRequestDispatcher(path).forward(request, response);

	}

}
