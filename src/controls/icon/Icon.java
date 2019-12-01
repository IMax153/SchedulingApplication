/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.icon;

import javafx.scene.Group;
import javafx.scene.shape.SVGPath;

/**
 * The abstract super class that all {@link Icon}s should derive from.
 *
 * @author maxbrown
 */
public abstract class Icon extends Group {

    protected final SVGPath path;

    public Icon(String svgPath) {
        this.path = new SVGPath();

        path.setContent(svgPath);
        path.getStyleClass().add("icon");
    }

    @Override
    protected void layoutChildren() {
        getChildren().addAll(path);
        super.layoutChildren();
    }
}
