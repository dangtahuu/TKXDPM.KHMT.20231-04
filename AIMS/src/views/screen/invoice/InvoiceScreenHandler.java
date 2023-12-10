package views.screen.invoice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import common.exception.ProcessInvoiceException;
import controller.PaymentController;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.payment.PaymentScreenHandler;

public class InvoiceScreenHandler extends BaseScreenHandler {

    private static Logger LOGGER = Utils.getLogger(InvoiceScreenHandler.class.getName());

    // FXML Controls coupling
	//Đây là mối quan hệ giữa các thành phần UI được định nghĩa trong file FXML và các biến được chú thích bằng @FXML trong mã điều khiển.
    @FXML
    private Label pageTitle;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label province;

    @FXML
    private Label address;

    @FXML
    private Label instructions;

    @FXML
    private Label subtotal;

    @FXML
    private Label shippingFees;

    @FXML
    private Label total;

    @FXML
    private VBox vboxItems;

    private Invoice invoice; // Coupling between InvoiceScreenHandler and Invoice entity

    // Constructor coupling
	//Mối quan hệ giữa lớp InvoiceScreenHandler và lớp Invoice thông qua việc truyền Invoice vào constructor.
    public InvoiceScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;
        setInvoiceInfo();
    }

    // Method coupling
	//Sự phụ thuộc giữa các phương thức trong InvoiceScreenHandler và các thuộc tính/phương thức của đối tượng Invoice và Order.
    private void setInvoiceInfo() {
        HashMap<String, String> deliveryInfo = invoice.getOrder().getDeliveryInfo(); // Coupling between Invoice and Order
        name.setText(deliveryInfo.get("name"));
        province.setText(deliveryInfo.get("province"));
        instructions.setText(deliveryInfo.get("instructions"));
        address.setText(deliveryInfo.get("address"));
        subtotal.setText(Utils.getCurrencyFormat(invoice.getOrder().getAmount())); // Coupling between Invoice and Order
        shippingFees.setText(Utils.getCurrencyFormat(invoice.getOrder().getShippingFees())); // Coupling between Invoice and Order
        int amount = invoice.getOrder().getAmount() + invoice.getOrder().getShippingFees(); // Coupling between Invoice and Order
        total.setText(Utils.getCurrencyFormat(amount));
        invoice.setAmount(amount);
        invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {
            try {
                MediaInvoiceScreenHandler mis = new MediaInvoiceScreenHandler(Configs.INVOICE_MEDIA_SCREEN_PATH);
                mis.setOrderMedia((OrderMedia) orderMedia);
                vboxItems.getChildren().add(mis.getContent());
            } catch (IOException | SQLException e) {
                System.err.println("errors: " + e.getMessage());
                throw new ProcessInvoiceException(e.getMessage());
            }

        });
    }

    // Event coupling
	//Mối quan hệ giữa sự kiện (ví dụ: confirmInvoice) và các hành động thực hiện khi sự kiện đó xảy ra.
    @FXML
    void confirmInvoice(MouseEvent event) throws IOException {
        BaseScreenHandler paymentScreen = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, invoice);
        paymentScreen.setBController(new PaymentController());
        paymentScreen.setPreviousScreen(this);
        paymentScreen.setHomeScreenHandler(homeScreenHandler);
        paymentScreen.setScreenTitle("Payment Screen");
        paymentScreen.show();
        LOGGER.info("Confirmed invoice"); 
		// Coupling with logging
		// Sự phụ thuộc vào việc sử dụng Logger để ghi log trong mã.
    }

}
