package com.example.nagoyameshi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionManagement {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SessionManagement.class);

	public static void updateSessionRole(HttpServletRequest request) {
		//現在の認証情報を取得
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {

			Object principal = authentication.getPrincipal();

			if (principal instanceof UserDetails) {

				UserDetails userDetails = (UserDetails) principal;
				//現在のRole情報を取得
				Collection<? extends GrantedAuthority> curreAuthorities = userDetails.getAuthorities();
				//Roleを更新
				List<GrantedAuthority> updatedAuthorities = new ArrayList<>(curreAuthorities);
				updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_PAYMEMBER"));
				//新しい認証情報を作成
				Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails,
						authentication.getCredentials(), updatedAuthorities);
				//SecurityContextを更新
				SecurityContextHolder.getContext().setAuthentication(newAuth);
				//セッションにSecurityContextを設定
				HttpSession session = request.getSession(false);

				if (Objects.nonNull(session)) {

					SecurityContext securityContext = SecurityContextHolder.getContext();
					session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
							securityContext);
				}

				logger.info("User role updated to ROLE_NEW_ROLE");

			} else {

				logger.warn("Principal is not an instance of UserDetails");
			}

		} else {

			logger.warn("Authentication is null or not authenticated");
		}
	}
}