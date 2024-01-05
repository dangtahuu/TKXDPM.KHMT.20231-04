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
		    "Báº¯c Giang", "Báº¯c Cáº¡n", "Cao Báº±ng", "HÃ  Giang", "Láº¡ng SÆ¡n", "PhÃº Thá»�",
		    "Quáº£ng Ninh", "ThÃ¡i NguyÃªn", "TuyÃªn Quang", "YÃªn BÃ¡i", "Ä�iá»‡n BiÃªn", "HÃ²a BÃ¬nh", "Lai ChÃ¢u", "SÆ¡n La",
		    "Báº¯c Ninh", "HÃ  Nam", "Háº£i DÆ°Æ¡ng", "HÆ°ng YÃªn", "Nam Ä�á»‹nh", "Ninh BÃ¬nh", "ThÃ¡i BÃ¬nh", "VÄ©nh PhÃºc", "HÃ  Ná»™i",
		    "Háº£i PhÃ²ng", "HÃ  TÄ©nh", "Nghá»‡ An", "Quáº£ng BÃ¬nh", "Quáº£ng Trá»‹", "Thanh HÃ³a", "Thá»«a ThiÃªn-Huáº¿", "Ä�áº¯k Láº¯k",
		    "Ä�áº¯k NÃ´ng", "Gia Lai", "Kon Tum", "LÃ¢m Ä�á»“ng", "BÃ¬nh Ä�á»‹nh", "BÃ¬nh Thuáº­n", "KhÃ¡nh HÃ²a", "Ninh Thuáº­n",
		    "PhÃº YÃªn", "Quáº£ng Nam", "Quáº£ng NgÃ£i", "Ä�Ã  Náºµng", "BÃ  Rá»‹a-VÅ©ng TÃ u", "BÃ¬nh DÆ°Æ¡ng", "BÃ¬nh PhÆ°á»›c", "Ä�á»“ng Nai",
		    "TÃ¢y Ninh", "Há»“ ChÃ­ Minh", "An Giang", "Báº¡c LiÃªu", "Báº¿n Tre", "CÃ  Mau", "Ä�á»“ng ThÃ¡p", "Háº­u Giang",
		    "KiÃªn Giang", "Long An", "SÃ³c TrÄƒng", "Tiá»�n Giang", "TrÃ  Vinh", "VÄ©nh Long", "Cáº§n ThÆ¡"
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
			        			if (ship_type.equals("Rush") && !ship_province.equals("HÃ  Ná»™i")) {
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
