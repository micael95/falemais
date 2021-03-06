package br.com.midt.falemais.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CustomerRequest {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String name;
    @NotNull
    @Email
    @Length(min = 10)
    private String email;
    @NotNull
    @NotEmpty
    @Length(min = 6)
    private String password;
    @NotNull
    @NotEmpty
    @Length(min = 6)
    private String passwordConfirmation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
