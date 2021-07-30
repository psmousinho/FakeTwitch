package br.com.inatel.faketwitch.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.repository.ChannelRepository;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private ChannelRepository repository;

	public AuthenticationByTokenFilter(TokenService tokenService, ChannelRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recoverToken(request);
		boolean valid = tokenService.isTokenValid(token);
		if (valid) {
			authenticateUser(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void authenticateUser(String token) {
		Long UserId = tokenService.getUserId(token);
		Channel user = repository.findById(UserId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
