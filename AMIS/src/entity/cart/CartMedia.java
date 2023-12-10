package entity.cart;

import entity.media.Media;

public class CartMedia {
    
    /*
     * Thuộc tính media:
        Thuộc tính này là một đối tượng của lớp Media, có nghĩa là có một mức độ nối kết dữ liệu với lớp Media.
        Phương thức getMedia và setMedia trực tiếp truy cập và thiết lập giá trị cho thuộc tính media.

      Thuộc tính quantity và price:
        Cả hai thuộc tính này là kiểu dữ liệu cơ bản (int), không tạo ra sự nối kết mạnh với các lớp khác.
        Cung cấp các phương thức get và set cho cả hai thuộc tính.
     */
    private Media media;
    private int quantity;
    private int price;

    public CartMedia(){

    }

    public CartMedia(Media media, Cart cart, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }
    
    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /*
     *  Phương thức toString:
        Phương thức này sử dụng media và quantity để tạo một chuỗi biểu diễn của đối tượng CartMedia.
        Phương thức này trực tiếp sử dụng media, có thể tạo ra sự nối kết dữ liệu.
     */
    @Override
    public String toString() {
        return "{" 
            + " media='" + media + "'" 
            + ", quantity='" + quantity + "'" 
            + "}";
    }

}

    
