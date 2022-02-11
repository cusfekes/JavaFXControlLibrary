module com.example.jfxcontrols {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires lombok;

    exports com.jfx.jfxcontrols;

    opens com.jfx.jfxcontrols;
    opens com.jfx.jfxcontrols.data.models;
    opens com.jfx.jfxcontrols.data.services;
    opens com.jfx.jfxcontrols.library.jfxgridview;
    opens com.jfx.jfxcontrols.library.jfxtextfield;
    exports com.jfx.jfxcontrols.controller;
    opens com.jfx.jfxcontrols.controller;
}