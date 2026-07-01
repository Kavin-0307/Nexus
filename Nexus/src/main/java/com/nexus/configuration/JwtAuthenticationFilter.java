package com.nexus.configuration;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.nexus.services.CustomUserDetailsService;
import com.nexus.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private final HandlerExceptionResolver handlerExceptionResolver;
	private final JwtService jwtService;
	private final CustomUserDetailsService customUserDetailsService;
	
	public JwtAuthenticationFilter(JwtService jwtService,HandlerExceptionResolver handlerExceptionResolver,
			CustomUserDetailsService customUserDetailsService) {
		this.jwtService=jwtService;
		this.handlerExceptionResolver=handlerExceptionResolver;
		this.customUserDetailsService=customUserDetailsService;
	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path=request.getServletPath();
		String method=request.getMethod();
		return path.startsWith("/nexus/auth");
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException{
		String authHeader=request.getHeader("Authorization");
		if(authHeader==null||!authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request,response);
			return;
		}
		String token=authHeader.substring(7);
		String username=null;
		try {
			username=jwtService.extractUsername(token);
		}
		catch(Exception e) {
			filterChain.doFilter(request,response);
			return;
		}
		if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=customUserDetailsService.loadUserByUsername(username);
			if(jwtService.validateToken(token,userDetails)) {
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			
		}
		filterChain.doFilter(request,response);
		
	}
}
