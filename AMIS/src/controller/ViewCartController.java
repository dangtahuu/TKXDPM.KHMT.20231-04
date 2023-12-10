package controller;

import java.sql.SQLException;
import java.util.List;

import entity.media.Media;
import entity.cart.Cart;
import entity.cart.CartMedia;

/**
 * This class controls the flow of events when users view the Cart
 * @author nguyenlm
 */
public class ViewCartController extends BaseController{
    
    /**
     * This method checks the available products in Cart
     * @throws SQLException
     */

     /*
      * Phương thức này gọi phương thức checkAvailabilityOfProduct của Cart để kiểm tra sự sẵn có của sản phẩm trong giỏ hàng.
        Phương thức này không trực tiếp truy cập hoặc phụ thuộc vào cấu trúc dữ liệu của các lớp khác, 
        nó chỉ gọi một phương thức của Cart, không làm nổi bật sự phụ thuộc vào cấu trúc dữ liệu cụ thể.
      */
    public void checkAvailabilityOfProduct() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct(); 
    }

    /**
     * This method calculates the cart subtotal
     * @return subtotal
     */

     /*
      * Phương thức này gọi phương thức calSubtotal của Cart để tính toán tổng giá trị của giỏ hàng.
        Phương thức này không trực tiếp truy cập hoặc phụ thuộc vào cấu trúc dữ liệu của các lớp khác, chỉ gọi một phương thức của Cart
      */
    public int getCartSubtotal(){
        int subtotal = Cart.getCart().calSubtotal(); 
        return subtotal;
    }

}
