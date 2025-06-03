package com.dealuni.demo.filter;

import com.dealuni.demo.services.CustomUserDetailsService;
import com.dealuni.demo.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthentificationFilter.class);

    public JwtAuthentificationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = null;

        //incercam sa obtinem jwt din Authorization Header
        String headerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer")) {
            jwt = headerToken.substring(7);
        }

        //daca jwt nu e in header incercam sa-l obtinem din cookie
        if (jwt == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        //daca avem un jwt si user-ul nu este autentificat
        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                //exatragem username din token

                String username = jwtUtil.extractUsername(jwt);

                //obtinem detalii despre user din db
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //validam token si cream o autentificare valida daca token-ul este valid
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    //adaugam request details pentru extra securitate
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //setam autentificarea inapoi in contextul security
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (JwtException e) {
                logger.error("JWT validation failed", e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        //continuam la urmatorul lant de filtre
        filterChain.doFilter(request, response);
    }
}
