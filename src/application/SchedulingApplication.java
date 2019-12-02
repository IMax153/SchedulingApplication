package application;

import controls.calendar.Calendar;
import controls.form.login.LoginForm;
import controls.table.CustomerTable;
import events.NavigationEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.User;
import utilities.Database;
import utilities.UserLogger;

/**
 * Class which handles initialization and closure of the main
 * {@link SchedulingApplication}.
 *
 * @author mab90
 */
public class SchedulingApplication extends Application {

    /**
     * The authenticated {@link User} of the application
     */
    public static User USER;

    private Stage primaryStage;

    private Scene scene;

    private BorderPane pane;

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

        // Create the root border pane
        this.pane = new BorderPane();
        pane.setTop(createNavigationBar());

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
            USER = e.getUser();
            showCalendarScreen();
        });
    }

    private void showCalendarScreen() {
        // Create a new calendar
        Calendar calendar = new Calendar();

        // Set the calendar to the center of the pane
        pane.setCenter(calendar);

        primaryStage.setWidth(1300);
        primaryStage.setHeight(1000);
        primaryStage.centerOnScreen();
        scene.setRoot(pane);
    }

    private void showCustomerScreen() {
        // Create a new customer table
        CustomerTable table = new CustomerTable();

        // Set the customer table to the center of the screen
        pane.setCenter(table);

        primaryStage.setWidth(1300);
        primaryStage.setHeight(1000);
        primaryStage.centerOnScreen();
        scene.setRoot(pane);
    }

    private MenuBar createNavigationBar() {
        // Create the parent menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.setPadding(new Insets(5));

        // Create the file menu
        Menu fileMenu = new Menu("File");

        // Create the file menu items
        MenuItem quitMenuItem = new MenuItem("Quit");
        quitMenuItem.setOnAction(e -> quit());

        // Add the file menu items to the file menu
        fileMenu.getItems().add(quitMenuItem);

        // Create the navigation menu
        Menu navigationMenu = new Menu("Navigation");

        // Create the navigation menu items
        MenuItem calendarMenuItem = new MenuItem("Calendar");
        calendarMenuItem.setOnAction(e -> showCalendarScreen());

        MenuItem customersMenuItem = new MenuItem("Customers");
        customersMenuItem.setOnAction(e -> showCustomerScreen());

        MenuItem reportsMenuItem = new MenuItem("Reports");

        // Add the navigation menu items to the navigation menu
        navigationMenu.getItems().addAll(calendarMenuItem, customersMenuItem, reportsMenuItem);

        // Add the menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, navigationMenu);

        return menuBar;
    }

    private void quit() {
        Platform.exit();
    }

}
