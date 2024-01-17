# TKXDPM.VN.20231-04

The actual source code and report lives in the Official_Project folder

## Team member

| Name           | Role        |
| :------------- | :---------- |
| Tạ Hữu Đăng    | Team Leader |
| Trịnh Quốc Đạt | Member      |
| Ngô Hoàng Hải Đăng  | Member      |
| Trần Văn Điền  | Member      |


## Report Content

<details>
  <summary>W1: 27/11/2023~03/12/2023 </summary>
<br>
<details>
<summary>Tạ Hữu Đăng</summary>
<br>

- Assigned tasks:
  - Find content coupling
  - Find common coupling
  - ...

- Implementation details:
  - Pull Request(s): [https://github.com/dangtahuu/TKXDPM.KHMT.20231-04/pull/1]()
  - Specific implementation details: Find content and common coupling in the code base but didn't find anything

</details>

<details>
<summary>Trịnh Quốc Đạt</summary>
<br>

- Assigned tasks:
  - Find Control coupling: 

- Implementation details:
  - Pull Request(s): https://github.com/dangtahuu/TKXDPM.KHMT.20231-04/pull/2()
  - Specific implementation details:

    -Trong CartScreenHandler.java:
    
      - Phương thức **CartScreenHandle(...)** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **homeScreenHandler.show()** khi hình ảnh aimsImage được click và gọi **requestToPlaceOrder()** khi nút **btnPlaceOrder** được click.

        -**Data Coupling**: Sử dụng dữ liệu từ **"assets/images/Logo.png"** để hiển thị hình ảnh. 

        -**Content Coupling**: Có mức độ coupling với nội dung của phương thức **homeScreenHandler.show()** và **requestToPlaceOrder()**.
      
      - Phương thức **getBController()** có thể được xác định mức độ coupling như sau:

        -**Data Coupling**: Ép kiểu kết quả của **super.getBController()** thành **ViewCartController**. 

        -**Content Coupling**: Có mức độ coupling với nội dung của lớp cơ sở (superclass) mà **getBController** kế thừa. 

      - Phương thức **requestToViewCart(...)** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **setPreviousScreen(prevScreen)** để thiết lập giá trị cho **previousScreen**. 

        -**Data Coupling**: Gọi **setPreviousScreen(prevScreen)** để thiết lập giá trị cho **previousScreen**. 

        -**Content Coupling**: Có mức độ coupling với nội dung của phương thức **getBController().checkAvailabilityOfProduct()**. 

      - Phương thức **requestToPlaceOrder()** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **placeOrderController.getListCartMedia().size()** để kiểm tra kích thước danh sách phương tiện trong giỏ hàng.

        -**Data Coupling**: Gọi **placeOrderController.getListCartMedia().size()** để kiểm tra kích thước danh sách phương tiện trong giỏ hàng.

        -**Content Coupling**: Có mức độ coupling với nội dung của các phương thức trong **PlaceOrderController** như **placeOrderController**.**getListCartMedia()**, **placeOrderController.placeOrder()**, và **placeOrderController.createOrder()**.

      - Phương thức **updateCart()** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **getBController().checkAvailabilityOfProduct()** để kiểm tra sự có sẵn của sản phẩm. 

        -**Data Coupling**: Gọi **getBController().checkAvailabilityOfProduct()** để kiểm tra sự có sẵn của sản phẩm. Gọi **displayCartWithMediaAvailability()** để hiển thị giỏ hàng.

        -**Content Coupling**: Có mức độ coupling với nội dung của phương thức **getBController().checkAvailabilityOfProduct()**.

      - Phương thức **updateCartAmount()** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **getBController().getCartSubtotal()** để lấy dữ liệu về tổng cộng giỏ hàng.

        -**Data Coupling**: Gọi **getBController().getCartSubtotal()** để lấy dữ liệu về tổng cộng giỏ hàng

        -**Content Coupling**: Có mức độ coupling với nội dung của phương thức **getBController().getCartSubtotal()**.

      - Phương thức **displayCartWithMediaAvailability()** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **getBController().getListCartMedia()** để lấy danh sách phương tiện trong giỏ hàng sau khi kiểm tra sự có sẵn. 

        -**Data Coupling**: Gọi **getBController().getListCartMedia()** để lấy danh sách phương tiện trong giỏ hàng sau khi kiểm tra sự có sẵn.

        -**Content Coupling**: Có mức độ coupling với nội dung của lớp **MediaHandler**, đặc biệt là khi tạo một đối tượng **MediaHandler** và gọi các phương thức như **setCartMedia** và **getContent**.

    -Trong **MediaHandler.java**:

      - Phương thức **MediaHandler(...)** có thể được xác định mức độ coupling như sau:

        -**Data Coupling**: Gán giá trị của **cartScreen** bằng **cartScreen** được truyền vào. Nếu **cartScreen** là một đối tượng của một lớp cụ thể, có mức độ coupling dữ liệu.

        -**Content Coupling**: Gọi **super(screenPath)** để khởi tạo lớp cơ sở **(BaseScreenHandler)**.

      - Phương thức **setCartMedia(...)** có thể được xác định mức độ coupling như sau:

        -**Data Coupling**: Gán giá trị của **cartMedia** cho thuộc tính **cartMedia**.

        -**Content Coupling**: Gọi **setMediaInfo()** để thiết lập thông tin về phương tiện. Nếu phương thức này sử dụng hoặc ảnh hưởng đến nội dung của lớp **MediaHandler**, có mức độ coupling nội dung.

      - Phương thức **setMediaInfo()** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **cartMedia.getMedia().getTitle()**, **cartMedia.getPrice()**, và **cartMedia.getMedia().getImageURL()** để lấy thông tin về phương tiện. Gọi **Cart.getCart().removeCartMedia(cartMedia)** để xóa phương tiện khỏi giỏ hàng.

        -**Data Coupling**: Gọi **cartMedia.getMedia().getTitle()** để lấy thông tin về tiêu đề phương tiện. Gọi **cartMedia.getPrice()** để lấy giá phương tiện. 

        -**Content Coupling**: Có mức độ coupling với các thành phần giao diện người dùng như title, price, image, và btnDelete.

      - Phương thức **initializeSpinner()** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **cartMedia.getQuantity()** và **cartMedia.getMedia().getQuantity()** để lấy thông tin về số lượng phương tiện trong giỏ hàng và số lượng tồn kho. Gọi **cartMedia.setQuantity(numOfProd)** để cập nhật số lượng phương tiện trong giỏ hàng.

        -**Data Coupling**: Gọi **cartMedia.getQuantity()** để lấy thông tin về số lượng phương tiện trong giỏ hàng. Gọi **cartMedia.getMedia().getQuantity()** để lấy thông tin về số lượng tồn kho của phương tiện.

        -**Content Coupling**: Có mức độ coupling với các thành phần giao diện người dùng như spinnerFX, spinner, và labelOutOfStock.

    -Trong **HomeScreenHandler.java**:

      - Phương thức **HomeScreenHandler(...)** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **super(stage, screenPath)** để gọi khởi tạo của lớp cơ sở **(BaseScreenHandler)**

      - Phương thức **show()** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **Cart.getCart().getListMedia().size()** để lấy số lượng phương tiện trong giỏ hàng. Gọi **super.show()** để gọi phương thức show của lớp cơ sở **(BaseScreenHandler)**.

        -**Data Coupling**: Gọi **Cart.getCart().getListMedia().size()** để lấy số lượng phương tiện trong giỏ hàng. 


      - Phương thức **initialize(...)** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Gọi **Cart.getCart().getListMedia().size()** để lấy số lượng phương tiện trong giỏ hàng. Gọi **super.show()** để gọi phương thức show của lớp cơ sở **(BaseScreenHandler)**.

        -**Data Coupling**: Gọi **Cart.getCart().getListMedia().size()** để lấy số lượng phương tiện trong giỏ hàng.

      - Phương thức **setImage()** có thể được xác định mức độ coupling như sau:

        -**Data Coupling**: Sử dụng **Configs.IMAGE_PATH** để xây dựng đường dẫn cho hình ảnh.

        -**Content Coupling**: Có mức độ coupling với các thành phần giao diện người dùng như **imsImage** và **cartImage**. 

      - Phương thức **addMediaHom(...)** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Sử dụng items để tạo ra một bản sao của danh sách phương tiện. Sử dụng **hboxMedia.getChildren().forEach**, **vBox**.**getChildren().clear()**, **hboxMedia.getChildren().indexOf(node)**, và **vBox.getChildren().add(media.getContent())** để thực hiện các thao tác trên giao diện.
 
        -**Data Coupling**: Gọi **items.size()** để lấy số lượng phương tiện trong danh sách.

        -**Content Coupling**: Có mức độ coupling với các thành phần giao diện người dùng như **hboxMedia** và **VBox**.

      - Phương thức **addMenuItem(...)** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Sử dụng menuButton.getItems().add(position, menuItem) để thêm một MenuItem vào menuButton.Sử dụng hboxMedia.getChildren().forEach, vBox.getChildren().clear(), và addMediaHome(filteredItems) để thực hiện các thao tác trên giao diện. 

        -**Data Coupling**: Sử dụng text, menuButton, menuButton.widthProperty(), và homeItems để tạo và cấu hình menu item.

        -**Content Coupling**: Có mức độ coupling với các thành phần giao diện người dùng như menuButton. 

    -Trong **MediaHandle.java**:

      - Phương thức **CartScreenHandle(...)** có thể được xác định mức độ coupling như sau:

        -**Control Coupling**: Sử dụng addToCartBtn.setOnMouseClicked để thiết lập sự kiện khi click vào nút "Add to Cart".

        -**Data Coupling**: Sử dụng screenPath, media, home, addToCartBtn, spinnerChangeNumber, media.getQuantity(), home.getBController(), media.getTitle(), media.getPrice(), home.getNumMediaCartLabel(), để tạo và cấu hình MediaHandler.

      - Phương thức **setMediaInfo()** có thể được xác định mức độ coupling như sau:

        -**Data Coupling**: Sử dụng media, media.getImageURL(), media.getTitle(), media.getPrice(), và media.getQuantity() để thiết lập thông tin của phương tiện.

        -**Content Coupling**: Sử dụng mediaImage, mediaTitle, mediaPrice, mediaAvail, và spinnerChangeNumber để thiết lập nội dung giao diện người dùng.


</details>

<details>
<summary>Trần Văn Điền</summary>
<br>

- Assigned tasks:
  - Find coupling trong paymentController, entity.media

- Implementation details:
  - Pull Request(s): 
  - Specific implementation details:
  1. PaymentController:
      Common Coupling :
        Class PaymentController có sử dụng các ngoại lệ như InvalidCardException, PaymentException, và UnrecognizedException từ package common.exception.

      Content Coupling :
        Phương thức getExpirationDate truy cập và xử lý nội dung của biến date.

      Control Coupling :
        Class PaymentController tương tác với InterbankSubsystem để thực hiện thanh toán thông qua gọi phương thức payOrder.

      Stamp Coupling :
        Class PaymentController sử dụng dữ liệu từ CreditCard và PaymentTransaction để thực hiện và xác nhận thanh toán.

      Data Coupling :
        Phương thức payOrder nhận dữ liệu từ các tham số như amount, contents, cardNumber, cardHolderName, expirationDate, và securityCode để thực hiện thanh toán.
  2. entity.media
      Common Coupling :
        Class Media có sử dụng Logger từ package java.util.logging để ghi log.

      Content Coupling:
        Các phương thức của class Media truy cập và thao tác nội dung của các trường như id, title, category, price, quantity, type, và imageURL.

      Control Coupling :
        Class Media tương tác với cơ sở dữ liệu thông qua các phương thức như getMediaById, getAllMedia, và updateMediaFieldById. Các phương thức này thực hiện kiểm soát cơ sở dữ liệu để truy vấn, cập nhật và lấy dữ liệu về đối tượng Media.

      Stamp Coupling :
        Class Media sử dụng AIMSDB để kết nối và thực hiện các thao tác cơ sở dữ liệu, cũng như sử dụng Utils để có được một đối tượng Logger.

      Data Coupling :
        Phương thức updateMediaFieldById nhận dữ liệu từ các tham số như tbname, id, field, và value để cập nhật dữ liệu trong cơ sở dữ liệu.
</details>

<details>
<summary>Ngô Hoàng Hải Đăng</summary>
<br>

- Assigned tasks:
  - Tìm control coupling trong file src

- Implementation details:
  - Pull Request(s): [Attach links to your pull requests here. You can attach multiple pull requests]()
  - Specific implementation details:
    ![Alt text](image.png)
    ![Alt text](image-1.png)
    ![Alt text](image-2.png)
    ![Alt text](image-3.png)
    ![Alt text](image-4.png)
</details>
</details>

<details>
  <summary>W2: 03/12/2023~10/12/2023 </summary>
<br>
<details>
<summary>Tạ Hữu Đăng</summary>
<br>
- Assigned tasks:
  - Find Cohesion trong InvoiceScreenHandler.java, MediaInvoiceScreenHandler.java, PaymentScreenHandler.java, ResultScreenHandler.java : 
- Implementation details:
  
  - Pull Request(s): [https://github.com/dangtahuu/TKXDPM.KHMT.20231-04/pull/6]()
    
  - Specific implementation details: 
  
  1. Trong InvoiceScreenHandler.java
      - Functional Cohesion: setInvoiceInfo() thực hiện các thao tác liên quan đến việc thiết lập thông tin hóa đơn. Các phương thức khác thường liên quan đến việc hiển thị hóa đơn và xác nhận thanh toán.
      - Communicational Cohesion: Có sự chia sẻ dữ liệu giữa InvoiceScreenHandler và đối tượng Invoice, cũng như giữa Invoice và Order.
      - Procedural Cohesion: Phần xử lý sự kiện confirmInvoice liên quan đến xác nhận thanh toán và hiển thị màn hình thanh toán.
      - Sequential Cohesion: Các dòng mã trong setInvoiceInfo() thực hiện các thao tác theo một thứ tự cụ thể để hiển thị thông tin hóa đơn.
  
  2. Trong MediaInvoiceScreenHandler.java
      - Functional Cohesion: Lớp này chủ yếu thực hiện các chức năng liên quan đến hiển thị thông tin về đối tượng OrderMedia trên màn hình hoá đơn.

      - Sequential Cohesion: Phương thức setMediaInfo() thực hiện các bước liên tiếp để thiết lập thông tin hình ảnh, tiêu đề, giá cả và số lượng sản phẩm.

      - Communicational Cohesion: Có sự chia sẻ dữ liệu giữa lớp MediaInvoiceScreenHandler và đối tượng OrderMedia để hiển thị thông tin chi tiết về sản phẩm trên hoá đơn.

      - Procedural Cohesion: Tất cả các phương thức của lớp này đều liên quan đến việc hiển thị thông tin và hình ảnh về đối tượng OrderMedia.

  3. Trong PaymentScreenHandler.java

        - Functional Cohesion: Lớp này chủ yếu thực hiện các chức năng liên quan đến thanh toán, bao gồm cả việc hiển thị giao diện người dùng và xử lý thanh toán thực tế thông qua PaymentController.

        - Sequential Cohesion: Các phương thức trong lớp được gọi theo một thứ tự nhất định để thực hiện quy trình thanh toán. Ví dụ, confirmToPayOrder gọi payOrder và sau đó tạo và hiển thị màn hình kết quả.

        - Communicational Cohesion: Các thành viên của lớp tương tác chủ yếu để thực hiện chức năng thanh toán và hiển thị kết quả.

  4. Trong ResultScreenHandler.java

        - Functional Cohesion: Các phương thức trong class ResultScreenHandler liên quan chặt chẽ đến việc xử lý và hiển thị kết quả màn hình.

        - Sequential Cohesion: Các phương thức và trình tự thực hiện các bước liên tục để xác nhận thanh toán và hiển thị kết quả.

        - Communicational Cohesion: Các thành phần UI (resultLabel, messageLabel, okButton) được cập nhật và tương tác chủ yếu qua các dữ liệu như result và message.

        - Temporal Cohesion: Các phương thức thường xuyên được gọi cùng nhau trong một chuỗi thời gian khi người dùng xác nhận thanh toán (confirmPayment gọi homeScreenHandler.show()).
</details>

<details>
<summary>Trịnh Quốc Đạt</summary>
<br>

- Assigned tasks:
  - Find Cohesion trong CartScreenHandler.java, cart.MediaHandler.java, HomeScreenHandler.java, home.MediaHandler.java : 

- Implementation details:
  - Pull Request(s): https://github.com/dangtahuu/TKXDPM.KHMT.20231-04/pull/5()
  - Specific implementation details: 

      1,Trong **cart.CartScreenHandler.java**:
    
      - Phương thức **CartScreenHandle(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Phương thức khởi tạo chủ yếu tập trung vào thiết lập các thành phần đồ họa (UI), đặc biệt là hình ảnh logo và xử lý sự kiện khi nút được nhấn. Điều này làm tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc khởi tạo màn hình và xử lý các sự kiện liên quan.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc cấu hình UI và xử lý sự kiện khi nhấn nút.

        -**Sequential Cohesion**: Các bước thực hiện theo một dãy sự kiện liên quan đến khởi tạo màn hình và xử lý sự kiện.

      - Phương thức **requestToViewCart(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm hiển thị giỏ hàng và xử lý việc kiểm tra sự có sẵn của sản phẩm trong giỏ hàng. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc hiển thị giỏ hàng và xử lý các kiểm tra.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc hiển thị giỏ hàng và kiểm tra sự có sẵn của sản phẩm.


      - Phương thức **requestToPlaceOrder(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Phương thức chịu trách nhiệm cho quy trình đặt hàng, kiểm tra có sẵn của sản phẩm và hiển thị màn hình vận chuyển. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến quy trình đặt hàng.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc hiển thị giỏ hàng và kiểm tra sự có sẵn của sản phẩm.


      - Phương thức **requestToPlaceOrder(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Phương thức chịu trách nhiệm cho quy trình đặt hàng, kiểm tra có sẵn của sản phẩm và hiển thị màn hình vận chuyển. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến quy trình đặt hàng.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến quy trình đặt hàng và hiển thị màn hình vận chuyển.


      - Phương thức **updateCart(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm kiểm tra lại sự có sẵn của sản phẩm trong giỏ hàng và hiển thị lại giỏ hàng. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc kiểm tra và hiển thị giỏ hàng.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc kiểm tra và hiển thị giỏ hàng.


      - Phương thức **updateCartAmount(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm tính toán và cập nhật các số liệu về giỏ hàng như tổng giá trị, thuế VAT, và tổng cộng. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến cập nhật các số liệu giỏ hàng.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc tính toán và cập nhật các số liệu giỏ hàng.


      - Phương thức **displayCartWithMediaAvailability(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm hiển thị danh sách sản phẩm trong giỏ hàng và cập nhật số liệu liên quan. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc hiển thị giỏ hàng và cập nhật số liệu.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc hiển thị giỏ hàng và cập nhật số liệu.

    2, Trong **cart.MediaHandler.java**:

      - Phương thức **MediaHandler(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Phương thức khởi tạo chủ yếu tập trung vào thiết lập các thành phần đồ họa (UI) của một phần tử truyền thông trên màn hình giỏ hàng. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc khởi tạo và hiển thị một phần tử truyền thông trong giỏ hàng.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc cấu hình UI và khởi tạo các thành phần.


      - Phương thức **CartScreenHandle(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: 

        -**Functional Cohesion**: 

        -**Sequential Cohesion**: 

      - Phương thức **setCartMedia(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm gán một CartMedia cụ thể cho MediaHandler và gọi setMediaInfo để hiển thị thông tin của nó. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc gán và hiển thị thông tin của CartMedia.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc gán CartMedia và hiển thị thông tin của nó.


      - Phương thức **setMediaInfo(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm thiết lập thông tin cơ bản của CartMedia như tiêu đề, giá, và hình ảnh, sau đó gọi initializeSpinner để cấu hình spinner và nút xóa. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc hiển thị thông tin và cấu hình các thành phần khác.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc thiết lập thông tin và cấu hình spinner.


      - Phương thức **initializeSpinner(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm khởi tạo và cấu hình Spinner để cho phép người dùng chọn số lượng sản phẩm. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc khởi tạo và cấu hình spinner.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc khởi tạo và cấu hình spinner.

    3,Trong **home.HomeScreenHandler.java**:

      - Phương thức **HomeScreenHandler(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Phương thức khởi tạo chủ yếu tập trung vào việc thiết lập các thành phần giao diện và khởi tạo dữ liệu ban đầu. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến quá trình khởi tạo màn hình chính của ứng dụng.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc cấu hình UI và khởi tạo dữ liệu.


      - Phương thức **getNumMediaCartLabel(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm trả về nhãn hiển thị số lượng sản phẩm trong giỏ hàng. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc hiển thị thông tin về giỏ hàng.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc trả về một thành phần giao diện cụ thể.


      - Phương thức **getBController(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Trả về đối tượng HomeController được gán cho BaseController của HomeScreenHandler. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc nhận HomeController.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc trả về một đối tượng HomeController.


      - Phương thức **show(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm hiển thị màn hình chính và cập nhật số lượng sản phẩm trong giỏ hàng. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc hiển thị thông tin và cập nhật giỏ hàng.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc hiển thị màn hình và cập nhật giỏ hàng.


      - Phương thức **initializeSpinner(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: 

        -**Functional Cohesion**: 

        -**Sequential Cohesion**: 

      - Phương thức **initialize(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Phương thức initialize chịu trách nhiệm khởi tạo màn hình, tải danh sách phương tiện, và cấu hình sự kiện cho các phần tử giao diện. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc khởi tạo màn hình và xử lý sự kiện.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc khởi tạo màn hình và xử lý sự kiện.


      - Phương thức **setImage(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: 

        -**Functional Cohesion**: 

        -**Sequential Cohesion**: 

      - Phương thức **initializeSpinner(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm cập nhật hình ảnh cho ImageView được sử dụng trong màn hình. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc cập nhật hình ảnh.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc cập nhật hình ảnh.


      - Phương thức **addMediaHome(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm thêm các mục phương tiện vào màn hình chính. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc thêm mục phương tiện vào màn hình.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc thêm mục phương tiện vào màn hình.


      - Phương thức **addMenuItem(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Chịu trách nhiệm thêm các mục menu vào SplitMenuButton để lọc các phương tiện. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc thêm mục menu và lọc danh sách phương tiện.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc thêm mục menu và lọc danh sách phương tiện.

    4,Trong **home.MediaHandle.java**:
        
      - Phương thức **MediaHandler(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Phương thức khởi tạo chủ yếu tập trung vào việc thiết lập các thành phần giao diện, khởi tạo dữ liệu, và xử lý sự kiện khi người dùng thêm phương tiện vào giỏ hàng. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc hiển thị thông tin phương tiện và xử lý thêm vào giỏ hàng.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc khởi tạo giao diện và xử lý sự kiện.


      - Phương thức **getMedia(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Trả về đối tượng phương tiện liên quan đến MediaHandler. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc nhận đối tượng phương tiện.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc trả về đối tượng phương tiện.


      - Phương thức **setMediaInfo(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Phương thức chịu trách nhiệm cập nhật thông tin về phương tiện trên giao diện người dùng. Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc cập nhật giao diện và hiển thị thông tin phương tiện.

        -**Functional Cohesion**: Các hành động trong phương thức chủ yếu liên quan đến việc cập nhật thông tin và hiển thị giao diện.


      - Phương thức **addToCartBtn(...)** có thể được xác định Cohesion như sau:

        -**Logical Cohesion**: Sự kiện chịu trách nhiệm xử lý thêm phương tiện vào giỏ hàng khi người dùng nhấp vào nút "Thêm vào giỏ hàng". Điều này tăng mức độ logical cohesion vì nó liên quan chặt chẽ đến việc xử lý thêm vào giỏ hàng.

        -**Functional Cohesion**: Các hành động trong sự kiện chủ yếu liên quan đến việc kiểm tra và thêm phương tiện vào giỏ hàng.


      
</details>
<details>
<summary>Trần Văn Điền</summary>
<br>

- Assigned tasks:
  - Find coupling trong entity.media, paymentController

- Implementation details:
  - Pull Request(s): 
  - Specific implementation details:
  1. PaymentController:
      Functional Cohesion:
        Các phương thức trong class tập trung vào chức năng cụ thể như getExpirationDate để xác thực và định dạng ngày hết hạn thẻ, và payOrder để thực hiện thanh toán.

      Sequential Cohesion:
        Các phương thức thường được gọi theo một thứ tự nhất định để thực hiện quy trình thanh toán, từ việc xác thực thông tin thẻ đến gọi dịch vụ thanh toán.

      Communicational Cohesion :
        Các thành viên của class tương tác để thực hiện một số chức năng cụ thể, như việc gửi thông tin thanh toán đến subsystem InterbankSubsystem.
  2. entity.media
      Functional Cohesion :
        Các phương thức trong class thường tập trung vào một chức năng cụ thể như lấy dữ liệu từ cơ sở dữ liệu (getMediaById, getAllMedia), cập nhật dữ liệu trong cơ sở dữ liệu (updateMediaFieldById), và các phương thức getter và setter.

      Sequential Cohesion:
        Các phương thức thường được gọi theo một thứ tự nhất định để thực hiện các bước của quy trình, chẳng hạn như lấy dữ liệu từ cơ sở dữ liệu, xử lý dữ liệu và cập nhật dữ liệu.

      Communicational Cohesion :
        Các thành viên của class tương tác để thực hiện một số chức năng cụ thể, như trao đổi thông tin với cơ sở dữ liệu và thao tác với dữ liệu đối tượng Media.

      Procedural Cohesion :
        Các phương thức trong class thường thực hiện các bước liên tiếp để đạt được một mục tiêu chức năng nhất định.

      Temporal Cohesion :
        Các phương thức được gọi cùng một lúc để thực hiện một số chức năng trong khoảng thời gian ngắn, như khi lấy dữ liệu từ cơ sở dữ liệu và cập nhật thông tin trạng thái.
</details>
<details>
<summary>Ngô Hoàng Hải Đăng</summary>
<br>

- Assigned tasks:
  - Find cohesion và coupling trong views/screen/shippingpayment, views/screen/invoice

</details>

</details>

<details>
  <summary>W3: 10/12/2023~17/12/2023 </summary>
<br>

<details>
<summary>Tạ Hữu Đăng</summary>
<br>

- Assigned tasks:
  - Tìm các nguyên tắc SOLID trong InvoiceScreenHandler.java, MediaInvoiceScreenHandler.java, PaymentScreenHandler.java, ResultScreenHandler.java :
- Implementation details:
  - Pull Request: https://github.com/dangtahuu/TKXDPM.KHMT.20231-04/pull/9()
  - Specific implementation details:

    1. Trong InvoiceScreenHandler.java
       - Single Responsibility Principle (SRP):
          Lớp InvoiceScreenHandler có trách nhiệm hiển thị và xử lý sự kiện liên quan đến hóa đơn (invoice). Nó thực hiện các nhiệm vụ liên quan đến giao diện người dùng và xử lý sự kiện, không chia sẻ nhiều lý do để thay đổi.
        - Open/Closed Principle (OCP):
          Mã có thể mở rộng để hỗ trợ các chức năng mới mà không cần sửa đổi mã hiện tại, ví dụ như thêm các phương thức mới.
        - Liskov Substitution Principle (LSP):
          Không có biểu hiện rõ ràng của việc thay thế đối tượng của lớp con cho đối tượng của lớp cha trong đoạn mã.
        - Interface Segregation Principle (ISP):
          Trong đoạn mã này, không có sự áp dụng rõ ràng của ISP vì không có interface nào được triển khai.
        -  Dependency Inversion Principle (DIP):
          Mã sử dụng dependency injection trong constructor để tránh sự phụ thuộc cứng vào cụm từ new. Tuy nhiên, còn một số mối quan hệ chặt chẽ (coupling) với các lớp cụ thể như PaymentScreenHandler và PaymentController.

    2. Trong MediaInvoiceScreenHandler.java
        - Single Responsibility Principle (SRP):
          Lớp MediaInvoiceScreenHandler chủ yếu tập trung vào hiển thị thông tin của một đối tượng OrderMedia và không thực hiện quá nhiều nhiệm vụ. Tuy nhiên, nếu việc hiển thị và việc xử lý hình ảnh có thể được chia thành các lớp riêng biệt, đó sẽ là một cải thiện.
        - Open/Closed Principle (OCP):
          Hiện tại, lớp không có mở rộng nhiều để hỗ trợ các chức năng mới, nhưng có thể mở rộng bằng cách thêm các phương thức mới hoặc tách các phương thức hiện tại thành các lớp riêng biệt nếu cần thiết.
        - Liskov Substitution Principle (LSP):
          Lớp MediaInvoiceScreenHandler không chứa sự thay thế đối tượng của lớp cha, nhưng nó là một ví dụ của việc sử dụng một đối tượng OrderMedia thay vì Media.
        - Dependency Inversion Principle (DIP):
          Lớp MediaInvoiceScreenHandler sử dụng abstraction thông qua OrderMedia, tuy nhiên, còn phụ thuộc chặt chẽ vào Image và các thành phần FXML.
    3. Trong PaymentScreenHandler.java
        - Single Responsibility Principle (SRP):
          Lớp PaymentScreenHandler có trách nhiệm quản lý giao diện người dùng, xử lý sự kiện, và thực hiện thanh toán. Có thể cân nhắc chia thành các lớp riêng biệt để tăng tính phân loại.
        - Open/Closed Principle (OCP):
          Hiện tại, lớp không có đặc điểm rõ ràng của việc mở rộng hoặc đóng cửa.
        - Liskov Substitution Principle (LSP):
          Lớp PaymentScreenHandler sử dụng một đối tượng Invoice, thuộc lớp con của Cart. Các đối tượng có thể thay thế mà không làm thay đổi tính đúng đắn của chương trình.
        - Dependency Inversion Principle (DIP):
          Lớp PaymentScreenHandler sử dụng abstraction thông qua Invoice và PaymentController

    4. Trong ResultScreenHandler.java
        - Single Responsibility Principle (SRP):
          Lớp ResultScreenHandler có nhiệm vụ chủ yếu là hiển thị kết quả và thông báo, không thực hiện nhiều nhiệm vụ khác. Tuy nhiên, có thể cần phân chia thành các lớp nhỏ hơn nếu có thêm chức năng được thêm vào.
        - Open/Closed Principle (OCP):
          Hiện tại, lớp không có đặc điểm rõ ràng của việc mở rộng hoặc đóng cửa.
        - Liskov Substitution Principle (LSP):
          Lớp ResultScreenHandler không có sự thay thế đối tượng của lớp cha, nhưng nó có thể hiển thị thông tin từ các lớp khác (đối với homeScreenHandler).
        - Dependency Inversion Principle (DIP):
          Lớp này không thể xác định mức độ tuân thủ DIP mà không biết về cách BaseScreenHandler, Stage, và homeScreenHandler được triển khai.
</details>
<details>
<summary>Trịnh Quốc Đạt</summary>
<br>

- Assigned tasks:
  - Tìm các nguyên tắc SOLID trong CartScreenHandler.java, cart.MediaHandler.java, HomeScreenHandler.java, home.MediaHandler.java : 

- Implementation details:
  - Pull Request(s): https://github.com/dangtahuu/TKXDPM.KHMT.20231-04/pull/8()
  - Specific implementation details: 

      1,Trong **cart.CartScreenHandler.java**:
    
      - Nguyên tắc Đơn Trách Nhiệm (Single Responsibility Principle - SRP):

        -Class CartScreenHandler có trách nhiệm quản lý giao diện người dùng của màn hình giỏ hàng.

        -Nó thực hiện các nhiệm vụ như hiển thị giỏ hàng, xử lý sự kiện nhấp chuột, và tương tác với các controller khác như PlaceOrderController và ViewCartController.

      - Nguyên tắc Mở Rộng/Đóng Gói (Open/Closed Principle - OCP):

        -Class này có thể được mở rộng để thêm các chức năng mới mà không cần sửa đổi mã nguồn hiện tại. Các phương thức như requestToPlaceOrder và updateCartAmount có thể được mở rộng bằng cách thêm mã nguồn mới mà không làm ảnh hưởng đến phần còn lại của lớp.

      - Nguyên tắc Thay Thế Liskov (Liskov Substitution Principle - LSP):

        -Class CartScreenHandler sử dụng một số controller khác nhau (PlaceOrderController, ViewCartController) mà không làm thay đổi đồng thời tính đúng đắn của chương trình.

      - Nguyên tắc Phân Chia Giao Diện (Interface Segregation Principle - ISP):

        -Class này không thực hiện giao diện nào, nhưng nếu có các giao diện liên quan, bạn nên chia thành các giao diện nhỏ hơn, chứa các phương thức cần thiết cho từng loại lớp sử dụng chúng.

      - Nguyên tắc Đảo Ngược Phụ Thuộc (Dependency Inversion Principle - DIP):

        -Class CartScreenHandler không trực tiếp tạo đối tượng PlaceOrderController. Thay vào đó, nó nhận một đối tượng đã được tạo từ bên ngoài và sử dụng nó.

    2, Trong **cart.MediaHandler.java**:

      - Nguyên tắc Đơn Trách Nhiệm (Single Responsibility Principle - SRP):

        -Class MediaHandler có trách nhiệm hiển thị thông tin về một sản phẩm trên giao diện người dùng (setMediaInfo), quản lý sự kiện khi số lượng sản phẩm thay đổi (initializeSpinner), và xử lý sự kiện khi người dùng muốn xóa sản phẩm khỏi giỏ hàng (btnDelete.setOnMouseClicked).

      - Nguyên tắc Mở Rộng/Đóng Gói (Open/Closed Principle - OCP):

        -Class này có thể được mở rộng bằng cách thêm các chức năng mới mà không làm thay đổi mã nguồn hiện tại. Các phương thức như initializeSpinner có thể được mở rộng để thêm các tính năng mới liên quan đến việc quản lý số lượng sản phẩm.

      - Nguyên tắc Thay Thế Liskov (Liskov Substitution Principle - LSP):

        -Class này sử dụng một instance của CartScreenHandler mà không làm thay đổi tính đúng đắn của chương trình.

      - Nguyên tắc Phân Chia Giao Diện (Interface Segregation Principle - ISP):

        -Class này không thực hiện bất kỳ giao diện nào.

      - Nguyên tắc Đảo Ngược Phụ Thuộc (Dependency Inversion Principle - DIP):

        -Class này không tạo trực tiếp đối tượng nào mà sử dụng một đối tượng đã được tạo từ bên ngoài (CartScreenHandler).

    3,Trong **home.HomeScreenHandler.java**:

      - Nguyên tắc Đơn Trách Nhiệm (Single Responsibility Principle - SRP):

        -Class này có trách nhiệm hiển thị giao diện người dùng cho màn hình chính (show, initialize, setImage, addMediaHome, addMenuItem). Nó cũng xử lý sự kiện khi người dùng nhấp chuột vào ảnh mục tiêu (aimsImage) và ảnh giỏ hàng (cartImage).

        -Ngoài ra, nó cũng quản lý số lượng sản phẩm trong giỏ hàng (numMediaInCart).

      - Nguyên tắc Mở Rộng/Đóng Gói (Open/Closed Principle - OCP):

        -Class này có thể mở rộng bằng cách thêm các chức năng mới mà không làm thay đổi mã nguồn hiện tại. Các phương thức như addMediaHome có thể được mở rộng để hỗ trợ các tính năng mới liên quan đến hiển thị sản phẩm trên màn hình chính.

      - Nguyên tắc Thay Thế Liskov (Liskov Substitution Principle - LSP):

        -Class này sử dụng một instance của HomeController và ViewCartController mà không làm thay đổi tính đúng đắn của chương trình.

      - Nguyên tắc Phân Chia Giao Diện (Interface Segregation Principle - ISP):

        -Class này không thực hiện giao diện nào.

      - Nguyên tắc Đảo Ngược Phụ Thuộc (Dependency Inversion Principle - DIP):

        -Class này không trực tiếp tạo đối tượng của HomeController và ViewCartController, mà là sử dụng chúng thông qua setBController.

    4,Trong **home.MediaHandle.java**:
        
      - Nguyên tắc Đơn Trách Nhiệm (Single Responsibility Principle - SRP):

        -Class này có trách nhiệm hiển thị thông tin chi tiết về một sản phẩm truyền vào (media). Nó cũng xử lý sự kiện khi người dùng nhấp vào nút "Add to Cart" (addToCartBtn).


      - Nguyên tắc Mở Rộng/Đóng Gói (Open/Closed Principle - OCP):

        -Class này có thể được mở rộng để thêm các chức năng mới mà không cần sửa đổi mã nguồn hiện tại. Các phương thức như setMediaInfo có thể được mở rộng để hỗ trợ các loại phương tiện mới hoặc các thuộc tính mới của phương tiện.

      - Nguyên tắc Thay Thế Liskov (Liskov Substitution Principle - LSP):

        -Class này sử dụng HomeScreenHandler mà không làm thay đổi tính đúng đắn của chương trình.

      - Nguyên tắc Phân Chia Giao Diện (Interface Segregation Principle - ISP):

        -Class này không thực hiện bất kỳ giao diện nào.

      - Nguyên tắc Đảo Ngược Phụ Thuộc (Dependency Inversion Principle - DIP):

        -Class này không trực tiếp tạo đối tượng HomeController và ViewCartController, mà là sử dụng chúng thông qua home.

</details>

<details>
<summary>Trần Văn Điền</summary>
<br>

- Assigned tasks:
  - Tìm nguyên tắc SOLID trong entity.media, Book, CD, DVD, paymentController

- Implementation details:
  - Pull Request(s): 
  - Specific implementation details:
  1. PaymentController:
      Single Responsibility Principle (SRP):
        Class PaymentController tập trung vào việc quản lý thanh toán và tương tác với InterbankSubsystem. Tuy nhiên, có thể có một số phương thức như getExpirationDate thực hiện nhiệm vụ kiểm tra và xử lý ngày hết hạn thẻ, mà có thể được chuyển sang một lớp hoặc phương thức riêng.

      Open/Closed Principle (OCP):
        Không có dấu hiệu rõ ràng về việc mở rộng hoặc đóng gói trong đoạn mã. Điều này có thể được cải thiện bằng cách sử dụng các giao diện và thiết kế theo hướng có thể mở rộng.

      Liskov Substitution Principle (LSP):
        Không có vấn đề rõ ràng về nguyên tắc này trong đoạn mã.

      Interface Segregation Principle (ISP):
        Class này không triển khai giao diện nào, và với số lượng phương thức hiện tại, không có vấn đề lớn về ISP.

      Dependency Inversion Principle (DIP):
        Class sử dụng InterbankSubsystem thông qua việc tạo đối tượng, nhưng có thể được cải thiện bằng cách sử dụng dependency injection, chẳng hạn qua constructor.
  2. entity.media
      Single Responsibility Principle (SRP):
        Class Media chịu trách nhiệm về việc tương tác với cơ sở dữ liệu (AIMSDB) và ghi log bằng cách sử dụng Logger. Tính năng này có vẻ không liên quan và có thể được tách thành các lớp riêng biệt.

      Open/Closed Principle (OCP):
        Không có dấu hiệu rõ ràng về việc mở rộng hoặc đóng gói trong đoạn mã. Điều này có thể được cải thiện bằng cách sử dụng các giao diện (interfaces) và thiết kế theo hướng có thể mở rộng.

      Liskov Substitution Principle (LSP):
        Không có vấn đề về nguyên tắc này trong đoạn mã.

      Interface Segregation Principle (ISP):
        Class Media không triển khai giao diện nào, nhưng nếu chia thành các phương thức có liên quan vào các giao diện nhỏ hơn có thể làm cho mã nguồn linh hoạt hơn.

      Dependency Inversion Principle (DIP):
        Có sử dụng AIMSDB và Utils thông qua việc tạo đối tượng, nhưng có thể được cải thiện bằng cách sử dụng dependency injection (cụ thể là qua constructor).
  3. entity.Book
      Single Responsibility Principle (SRP):
        Class Book có vẻ tập trung vào quản lý thông tin của một cuốn sách, và nói chung, không có biểu hiện rõ ràng về việc vi phạm SRP. Tuy nhiên, có thể xem xét chia thành các lớp con chuyên biệt hơn cho việc quản lý dữ liệu từ các bảng khác nhau trong cơ sở dữ liệu.

      Open/Closed Principle (OCP):
        Class này mở rộng từ lớp Media, có nghĩa là có khả năng mở rộng và thêm các tính năng mới mà không cần sửa đổi mã nguồn của lớp cha.

      Liskov Substitution Principle (LSP):
        Class Book thực hiện phương thức getMediaById từ lớp cha Media và không có vấn đề rõ ràng về nguyên tắc này.

      Interface Segregation Principle (ISP):
        Class này không triển khai giao diện nào, và với số lượng phương thức hiện tại, không có vấn đề lớn về ISP.

      Dependency Inversion Principle (DIP):
        Class này sử dụng AIMSDB thông qua việc tạo đối tượng. Có thể cải thiện bằng cách sử dụng dependency injection để chuyển AIMSDB vào qua constructor.  
  4. entity.CD
      Single Responsibility Principle (SRP):
        Class CD có vẻ tập trung vào quản lý thông tin của một đĩa CD và không có biểu hiện rõ ràng về việc vi phạm SRP. Tuy nhiên, có thể xem xét chia thành các lớp con chuyên biệt hơn cho việc quản lý dữ liệu từ các bảng khác nhau trong cơ sở dữ liệu.

      Open/Closed Principle (OCP):
        Class này mở rộng từ lớp Media, có nghĩa là có khả năng mở rộng và thêm các tính năng mới mà không cần sửa đổi mã nguồn của lớp cha.

      Liskov Substitution Principle (LSP):
        Class CD thực hiện phương thức getMediaById từ lớp cha Media và không có vấn đề rõ ràng về nguyên tắc này.

      Interface Segregation Principle (ISP):
        Class này không triển khai giao diện nào, và với số lượng phương thức hiện tại, không có vấn đề lớn về ISP.

      Dependency Inversion Principle (DIP):
        Class này không sử dụng trực tiếp các phụ thuộc cụ thể (ví dụ: database connection) mà thông qua lớp cha Media. Điều này làm tăng tính linh hoạt và giảm sự phụ thuộc vào chi tiết cụ thể.
  5. entity.DVD
      Single Responsibility Principle (SRP):
        Class DVD tập trung vào quản lý thông tin của một đĩa DVD và không có biểu hiện rõ ràng về việc vi phạm SRP. Tuy nhiên, có thể xem xét chia thành các lớp con chuyên biệt hơn cho việc quản lý dữ liệu từ các bảng khác nhau trong cơ sở dữ liệu.
      Open/Closed Principle (OCP):
        Class này mở rộng từ lớp Media, có nghĩa là có khả năng mở rộng và thêm các tính năng mới mà không cần sửa đổi mã nguồn của lớp cha.

      Liskov Substitution Principle (LSP):
        Class DVD thực hiện phương thức getMediaById từ lớp cha Media và không có vấn đề rõ ràng về nguyên tắc này.

      Interface Segregation Principle (ISP):
        Class này không triển khai giao diện nào, và với số lượng phương thức hiện tại, không có vấn đề lớn về ISP.

      Dependency Inversion Principle (DIP):
        Class này không sử dụng trực tiếp các phụ thuộc cụ thể (ví dụ: database connection) mà thông qua lớp cha Media. Điều này làm tăng tính linh hoạt và giảm sự phụ thuộc vào chi tiết cụ thể
</details>

<details>
<summary>Ngô Hoàng Hải Đăng</summary>
<br>

- Assigned tasks:
 - Tìm SOLID trong folder entity/order
 - Gồm 2 file Order.java và OrderMedia.java
  1. Trong file Order.java: Trong đoạn mã này, chúng ta có một lớp OrderMedia đại diện cho việc đặt hàng cho các đơn vị của Media. Để xem xét việc áp dụng nguyên tắc SOLID:
  - Single Responsibility Principle (Nguyên tắc Đơn trách nhiệm): Lớp OrderMedia có trách nhiệm quản lý thông tin đặt hàng, bao gồm Media, quantity, và price. Nó không có vẻ quá phức tạp và có thể coi là đang tuân theo nguyên tắc này.
  - Open/Closed Principle (Nguyên tắc Mở rộng đóng cửa): Lớp này có thể cần sự mở rộng trong tương lai nếu có yêu cầu mới liên quan đến đơn hàng của Media. Tuy nhiên, việc mở rộng có thể gây ra sửa đổi trong lớp hiện tại (ví dụ: thêm thuộc tính mới hoặc phương thức). Có thể cần xem xét cách để thiết kế sao cho lớp này không cần phải sửa đổi khi có yêu cầu mở rộng.
  - Liskov Substitution Principle (Nguyên tắc Thay thế Liskov): Lớp này không kế thừa từ lớp nào khác, vì vậy không có vấn đề về nguyên tắc này.
  - Interface Segregation Principle (Nguyên tắc Phân chia Giao diện): Lớp này không triển khai giao diện nào, vì vậy không có vấn đề liên quan đến việc phân chia giao diện.
  - Dependency Inversion Principle (Nguyên tắc Đảo ngược phụ thuộc): Lớp OrderMedia sử dụng Media như một phần của dữ liệu của nó. Tuy nhiên, không có một mức độ phức tạp cao về phụ thuộc. Tuy nhiên, nếu Media là một interface hoặc abstract class, thì nguyên tắc này có thể được áp dụng tốt hơn.
  2. Trong file OrderMedia.java:
  - Single Responsibility Principle (Nguyên tắc Đơn trách nhiệm): Lớp này có nhiều trách nhiệm bao gồm quản lý thông tin đơn hàng, thêm/xóa sản phẩm, tính toán tổng giá trị đơn hàng và quản lý thông tin vận chuyển. Điều này có thể làm cho lớp trở nên quá tải với nhiều trách nhiệm và không tuân theo nguyên tắc SRP.
  - Open/Closed Principle (Nguyên tắc Mở rộng đóng cửa): Lớp này có thể cần được mở rộng nếu có yêu cầu mới, ví dụ: thêm tính năng mới cho đơn hàng. Việc thêm mới có thể yêu cầu sửa đổi trong lớp này, không tuân theo nguyên tắc OCP.
  - Liskov Substitution Principle (Nguyên tắc Thay thế Liskov): Lớp này không kế thừa từ lớp nào khác, vì vậy không có vấn đề liên quan đến nguyên tắc này.
  - Interface Segregation Principle (Nguyên tắc Phân chia Giao diện): Lớp này không triển khai giao diện, vì vậy không có vấn đề liên quan đến nguyên tắc này.
  - ependency Inversion Principle (Nguyên tắc Đảo ngược phụ thuộc): Lớp này phụ thuộc trực tiếp vào OrderMedia, Configs và các kiểu dữ liệu cụ thể như List và HashMap, không sử dụng các abstraction hoặc interface. Điều này có thể làm cho việc thay đổi và bảo trì khó khăn khi có sự thay đổi trong các đối tượng phụ thuộc.
  
---
ngohoanghaidang
<details>
  <summary>W3: 10/12/2023~17/12/2023 </summary>
<br>
<details>
<summary>Ngô Hoàng Hải Đăng</summary>
<br>

- Assigned tasks:
 - Tìm SOLID trong folder entity/order
 - SOLID là một nguyên tắc quan trọng trong lập trình hướng đối tượng được đưa ra bởi Robert C. Martin, giúp xây dựng các hệ thống phần mềm linh hoạt, dễ bảo trì và mở rộng. Đây là một tập hợp các nguyên tắc thiết kế phần mềm, mỗi chữ cái đề cập đến một nguyên tắc cụ thể trong đó
 - S - Single Responsibility Principle (Nguyên tắc Đơn trách nhiệm): Một lớp chỉ nên có một lý do để thay đổi. Điều này đảm bảo rằng mỗi lớp hoặc module chỉ chịu trách nhiệm cho một nhiệm vụ cụ thể duy nhất, giúp dễ dàng bảo trì và mở rộng.
 - O - Open/Closed Principle (Nguyên tắc Mở rộng đóng cửa): Mã nguồn phải được thiết kế sao cho có thể mở rộng (extendable) mà không cần phải sửa đổi mã nguồn gốc (closed). Điều này thúc đẩy việc sử dụng kế thừa, giao diện, và việc triển khai lớp cũng như tránh sửa đổi mã nguồn hiện có mỗi khi cần mở rộng tính năng mới.
 - L - Liskov Substitution Principle (Nguyên tắc Thay thế Liskov): Đối tượng của một lớp con có thể thay thế hoàn toàn đối tượng của lớp cơ sở mà không làm thay đổi tính đúng đắn của chương trình. Điều này đảm bảo tính nhất quán và đúng đắn khi kế thừa.
 - I - Interface Segregation Principle (Nguyên tắc Phân chia Giao diện): Nguyên tắc này khuyến khích việc chia nhỏ các giao diện thành các giao diện nhỏ hơn, cụ thể hơn, để các lớp chỉ phải triển khai các phần của giao diện mà chúng cần. Điều này giúp tránh việc lớp cài đặt phải triển khai các phương thức không cần thiết.
 - D - Dependency Inversion Principle (Nguyên tắc Đảo ngược phụ thuộc): Module cấp cao không nên phụ thuộc vào module cấp thấp, cả hai nên phụ thuộc vào một abstraction. Điều này khuyến khích sử dụng interfaces hoặc abstract classes để giảm sự phụ thuộc giữa các phần của hệ thống, làm cho mã nguồn dễ dàng bảo trì và thay đổi.
 - Gồm 2 file Order.java và OrderMedia.java
  1. Trong file Order.java: Trong đoạn mã này, chúng ta có một lớp OrderMedia đại diện cho việc đặt hàng cho các đơn vị của Media. Để xem xét việc áp dụng nguyên tắc SOLID
  - Single Responsibility Principle (Nguyên tắc Đơn trách nhiệm): Lớp OrderMedia có trách nhiệm quản lý thông tin đặt hàng, bao gồm Media, quantity, và price. Nó không có vẻ quá phức tạp và có thể coi là đang tuân theo nguyên tắc này.
  - Open/Closed Principle (Nguyên tắc Mở rộng đóng cửa): Lớp này có thể cần sự mở rộng trong tương lai nếu có yêu cầu mới liên quan đến đơn hàng của Media. Tuy nhiên, việc mở rộng có thể gây ra sửa đổi trong lớp hiện tại (ví dụ: thêm thuộc tính mới hoặc phương thức). Có thể cần xem xét cách để thiết kế sao cho lớp này không cần phải sửa đổi khi có yêu cầu mở rộng.
  - Liskov Substitution Principle (Nguyên tắc Thay thế Liskov): Lớp này không kế thừa từ lớp nào khác, vì vậy không có vấn đề về nguyên tắc này.
  - Interface Segregation Principle (Nguyên tắc Phân chia Giao diện): Lớp này không triển khai giao diện nào, vì vậy không có vấn đề liên quan đến việc phân chia giao diện.
  - Dependency Inversion Principle (Nguyên tắc Đảo ngược phụ thuộc): Lớp OrderMedia sử dụng Media như một phần của dữ liệu của nó. Tuy nhiên, không có một mức độ phức tạp cao về phụ thuộc. Tuy nhiên, nếu Media là một interface hoặc abstract class, thì nguyên tắc này có thể được áp dụng tốt hơn.
  2. Trong file OrderMedia.java:
  - Single Responsibility Principle (Nguyên tắc Đơn trách nhiệm): Lớp này có nhiều trách nhiệm bao gồm quản lý thông tin đơn hàng, thêm/xóa sản phẩm, tính toán tổng giá trị đơn hàng và quản lý thông tin vận chuyển. Điều này có thể làm cho lớp trở nên quá tải với nhiều trách nhiệm và không tuân theo nguyên tắc SRP.
  - Open/Closed Principle (Nguyên tắc Mở rộng đóng cửa): Lớp này có thể cần được mở rộng nếu có yêu cầu mới, ví dụ: thêm tính năng mới cho đơn hàng. Việc thêm mới có thể yêu cầu sửa đổi trong lớp này, không tuân theo nguyên tắc OCP.
  - Liskov Substitution Principle (Nguyên tắc Thay thế Liskov): Lớp này không kế thừa từ lớp nào khác, vì vậy không có vấn đề liên quan đến nguyên tắc này.
  - Interface Segregation Principle (Nguyên tắc Phân chia Giao diện): Lớp này không triển khai giao diện, vì vậy không có vấn đề liên quan đến nguyên tắc này.
  - Dependency Inversion Principle (Nguyên tắc Đảo ngược phụ thuộc): Lớp này phụ thuộc trực tiếp vào OrderMedia, Configs và các kiểu dữ liệu cụ thể như List và HashMap, không sử dụng các abstraction hoặc interface. Điều này có thể làm cho việc thay đổi và bảo trì khó khăn khi có sự thay đổi trong các đối tượng phụ thuộc.
 main
</details>
