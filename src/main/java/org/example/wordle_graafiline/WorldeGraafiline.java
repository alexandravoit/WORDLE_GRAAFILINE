package org.example.wordle_graafiline;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class WorldeGraafiline extends Application {

    static Rectangle[][] ruudud;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {

        // SUUREM PILT
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);


        Label pealkiri = new Label("WORDLE");
        pealkiri.setStyle("-fx-font-weight: bold; -fx-font-size: 40px; -fx-text-fill: #3d5b37;");
        vBox.getChildren().add(pealkiri);

        ruudud = new Rectangle[8][6];

        // RUUDUSTIKU LOOMINE
        for (int i = 0; i < 8; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);

            hBox.setAlignment(Pos.CENTER);

            for (int j = 0; j < 6; j++) {
                double ruuduKülg = 50;

                Rectangle ruut = new Rectangle(ruuduKülg , ruuduKülg);
                ruut.setFill(Color.LIGHTGREY);

                ruudud[i][j] = ruut;

                hBox.getChildren().add(ruut);
            }

            vBox.getChildren().add(hBox);
        }

        TextField guess = new TextField();
        guess.setMaxSize(100,20);
        guess.setPadding(new Insets(10));
        VBox.setMargin(guess, new Insets(20, 0, 0, 0));

        vBox.getChildren().add(guess);


        Scene stseen = new Scene(vBox, 800,800);

        primaryStage.setScene(stseen);
        primaryStage.setTitle("WORDLE");

        vBox.prefWidthProperty().bind(stseen.widthProperty());
        vBox.prefHeightProperty().bind(stseen.heightProperty());

        primaryStage.show();

    }
}
