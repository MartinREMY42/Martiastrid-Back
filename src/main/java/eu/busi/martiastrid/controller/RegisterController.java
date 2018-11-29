package eu.busi.martiastrid.controller;


import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.model.Authority;
import eu.busi.martiastrid.model.User;
import eu.busi.martiastrid.model.form.UserRegistrationForm;
import eu.busi.martiastrid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;

import static eu.busi.martiastrid.constants.Constants.USER_TO_REGISTER;
import static eu.busi.martiastrid.constants.Constantsi18n.ERROR_MESSAGE;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegisterPage(Model model) {
        model.addAttribute(USER_TO_REGISTER, new UserRegistrationForm());
        return "loginTemplate:register";
    }

    @PostMapping
    public String registerNewUser(Model model,
                                  @Valid @ModelAttribute(USER_TO_REGISTER) UserRegistrationForm userRegistrationForm,
                                  BindingResult bindingResult,
                                  Locale locale) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(USER_TO_REGISTER, userRegistrationForm);
            return "loginTemplate:register";
        }
        String birthString = userRegistrationForm.getBirthdate();
        Date birthday = birthString.equals("") ? null : Date.valueOf(birthString);
        User user = new User(
                userRegistrationForm.getUsername(),
                userRegistrationForm.getPassword(),
                true,
                true,
                true,
                true,
                null,
                birthday,
                Date.valueOf(LocalDate.now()),
                null
        );
        user.addAuthority(new Authority("ROLE_USER"));
        try {
            userService.saveNewUser(user);
            return "redirect:/showMyLoginPage";
        } catch (PizzaDatabaseException e) {
            model.addAttribute(ERROR_MESSAGE, messageSource.getMessage(e.getMessage(), null, locale));
            model.addAttribute(USER_TO_REGISTER, userRegistrationForm);
            return "loginTemplate:register";
        }


    }
}
