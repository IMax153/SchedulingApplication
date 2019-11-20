/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mab90
 */
public class LoginScreenController implements Initializable {

    @FXML
    private TextField userNameInput;
    @FXML
    private PasswordField passwordInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onSubmit(ActionEvent event) {
        String userName = userNameInput.getText();
        String password = passwordInput.getText();

        System.out.println(userName + " " + password);
    }

}
