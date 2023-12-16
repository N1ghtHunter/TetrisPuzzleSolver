module com.example.make_a_square_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    opens make_a_square_gui to javafx.fxml;

    exports make_a_square_gui;
}