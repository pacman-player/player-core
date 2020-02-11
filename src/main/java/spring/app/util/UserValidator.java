package spring.app.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.app.model.User;
import spring.app.service.abstraction.UserService;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        //если логин или пароль не совпадает
        if (userService.getUserByLogin(user.getLogin()) == null || !userService.getUserByLogin(user.getLogin()).getPassword().equals(user.getPassword())) {
            errors.rejectValue("username", "Invalid.userForm.loginAndPassword");
        }

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

    }
}
