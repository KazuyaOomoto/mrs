package jp.co.mgnc.business.reserve.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.mgnc.business.reserve.flowbean.ReserveCurrentListFlowBean;
import jp.co.mgnc.business.reserve.formbean.ReserveDeleteInputFormBean;


@WebServlet("/admin/reserve-delete-confirm")
public class ReserveDeleteConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReserveDeleteConfirmServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			//フォームのバリデーション
			ReserveDeleteInputFormBean formbean = new ReserveDeleteInputFormBean();
			formbean.validate(request);
			
			HttpSession session = request.getSession(false);
			ReserveCurrentListFlowBean[] flowbeans = (ReserveCurrentListFlowBean[])(session.getAttribute("ReserveCurrentListFlowBeanList"));
			int size = flowbeans.length;
			int index = formbean.getIndex();
			
			//該当従業員の予約リスト以外のインデックスが指定された
			if(index < 0 || index >= size) {
				//エラー画面に遷移する。
			 	response.sendRedirect(request.getContextPath() + "/error");
				return; 
			}
			//指定した予約をセッションに設定し、一覧は削除する
			session.setAttribute("ReserveCurrentListFlowBean", flowbeans[formbean.getIndex()]);
			session.removeAttribute("ReserveCurrentListFlowBeanList");
			
		} catch (Exception e) {
			e.printStackTrace();
			//エラー画面に遷移する。
		 	response.sendRedirect(request.getContextPath() + "/error");
			return; 
		}

		// 次画面「/WEB-INF/jsp/reserve/reserve-delete-confirm.jsp」に遷移する
		request.getRequestDispatcher("/WEB-INF/jsp/reserve/reserve-delete-confirm.jsp").forward(request, response);

	}

}
