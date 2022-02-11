package com.jfx.jfxcontrols.library.jfxgridview;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class JFXGridColumn<S, T> extends TableColumn<S, T> {
    private final DoubleProperty percentWidth = new SimpleDoubleProperty(1);
    public final DoubleProperty percentWidthProperty() {
        return this.percentWidth;
    }

    private String dataField;
    private boolean searchable = false;
    private boolean sortableColumn = false;

    public JFXGridColumn() {
        tableViewProperty().addListener(new ChangeListener<TableView<S>>() {
            @Override
            public void changed(ObservableValue<? extends TableView<S>> ov, TableView<S> t, TableView<S> t1) {
                if (JFXGridColumn.this.prefWidthProperty().isBound()) {
                    JFXGridColumn.this.prefWidthProperty().unbind();
                }
                JFXGridColumn.this.prefWidthProperty().bind(t1.widthProperty().multiply(percentWidth.doubleValue() / 100.0));
            }
        });
    }

    public final double getPercentWidth() {
        return this.percentWidthProperty().get();
    }

    public final void setPercentWidth(double value) throws IllegalArgumentException {
        if (value >= 0 && value <= 100) {
            this.percentWidthProperty().set(value);
        } else {
            throw new IllegalArgumentException(String.format("The provided percentage width is not between 0 and 100. Value is: %1$s", value));
        }
    }

    public String getDataField() {
        return dataField;
    }

    public void setDataField(String dataField) {
        this.dataField = dataField;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public boolean isSortableColumn() {
        return sortableColumn;
    }

    public void setSortableColumn(boolean sortableColumn) {
        this.sortableColumn = sortableColumn;
        setSortable(sortableColumn);
    }
}
