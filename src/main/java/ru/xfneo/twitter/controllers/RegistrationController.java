package ru.xfneo.twitter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.xfneo.twitter.domain.User;
import ru.xfneo.twitter.domain.dto.CaptchaResponseDto;
import ru.xfneo.twitter.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "/registration";
    }

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String password2,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
        Model model
    ) {
        String url = String.format(RECAPTCHA_URL, recaptchaSecret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, null, CaptchaResponseDto.class);
        if (!response.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isPassword2Empty = StringUtils.isEmpty(password2);

        if (isPassword2Empty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }
        if (user.getPassword() != null && !user.getPassword().equals(password2)) {
            model.addAttribute("passwordError", "Passwords are different!");
        }
        if (isPassword2Empty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errorsMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User already exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }
}
