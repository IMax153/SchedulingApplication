/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports.consultant;

import dao.UserDao;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import models.User;

/**
 *
 * @author maxbrown
 */
public class ConsultantReport extends Control {

    private final UserDao userDao = new UserDao();

    public ConsultantReport() {
        String stylesheet = getClass().getResource("consultant-report.css").toExternalForm();
        getStylesheets().add(stylesheet);

        // Add consultants to the consultant list
        userDao.findAll().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(consultant -> getConsultants().add(consultant));
    }

    private final ObservableList<User> consultants = FXCollections.observableArrayList();

    /**
     * Returns the list of {@link User}s for the {@link ConsultantReport}.
     *
     * @return The list of consultants.
     */
    public ObservableList<User> getConsultants() {
        return consultants;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ConsultantReportSkin(this);
    }

}
