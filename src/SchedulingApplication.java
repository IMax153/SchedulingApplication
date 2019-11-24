/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.Database;

/**
 * Class which handles initialization and closure of the main scheduling
 * application.
 *
 * @author mab90
 */
public class SchedulingApplication extends Application {

    /**
     * The primary stage of the application.
     */
    private Stage primaryStage;

    /**
     * The main entry point for the application.
     *
     * @param args - The command line arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Database.createConnection();

        AppointmentDao dao = new AppointmentDao();
//        dao.findById(1).ifPresent(e -> System.out.println(e.toString()));
//        dao.findAll().forEach(oe -> oe.ifPresent(e -> System.out.println(e.toString())));
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

        this.showLoginScreen();
    }

    /**
     * Displays the login screen to the user.
     */
    private void showLoginScreen() throws IOException {
//        navigate("/views/login/LoginScreen.fxml");
        navigate("/views/calendar/CalendarView.fxml");

    }

    /**
     * Navigates the user to the screen at the specified path.
     *
     * @param pathToScreen - The path to the screen to display.
     */
    private FXMLLoader navigate(String pathToScreen) throws IOException {
        try {
            // Load the resource at the given path
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(pathToScreen)
            );

            // Show the scene
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

            return loader;
        } catch (IOException e) {
            System.out.println("Unable to load screen at " + pathToScreen);
            throw e;
        }
    }

}
