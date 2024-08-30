package com.example.nagoyameshi.service;

import java.io.Writer;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.opencsv.CSVWriter;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User create(SignupForm signupForm) {
		User user = new User();
		Role role = roleRepository.findByName("ROLE_FREEMEMBER");

		user.setName(signupForm.getName());
		user.setFurigana(signupForm.getFurigana());
		user.setBirthday(signupForm.getBirthday());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		user.setProfession(signupForm.getProfession());
		user.setMail(signupForm.getMail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		user.setRole(role);
		user.setEnabled(false);

		return userRepository.save(user);
	}

	@Transactional
	public void update(UserEditForm userEditForm) {
		User user = userRepository.getReferenceById(userEditForm.getId());

		user.setName(userEditForm.getName());
		user.setFurigana(userEditForm.getFurigana());
		user.setBirthday(userEditForm.getBirthday());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		user.setProfession(userEditForm.getProfession());
		user.setMail(userEditForm.getMail());

		userRepository.save(user);
	}

	//メールアドレスが登録済みかどうかをチェックする
	public boolean isEmailRegistered(String mail) {
		User user = userRepository.findBymail(mail);
		return user != null;
	}

	//パスワードとパスワード（確認用）の入力値が一致するかどうかチェック
	public boolean isSamePassword(String password, String passwordConfirmat) {
		return password.equals(passwordConfirmat);
	}

	//ユーザーを有効にする
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}

	//メールアドレスが変更されたかどうかをチェックする
	public boolean isEmailChanged(UserEditForm userEditForm) {
		User currentUser = userRepository.getReferenceById(userEditForm.getId());
		return !userEditForm.getMail().equals(currentUser.getMail());
	}

	@Transactional
	public void upgradeRole(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		Role role = roleRepository.findByName("ROLE_PAYMEMBER");

		user.setRole(role);
		userRepository.save(user);

		// Spring Security コンテキストに新しいロールを反映
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
				AuthorityUtils.createAuthorityList("ROLE_PAYMEMBER"));
		SecurityContextHolder.getContext().setAuthentication(newAuth);

		// デバッグログ
		System.out
				.println("Authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
		System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		//セッションに変更を反映
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession();
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());

		// セッション情報をデバッグログに出力
		SecurityContext context = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		System.out.println("Session Authenticated: " + context.getAuthentication().isAuthenticated());
		System.out.println("Session Authorities: " + context.getAuthentication().getAuthorities());
	}

	@Transactional
	public void downgradeRole(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		Role role = roleRepository.findByName("ROLE_FREEMEMBER");

		user.setRole(role);
		userRepository.save(user);

		// Spring Security コンテキストに新しいロールを反映
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
				AuthorityUtils.createAuthorityList("ROLE_PAYMEMBER"));
		SecurityContextHolder.getContext().setAuthentication(newAuth);

		// デバッグログ
		System.out
				.println("Authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
		System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		//セッションに変更を反映
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession();
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());

		// セッション情報をデバッグログに出力
		SecurityContext context = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		System.out.println("Session Authenticated: " + context.getAuthentication().isAuthenticated());
		System.out.println("Session Authorities: " + context.getAuthentication().getAuthorities());
	}

	public void writeUsersToCsv(Writer writer) {
		List<User> users = userRepository.findByRole_IdNot(1);
		try (CSVWriter csvWriter = new CSVWriter(writer)) {
			//ファイルに書き込み
			csvWriter.writeNext(new String[] { "ID", "名前", "フリガナ", "生年月日", "電話番号", "職業", "メール", "登録日", "更新日" });
			for (User user : users) {
				csvWriter.writeNext(new String[] {
						String.valueOf(user.getId()),
						user.getName(),
						user.getFurigana(),
						user.getBirthday(),
						user.getPhoneNumber(),
						user.getProfession(),
						user.getMail(),
						String.valueOf(user.getCreatedAt()),
						String.valueOf(user.getUpdatedAt())
				});
			}
		} catch (Exception e) {
			throw new RuntimeException("CSV書き込み中にエラーが発生しました。", e);
		}
	}

	//年齢を計算するメソッド
	public int calculateAge(String birthday) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate birthDate = LocalDate.parse(birthday, formatter); //formatter形式に変換
		//｢Period.between｣で｢.getYears｣で年差分計算
		return Period.between(birthDate, LocalDate.now()).getYears();
	}

	//年代を求めるメソッド
	public String calculateAgeGroup(int age) {
		if (age < 10) {
			return "0-9歳";
		} else if (age < 20) {
			return "10代";
		} else if (age < 30) {
			return "20代";
		} else if (age < 40) {
			return "30代";
		} else if (age < 50) {
			return "40代";
		} else if (age < 60) {
			return "50代";
		} else if (age < 70) {
			return "60代";
		} else if (age < 80) {
			return "70代";
		} else if (age < 90) {
			return "80代";
		} else if (age < 100) {
			return "90代";
		} else {
			return "100歳以上";
		}
	}

	//年代毎の無料会員・有料会員の集計
	public Map<String, Map<String, Integer>> getMemberCountByAgeGroup(List<User> users) {
		Map<String, Map<String, Integer>> memberCountByAgeGroup = new HashMap<>();

		for (User user : users) {
			int age = calculateAge(user.getBirthday());
			String ageGroup = calculateAgeGroup(age);
			String userRole = user.getRole().getName(); //Roleを区別する

			//年代ごとの集計用マップを初期化
			memberCountByAgeGroup.putIfAbsent(ageGroup, new HashMap<>());
			Map<String, Integer> memberCount = memberCountByAgeGroup.get(ageGroup);
			//無料会員と有料会員のカウントを更新(.getOrDefaultは、データ取得できない時に初期値を設定。)
			memberCount.put(userRole, memberCount.getOrDefault(userRole, 0) + 1);
		}
		return memberCountByAgeGroup;
	}

	//職業ごとの無料会員・有料会員の集計
	public Map<String, Map<String, Integer>> getMemberCountByprofession(List<User> users) {
		Map<String, Map<String, Integer>> memberCountByProfession = new HashMap<>();

		for (User user : users) {
			String profession = user.getProfession();
			String userRole = user.getRole().getName(); //Roleを区別する

			//職業ごとの集計用マップを初期化
			memberCountByProfession.putIfAbsent(profession, new HashMap<>());
			Map<String, Integer> memberCount = memberCountByProfession.get(profession);
			//無料会員と有料会員のカウントを更新
			memberCount.put(userRole, memberCount.getOrDefault(userRole, 0) + 1);
		}
		return memberCountByProfession;
	}
}
