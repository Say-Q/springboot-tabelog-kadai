package com.example.nagoyameshi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/shops",
								"/shops/{id}", "/company/**", "/pass/**", "stripe/webhook").permitAll() //すべてのユーザーにアクセスを許可するＵＲＬ
						.requestMatchers("/admin/**").hasRole("ADMIN") //管理者にのみアクセスを許可するURL
						.requestMatchers("/user/", "stripe/webhook").hasRole("PAYMEMBER") //有料会員のみアクセスを許可するURL
						.requestMatchers("/user/", "stripe/webhook").hasRole("FREEMEMBER") //無料会員のみアクセスを許可するURL
						.anyRequest().authenticated() //上記以外のＵＲＬはログインが必要（会員または管理者のどちらでもＯＫ）
				)
				.formLogin((form) -> form
						.loginPage("/login") //ログインページのＵＲＬ
						.loginProcessingUrl("/login") //ログインフォームの送信先ＵＲＬ
						//				.defaultSuccessUrl("/?loggedIn") //ログイン成功時のリダイレクト先ＵＲＬ
						.successHandler(customAuthenticationSuccessHandler) //カスタムの成功ハンドラを指定
						.failureUrl("/login?error") //ログイン失敗時のリダイレクト先ＵＲＬ
						.permitAll())
				.logout((logout) -> logout
						.logoutSuccessUrl("/?loggedOut") //ログアウト時のリダイレクト先ＵＲＬ
						.permitAll())
				.headers(headers -> headers
						.frameOptions(frameOptions -> frameOptions.sameOrigin()) // 同一オリジンでのiframeを許可する
				)
				.csrf(csrf -> csrf.ignoringRequestMatchers("/stripe/webhook"));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
