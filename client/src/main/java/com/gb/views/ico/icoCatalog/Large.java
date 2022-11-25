package com.gb.views.ico.icoCatalog;

import com.gb.net.MessageReceived;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.io.File;

public class Large extends TileElement {

    public Large(ImageView imageView, File file, MessageReceived received) {
        super(file, received);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(imageView);
        Label label = new Label(file.getName());
        this.getChildren().add(label);
        this.getStyleClass().addAll("large");
        this.setFillWidth(false);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(0, 5, 0, 5));
    }
}
