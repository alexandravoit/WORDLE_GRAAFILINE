package org.example.wordle_graafiline;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Arrays;

public class WorldeGraafiline extends Application {

    static Rectangle[][] ruudud;
    static int vastused = 0;
    static String font = "Impact";

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
        pealkiri.setTextFill(Paint.valueOf("#3d5b37"));
        pealkiri.setFont(Font.font(font, 50));

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


        HBox hBox = new HBox(); // HBOX VASTUSE LAHTRI JA NUPU JAOKS
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        TextField guess = new TextField(); // LAHTER VASTUSE JAOKS
        Button guessButton = new Button("VASTA"); // NUPP VASTUSE SUBMITTIMISEKS



        // KOM VASTUSE SISESTUS JA SELLE ANALÜÜSI OSA
        guessButton.setOnAction(e -> {

            String sisestus = guess.getText(); // KASUTAJA GUESS

            if (sisestus.length() == 6) vastused ++;
            if (sisestus.length() != 6) {
                Text teade = new Text("sisesta 6-täheline sõna!");
                teade.setFont(Font.font(font, 20));
                teade.setFill(Paint.valueOf("#3d5b37"));


                vBox.getChildren().add(teade);

                // KOM VEATEADE VÄLJASTATAKSE 3ks SEKUNDIKS
                PauseTransition paus = new PauseTransition(Duration.seconds(3));
                paus.setOnFinished(event -> vBox.getChildren().remove(teade));
                paus.play();

            }

            // MÄNGU LÕPP
            if (vastused == 6) {
                System.out.println("LÄBI"); // enda infoks, pole lõppversioonis vajalik

                // TODO LISA MINGI MÄNGU LÕPU TEAVITUS JA STATS
            }



            System.out.println("Sisend: " + sisestus + ", kord: " + vastused);

        });


        // LAHTRI JA NUPU FORMATTING
        guess.setMaxSize(100,20);
        guess.setPadding(new Insets(10));
        hBox.getChildren().addAll(guess,guessButton);
        vBox.getChildren().add(hBox);
        VBox.setMargin(hBox, new Insets(20, 0, 0, 0));


        Scene stseen = new Scene(vBox, 800,800);
        stseen.setFill(Color.DARKGREEN);



        primaryStage.setScene(stseen);
        primaryStage.setTitle("WORDLE");



        vBox.prefWidthProperty().bind(stseen.widthProperty());
        vBox.prefHeightProperty().bind(stseen.heightProperty());

        primaryStage.show();

    }
}
