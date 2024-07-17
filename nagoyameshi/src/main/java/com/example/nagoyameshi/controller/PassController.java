package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.EmailForm;
import com.example.nagoyameshi.form.PasswordChangeForm;
import com.example.nagoyameshi.repository.PasswordResetTokenRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.PassService;

@Controller
@RequestMapping("/pass")
public class PassController {
	private final UserRepository userRepository;
	private final PassService passService;
	private final PasswordResetTokenRepository resetTokenRepository;

	public PassController(UserRepository userRepository, PassService passService, PasswordResetTokenRepository resetTokenRepository) {
		this.userRepository = userRepository;
		this.passService = passService;
		this.resetTokenRepository = resetTokenRepository;
	}

	@GetMapping
	public String edit(Model model) {

		model.addAttribute("passwordChangeForm", new PasswordChangeForm());

		return "pass/edit";
	}

	@PostMapping("/update")
	public String change(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@ModelAttribute @Validated PasswordChangeForm passwordChangeForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {

			return "pass/edit";
		}

		User user = userDetailsImpl.getUser();

		if (!passService.isSamePassword(passwordChangeForm.getPassword(),
				passwordChangeForm.getPasswordConfirmation())) {

			bindingResult.rejectValue("passwordConfirmation", "error.passwordConfirmation", "パスワードが一致しません");
			return "pass/edit";
		}

		passService.update(user, passwordChangeForm.getPassword());
		redirectAttributes.addFlashAttribute("successMessage", "パスワードを変更しました。");

		return "redirect:/user";
	}

	@GetMapping("/reset")
	public String reset(Model model) {
		model.addAttribute("emailForm", new EmailForm());
		
		return "pass/reset";
	}

	@PostMapping("/send")
	public String Resetsend(@ModelAttribute EmailForm emailForm, Model model,
			RedirectAttributes redirectAttributes) {
		String email = emailForm.getMail();
		User user = userRepository.findBymail(email);

		if (user == null) {
			model.addAttribute("errorMessage", "入力されたメールアドレスは存在しません。");
			return "pass/reset";
		}

		// パスワード再設定リンクを生成してメール送信
		passService.sendResetLink(email);

		redirectAttributes.addFlashAttribute("successMessage", "パスワード再設定リンクをメールで送信しました。");
		return "redirect:/pass/reset";
	}

}