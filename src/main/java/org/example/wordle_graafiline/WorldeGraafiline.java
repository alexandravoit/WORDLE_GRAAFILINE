package org.example.wordle_graafiline;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorldeGraafiline extends Application {

    SõnaValija sõnaValija = new SõnaValija(); // sõnaValija klass, et valida suvaline sõna
    String[] sõnaJaTähendus = sõnaValija.valiSõna(); // String[] sõnast ja selle tähendusest
    String otsitavSõna = sõnaJaTähendus[0];
    String otsitavaSõnaTähendus = sõnaJaTähendus[1];


    boolean võit = false;

    static StackPane[][] ruudud;
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

        Label pealkiri = new Label("VÖRDEL");
        pealkiri.setTextFill(Paint.valueOf("#3d5b37"));
        pealkiri.setFont(Font.font(font, 50));

        vBox.getChildren().add(pealkiri);

        ruudud = new StackPane[8][6];

        // MÄNGU LÕPUS VÄLJUMISE NUPP
        Button exitButton = new Button("LAHKU MÄNGUST");
        exitButton.setVisible(false);
        exitButton.setMinSize(100, 50);


        exitButton.setOnAction(e -> {

            // LAHKUDES ON LAHE FADE-OUT JA TULEMUS KIRJUTATAKSE FAILI vördel.txt
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), vBox);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);

            fadeTransition.setOnFinished(event -> {
                // INFO KIRJUTATAKSE FAILI
                kirjutaFaili("vördel.txt");
                Platform.exit();
            });

            fadeTransition.play();
        });




        exitButton.setStyle(
                "-fx-background-color: #3d5b37;" +
                        "-fx-background-radius: 15;" +
                        "-fx-font-family: 'Impact';" +
                        "-fx-font-size: 16px;" +
                        "-fx-text-fill: #ffffff;"
        );


        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(exitButton);
        vBox.getChildren().add(buttonBox);

        // RUUDUSTIKU LOOMINE
        for (int i = 0; i < 8; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);

            hBox.setAlignment(Pos.CENTER);

            for (int j = 0; j < 6; j++) {

                Rectangle ruut = new Rectangle(50 , 50);
                ruut.setFill(Color.LIGHTGREY);

                StackPane stack = new StackPane();
                stack.getChildren().add(ruut);

                ruudud[i][j] = stack;

                hBox.getChildren().add(stack);
            }

            vBox.getChildren().add(hBox);
        }

        HBox hBox = new HBox(); // HBOX VASTUSE LAHTRI JA NUPU JAOKS
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        TextField guess = new TextField(); // LAHTER VASTUSE JAOKS
        Button guessButton = new Button("VASTA"); // NUPP VASTUSE SUBMITTIMISEKS

        guess.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: #3d5b37;" +
                        "-fx-border-width: 2px;" +
                        "-fx-font-family: 'Impact';" +
                        "-fx-font-size: 16px;" +
                        "-fx-text-fill: #3d5b37;"
        );

        guessButton.setStyle(
                "-fx-background-color: #3d5b37;" +
                        "-fx-background-radius: 15;" +
                        "-fx-font-family: 'Impact';" +
                        "-fx-font-size: 16px;" +
                        "-fx-text-fill: #ffffff;"
        );




        // KOM VASTUSE SISESTUS JA SELLE ANALÜÜSI OSA
        guessButton.setOnAction(e -> {

            String sisestus = guess.getText(); // KASUTAJA GUESS

            if (vastused == 7) {
                Text teade = new Text("Te ei arvanud sõna ära :(\nOtsitav sõna oli '" + otsitavSõna + "'\nSõna tähendus: " + otsitavaSõnaTähendus);
                teade.setFont(Font.font(font, 20));
                teade.setFill(Paint.valueOf("#3d5b37"));
                vBox.getChildren().add(teade);
                teade.setTextAlignment(TextAlignment.CENTER);
                guess.setDisable(true);
                guessButton.setDisable(true);
                exitButton.setVisible(true);

            }

            if (sisestus.length() == 6) {
                System.out.println(otsitavSõna);
                if (sisestus.equals(otsitavSõna)) {
                    võit = true;
                    Text teade = new Text("Arvasite sõna ära " + (vastused + 1) + ". katsel!\nOtsitav sõna oli '" + otsitavSõna + "'\nSõna tähendus: " + otsitavaSõnaTähendus);
                    teade.setFont(Font.font(font, 20));
                    teade.setFill(Paint.valueOf("#3d5b37"));
                    vBox.getChildren().add(teade);
                    teade.setTextAlignment(TextAlignment.CENTER);
                    // Tekstivälja ja lahtri väljalülitus
                    guess.setDisable(true);
                    guessButton.setDisable(true);
                    exitButton.setVisible(true);

                }

                // KOM SISENDI ANALÜÜS - KASTIDESSE JAOTUS JA ANIMATSIOON
                // Õigel kohal tähtede kastid on rohelised. Valel kohal, kuid otsitavas sõnas leiduvad kastid, on kollased.
                for (int i = 0; i < 6; i++) {
                    if (sisestus.toLowerCase().charAt(i) == otsitavSõna.charAt(i)) {
                        Rectangle ruut = new Rectangle(50 , 50);

                        // väike animatsioon
                        Color värv = Color.valueOf("#3d5b37");
                        FillTransition ft = new FillTransition(Duration.millis(1000), ruut, Color.LIGHTGREY, värv);
                        ft.play();

                        Text text = new Text(String.valueOf(sisestus.toUpperCase().charAt(i)));
                        text.setFill(Color.WHITE);
                        text.setFont(Font.font("Impact", FontWeight.BOLD, 20));
                        ruudud[vastused][i].getChildren().addAll(ruut, text);
                    }
                    else if (otsitavSõna.contains(String.valueOf(sisestus.toLowerCase().charAt(i)))) {
                        Rectangle ruut = new Rectangle(50 , 50);

                        // väike animatsioon
                        Color värv = Color.valueOf("#bd981dff");
                        FillTransition ft = new FillTransition(Duration.millis(1000), ruut, Color.LIGHTGREY, värv);
                        ft.play();


                        Text text = new Text(String.valueOf(sisestus.toUpperCase().charAt(i)));
                        text.setFill(Color.WHITE);
                        text.setFont(Font.font("Impact", FontWeight.BOLD, 20));
                        ruudud[vastused][i].getChildren().addAll(ruut, text);
                    }
                    else {
                        Rectangle ruut = new Rectangle(50, 50);
                        ruut.setFill(Color.LIGHTGREY);
                        Text text = new Text(String.valueOf(sisestus.toUpperCase().charAt(i)));
                        text.setFill(Color.WHITE);
                        text.setFont(Font.font("Impact", FontWeight.BOLD, 20));
                        ruudud[vastused][i].getChildren().addAll(ruut, text);
                    }
                }
                vastused++;
            }

            if (sisestus.length() != 6) {
                Text teade = new Text("sisesta 6-täheline sõna!");
                teade.setFont(Font.font(font, 20));
                teade.setFill(Paint.valueOf("#3d5b37"));
                vBox.getChildren().add(teade);

                // KOM VEATEADE VÄLJASTATAKSE 2ks SEKUNDIKS
                PauseTransition paus = new PauseTransition(Duration.seconds(2));
                paus.setOnFinished(event -> vBox.getChildren().remove(teade));
                paus.play();
            }
        });

        // LAHTRI JA NUPU FORMATTING
        guess.setMaxSize(100,20);
        guess.setPadding(new Insets(10));
        hBox.getChildren().addAll(guess,guessButton);
        vBox.getChildren().add(hBox);
        VBox.setMargin(hBox, new Insets(20, 0, 0, 0));

        Scene stseen = new Scene(vBox, 800,800);
        stseen.setFill(Color.valueOf("#3d5b37"));

        primaryStage.setScene(stseen);
        primaryStage.setTitle("VÖRDEL");

        vBox.prefWidthProperty().bind(stseen.widthProperty());
        vBox.prefHeightProperty().bind(stseen.heightProperty());

        primaryStage.show();

    }


    // KOM MEETOD FAILI KIRJUTAMISEKS
    private void kirjutaFaili(String failinimi) {

        String arvatiÄraSõnum = võit ? "JAH, " + vastused + ". katsel" : "EI";
        LocalDateTime aeg = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = aeg.format(formatter);

        String info = formattedDateTime + " - Sõna: '" + otsitavSõna  + "', Tähendus: '" + otsitavaSõnaTähendus + "', Arvati ära: " + arvatiÄraSõnum;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(failinimi, true))) {
            writer.write(info);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}