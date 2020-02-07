package spring.app.security.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import spring.app.model.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
										HttpServletResponse httpServletResponse,
										Authentication authentication) throws IOException {
		System.out.println("success");
		HttpSession session = httpServletRequest.getSession();

		session.setAttribute("loginCount", 1);
		int loginAttempt = (Integer) session.getAttribute("loginCount");
		int i = loginAttempt;
		handle(httpServletRequest, httpServletResponse, authentication);
		clearAuthenticationAttributes(httpServletRequest);
	}

	protected void handle(HttpServletRequest request,
						  HttpServletResponse response, Authentication authentication) throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private String determineTargetUrl(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		if (authorities.contains(new Role("ADMIN"))){
			return "/admin/users";
		} else if (authorities.contains(new Role("USER"))) {
			return "user/statistics";
			//если пользователь недорегился перенаправляем на 2шаг регистрации
		} else if (authorities.contains(new Role("PREUSER"))) {
			return "registration/preuser";
		} else {
			return "/public/error";
		}
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		//удаление атрибутов-исключений из сессии
	}
}
