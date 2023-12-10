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
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */

     /*
      * Phương thức này gọi phương thức checkAvailabilityOfProduct của Cart để kiểm tra tính sẵn có của sản phẩm.
        Phương thức này trực tiếp phụ thuộc vào cấu trúc dữ liệu của lớp Cart.
      */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct(); 
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */

     /*
      *  Phương thức này tạo một đối tượng Order dựa trên thông tin trong Cart.
        Phương thức này trực tiếp truy cập vào các thuộc tính và phương thức của Cart và CartMedia.
      */
    public Order createOrder() throws SQLException{
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

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */

     /*
      * Phương thức này tạo một đối tượng Invoice dựa trên thông tin trong Order.
        Phương thức này trực tiếp truy cập vào các thuộc tính và phương thức của Order.
      */
    public Invoice createInvoice(Order order) {
        return new Invoice(order); 
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */

     /*
      * Phương thức này xử lý thông tin vận chuyển từ người dùng và gọi phương thức validateDeliveryInfo.
        Phương thức này trực tiếp phụ thuộc vào cấu trúc dữ liệu của LOGGER và gọi phương thức validateDeliveryInfo.
      */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */

   /*
    * Các phương thức này chịu trách nhiệm kiểm tra thông tin vận chuyển, như số điện thoại, tên, địa chỉ, nhưng hiện tại chưa có nội dung cụ thể.
        Các phương thức này có thể cần được triển khai và sẽ có mức độ nối kết dữ liệu với các dữ liệu cụ thể cần kiểm tra.
    */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
    	// TODO: your work
    	return false;
    }
    
    public boolean validateName(String name) {
    	// TODO: your work
    	return false;
    }
    
    public boolean validateAddress(String address) {
    	// TODO: your work
    	return false;
    }
    

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */

     /*
      * Phương thức này tính phí vận chuyển dựa trên giá trị đơn hàng.
        Phương thức này trực tiếp truy cập vào thuộc tính và phương thức của Order.
      */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
