package controller.user.pages;

import app.utils.HelperMethods;

import controller.UserSessionController;
import controller.admin.pages.medias.Book.ViewBookController;
import controller.admin.pages.medias.CD.ViewCDController;
import controller.admin.pages.medias.DVD.ViewDVDController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.CartMedia;
import model.Datasource;
import model.Order;
import model.media.Media;
import model.media.Media;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * This class handles the users medias page.
 * @author      Sajmir Doko
 */
public class UserOrdersController {

    @FXML
    private TableView<Order> tableOrdersPage;
    int user_id = UserSessionController.getUserId();
    String user_name = UserSessionController.getUserFullName();

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
                return FXCollections.observableArrayList(Datasource.getInstance().getUserOrders(Datasource.ORDER_BY_NONE, user_id));
            }
        };

        tableOrdersPage.itemsProperty().bind(getAllOrderTask.valueProperty());
      
        addActionButtonsToTable();
        new Thread(getAllOrderTask).start();

    }
    @FXML
    private void addActionButtonsToTable() {
        TableColumn colBtnView = new TableColumn("Actions");

        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                return new TableCell<Order, Void>() {

                    private final Button viewButton = new Button("View");

                    {
                        viewButton.getStyleClass().add("button");
                        viewButton.getStyleClass().add("xs");
                        viewButton.getStyleClass().add("info");
                        viewButton.setOnAction((ActionEvent event) -> {
                        	Order orderData = getTableView().getItems().get(getIndex());
//                            btnViewOrder(orderData.getId());
                        });
                    }

                    
                    private final HBox buttonsPane = new HBox();

                    {
                        buttonsPane.setSpacing(10);
                        buttonsPane.getChildren().add(viewButton);
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

        colBtnView.setCellFactory(cellFactory);

        tableOrdersPage.getColumns().add(colBtnView);

    }
    

    
 

}