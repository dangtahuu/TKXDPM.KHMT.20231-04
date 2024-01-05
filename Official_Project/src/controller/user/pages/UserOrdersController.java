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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
                        	
                            Task<ObservableList<CartMedia>> getAllOrderMedias = new Task<ObservableList<CartMedia>>() {
                                @Override
                                protected ObservableList<CartMedia> call() {
                                	
                                    return FXCollections.observableArrayList(Datasource.getInstance().getOrderCartMedias(Datasource.ORDER_BY_NONE, orderData.getId() ));
                                    
                                }
                            };
                            getAllOrderMedias.setOnSucceeded(event1 -> {
                            	System.out.print("1245");
                            	ObservableList<CartMedia> listOrderMedias = getAllOrderMedias.getValue();                                                            
                                double totalPrice = calculateTotalPrice(listOrderMedias);
                                Stage dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/pages/orders/orderview.fxml"));
				    	        Parent root;
								try {
									root = loader.load();

				    	        int user_id = UserSessionController.getUserId();
				    	        int order_id = orderData.getId();
				    	        UserInvoiceController invoiceController = loader.getController();				    	        
				    	        invoiceController.setData(UserSessionController.getUserFullName(), Datasource.getInstance().getPhonebyOrderId(order_id),Datasource.getInstance().getAddressByOrderId(order_id), Datasource.getInstance().getInstructionsByOrderId(order_id),Datasource.getInstance().getCitybyOrderId(order_id), calculateTotalPrice( listOrderMedias),listOrderMedias, orderData.getType());
				    	        invoiceController.listOrders();
				    	        Scene scene = new Scene(root);
				    	        dialogStage.setScene(scene);
				    	        dialogStage.show();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									System.out.print("1245");
									e.printStackTrace();
								}
                            });
                            new Thread(getAllOrderMedias).start();
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
    private double calculateTotalPrice(ObservableList<CartMedia> listOrderMedias) {
        double totalPrice = 0.0;
        if(listOrderMedias == null) {return 0;}
        else {
        for (CartMedia media : listOrderMedias) {
            totalPrice += media.getPrice();
        }
        }
        return totalPrice;
    }
    

    
 

}