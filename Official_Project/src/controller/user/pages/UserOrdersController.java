package controller.user.pages;

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
public class UserOrdersController {

    @FXML
    private TableView<Order> tableOrdersPage;
 

    /**
     * This method lists all the media to the view table.
     * It starts a new Task, gets all the medias from the database then bind the results to the view.
     * @since                   1.0.0
     */
    @FXML
    public void listOrders() {
    	  int user_id = UserSessionController.getUserId();
    	  
        Task<ObservableList<Order>> getAllOrderTask = new Task<ObservableList<Order>>() {
            @Override
            protected ObservableList<Order> call() {
                return FXCollections.observableArrayList(Datasource.getInstance().getUserOrders(Datasource.ORDER_BY_NONE, user_id));
            }
        };

        tableOrdersPage.itemsProperty().bind(getAllOrderTask.valueProperty());
      
      
        new Thread(getAllOrderTask).start();

    }
    
 

}