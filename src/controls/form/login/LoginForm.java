/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.form.login;

import dao.UserDao;
import java.util.function.Consumer;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import models.User;

import static java.util.Objects.requireNonNull;

/**
 * A {@link Control} used for authenticating {@link User}s.
 *
 * @author maxbrown
 */
public class LoginForm extends Control {

    private final ResourceBundle messages = ResourceBundle.getBundle("resources/login", Locale.getDefault());

    private final UserDao userDao;

    public LoginForm() {
        this.userDao = new UserDao();

        String stylesheet = getClass().getResource("login-form.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    /**
     * Returns the value of {@link LoginForm#resourceBundle}.
     *
     * @return The resource bundle.
     */
    public ResourceBundle getMessages() {
        return messages;
    }

    private final StringProperty userName = new SimpleStringProperty(this, "userName", "");

    /**
     * Returns the {@link LoginForm#userName} property.
     *
     * @return The userName property.
     */
    public StringProperty userNameProperty() {
        return userName;
    }

    /**
     * Returns the value of {@link LoginForm#userName}.
     *
     * @return The userName.
     */
    public String getUserName() {
        return userNameProperty().get();
    }

    /**
     * Sets the value of {@link LoginForm#userName}.
     *
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        requireNonNull(userName);
        userNameProperty().set(userName);
    }

    private final StringProperty password = new SimpleStringProperty(this, "password", "");

    /**
     * Returns the {@link LoginForm#password} property.
     *
     * @return The password property.
     */
    public StringProperty passwordProperty() {
        return password;
    }

    /**
     * Returns the value of {@link LoginForm#password}.
     *
     * @return The password.
     */
    public String getPassword() {
        return passwordProperty().get();
    }

    private final StringProperty error = new SimpleStringProperty(this, "error", "");

    /**
     * Returns the {@link LoginForm#error} property.
     *
     * @return The error property.
     */
    public StringProperty errorProperty() {
        return error;
    }

    /**
     * Returns the value of {@link LoginForm#error}.
     *
     * @return The error.
     */
    public String geterror() {
        return errorProperty().get();
    }

    /**
     * Sets the value of {@link LoginForm#error}.
     *
     * @param error The error to set.
     */
    public void setError(String error) {
        requireNonNull(error);
        errorProperty().set(error);
    }

    public final ObjectProperty<Consumer<User>> onLogin = new SimpleObjectProperty<>(this, "onLogin");

    /**
     * Returns the {@link LoginForm#onLogin} property.
     *
     * @return The login consumer property.
     */
    public ObjectProperty<Consumer<User>> onLoginProperty() {
        return onLogin;
    }

    /**
     * Returns the value of the {@link LoginForm#onLogin} property.
     *
     * @return The on login consumer.
     */
    public Consumer<User> getOnLogin() {
        return onLoginProperty().get();
    }

    /**
     * Sets the value of the {@link LoginForm#onLogin} property.
     *
     * @param onLogin The on login consumer.
     */
    public void setOnLogin(Consumer<User> onLogin) {
        requireNonNull(onLogin);
        onLoginProperty().set(onLogin);
    }

    /**
     * Attempts to authenticate a {@link User}. Redirects authenticated users to
     * the {@link SchedulingApplication}.
     */
    public void submit() {
        boolean isValid = validateForm();

        if (isValid) {
            Optional<User> maybeUser = userDao.authenticateUser(getUserName(), getPassword());

            if (maybeUser.isPresent()) {
                getOnLogin().accept(maybeUser.get());
            } else {
                // No user returned from the database
                setError(messages.getString("invalid_username_password"));
            }
        }
    }

    private boolean validateForm() {
        if (getUserName() == null || getUserName().isEmpty()) {
            setError(messages.getString("enter_username"));
            return false;
        } else if (getPassword() == null || getPassword().isEmpty()) {
            setError(messages.getString("enter_password"));
            return false;
        }
        return true;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LoginFormSkin(this);
    }

}
