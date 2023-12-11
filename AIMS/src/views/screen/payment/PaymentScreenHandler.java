package views.screen.payment;

import java.io.IOException;
import java.util.Map;

import controller.PaymentController;
import entity.cart.Cart;
import common.exception.PlaceOrderException;
import entity.invoice.Invoice;

/*
 * Common Coupling:
    Trong class PaymentScreenHandler, có sử dụng Configs từ package utils để lấy đường dẫn của màn hình kết quả.
    Sử dụng Platform từ package javafx.application để gọi phương thức runLater để thực hiện một nhiệm vụ trên luồng giao diện người dùng.
 */
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

public class PaymentScreenHandler extends BaseScreenHandler {

	@FXML
	private Button btnConfirmPayment;

	@FXML
	private ImageView loadingImage;

	private Invoice invoice;

	public PaymentScreenHandler(Stage stage, String screenPath, int amount, String contents) throws IOException {
		super(stage, screenPath);
	}

	/*
	 * Stamp Coupling:
    Class PaymentScreenHandler sử dụng một đối tượng Invoice để lưu thông tin đơn hàng.
	 */
	public PaymentScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		
		/*
		 * Control Coupling:
    Sự kiện nhấn nút btnConfirmPayment kích hoạt phương thức confirmToPayOrder, giữa màn hình thanh toán và controller PaymentController.
		 */
		btnConfirmPayment.setOnMouseClicked(e -> {
			try {
				confirmToPayOrder();
				((PaymentController) getBController()).emptyCart();
			} catch (Exception exp) {
				System.out.println(exp.getStackTrace());
			}
		});
	}

	@FXML
	private Label pageTitle;

	@FXML
	private TextField cardNumber;

	@FXML
	private TextField holderName;

	@FXML
	private TextField expirationDate;

	@FXML
	private TextField securityCode;

	/*
	 * Content Coupling:
    Các phương thức như confirmToPayOrder sử dụng trực tiếp dữ liệu từ các trường như 
	invoice, cardNumber, holderName, expirationDate, và securityCode.
	 */

	 /*
	  * Data Coupling:
    Phương thức confirmToPayOrder sử dụng dữ liệu trực tiếp từ các trường (invoice, cardNumber, holderName, expirationDate, và securityCode).
    Class PaymentController được sử dụng để xử lý thanh toán và chứa logic xử lý đặt hàng.
	  */

	  /*
	   *   
    Sequential Cohesion :
        Các phương thức trong class được gọi theo một thứ tự nhất định để thực hiện quy trình thanh toán.
		Ví dụ: confirmToPayOrder sau đó gọi payOrder và sau đó tạo và hiển thị màn hình kết quả.

    Communicational Cohesion:
        Các thành viên của class (các trường và phương thức) tương tác chủ yếu để thực hiện chức năng thanh toán và hiển thị kết quả.
    
	   */
	void confirmToPayOrder() throws IOException{
		String contents = "pay order";
		PaymentController ctrl = (PaymentController) getBController();
		Map<String, String> response = ctrl.payOrder(invoice.getAmount(), contents, cardNumber.getText(), holderName.getText(),
				expirationDate.getText(), securityCode.getText());

		BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH, response.get("RESULT"), response.get("MESSAGE") );
		resultScreen.setPreviousScreen(this);
		resultScreen.setHomeScreenHandler(homeScreenHandler);
		resultScreen.setScreenTitle("Result Screen");
		resultScreen.show();
	}

}