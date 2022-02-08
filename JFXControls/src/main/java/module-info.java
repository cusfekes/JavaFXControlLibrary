module com.example.jfxcontrols {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.jfx.jfxcontrols to javafx.fxml;
    opens com.jfx.jfxcontrols.library;
    exports com.jfx.jfxcontrols;
}