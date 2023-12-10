package entity.cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.exception.MediaNotAvailableException;
import entity.media.Media;

public class Cart {
    
    private List<CartMedia> lstCartMedia;
    private static Cart cartInstance;

    public static Cart getCart(){
        if(cartInstance == null) cartInstance = new Cart();
        return cartInstance;
    }

    private Cart(){
        lstCartMedia = new ArrayList<>();
    }

    /*
     * Phương thức addCartMedia và removeCartMedia:
        Phương thức này nhận một đối tượng CartMedia và thêm hoặc loại bỏ nó khỏi danh sách lstCartMedia.
        Phương thức này tương tác trực tiếp với CartMedia, có mức độ nối kết dữ liệu.
     */
    public void addCartMedia(CartMedia cm){
        lstCartMedia.add(cm);
    }

    public void removeCartMedia(CartMedia cm){
        lstCartMedia.remove(cm);
    }

    /*
     * Phương thức getListMedia:
        Phương thức này trả về danh sách lstCartMedia.
        Phương thức này không trực tiếp tương tác với dữ liệu chi tiết của CartMedia, 
        nhưng nó trả về danh sách, có thể dẫn đến sự nối kết dữ liệu khi ai đó có thể thay đổi danh sách này.
     */
    public List getListMedia(){
        return lstCartMedia;
    }

    /*
     * Phương thức emptyCart:
        Phương thức này xóa toàn bộ danh sách lstCartMedia.
        Phương thức này không trực tiếp tương tác với dữ liệu chi tiết của CartMedia, 
        nhưng nó xóa toàn bộ danh sách, có thể dẫn đến sự nối kết dữ liệu khi ai đó có thể thay đổi cách danh sách được quản lý.
     */
    public void emptyCart(){
        lstCartMedia.clear();
    }

    /*
     * Phương thức getTotalMedia:
        Phương thức này tính tổng số lượng các sản phẩm trong giỏ hàng.
        Phương thức này tương tác trực tiếp với CartMedia, có mức độ nối kết dữ liệu.
     */
    public int getTotalMedia(){
        int total = 0;
        for (Object obj : lstCartMedia) {
            CartMedia cm = (CartMedia) obj;
            total += cm.getQuantity();
        }
        return total;
    }

    /*
     * Phương thức calSubtotal:
        Phương thức này tính tổng số tiền cho các sản phẩm trong giỏ hàng.
        Phương thức này tương tác trực tiếp với CartMedia, có mức độ nối kết dữ liệu.
     */
    public int calSubtotal(){
        int total = 0;
        for (Object obj : lstCartMedia) {
            CartMedia cm = (CartMedia) obj;
            total += cm.getPrice()*cm.getQuantity();
        }
        return total;
    }

    /*
     * Phương thức checkAvailabilityOfProduct:
        Phương thức này kiểm tra tính sẵn có của sản phẩm trong giỏ hàng.
        Phương thức này tương tác trực tiếp với CartMedia và Media, có mức độ nối kết dữ liệu.
     */
    public void checkAvailabilityOfProduct() throws SQLException{
        boolean allAvai = true;
        for (Object object : lstCartMedia) {
            CartMedia cartMedia = (CartMedia) object;
            int requiredQuantity = cartMedia.getQuantity(); 
            int availQuantity = cartMedia.getMedia().getQuantity();
            if (requiredQuantity > availQuantity) allAvai = false;
        }
        if (!allAvai) throw new MediaNotAvailableException("Some media not available");
    }

    /*
     * Phương thức checkMediaInCart:
        Phương thức này kiểm tra xem một sản phẩm có trong giỏ hàng không.
        Phương thức này tương tác trực tiếp với CartMedia và Media, có mức độ nối kết dữ liệu.
     */
    public CartMedia checkMediaInCart(Media media){
        for (CartMedia cartMedia : lstCartMedia) {
            if (cartMedia.getMedia().getId() == media.getId()) return cartMedia; 
        }
        return null;
    }

}
