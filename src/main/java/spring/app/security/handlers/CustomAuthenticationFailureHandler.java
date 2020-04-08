package spring.app.security.handlers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    int loginAttempt = 0;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginCount") == null) {
            session.setAttribute("loginCount", 1);
            loginAttempt = 1;
        } else {
            loginAttempt = (Integer) session.getAttribute("loginCount");
            loginAttempt++;
            session.setAttribute("loginCount", loginAttempt);
        }
        switch (exception.getMessage()) {
            case "User is disabled":
                session.setAttribute("error", "Ваш аккаунт забанен.");
                break;
            case "Bad credentials":
                session.setAttribute("error", "Неверная пара логин/пароль.");
                break;
        }
        String targetUrl = determineTargetUrl();
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl() {
        if (loginAttempt > 3) {
            return "/login-captcha";
        } else {
            return "/login";
        }
    }
}
