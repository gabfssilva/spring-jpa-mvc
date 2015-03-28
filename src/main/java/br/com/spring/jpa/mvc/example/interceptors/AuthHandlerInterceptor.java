package br.com.spring.jpa.mvc.example.interceptors;

import br.com.spring.jpa.mvc.example.entities.User;
import br.com.spring.jpa.mvc.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //isso é uma autenticação bem besta, ele verifica existe o usuário e senha passados pelo query param, dps vc melhora isso

        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        User user = userRepository.find(username, password);

        if(user != null){
            return true; //se o usuário existir, ele pode acessar essa página
        }

        //senão... unauthorized :(
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().write("UNAUTHORIZED");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
