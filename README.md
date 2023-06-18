<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">TRƯỜNG ĐẠI HỌC KHOA HỌC TỰ NHIÊN - ĐẠI HỌC QUỐC GIA THÀNH PHỐ HỒ CHÍ MINH</h3>

  <p align="center">
    ĐỒ ÁN QUẢN LÝ HỌC SINH - THIẾT KẾ PHẦN MIỀM
    <br />
   <strong>Nhóm gồm 4 thành viên: </strong>
    <br />
    <ol  style="text-align:left">
    <li>
     MSSV: 20120598. Họ và tên: DƯƠNG TẤN TỒN (Nhóm trưởng)
    </li>
    <li>
     MSSV: 20120449. Họ và tên: TRẦN TRỌNG ĐẠI
    </li>
    <li>
     MSSV: 20120549. Họ và tên: LÊ HOÀNG PHÚC
    </li>
    <li>
     MSSV: 20120532. Họ và tên: NGUYỄN NHẬT NAM
    </li>
    </ol>
  </p>
</div>


## GIỚI THIỆU ĐỒ ÁN
- Link ứng dụng : http://18.138.240.94 
- Ứng dụng quản lý điểm học sinh cấp 3 là một công cụ hiệu quả và tiện ích giúp giáo viên và phụ huynh theo dõi và quản lý điểm số của học sinh trong giai đoạn quan trọng của họ. Với sự phát triển không ngừng của công nghệ, việc sử dụng ứng dụng này mang lại nhiều lợi ích cho tất cả các bên liên quan. <br/>
- Với ứng dụng quản lý điểm học sinh cấp 3, giáo viên có thể dễ dàng nhập và cập nhật điểm số của học sinh một cách nhanh chóng và chính xác. Họ có thể tạo ra các bảng điểm tự động, tính toán điểm trung bình và đánh giá kết quả học tập của từng học sinh dễ dàng. Điều này giúp giáo viên tiết kiệm thời gian và công sức, tăng khả năng phân tích và đánh giá kết quả học tập, và cung cấp thông tin chính xác và minh bạch cho phụ huynh. <br/>
- Ngoài ra, ứng dụng quản lý điểm học sinh cấp 3 còn cung cấp các tính năng bổ sung như thông báo kết quả học tập, nhắc nhở lịch nộp bài, tạo thống kê điểm số tổng quát, và tương tác trực tiếp với giáo viên thông qua hệ thống tin nhắn hoặc trò chuyện trực tuyến. Điều này tạo điều kiện thuận lợi cho sự giao tiếp và hỗ trợ giữa giáo viên, học sinh và phụ huynh.


Trình bày sơ lược về sản phẩm. Về mục đích sử dụng sẽ chia ra làm 2 nhóm người sử dụng chính:

-	Admin: là chủ app tạo ra các tài khoản user nhập điểm, cũng có thể nhập điểm, xuất kết quả. 
-	User: là người nhập điểm, xuất kết quả.

Đồ án môn học Thiết kế phần miềm với đề tài Quản lý học sinh là kết quả của quá trình cố gắng không ngừng nghỉ của cả nhóm và nhận được sự hướng dẫn tận tình của thầy cô  cùng các bạn học. Qua đây, chúng em xin gửi lời cảm ơn chân thành tới những người đã giúp đỡ chúng  em hoàn thành được đồ án này!

## Tham khảo 
UI: [matrix-admin-bt5](https://github.com/wrappixel/matrix-admin-bt5),

Documentation: [Spring boot](https://spring.io/projects/spring-boot), [Thymeleaf](http://thymeleaf.org)


## Môi trường thực thi 
1. Java
2. Databse mysql
3. Famework + library: spring boot + thymeleaf + ... 
4. Visual studio code
 
## Cài đặt
1. Clone the repo
   ```sh
   git clone https://github.com/duongtanton/TKPM 
   ```
2. Init database
   - Chạy sql cmd trong: TKPM\sources\StudentsManagement\src\main\resources\db
3. Chạy project
 - Chạy file Application.java trong TKPM\sources\StudentsManagement\src\main\java\com\tkpm\studentsmanagement\Application.java
## Deploy CI/CD with EC2, Docker, Github action, ...
- Sử dụng máy unbutu (Tạo trên EC2 của AWS)
- Docker, Dockerhub
- Databae mysql (Tạo trên 123host)
![alt text](./flow.jpeg)

1. Tạo EC2, key pair, RDS(mysql), tạo security group, mở inbound, outbound ở ec2 cần thiết cho quá trình deploy và download file pem tạo ra khi tạo key pair.

    [Tạo ec2 aws](https://aws.amazon.com/premiumsupport/knowledge-center/free-tier-windows-instance) 
    
    [Tạo key pair aws](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/create-key-pairs.html) 
    
    [Tạo RDS aws](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/USER_CreateDBInstance.html) 

2. Sử dụng file pem vừa mới download connect tới ec2 vừa tạo. [Xem tại đây](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AccessingInstancesLinux.html)

3. Cài đặt docker cho máy ec2. [Xem tại đây](https://azdigi.com/blog/linux-server/tools/huonng-dan-cai-dat-docker-tren-ubuntu-22-04/)

4. Tạo file config như project. Lưu ý nhớ chuyển config từ db local sang db trên aws(rds). [Xem tại đây](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/CHAP_RDS_Configuring.html)

7. Hoàn thành. Có thể truy cập thông qua địa chỉ ip và port chạy ở bước 5. Ví dụ ( 18.138.240.94 ) nếu hiện ra website của mình là thành công.

### KẾT THÚC.
Chúng em xin tỏ lòng kính trọng và biết ơn sâu sắc đến thầy cô giáo là người trực tiếp hướng dẫn đồ án. Thầy cô đã cung cấp cho chúng em những tài liệu cần thiết cho đồ án của chúng em. 
Em xin chân thành cảm ơn!

<br/>

