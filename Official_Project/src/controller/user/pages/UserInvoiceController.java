package controller.user.pages;

import java.io.IOException;

import controller.UserSessionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Datasource;
import subsystem.Interbank;
import model.CartMedia;
import javafx.scene.Parent;
import javafx.scene.Node;

public class UserInvoiceController {
	@FXML
	public TableView tableOrdersPage;
	@FXML
    private Label ivname;
    @FXML
    private Label ivphone;
    @FXML
    private Label ivaddress;
    @FXML
    private Label ivinstructions;
    @FXML
    private Label ivprovince; 
    @FXML
    private Label order_type; 
    @FXML
    private Label subtotal;
    @FXML
    private Label shippingFees;
    @FXML
    private Label total;
    @FXML
    private Label vat;
    @FXML
	private ObservableList<CartMedia> ordersData;
    public double price;
    public double totalAll;
	@FXML
	public void handleBackButtonClick(ActionEvent event1) throws IOException {
		Stage dialogStage = (Stage) ((Node) event1.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/pages/shipping/shipping.fxml"));
        Parent root = loader.load();

        UserShippingController shippingController = loader.getController();
        shippingController.setOrdersData(tableOrdersPage.getItems());

        shippingController.setTotalPrice(price);;
        shippingController.setData(ivname.getText(), ivphone.getText(), ivaddress.getText(), ivinstructions.getText(), ivprovince.getText(), ordersData,order_type.getText());

        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
	 @FXML
	    public void listOrders() {
	            tableOrdersPage.setItems(ordersData); 
	            double totalPrice = calculateTotalPrice(ordersData);
	            price = totalPrice;
	            


	    }
	 private double getShippingFee(String type, String province, int quantity) {
		 if(type.equals("Normal")) {
			 if(province.equals("Hà Nội")||province.equals("Hồ Chí Minh")) {
				 return 22000+(quantity>5?(quantity-5)*500:0);
			 }
			 else return 30000 + (quantity>5?(quantity-5)*500:0);
		 }
		 else {
				 return 32000+(quantity>5?(quantity-5)*500:0);
		 }
	 }
	 private double calculateTotalPrice(ObservableList<CartMedia> orders) {
	        double totalPrice = 0.0;
	        for (CartMedia order : orders) {
	            totalPrice += order.getPrice();
	        }
	        return totalPrice;
	    }
	 private int calculateTotalQuantity(ObservableList<CartMedia> orders) {
	        int totalQuantity=0;
	        for (CartMedia order : orders) {
	        	totalQuantity += order.getQuantity();
	        }
	        return totalQuantity;
	    }
	
	@FXML
	public void setData(String name, String phone, String address, String instructions, String province, double price, ObservableList<CartMedia> ordersData,String ship_type) {
		ivname.setText(name);
		ivphone.setText(phone);
		ivaddress.setText(address);
		ivinstructions.setText(instructions);
		ivprovince.setText( province);
		order_type.setText(ship_type);
		this.ordersData = ordersData;
		subtotal.setText(String.format("%.2f VND",price));
		vat.setText(String.format("%.3f VND",price*0.1));
		shippingFees.setText(String.format("%.2f VND",getShippingFee(ship_type, province, calculateTotalQuantity( ordersData))));
		totalAll = price*1.1+getShippingFee(ship_type, province, calculateTotalQuantity( ordersData));
		total.setText(String.format("%.3f VND", totalAll));
    }
	
	@FXML
	public void handleConfirmOrder() throws IOException {
		Interbank interbank = new Interbank();
		interbank.openPay(totalAll);
	}

}
