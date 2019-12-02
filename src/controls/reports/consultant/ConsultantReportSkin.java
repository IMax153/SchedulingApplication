/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports.consultant;

import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import models.User;

/**
 * The {@link Skin} for the {@link ConsultantReport} control.
 *
 * @author maxbrown
 */
public class ConsultantReportSkin extends SkinBase<ConsultantReport> {

    public ConsultantReportSkin(ConsultantReport report) {
        super(report);

        // Create the reports grid
        GridPane grid = new GridPane();
        grid.setMinSize(0, 0);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid.setHgap(20);
        grid.setVgap(20);

        // Add column constraints to the grid
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);
        grid.getColumnConstraints().add(columnConstraints);

        // Create a scroll pane to allow the grid to scroll if necessary
        ScrollPane scroller = new ScrollPane(grid);
        scroller.getStyleClass().add("container");
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Add consultant schedules to the grid 
        List<User> consultants = report.getConsultants();
        for (int i = 0; i < consultants.size(); i++) {
            grid.addRow(i, new ConsultantSchedule(consultants.get(i)));
        }

        // Add the scroller to the view
        getChildren().add(scroller);
    }

}
