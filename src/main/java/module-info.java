module org.example.wordle_graafiline {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.wordle_graafiline to javafx.fxml;
    exports org.example.wordle_graafiline;
}