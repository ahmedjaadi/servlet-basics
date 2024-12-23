package servlet_basics;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = { "/http-balance" , "/balance" } )

public class AuthenticationFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String authenticationHeader = req.getHeader("Authorization");
        if (authenticationHeader == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization Header must be supplied");
            return;
        }
        if (!authenticationHeader.startsWith("Basic ")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only Basic Authorization is supported");
            return;
        }
        String[] credentials = authenticationHeader.substring(6).split(":");
        if (credentials[0].equalsIgnoreCase("user") && credentials[1].equals("password")) {
            chain.doFilter(req, res);
        } else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Wrong username/password combination");
        }
    }
}
