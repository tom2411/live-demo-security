package fr.univ.orleans.webservices.livedemosecurity.config;

import fr.univ.orleans.webservices.livedemosecurity.exception.MauvaisTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtTokens jwtTokens;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokens jwtTokens) {
        super(authenticationManager);
        this.jwtTokens = jwtTokens;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // a chaque requete
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = jwtTokens.decodeToken(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (MauvaisTokenException e) {
            SecurityContextHolder.clearContext();
        }


        chain.doFilter(request,response);
    }
}
