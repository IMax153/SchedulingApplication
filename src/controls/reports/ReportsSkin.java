/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports;

import controls.reports.appointment.AppointmentReport;
import controls.reports.consultant.ConsultantReport;
import controls.reports.customer.CustomerReport;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
        CustomerReport customerReport = new CustomerReport();

        // Create a new tab pane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Create the appointment report tab
        Tab appointmentReportTab = new Tab("Appointment Report");
        appointmentReportTab.getStyleClass().add("appointment");
        appointmentReportTab.setContent(appointmentReport);

        // Create the consultant report tab
        Tab consultantReportTab = new Tab("Consultant Report");
        consultantReportTab.getStyleClass().add("consultant");
        consultantReportTab.setContent(consultantReport);

        // Create the customer report tab
        Tab customerReportTab = new Tab("Customer Report");
        customerReportTab.getStyleClass().add("customer");
        customerReportTab.setContent(customerReport);

        // Add the tabs to the tab pane
        tabPane.getTabs().addAll(appointmentReportTab, consultantReportTab, customerReportTab);

        // Add the tab pane to the view
        getChildren().add(tabPane);
    }

}
