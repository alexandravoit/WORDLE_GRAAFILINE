package org.example.wordle_graafiline;
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
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class WorldeGraafiline extends Application {

    SõnaValija sõnaValija = new SõnaValija(); // sõnaValija klass, et valida suvaline sõna
    String[] sõnaJaTähendus = sõnaValija.valiSõna(); // String[] sõnast ja selle tähendusest
    String otsitavSõna = sõnaJaTähendus[0];
    String otsitavaSõnaTähendus = sõnaJaTähendus[1];
    Button exitButton = new Button("Lahku mängust");

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

        Label pealkiri = new Label("WORDLE");
        pealkiri.setTextFill(Paint.valueOf("#3d5b37"));
        pealkiri.setFont(Font.font(font, 50));

        vBox.getChildren().add(pealkiri);

        ruudud = new StackPane[8][6];
        exitButton.setTextFill(Color.RED);
        exitButton.setVisible(false);
        exitButton.setMinSize(100, 50); // Set the minimum size of the button
        exitButton.setOnAction(e -> Platform.exit());
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

        // KOM VASTUSE SISESTUS JA SELLE ANALÜÜSI OSA
        guessButton.setOnAction(e -> {

            String sisestus = guess.getText(); // KASUTAJA GUESS

            if (vastused == 7) {
                Text teade = new Text("Te ei arvanud sõna ära :(\nOtsitav sõna oli '" + otsitavSõna + "'\nSõna tähendus: " + otsitavaSõnaTähendus);
                teade.setFont(Font.font(font, 20));
                teade.setFill(Paint.valueOf("#3d5b37"));
                vBox.getChildren().add(teade);
                guess.setDisable(true);
                guessButton.setDisable(true);
                exitButton.setVisible(true);

            }

            if (sisestus.length() == 6) {
                System.out.println(otsitavSõna);
                if (sisestus.equals(otsitavSõna)) {
                    Text teade = new Text("Arvasite sõna ära " + (vastused + 1) + ". katsel!\nOtsitav sõna oli '" + otsitavSõna + "'\nSõna tähendus: " + otsitavaSõnaTähendus);
                    teade.setFont(Font.font(font, 20));
                    teade.setFill(Paint.valueOf("#3d5b37"));
                    vBox.getChildren().add(teade);
                    // Tekstivälja ja lahtri väljalülitus
                    guess.setDisable(true);
                    guessButton.setDisable(true);
                    exitButton.setVisible(true);

                }

                //Õigel kohal tähtede kastid on rohelised. Valel kohal, kuid otsitavas sõnas leiduvad kastid, on kollased.
                for (int i = 0; i < 6; i++) {
                    if (sisestus.toLowerCase().charAt(i) == otsitavSõna.charAt(i)) {
                        Rectangle ruut = new Rectangle(50 , 50);
                        ruut.setFill(Color.GREEN);
                        Text text = new Text(String.valueOf(sisestus.toUpperCase().charAt(i)));
                        text.setFont(Font.font("Impact", FontWeight.BOLD, 20));
                        ruudud[vastused][i].getChildren().addAll(ruut, text);
                    }
                    else if (otsitavSõna.contains(String.valueOf(sisestus.toLowerCase().charAt(i)))) {
                        Rectangle ruut = new Rectangle(50 , 50);
                        ruut.setFill(Color.YELLOW);
                        Text text = new Text(String.valueOf(sisestus.toUpperCase().charAt(i)));
                        text.setFont(Font.font("Impact", FontWeight.BOLD, 20));
                        ruudud[vastused][i].getChildren().addAll(ruut, text);
                    }
                    else {
                        Rectangle ruut = new Rectangle(50, 50);
                        ruut.setFill(Color.LIGHTGREY);
                        Text text = new Text(String.valueOf(sisestus.toUpperCase().charAt(i)));
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

                // KOM VEATEADE VÄLJASTATAKSE 3ks SEKUNDIKS
                PauseTransition paus = new PauseTransition(Duration.seconds(3));
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
        stseen.setFill(Color.DARKGREEN);

        primaryStage.setScene(stseen);
        primaryStage.setTitle("WORDLE");

        vBox.prefWidthProperty().bind(stseen.widthProperty());
        vBox.prefHeightProperty().bind(stseen.heightProperty());

        primaryStage.show();

    }
}
