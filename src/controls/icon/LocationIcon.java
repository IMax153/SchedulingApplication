/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.icon;

import javafx.scene.shape.Circle;

/**
 * The location icon.
 *
 * @author maxbrown
 */
public class LocationIcon extends Icon {

    private final Circle circle;

    public LocationIcon() {
        super("M 12 2 C 8.13 2 5 5.13 5 9 c 0 5.25 7 13 7 13 s 7 -7.75 7 -13 c 0 -3.87 -3.13 -7 -7 -7 Z M 7 9 c 0 -2.76 2.24 -5 5 -5 s 5 2.24 5 5 c 0 2.88 -2.88 7.19 -5 9.88 C 9.92 16.21 7 11.85 7 9 Z");
        this.circle = new Circle(12, 9, 2.5);
        this.circle.getStyleClass().add("icon");
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        getChildren().add(circle);
    }
}
