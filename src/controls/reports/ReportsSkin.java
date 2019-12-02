/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports;

import controls.reports.appointment.AppointmentReport;
import controls.reports.consultant.ConsultantReport;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * The {@link Skin} for the {@link Reports}.
 *
 * @author maxbrown
 */
public class ReportsSkin extends SkinBase<Reports> {

    public ReportsSkin(Reports pane) {
        super(pane);

        // Create the report view content
        AppointmentReport appointmentReport = new AppointmentReport();
        ConsultantReport consultantReport = new ConsultantReport();

        // Create a new tab pane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Create the appointment report tab
        Tab appointmentReportTab = new Tab("Appointment Reports");
        appointmentReportTab.getStyleClass().add("appointment");
        appointmentReportTab.setContent(appointmentReport);

        // Create the consultant report tab
        Tab consultantReportTab = new Tab("Consultant Reports");
        consultantReportTab.getStyleClass().add("consultant");
        consultantReportTab.setContent(consultantReport);

        // Add the tabs to the tab pane
        tabPane.getTabs().addAll(appointmentReportTab, consultantReportTab);

        // Add the tab pane to the view
        getChildren().add(tabPane);
    }

}
