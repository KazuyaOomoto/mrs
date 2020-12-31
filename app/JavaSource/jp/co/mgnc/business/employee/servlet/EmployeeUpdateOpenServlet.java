package jp.co.mgnc.business.employee.servlet;

import java.io.IOException;
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


@WebServlet("/admin/employee-update-open")
public class EmployeeUpdateOpenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeUpdateOpenServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//セッションを取得する
		HttpSession session = request.getSession(false);
		try {
			
			//利用者情報確定画面の戻るボタンを押した際、EmployeeUpdateFlowBeanが残るため、一旦削除
			if(session.getAttribute("EmployeeUpdateFlowBean") != null) {
				session.removeAttribute("EmployeeUpdateFlowBean");
			}
			
			//EmployeeDAOに従業員番号を渡し、従業員情報を取得する。
			Employee employee = new EmployeeDAO().selectById((String)session.getAttribute("empNo"));
			
			//取得した従業員情報を参照し、EmployeeUpdateFlowBeanを組み立てる。
			EmployeeUpdateFlowBean flowbean = new EmployeeUpdateFlowBean();
			
			//従業員番号					セッションから取得
			flowbean.setEmpNo((String)session.getAttribute("empNo"));
			
			//パスワード						Null(ここでは設定しない)
			
			//所属部署					DBから取得
			flowbean.setDepartment(employee.getDepartment());
			
			//従業員名					セッションから取得
			flowbean.setEmpName((String)session.getAttribute("empName"));
			
			//既定連絡先					DBから取得
			flowbean.setDefaultTel(employee.getDefaultTel());
			
			//新パスワード					Null(ここでは設定しない)
			
			//新連絡先					Null(ここでは設定しない)
			
			//セッションにEmployeeUpdateFlowBeanをセットする。
			session.setAttribute("EmployeeUpdateFlowBean", flowbean);
		}
		//データベース例外（DaoException）の場合
		catch (DaoException e) {
			//エラー画面（/error）に遷移する。
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/error");
			return;
		}
		//次画面（/WEB-INF/jsp/employee/employee-update-input.jsp）に遷移する。
		request.getRequestDispatcher("/WEB-INF/jsp/employee/employee-update-input.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
