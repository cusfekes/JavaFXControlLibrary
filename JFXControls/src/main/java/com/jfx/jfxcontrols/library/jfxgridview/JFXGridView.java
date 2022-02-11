package com.jfx.jfxcontrols.library.jfxgridview;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JFXGridView<T> extends StackPane {
    private final ObservableList<JFXGridColumn<T, ?>> columns;
    private final ListChangeListener<JFXGridColumn<T, ?>> columnsListener;
    private ObservableList<T> dataSource;
    private SortedList<T> sortedDataSource;
    private Integer rowsCount = 1;
    private boolean searchable = false;

    private TableView<T> tableView;
    private Pagination pagination;
    private TextField searchTextField;
    private HBox searchBox;
    private List<String> searchableFields;

    private final int SEARCH_TEXT_FIELD_WIDTH = 300;
    private final int SEARCH_TEXT_FIELD_HEIGHT = 40;
    private final int SEARCH_BAR_WIDTH = 320;
    private final int SEARCH_BAR_HEIGHT = 60;
    private final String SEARCH_TEXT_FIELD_PROMPT_TEXT = "Search in table...";
    private final String EMPTY_GRID_PROMPT_TEXT = "There is no item in the table...";
    private final String SEARCH_BAR_BACKGROUND_COLOR = "transparent; ";
    private final String SEARCH_BAR_BORDER_COLOR = "#CCCCCE; ";
    private final int SEARCH_BAR_BORDER_WIDTH = 8;
    private final int SEARCH_BAR_BORDER_RADIUS = 6;
    private final String BACKGROUND_COLOR_STYLE = "-fx-background-color: ";
    private final String BORDER_RADIUS_STYLE = "-fx-border-radius: ";
    private final String BORDER_WIDTH_STYLE = "-fx-border-width: ";
    private final String BORDER_COLOR_STYLE = "-fx-border-color: ";

    public JFXGridView() {
        this.dataSource = FXCollections.observableArrayList();
        this.searchableFields = new ArrayList<>();

        searchBox = createSearchBoxElement();
        tableView = createTableViewElement();
        pagination = createPaginationElement();
        pagination.setVisible(false);

        getChildren().add(searchBox);
        getChildren().add(tableView);
        getChildren().add(pagination);

        this.columns = FXCollections.observableArrayList();
        this.columnsListener = new ListChangeListener<JFXGridColumn<T, ?>>() {
            public void onChanged(Change<? extends JFXGridColumn<T, ?>> var1) {
                Integer lastIndex = var1.getList().size() - 1;
                JFXGridColumn gridColumn = var1.getList().get(lastIndex);
                gridColumn.setCellValueFactory(
                        new PropertyValueFactory<>(gridColumn.getDataField())
                );
                tableView.getColumns().add(gridColumn);
                if (gridColumn.isSortableColumn())
                    tableView.getSortOrder().add(gridColumn);
                if (gridColumn.isSearchable())
                    searchableFields.add(gridColumn.getDataField());
            }
        };
        getGridColumns().addListener(this.columnsListener);
    }

    private TableView<T> createTableViewElement() {
        tableView = new TableView<T>();
        tableView.setPlaceholder(new Label(EMPTY_GRID_PROMPT_TEXT));
        return tableView;
    }

    private Pagination createPaginationElement() {
        pagination = new Pagination();
        return pagination;
    }

    private HBox createSearchBoxElement() {
        searchBox = new HBox();
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setMaxWidth(SEARCH_BAR_WIDTH);
        searchBox.setMinWidth(SEARCH_BAR_WIDTH);
        searchBox.setMaxHeight(SEARCH_BAR_HEIGHT);
        searchBox.setMinHeight(SEARCH_BAR_HEIGHT);
        searchBox.setStyle(createSearchBarStyle());
        searchTextField = new TextField();
        searchTextField.setMaxWidth(SEARCH_TEXT_FIELD_WIDTH);
        searchTextField.setMinWidth(SEARCH_TEXT_FIELD_WIDTH);
        searchTextField.setMinHeight(SEARCH_TEXT_FIELD_HEIGHT);
        searchTextField.setMaxHeight(SEARCH_TEXT_FIELD_HEIGHT);
        searchTextField.setPromptText(SEARCH_TEXT_FIELD_PROMPT_TEXT);
        searchBox.setVisible(false);
        searchBox.getChildren().add(searchTextField);
        return searchBox;
    }

    private String createSearchBarStyle() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(BACKGROUND_COLOR_STYLE);
        strBuilder.append(SEARCH_BAR_BACKGROUND_COLOR);
        strBuilder.append(BORDER_RADIUS_STYLE);
        strBuilder.append(SEARCH_BAR_BORDER_RADIUS + "; ");
        strBuilder.append(BORDER_WIDTH_STYLE);
        strBuilder.append(SEARCH_BAR_BORDER_WIDTH + "; ");
        strBuilder.append(BORDER_COLOR_STYLE);
        strBuilder.append(SEARCH_BAR_BORDER_COLOR);
        return strBuilder.toString();
    }

    private int getPageCount(int itemCount) {
        if (itemCount == 0)
            return 1;
        Integer pageCount = itemCount / rowsCount;
        if (itemCount % rowsCount != 0)
            pageCount += 1;
        return pageCount;
    }

    private Node createPage(int pageIndex, SortedList<T> dataSource) {
        int fromIndex = pageIndex * rowsCount;
        int toIndex = Math.min(fromIndex + rowsCount, dataSource.size());
        tableView.getItems().setAll(dataSource.subList(fromIndex, toIndex));
        return tableView;
    }

    public final ObservableList<JFXGridColumn<T, ?>> getGridColumns() {
        return this.columns;
    }

    public final void setDataSource(ObservableList<T> dataSource) {
        this.dataSource = dataSource;
        this.sortedDataSource = new SortedList<>(dataSource);
        this.sortedDataSource.comparatorProperty().bind(tableView.comparatorProperty());
        activatePagination(this.sortedDataSource);
    }

    public final void clearDataSource() {
        if (this.dataSource != null) {
            this.dataSource.clear();
            this.sortedDataSource = new SortedList<T>(FXCollections.emptyObservableList());
            this.sortedDataSource.comparatorProperty().bind(tableView.comparatorProperty());
        }
        setDataSource(this.dataSource);
    }

    private void activatePagination(SortedList<T> dataSource) {
        pagination.setVisible(true);
        pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex, dataSource));
        pagination.setPageCount(getPageCount(dataSource.size()));
        pagination.setCurrentPageIndex(0);
    }

    public ObservableList<T> getSelectedItems() {
        return tableView.getSelectionModel().getSelectedItems();
    }

    public TableView<T> getTableView() {
        return this.tableView;
    }

    private void manageSearchActivation() {
        if (this.searchable)
            tableView.setOnKeyPressed(event -> ControlFKeyPressed(event));
        else
            tableView.removeEventHandler(KeyEvent.KEY_PRESSED, event -> ControlFKeyPressed(event));
    }

    public void ControlFKeyPressed(KeyEvent keyCode) {
        final KeyCombination keyCodeCombination = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
        if (keyCodeCombination.match(keyCode)) {
            activateSearch();
        }
    }

    private void activateSearch() {
        searchBox.setVisible(true);
        searchBox.toFront();
        searchTextField.clear();
        searchTextField.requestFocus();
        searchTextField.setOnKeyPressed(event -> escapeKeyPressed(event.getCode()));
        tableView.setOnMouseClicked((MouseEvent e) -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                deactivateSearch();
            }
        });
        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @SneakyThrows
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                String oldValue, String newValue) {
                if (!newValue.trim().isEmpty() && newValue.trim().length() > 1) {
                    ObservableList<T> searchResult = FXCollections.observableArrayList();
                    String searchText = newValue.toLowerCase();
                    boolean isFound = false;
                    for (T data : dataSource) {
                        for (String fieldName : searchableFields) {
                            Field field = data.getClass().getDeclaredField(fieldName);
                            field.setAccessible(true);
                            Object value = field.get(data);
                            if (value != null && value.toString().toLowerCase().contains(searchText)) {
                                isFound = true;
                                break;
                            }
                        }
                        if (isFound)
                            searchResult.add(data);
                        isFound = false;
                    }
                    activatePagination(new SortedList<>(searchResult));
                } else
                    setDataSource(dataSource);
            }
        });
    }

    private void escapeKeyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.ESCAPE)
            deactivateSearch();
    }

    public void deactivateSearch() {
        searchBox.setVisible(false);
        searchBox.toBack();
        tableView.requestFocus();
        setDataSource(this.dataSource);
    }

    public Integer getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(Integer rowsCount) {
        this.rowsCount = rowsCount;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
        manageSearchActivation();
    }
}
