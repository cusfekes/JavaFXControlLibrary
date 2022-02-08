package com.jfx.jfxcontrols.library;

import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class JFXGridView<T> extends GridPane {
    private TextField searchTextField;
    private TableView<T> tableView;
    private Pagination pagination;

    public JFXGridView() {
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        getColumnConstraints().add(column);

        RowConstraints rowSearch = new RowConstraints();
        rowSearch.setPercentHeight(10);
        getRowConstraints().add(rowSearch);

        RowConstraints rowTable = new RowConstraints();
        rowSearch.setPercentHeight(70);
        getRowConstraints().add(rowTable);

        RowConstraints rowPagination = new RowConstraints();
        rowSearch.setPercentHeight(20);
        getRowConstraints().add(rowPagination);

        searchTextField = new TextField();
        setColumnIndex(searchTextField, 0);
        setColumnIndex(searchTextField, 0);

        tableView = new TableView<T>();
        setColumnIndex(searchTextField, 1);
        setColumnIndex(searchTextField, 0);
    }
}
