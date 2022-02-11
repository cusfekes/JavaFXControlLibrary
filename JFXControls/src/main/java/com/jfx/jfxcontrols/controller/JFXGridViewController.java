package com.jfx.jfxcontrols.controller;

import com.jfx.jfxcontrols.data.models.Person;
import com.jfx.jfxcontrols.data.services.PersonService;
import com.jfx.jfxcontrols.library.jfxgridview.JFXGridView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JFXGridViewController implements Initializable {
    @FXML
    private JFXGridView<Person> gridPerson;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Person> personList = PersonService.GetPersonList();
        gridPerson.setDataSource(FXCollections.observableList(personList));
    }
}