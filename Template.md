# TKXDPM.VN.20231-04

This is a Capstone's source code for Software Design and Construction project

## Team member

| Name           | Role        |
| :------------- | :---------- |
| Tạ Hữu Đăng    | Team Leader |
| Trịnh Quốc Đạt | Member      |
| Ngô Hoàng Hải Đăng  | Member      |
| Trần Văn Điền  | Member      |

## Report Content

<>
  <summary>27/11/2023~03/12/2023 </summary>
<br>
<details>
<summary>Trần Văn Điền</summary>
<br>

- Assigned tasks:
  - Chỉ ra các data coupling 


- Implementation details:
  - Pull Request(s): https://github.com/dangtahuu/TKXDPM.KHMT.20231-04/tree/dien/
  - Specific implementation details:

  - BaseController.java: BaseController phụ thuộc dữ liệu vào các lớp Media và Cart qua 2 class checkMediaInCart, getListCartMedia

  - HomeController: HomeController phụ thuộc dữ liệu vào các lớp Media qua class getAllMedia

  - PaymentCOntroller: 
      Phương thức getExpirationDate:
        Phương thức này nhận một chuỗi date và trả về một chuỗi biểu diễn ngày hết hạn theo định dạng yêu cầu.
        Phương thức này trực tiếp phụ thuộc vào cấu trúc dữ liệu của lớp CreditCard và thực hiện kiểm tra định dạng của ngày hết hạn.

      Phương thức payOrder:
        Phương thức này thực hiện thanh toán và trả về kết quả dưới dạng một Map.
        Phương thức này tạo một đối tượng CreditCard và thực hiện thanh toán thông qua InterbankSubsystem.
        Phương thức này trực tiếp phụ thuộc vào cấu trúc dữ liệu của lớp CreditCard, InterbankSubsystem, PaymentTransaction, và Cart.

      Thuộc tính card và interbank:
        Hai thuộc tính này đại diện cho thẻ tín dụng và hệ thống ngân hàng, tạo ra một mức độ nối kết dữ liệu với các lớp liên quan.

  - PlaceOrderController: 
      Phương thức placeOrder:
        Phương thức này gọi phương thức checkAvailabilityOfProduct của Cart để kiểm tra tính sẵn có của sản phẩm.
        Phương thức này trực tiếp phụ thuộc vào cấu trúc dữ liệu của lớp Cart.

      Phương thức createOrder:
        Phương thức này tạo một đối tượng Order dựa trên thông tin trong Cart.
        Phương thức này trực tiếp truy cập vào các thuộc tính và phương thức của Cart và CartMedia.

      Phương thức createInvoice:
        Phương thức này tạo một đối tượng Invoice dựa trên thông tin trong Order.
        Phương thức này trực tiếp truy cập vào các thuộc tính và phương thức của Order.

      Phương thức processDeliveryInfo:
        Phương thức này xử lý thông tin vận chuyển từ người dùng và gọi phương thức validateDeliveryInfo.
        Phương thức này trực tiếp phụ thuộc vào cấu trúc dữ liệu của LOGGER và gọi phương thức validateDeliveryInfo.

      Phương thức validateDeliveryInfo và các phương thức hỗ trợ:
        Các phương thức này chịu trách nhiệm kiểm tra thông tin vận chuyển, như số điện thoại, tên, địa chỉ, nhưng hiện tại chưa có nội dung cụ thể.
        Các phương thức này có thể cần được triển khai và sẽ có mức độ nối kết dữ liệu với các dữ liệu cụ thể cần kiểm tra.

      Phương thức calculateShippingFee:
        Phương thức này tính phí vận chuyển dựa trên giá trị đơn hàng.
        Phương thức này trực tiếp truy cập vào thuộc tính và phương thức của Order.
  
  - ViewCartController: 
      Phương thức checkAvailabilityOfProduct:
        Phương thức này gọi phương thức checkAvailabilityOfProduct của Cart để kiểm tra sự sẵn có của sản phẩm trong giỏ hàng.
        Phương thức này không trực tiếp truy cập hoặc phụ thuộc vào cấu trúc dữ liệu của các lớp khác, nó chỉ gọi một phương thức của Cart, không làm nổi bật sự phụ thuộc vào cấu trúc dữ liệu cụ thể.

      Phương thức getCartSubtotal:
        Phương thức này gọi phương thức calSubtotal của Cart để tính toán tổng giá trị của giỏ hàng.
        Phương thức này không trực tiếp truy cập hoặc phụ thuộc vào cấu trúc dữ liệu của các lớp khác, chỉ gọi một phương thức của Cart.

  - Cart: 
    - Cart:
      Phương thức addCartMedia và removeCartMedia:
        Phương thức này nhận một đối tượng CartMedia và thêm hoặc loại bỏ nó khỏi danh sách lstCartMedia.
        Phương thức này tương tác trực tiếp với CartMedia, có mức độ nối kết dữ liệu.

      Phương thức getListMedia:
        Phương thức này trả về danh sách lstCartMedia.
        Phương thức này không trực tiếp tương tác với dữ liệu chi tiết của CartMedia, nhưng nó trả về danh sách, có thể dẫn đến sự nối kết dữ liệu khi ai đó có thể thay đổi danh sách này.

      Phương thức emptyCart:
        Phương thức này xóa toàn bộ danh sách lstCartMedia.
        Phương thức này không trực tiếp tương tác với dữ liệu chi tiết của CartMedia, nhưng nó xóa toàn bộ danh sách, có thể dẫn đến sự nối kết dữ liệu khi ai đó có thể thay đổi cách danh sách được quản lý.

      Phương thức getTotalMedia:
        Phương thức này tính tổng số lượng các sản phẩm trong giỏ hàng.
        Phương thức này tương tác trực tiếp với CartMedia, có mức độ nối kết dữ liệu.

      Phương thức calSubtotal:
        Phương thức này tính tổng số tiền cho các sản phẩm trong giỏ hàng.
        Phương thức này tương tác trực tiếp với CartMedia, có mức độ nối kết dữ liệu.

      Phương thức checkAvailabilityOfProduct:
        Phương thức này kiểm tra tính sẵn có của sản phẩm trong giỏ hàng.
        Phương thức này tương tác trực tiếp với CartMedia và Media, có mức độ nối kết dữ liệu.

      Phương thức checkMediaInCart:
        Phương thức này kiểm tra xem một sản phẩm có trong giỏ hàng không.
        Phương thức này tương tác trực tiếp với CartMedia và Media, có mức độ nối kết dữ liệu.
    
    - CartMedia:
      Thuộc tính media:
        Thuộc tính này là một đối tượng của lớp Media, có nghĩa là có một mức độ nối kết dữ liệu với lớp Media.
        Phương thức getMedia và setMedia trực tiếp truy cập và thiết lập giá trị cho thuộc tính media.

      Thuộc tính quantity và price:
        Cả hai thuộc tính này là kiểu dữ liệu cơ bản (int), không tạo ra sự nối kết mạnh với các lớp khác.
        Cung cấp các phương thức get và set cho cả hai thuộc tính.

       Phương thức toString:
        Phương thức này sử dụng media và quantity để tạo một chuỗi biểu diễn của đối tượng CartMedia.
        Phương thức này trực tiếp sử dụng media, có thể tạo ra sự nối kết dữ liệu.
</details>


---
