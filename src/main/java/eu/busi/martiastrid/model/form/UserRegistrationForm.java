package eu.busi.martiastrid.model.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class UserRegistrationForm implements Serializable {

    @Length(min = 5, message = "{username.tooShort}")
    @Length(max = 50, message = "{username.tooLong}")
    private String username;

    @NotNull(message = "{password.required}")
    @Length(min = 5, message = "{password.tooShort}")
    @Length(max = 50, message = "{password.tooLong}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "{password.tooSimple}")
    private String password;

    private String passwordConfirm;

    @Pattern(regexp = "(^\\d{4}(-)(((0)[0-9])|((1)[0-2]))(-)([0-2][0-9]|(3)[0-1])$)|()",
            message = "{invalidDate}")
    private String birthdate;

    public UserRegistrationForm() {
    }

    public UserRegistrationForm(String username, String password, String passwordConfirm) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}