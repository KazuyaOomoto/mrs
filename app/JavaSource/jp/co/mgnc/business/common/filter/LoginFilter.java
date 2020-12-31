package jp.co.mgnc.business.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログイン処理のフィルタクラスです。URLに/admin/が含まれる場合、ログイン必須とします。
 * @author Oomoto Kazuya
 */
@WebFilter(filterName = "/LoginFilter", urlPatterns = { "/admin/*" })
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		//セッションが存在しない、もしくはセッションキーにempNoが存在しなければエラーとする。
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("empNo") == null) {
			response.sendRedirect(request.getContextPath() + "/error");	
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
