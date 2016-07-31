package com.daniilyurov.training.project.web.model.dao.api.entity;

import com.daniilyurov.training.project.web.i18n.FirstLastNameLocalizable;
import com.daniilyurov.training.project.web.i18n.Value;

import java.util.Locale;
import java.util.Optional;

public class User extends Entity implements FirstLastNameLocalizable {

    private String login;
    private String password;
    private String email;
    private String role;
    private Locale locale;
    private String latinFirstName;
    private String latinLastName;
    private String cyrillicFirstName;
    private String cyrillicLastName;
    private double averageSchoolResult;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String getLatinFirstName() {
        return latinFirstName;
    }

    public void setLatinFirstName(String latinFirstName) {
        this.latinFirstName = latinFirstName;
    }

    @Override
    public String getLatinLastName() {
        return latinLastName;
    }

    public void setLatinLastName(String latinLastName) {
        this.latinLastName = latinLastName;
    }

    @Override
    public String getCyrillicFirstName() {
        return cyrillicFirstName;
    }

    public void setCyrillicFirstName(String cyrillicFirstName) {
        this.cyrillicFirstName = cyrillicFirstName;
    }

    @Override
    public String getCyrillicLastName() {
        return cyrillicLastName;
    }

    public void setCyrillicLastName(String cyrillicLastName) {
        this.cyrillicLastName = cyrillicLastName;
    }

    public double getAverageSchoolResult() {
        return averageSchoolResult;
    }

    public void setAverageSchoolResult(double averageSchoolResult) {
        this.averageSchoolResult = averageSchoolResult;
    }

    @Override
    public int hashCode() {
        return Optional.ofNullable(id).orElse(0L).intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return this.id.equals(other.id);
    }
}
