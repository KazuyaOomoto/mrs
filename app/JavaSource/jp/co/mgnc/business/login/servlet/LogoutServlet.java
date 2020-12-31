package jp.co.mgnc.business.login.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログアウト処理を行い、ログイン画面に遷移します。
 * @author Oomoto Kazuya
 * @version 1.0
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//セッションがあれば、セッションを削除する。
		HttpSession session = request.getSession(false);
		if(session != null){	
			session.invalidate();
		}
		
		//ログイン画面に遷移する。
		response.sendRedirect(request.getContextPath() + "/login.jsp");		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
