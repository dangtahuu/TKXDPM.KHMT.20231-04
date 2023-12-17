# TKXDPM.VN.20231-01

This is a Capstone's source code for Software Design and Construction project

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
  - Pull Request(s): [https://github.com/dangtahuu/TKXDPM.KHMT.20231-04/pull/2]()
  - Specific implementation details: Find Control coupling.
</details>

<details>
<summary>Trần Văn Điền</summary>
<br>

- Assigned tasks:
  - Find Data coupling: 

- Implementation details:
  - Pull Request(s): [https://github.com/dangtahuu/TKXDPM.KHMT.20231-04/tree/dien/]
  - Specific implementation details: Find Data coupling.
</details>
</details>

---
<details>
  <summary>W3: 10/12/2023~17/12/2023 </summary>
<br>
<details>
<summary>Ngô Hoàng Hải Đăng</summary>
<br>

- Assigned tasks:
 - Tìm SOLID trong folder entity/order
 - Gồm 2 file Order.java và OrderMedia.java
  1. Trong file Order.java: Trong đoạn mã này, chúng ta có một lớp OrderMedia đại diện cho việc đặt hàng cho các đơn vị của Media. Để xem xét việc áp dụng nguyên tắc SOLID ta thấy
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
  
---
</details>