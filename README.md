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

</details>