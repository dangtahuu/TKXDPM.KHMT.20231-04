package controller.user.pages;

import java.io.IOException;
import java.sql.SQLException;

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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.media.Media;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import model.User;
import model.Datasource;
import model.CartMedia;
public class UserShippingController {
	@FXML
	public TableView tableOrdersPage;
	@FXML
    public TextField spname;
	@FXML
    public TextField spphone;
	@FXML
    public TextField spaddress;
	@FXML
    public TextField spinstructions;
	@FXML
    public ComboBox<String> spprovinceComboBox;
	@FXML
    public ComboBox<String> type_order;
	@FXML
	double totalPrice = 0;
	@FXML
	private ObservableList<CartMedia> ordersData;
	@FXML
	public void setTotalPrice(double price) {
		this.totalPrice = price;
	}
	public static String[] PROVINCES = {
			"Bắc Giang", "Bắc Kạn", "Cao Bằng", "Hà Giang", "Lạng Sơn", "Phú Thọ",
			"Quảng Ninh", "Thái Nguyên", "Tuyên Quang", "Yên Bái", "Điện Biên", "Hòa Bình", "Lai Châu", "Sơn La",
			"Bắc Ninh", "Hà Nam", "Hải Dương", "Hưng Yên", "Nam Định", "Ninh Bình", "Thái Bình", "Vĩnh Phúc", "Hà Nội",
			"Hải Phòng", "Hà Tĩnh", "Nghệ An", "Quảng Bình", "Quảng Trị", "Thanh Hóa", "Thừa Thiên-Huế", "Đắk Lắk",
			"Đắk Nông", "Gia Lai", "Kon Tum", "Lâm Đồng", "Bình Định", "Bình Thuận", "Khánh Hòa", "Ninh Thuận",
			"Phú Yên", "Quảng Nam", "Quảng Ngãi", "Đà Nẵng", "Bà Rịa-Vũng Tàu", "Bình Dương", "Bình Phước", "Đồng Nai",
			"Tây Ninh", "Hồ Chí Minh", "An Giang", "Bạc Liêu", "Bến Tre", "Cà Mau", "Đồng Tháp", "Hậu Giang",
			"Kiên Giang", "Long An", "Sóc Trăng", "Tiền Giang", "Trà Vinh", "Vĩnh Long", "Cần Thơ"

		};
	
