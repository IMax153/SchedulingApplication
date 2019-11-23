/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a calendar within the application.
 *
 * @author mab90
 */
public class Calendar {

    /**
     * The list of appointments contained within the calendar.
     */
    private final List<Appointment> appointments;

    public Calendar() {
        this.appointments = new ArrayList<>();
    }

    public Calendar(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}
