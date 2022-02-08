module com.example.jfxcontrols {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.example.jfxcontrols to javafx.fxml;
    exports com.example.jfxcontrols;
}