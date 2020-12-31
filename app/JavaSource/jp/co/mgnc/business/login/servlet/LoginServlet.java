package jp.co.mgnc.business.login.servlet;

import java.io.IOException;
import java.util.List;
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
import jp.co.mgnc.business.login.flowbean.LoginFlowBean;
import jp.co.mgnc.business.login.formbean.LoginFormBean;

/**
 * ログイン処理を管理するサーブレットクラスです。
 * @author Oomoto Kazuya
 * @version 1.0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//入力値を格納し、チェックする。
		LoginFormBean formBean = new LoginFormBean();
		
		//TODO:１：FormBeanのvalidateメソッドにrequestオブジェクトを渡す
		List<String> errMsgList = formBean.validate(request);
		
		//入力不備がある場合
		if(!errMsgList.isEmpty()){
			
			//次画面の情報を格納し、login.jspに遷移する。
			forward(request,response,formBean,errMsgList);
			return;
			
		}
		
		//従業員番号とパスワードでログインする。
		Employee employee = null;
		try {
			
			//TODO:３：EmployeeDAOからselectByIdPassメソッドを呼び出し、formBeanのEmp_noとPasswordを渡す
			//ログイン処理
			employee = new EmployeeDAO().selectByIdPass(formBean.getEmp_no(), formBean.getPassword());
			
			//ログインできない場合
			if(employee == null){
				
				//エラーメッセージを格納する。
				//TODO:７（補足）エラーメッセージはmessage_ja.propertiesでまとめる。
				errMsgList.add(ResourceBundle.getBundle("message").getString("LOGIN_ERR"));
				
				//次画面の情報を格納し、login.jspに遷移する。
				forward(request,response,formBean,errMsgList);
				return;				
				
			}
			
		} catch (DaoException e) {
			
			e.printStackTrace();
			//エラー画面に遷移する
			response.sendRedirect(request.getContextPath() + "/error");
			return;
			
		}
		
		//セッションを取得し、従業員No、従業員氏名を格納する。
		HttpSession session = request.getSession(true);
		session.setAttribute("empNo", employee.getEmpNo());
		session.setAttribute("empName", employee.getEmpName());
		
		//メニュー画面に遷移する（リダイレクト）。
		//TODO:５：メニュー画面はMenuOpenServletで呼び出す。
		response.sendRedirect(request.getContextPath() + "/admin/menu");
		
	}
	
	private void forward(HttpServletRequest request, HttpServletResponse response, LoginFormBean formBean, List<String> errMsgList) throws ServletException, IOException{
		
		//次画面の情報を格納し、login.jspに遷移する。
		LoginFlowBean flowBean = new LoginFlowBean();
		flowBean.setEmpNo(formBean.getEmp_no());
		request.setAttribute("LoginFlowBean", flowBean);
		
		//TODO:６（補足）エラーメッセージは「/WEB-INF/jsp/common/errormsg.jsp」で処理される。キー「errMsgList」値「エラーメッセージリスト」をセットする。
		request.setAttribute("errMsgList", errMsgList);
		request.getRequestDispatcher("login.jsp").forward(request, response);
		
	}
	
}
