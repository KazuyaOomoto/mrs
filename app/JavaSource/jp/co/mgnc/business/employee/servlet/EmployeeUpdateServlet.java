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


@WebServlet("/admin/employee-update")
public class EmployeeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public EmployeeUpdateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//セッションからEmployeeUpdateFlowBeanを取得
		HttpSession session = request.getSession(false);
		EmployeeUpdateFlowBean flowbean = (EmployeeUpdateFlowBean)session.getAttribute("EmployeeUpdateFlowBean");
		
		//従業員情報オブジェクトを組み立てる。
		Employee employee = new Employee();
		employee.setEmpNo(flowbean.getEmpNo());
		employee.setEmpName(flowbean.getEmpName());
		employee.setDepartment(flowbean.getDepartment());
		
		//EmployeeUpdateFlowBeanに新パスワードが存在する場合
		String new_password = flowbean.getNew_password();
		if(new_password != null) {
			//EmployeeUpdateFlowBeanの新パスワードを従業員情報オブジェクトのパスワードフィールドに代入する。
			employee.setPassword(new_password);
		}
		
		//EmployeeUpdateFlowBeanに新連絡先が存在する場合
		String new_tel = flowbean.getNew_default_tel();
		if(new_tel != null) {
			//EmployeeUpdateFlowBeanの新連絡先を従業員情報オブジェクトの既定連絡先フィールドに代入する。
			employee.setDefaultTel(new_tel);
		}else {
			//EmployeeUpdateFlowBeanの既定連絡先を従業員情報オブジェクトの既定連絡先フィールドに代入する。
			employee.setDefaultTel(flowbean.getDefaultTel());
		}
		
		try {
			//Employeeオブジェクトのパスワード有無を調べて、実行するSQLを分岐する。
			//新パスワードが設定されているか否かで
			//・パスワード及び連絡先の更新の実行
			//・連絡先のみの更新の実行
			EmployeeDAO dao = new EmployeeDAO();
			if(employee.getPassword() != null) {
				dao.update(employee);
			}else {
				dao.updateTelById(employee);
			}
		
			//セッションからキーEmployeeUpdateFlowBeanを削除する。
			session.removeAttribute("EmployeeUpdateFlowBean");
		} 
		//データベース例外発生
		catch(DaoException e) {
			e.printStackTrace();
			//エラー画面（/error）に遷移する。
			response.sendRedirect(request.getContextPath() +"/error"); 
			return; 
		}
		
		//次画面（/WEB-INF/jsp/employee/employee-update-complete.jsp）に遷移する。
		request.getRequestDispatcher("/WEB-INF/jsp/employee/employee-update-complete.jsp").forward(request, response);
	}

}
