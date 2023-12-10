package entity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Configs;

public class Order {
    
    private int shippingFees;

    /*
     * Thuộc tính lstOrderMedia:
        Lớp này chứa một danh sách lstOrderMedia đại diện cho các mục trong đơn đặt hàng. Tuy nhiên, kiểu của danh sách này không được chỉ định (sử dụng raw type). 
        Nên sử dụng generics để chỉ định kiểu của danh sách (ví dụ: List<OrderMedia>).
     */
    private List lstOrderMedia;
    private HashMap<String, String> deliveryInfo;

    public Order(){
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    /*
     *  Phương thức addOrderMedia và removeOrderMedia:
        Được sử dụng để thêm và xóa OrderMedia khỏi danh sách lstOrderMedia.
     */
    public void addOrderMedia(OrderMedia om){
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om){
        this.lstOrderMedia.remove(om);
    }

    public List getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    /*
     * Phương thức setDeliveryInfo và getDeliveryInfo:
        Lớp này sử dụng một HashMap để lưu trữ thông tin giao hàng. Tuy nhiên, kiểu của HashMap cũng không được chỉ định (sử dụng raw type). Nên sử dụng generics để chỉ định kiểu của HashMap (ví dụ: HashMap<String, String>).
     */
    public HashMap getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HashMap deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    /*
     * Phương thức getAmount:
        Phương thức này tính toán và trả về tổng số tiền của đơn đặt hàng, bao gồm cả phí VAT. Tuy nhiên, nó không trả về một giá trị kiểu double, nhưng thay vào đó, nó ép kiểu về int. 
        Điều này có thể dẫn đến mất mát chính xác nếu phép toán chia không chia hết. Nên xem xét việc sử dụng kiểu dữ liệu thích hợp.
     */
    public int getAmount(){
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) (amount + (Configs.PERCENT_VAT/100)*amount); 
    }

}