	@FXML
	public void handleBackButtonClick(ActionEvent event) throws IOException {
    	Stage dialogStage;
        Node node = (Node) event.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/user/main-dashboard.fxml")));
        dialogStage.setScene(scene);
        dialogStage.show();
    }
	@FXML
	public void setOrdersData(ObservableList<CartMedia> ordersData) {
        this.ordersData = ordersData;
        tableOrdersPage.setItems(ordersData); 
        type_order.setItems(FXCollections.observableArrayList("Rush", "Normal"));
        spprovinceComboBox.setItems(FXCollections.observableArrayList(PROVINCES));

        spprovinceComboBox.setOnAction(event -> {

            String selectedProvince = spprovinceComboBox.getSelectionModel().getSelectedItem();
        });
    }
	@FXML
	public void handleDeliveryButtonClick(ActionEvent event) throws IOException, SQLException {
			String ship_name = spname.getText();
			String ship_phone = spphone.getText();
			String ship_address = spaddress.getText();
			String ship_instructions = spinstructions.getText();
			String ship_province = spprovinceComboBox.getValue();
			String ship_type = type_order.getValue();
			if (ship_name.isEmpty() || !ship_name.matches("[\\p{L} ]+")) {
	            System.out.println("TÃªn khÃ´ng há»£p lá»‡");
	            HelperMethods.alertBox("Please fill in a valid name!", null, "Invalid!");
	        }
			else {
				if (ship_phone.isEmpty() || !ship_phone.matches("[0-9]+") || (ship_phone.length() != 10 && ship_phone.length() != 11 && ship_phone.length() != 9)) {
		            System.out.println("Sá»‘ Ä‘iá»‡n thoáº¡i khÃ´ng há»£p lá»‡");
		            HelperMethods.alertBox("Please fill in a valid phone!", null, "Invalid!");
		        }
				else {
			        if (ship_address.isEmpty() || !ship_address.matches("[\\p{L}0-9., ]+")) {
			            System.out.println("Ä�á»‹a chá»‰ khÃ´ng há»£p lá»‡");
			            HelperMethods.alertBox("Please fill in a valid address!", null, "Invalid!");
			        }
			        else {
			        	if (ship_province == null || ship_province.isEmpty()) {
			                System.out.println("Vui lÃ²ng chá»�n tá»‰nh/thÃ nh phá»‘");
			                HelperMethods.alertBox("Please choose a province!", null, "Invalid!");
			        }
			        	else {
			        		if (ship_type == null || ship_type.isEmpty()) {
			        			System.out.println("Vui lÃ²ng chá»�n tá»‰nh/thÃ nh phá»‘");
				                HelperMethods.alertBox("Please choose order type!", null, "Invalid!");
			        		}
			        		else {
			        			if (ship_type.equals("Rush") && !ship_province.equals("Hà Nội")) {
				        			System.out.println("Vui lÃ²ng chá»�n tá»‰nh/thÃ nh phá»‘");
					                HelperMethods.alertBox("You can only choose rush order when the address is in Hanoi", null, "Invalid!");
				        		}
			        			else {
			        				if (ship_type.equals("Rush") && !checkRush( ordersData)) {
					        			System.out.println("Vui lÃ²ng chá»�n tá»‰nh/thÃ nh phá»‘");
						                HelperMethods.alertBox("There are some medias that do not support express delivery", null, "Invalid!");
					        		}
			        				else {
			        					if (checkQuantity(ordersData)>0) {
						        			System.out.println("Vui lÃ²ng chá»�n tá»‰nh/thÃ nh phá»‘");
							                HelperMethods.alertBox(Datasource.getInstance().getMediaByID(checkQuantity(ordersData)).getName()+" is not enough in stock!!", null, "Invalid!");
						        		}
			        					else {

					        				Stage dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
							    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/pages/invoice/invoice.fxml"));
							    	        Parent root = loader.load();
							    	        int user_id = UserSessionController.getUserId();
							    	        UserInvoiceController invoiceController = loader.getController();
							    	        Datasource.getInstance().updateOneUser(ship_name,ship_address, ship_phone, ship_province, user_id);
							    	        invoiceController.setData(ship_name, ship_phone,ship_address, ship_instructions,ship_province, totalPrice, ordersData, ship_type);
							    	        invoiceController.listOrders();
							    	        Scene scene = new Scene(root);
							    	        dialogStage.setScene(scene);
							    	        dialogStage.show();
			        					}
				        				
				        			}
			        			}
			        			
			        			
			        		}
			        		
			        	}
				}
			}
			
			
		}
    }
	@FXML
	public void setData(String name, String phone, String address, String instructions, String province,  ObservableList<CartMedia> ordersData, String order_type) {
		spname.setText(name);
		spphone.setText(phone);
		spaddress.setText(address);
		spinstructions.setText(instructions);
		spprovinceComboBox.setValue( province);
		type_order.setValue(order_type);
		this.ordersData = ordersData;
		
    }
	
	public boolean checkRush(ObservableList<CartMedia> ordersData) {
		for (CartMedia cartmedia : ordersData) {
            if(!cartmedia.getRushSupport()) return false;
        }
		return true;
	}
	public int checkQuantity(ObservableList<CartMedia> ordersData) throws SQLException {
		for (CartMedia cartmedia : ordersData) {
            if(cartmedia.getQuantity()>Datasource.getInstance().getMediaByID(cartmedia.getMedia_id()).getQuantity()) return Datasource.getInstance().getMediaByID(cartmedia.getMedia_id()).getId();
        }
		return 0;
	}


}
