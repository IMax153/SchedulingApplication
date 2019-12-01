/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import models.User;

/**
 * An {@link Event} that signals the {@link SchedulingApplication} to navigate
 * to a new view.
 *
 * @author maxbrown
 */
public class NavigationEvent extends Event {

    /**
     * The default {@link NavigationEvent} type.
     */
    public static final EventType<NavigationEvent> NAVIGATE = new EventType<>(NavigationEvent.ANY, "NAVIGATE");

    /**
     * The login {@link NavigationEvent} type.
     */
    public static final EventType<NavigationEvent> LOGIN = new EventType<>(NavigationEvent.NAVIGATE, "LOGIN");

    private final User user;

    public NavigationEvent(Object source, EventTarget target, User user) {
        super(source, target, LOGIN);
        this.user = user;
    }

    /**
     * Returns the value of {@link NavigationEvent#user}.
     *
     * @return The user.
     */
    public User getUser() {
        return user;
    }

}
