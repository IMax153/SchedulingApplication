/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports.customer;

import dao.CustomerDao;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.util.Pair;

/**
 * The {@link Control} used for displaying the number of {@link Customer}s by
 * {@link City} as a {@link BarChart}.
 *
 * @author maxbrown
 */
public class CustomerReport extends Control {

    private final CustomerDao customerDao = new CustomerDao();

    public CustomerReport() {
        String stylesheet = getClass().getResource("customer-report.css").toExternalForm();
        getStylesheets().add(stylesheet);

        refreshData();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CustomerReportSkin(this);
    }

    private final ObservableList<Data<String, Number>> data = FXCollections.observableArrayList();

    /**
     * Returns the property containing the {@link ObservableList} of
     * {@link Data} for the {@link CustomerReport}.
     *
     * @return The list of data.
     */
    public ObservableList<Data<String, Number>> getData() {
        return data;
    }

    private void refreshData() {
        getData().clear();

        customerDao.findAllByCity().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::createData)
                .forEach(item -> getData().add(item));
    }

    private Data<String, Number> createData(Pair<String, Integer> pair) {
        return new Data<>(pair.getKey(), pair.getValue());
    }
}
