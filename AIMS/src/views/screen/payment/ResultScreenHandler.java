package views.screen.payment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;

public class ResultScreenHandler extends BaseScreenHandler {

	private String result;
	private String message;

	/*
	 * Common Coupling 
    Trong class ResultScreenHandler, có thể có kết nối thông thường với các tài nguyên (resource) chung khi sử dụng URL và ResourceBundle để khởi tạo controller
	Content Coupling 
    Class ResultScreenHandler nhận dữ liệu result và message từ constructor và sử dụng chúng để cập nhật nội dung của các thành phần UI như resultLabel và messageLabel. 
	Data Coupling 
    Class ResultScreenHandler nhận dữ liệu result và message từ constructor và sử dụng chúng để cập nhật nội dung UI.
	*/
	public ResultScreenHandler(Stage stage, String screenPath, String result, String message) throws IOException {
		super(stage, screenPath);
		resultLabel.setText(result);
		messageLabel.setText(message);
	}

	@FXML
	private Label pageTitle;

	@FXML
	private Label resultLabel;

	@FXML
	private Button okButton;
	
	@FXML
	private Label messageLabel;

	@FXML

	/*
	 * Control Coupling 
    Phương thức confirmPayment có kết nối kiểm soát với homeScreenHandler khi gọi show() để hiển thị màn hình chính.
	 */
	void confirmPayment(MouseEvent event) throws IOException {
		homeScreenHandler.show();
	}

}
