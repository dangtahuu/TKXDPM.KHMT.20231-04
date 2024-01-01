package controller.admin.pages;

import app.utils.HelperMethods;

import controller.UserSessionController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.Datasource;
import model.Order;
import model.User;
import model.media.Media;
import model.media.Media;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * This class handles the users medias page.
 * @author      Sajmir Doko
 */
public class OrdersController {

    @FXML
    private TableView<Order> tableOrdersPage;
 

    /**
     * This method lists all the media to the view table.
     * It starts a new Task, gets all the medias from the database then bind the results to the view.
     * @since                   1.0.0
     */
    @FXML
    public void listOrders() {
    	 
        Task<ObservableList<Order>> getAllOrderTask = new Task<ObservableList<Order>>() {
            @Override
            protected ObservableList<Order> call() {
                return FXCollections.observableArrayList(Datasource.getInstance().getAllOrders(Datasource.ORDER_BY_NONE));
            }
        };

        tableOrdersPage.itemsProperty().bind(getAllOrderTask.valueProperty());
        addActionButtonsToTable();
      
        new Thread(getAllOrderTask).start();

    }
    
    /**
     * This private method adds the action buttons to the table rows.
     * @since                   1.0.0
     */
    @FXML
    private void addActionButtonsToTable() {
        TableColumn colBtnEdit = new TableColumn("Actions");

        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                return new TableCell<Order, Void>() {

//                    private final Button viewButton = new Button("View");
//                    {
//                        viewButton.getStyleClass().add("button");
//                        viewButton.getStyleClass().add("xs");
//                        viewButton.getStyleClass().add("info");
//                        viewButton.setOnAction((ActionEvent event) -> {
//                            User customerData = getTableView().getItems().get(getIndex());
//                            btnViewCustomer((int) customerData.getId());
//                            System.out.println("View User");
//                            System.out.println("customer id: " + customerData.getId());
//                            System.out.println("customer name: " + customerData.getFullname());
//                        });
//                    }

                
                    private final Button acceptBtn = new Button("Accept");

                    {
                    	acceptBtn.getStyleClass().add("button");
                    	acceptBtn.getStyleClass().add("xs");
                    	acceptBtn.getStyleClass().add("success");
                    	acceptBtn.setOnAction((ActionEvent event) -> {
                            Order orderData = getTableView().getItems().get(getIndex());
                            if (Datasource.getInstance().updateOrder("ACCEPTED",orderData.getId())) {
                                int rowIndex = getIndex();
                                     Order customer = getTableView().getItems().get(rowIndex);

                                     // Modify the field you want to change
                                     customer.setStatus("ACCEPTED");

                                     // Optionally refresh the TableView if not bound to the data
                                     getTableView().refresh();
                                 }
                      
                        });
                    }

                    private final HBox buttonsPane = new HBox();

                    {
                        buttonsPane.setSpacing(10);
//                        buttonsPane.getChildren().add(viewButton);
//                        buttonsPane.getChildren().add(editButton);
                        buttonsPane.getChildren().add(acceptBtn);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buttonsPane);
                        }
                    }
                };
            }
        };

        colBtnEdit.setCellFactory(cellFactory);

        tableOrdersPage.getColumns().add(colBtnEdit);

    }

}