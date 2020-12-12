package com.clemhlrdt.recipeapp.security.filters;

import com.clemhlrdt.recipeapp.security.CustomUserDetailsService;
import com.clemhlrdt.recipeapp.security.JwtUtil;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

		String email = null;
		String jwt = null;

		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
			jwt = authorizationHeader.substring(7);
			try {
				email = jwtUtil.extractEmail(jwt);
			} catch (SignatureException e){
				logger.error("WRONG CREDENTIALS");
			}
		}

		if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
			UserPrincipal userDetails = (UserPrincipal) customUserDetailsService.loadUserByUsername(email);

			if(jwtUtil.validateToken(jwt, userDetails)){

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}
}
