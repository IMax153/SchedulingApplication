package application;

import controls.calendar.Calendar;
import controls.form.login.LoginForm;
import events.NavigationEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;
import utilities.Database;
import utilities.UserLogger;

/**
 * Class which handles initialization and closure of the main scheduling
 * application.
 *
 * @author mab90
 */
public class SchedulingApplication extends Application {

    /**
     * The authenticated user of the application
     */
    public static User user;

    private Stage primaryStage;

    private Scene scene;

    /**
     * The main entry point for the application.
     *
     * @param args - The command line arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Database.createConnection();
        launch(args);
        Database.closeConnection();
    }

    /**
     * The main entry point for the GUI of the application.
     *
     * @param primaryStage - The primary stage of the application.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("C195 - Scheduling Application");

        // Initially display the login form to authenticate users
        LoginForm loginForm = new LoginForm();

        // Set the user on login
        loginForm.setOnLogin(authenticatedUser -> {
            loginForm.fireEvent(new NavigationEvent(loginForm, loginForm, authenticatedUser));
            UserLogger.log(authenticatedUser);
        });

        // Set the initial scene view to the login form
        scene = new Scene(loginForm);

        // Show the scene
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add navigation event handlers
        scene.addEventFilter(NavigationEvent.LOGIN, e -> {
            loginForm.setVisible(false);
            user = e.getUser();
            showCalendarScreen();
        });
    }

    private void showCalendarScreen() {
        // Create a new calendar
        Calendar calendar = new Calendar();

        primaryStage.setWidth(1300);
        primaryStage.setHeight(1000);
        primaryStage.centerOnScreen();
        scene.setRoot(calendar);
    }

}
