package controller.user.pages;

import controller.UserSessionController;
import app.utils.HelperMethods;
import controller.UserSessionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.media.Media;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import model.User;
import model.Datasource;
import model.CartMedia;

/**
 * This class handles the users orders page.
 * @author      Sajmir Doko
 */
public class UserCartController {
    public TableView tableMedias;
    
    @FXML
    public Label SumTotalPrice;
    public double price;
    int user_id = UserSessionController.getUserId();
    /**
     * This method lists all the orders to the view table.
     * It starts a new Task, gets all the products from the database then bind the results to the view.
     * @since                   1.0.0
     */
    @FXML
    public void listCartMedias() {

        Task<ObservableList<CartMedia>> getAllCartMedias = new Task<ObservableList<CartMedia>>() {
            @Override
            protected ObservableList<CartMedia> call() {
                return FXCollections.observableArrayList(Datasource.getInstance().getUserCartMedias(Datasource.ORDER_BY_NONE, UserSessionController.getUserId()));
            }
        };

        getAllCartMedias.setOnSucceeded(event -> {
            ObservableList<CartMedia> medias = getAllCartMedias.getValue();
            tableMedias.setItems(medias); 
            addActionButtonsToTable();
            
            double totalPrice = calculateTotalPrice(medias);
            price = totalPrice;
            SumTotalPrice.setText(String.format("%.2f VND", totalPrice));
        });

        new Thread(getAllCartMedias).start();

    }
    @FXML
    public void refresh() {

        Task<ObservableList<CartMedia>> getAllCartMedias = new Task<ObservableList<CartMedia>>() {
            @Override
            protected ObservableList<CartMedia> call() {
                return FXCollections.observableArrayList(Datasource.getInstance().getUserCartMedias(Datasource.ORDER_BY_NONE, UserSessionController.getUserId()));
            }
        };
        getAllCartMedias.setOnSucceeded(event -> {
            ObservableList<CartMedia> medias = getAllCartMedias.getValue();
            tableMedias.setItems(medias); 
            
            
            double totalPrice = calculateTotalPrice(medias);
            SumTotalPrice.setText(String.format("%.2f VND", totalPrice));
        });

        new Thread(getAllCartMedias).start();

    }
    
   
    
    @FXML
    private void btnPlaceOrderAction(ActionEvent event1) throws IOException {

        Stage dialogStage = (Stage) ((Node) event1.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/pages/shipping/shipping.fxml"));
        Parent root = loader.load();

        UserShippingController shippingController = loader.getController();
        shippingController.setOrdersData(tableMedias.getItems());

        shippingController.setTotalPrice(price);;

			User user;
			try {
				user = Datasource.getInstance().getUserByID(user_id);
				shippingController.setData(user.getFullname(), user.getPhone(), user.getAddress(), " ", user.getCity(), tableMedias.getItems(), "Normal");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			


        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
    
    
    private double calculateTotalPrice(ObservableList<CartMedia> medias) {
        double totalPrice = 0;
        for (CartMedia media : medias) {
            totalPrice += media.getPrice();
        }
        return totalPrice;
    }
    private void addActionButtonsToTable() {
        TableColumn colBtnDel = new TableColumn("Actions");

        Callback<TableColumn<CartMedia, Void>, TableCell<CartMedia, Void>> cellFactory = new Callback<TableColumn<CartMedia, Void>, TableCell<CartMedia, Void>>() {
            @Override
            public TableCell<CartMedia, Void> call(final TableColumn<CartMedia, Void> param) {
                return new TableCell<CartMedia, Void>() {

                    private final Button DelButton = new Button("Delete");

                    {
                    	DelButton.getStyleClass().add("button");
                    	DelButton.getStyleClass().add("xs");
                    	DelButton.getStyleClass().add("danger");
                        DelButton.setOnAction((ActionEvent event) -> {
                        	CartMedia mediaData = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Are you sure that you want to delete " + mediaData.getMedia_name() + " ?");
                            alert.setTitle("Delete " + mediaData.getMedia_name() + " ?");
                            Optional<ButtonType> deleteConfirmation = alert.showAndWait();

                            if (deleteConfirmation.get() == ButtonType.OK) {                    
                                if (Datasource.getInstance().deleteCartMedia(mediaData.getId())) {
                                	// Datasource.getInstance().increaseStock(mediaData.getMedia_id(), mediaData.getQuantity());
                                    refresh();
                                }
                            }
                        });
                    }

                    private final HBox buttonsPane = new HBox();

                    {
                        buttonsPane.setSpacing(10);
                        buttonsPane.getChildren().add(DelButton);
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

        colBtnDel.setCellFactory(cellFactory);

        tableMedias.getColumns().add(colBtnDel);

    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        // TODO
        //  Add orders search functionality.
        System.out.println("TODO: Add orders search functionality.");
    }
}
