package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController {

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the availability of products when the user clicks the Place Order button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }
    // Method coupling: Coupling with Cart for checking product availability

    /**
     * This method creates a new Order based on the Cart contents
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException {
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                                                   cartMedia.getQuantity(),
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }
    // Method coupling: Coupling with Cart for accessing cart content
    // Các phương thức trong PlaceOrderController phụ thuộc vào các phương thức trong lớp Cart để kiểm tra sự có sẵn của sản phẩm và lấy danh sách sản phẩm trong giỏ hàng.

    /**
     * This method creates a new Invoice based on an order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }
    // Method coupling: Coupling with Invoice for creating an invoice
    // Lớp PlaceOrderController tạo các đối tượng Order và Invoice dựa trên dữ liệu từ giỏ hàng (Cart).

    /**
     * This method processes the shipping info provided by the user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    // Method coupling: Coupling with Logger for logging information
    // Sử dụng Logger để ghi log thông tin về quá trình xử lý và tính toán phí vận chuyển.

    /**
     * The method validates the provided delivery information
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
        // No specific implementation provided, placeholders for validation methods
    }
    // Method coupling: Placeholder for validation methods

    public boolean validatePhoneNumber(String phoneNumber) {
        // Placeholder method for phone number validation
        return false;
    }
    // Method coupling: Placeholder for phone number validation

    public boolean validateName(String name) {
        // Placeholder method for name validation
        return false;
    }
    // Method coupling: Placeholder for name validation

    public boolean validateAddress(String address) {
        // Placeholder method for address validation
        return false;
    }
    // Method coupling: Placeholder for address validation

    /**
     * This method calculates the shipping fees for an order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order) {
        Random rand = new Random();
        int fees = (int)(((rand.nextFloat() * 10) / 100) * order.getAmount());
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
    // Method coupling: Coupling with Logger for logging information
}
