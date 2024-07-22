package com.example.nagoyameshi.security;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionManagement {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SessionManagement.class);

	public static void updateSessionRole(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
System.out.println(session);
		if (session != null) {
			SecurityContext securityContext = (SecurityContext) session
					.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

			if (securityContext != null) {
				Authentication auth = securityContext.getAuthentication();
				Authentication newAuth = new UsernamePasswordAuthenticationToken(
						auth.getPrincipal(), auth.getCredentials(),
						AuthorityUtils.createAuthorityList("ROLE_PAYMEMBER"));
				securityContext.setAuthentication(newAuth);
				session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

				logger.info("User role updated to ROLE_PAYMEMBER");
			}
		}
	}
}
