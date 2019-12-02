/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * The {@link Control} that handles displaying reports to the {@link User}.
 *
 * @author maxbrown
 */
public class Reports extends Control {

    public Reports() {
        String stylesheet = getClass().getResource("reports.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ReportsSkin(this);
    }

}
