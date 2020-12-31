package jp.co.mgnc.business.employee.servlet;

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
import jp.co.mgnc.business.employee.flowbean.EmployeeUpdateFlowBean;
import jp.co.mgnc.business.employee.formbean.EmployeeUpdateFormBean;

@WebServlet("/admin/employee-update-confirm")
public class EmployeeUpdateConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EmployeeUpdateConfirmServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//employee-update-confirmのフォームのバリデーション
		EmployeeUpdateFormBean formbean = new EmployeeUpdateFormBean();
		ArrayList<String> errMsg = formbean.validate(request);

		//セッションからEmployeeUpdateFlowBeanを取得
		HttpSession session = request.getSession(false);
		EmployeeUpdateFlowBean flowbean = (EmployeeUpdateFlowBean)session.getAttribute("EmployeeUpdateFlowBean");

		//employee-update-confirm.jspに遷移すると仮定して初期化
		String path = "/WEB-INF/jsp/employee/employee-update-confirm.jsp";

		//バリデーションのエラーなし
		// 成立するケース
		// new_password   new_tel
		//	1		〇						×
		//	2		〇						〇
		//	3		×						〇
		if(errMsg.isEmpty()) {

			// EmployeeUpdateFormBeanで現在のパスワード、新しいパスワード、新しいパスワード（確認用）、連絡先を確認する。
			String  password = formbean.getPassword();
			String new_password = formbean.getNew_password();
			String new_tel = formbean.getTel();

			//ケース1,2:新しいパスワードが入力されている場合
			if(!("".equals(new_password))) {
				try {
					//入力した現在のパスワードをEmployeeDAOに渡し、従業員情報を取得する。
					Employee employee = new EmployeeDAO().selectByIdPass(flowbean.getEmpNo(), password);

					// 従業員情報が存在しない場合
					if(employee == null) {
						//エラーメッセージLOGIN_ERRをエラーメッセージリストに追加する。
						errMsg.add(ResourceBundle.getBundle("message").getString("LOGIN_ERR"));

						//エラーメッセージリストをrequestスコープにセットし、前画面（/WEB-INF/jsp/employee/employee-update-input.jsp）に遷移する。
						request.setAttribute("EmployeeUpdateInputErr", errMsg);
						path = "/WEB-INF/jsp/employee/employee-update-input.jsp";
					}
					// 従業員情報が存在する場合
					else {
						//新しいパスワードをEmployeeUpdateFlowBeanの新パスワードに代入する。
						flowbean.setNew_password(new_password);
						request.setAttribute("New_Password", hideNewPassword(new_password));
						//連絡先が入力されている場合、EmployeeUpdateFlowBeanの新連絡先を更新する。
						if(!("".equals(new_tel))) {
							flowbean.setNew_default_tel(new_tel);
						}
					}
				}
				//データベース例外（DaoException）の場合
				catch (DaoException e) {
					e.printStackTrace();
					//エラー画面（/error）に遷移する。
					response.sendRedirect(request.getContextPath() +"/error");
					return;
				}
			}
			// ケース3:新しいパスワードが入力されていない場合
			else {
				//EmployeeUpdateFlowBeanの新連絡先を更新する。
				flowbean.setNew_default_tel(new_tel);
			}
		}
		//エラーあり(入力フォームの不備あり)
		else {
			//エラーメッセージリストをrequestスコープにセットし、前画面（/WEB-INF/jsp/employee/employee-update-input.jsp）に遷移する。
			request.setAttribute("EmployeeUpdateInputErr", errMsg);
			path = "/WEB-INF/jsp/employee/employee-update-input.jsp";
		}

		request.getRequestDispatcher(path).forward(request, response);
	}
	
	private String hideNewPassword(String password) {
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<password.length(); i++) {
			buf.append("*");
		}
		return buf.toString();
	}

}
