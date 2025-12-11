# Bài Tập 8: Ứng Dụng Video Shorts & Firebase Integration

Đây là dự án Android mô phỏng chức năng của ứng dụng Video Shorts (tương tự TikTok/YouTube Shorts), tích hợp hệ thống Backend của Google Firebase để quản lý người dùng và lưu trữ dữ liệu.

## 🚀 Tính Năng Chính

1.  **Hệ thống Tài khoản (Firebase Authentication):**
    *   **Đăng ký:** Tạo tài khoản mới sử dụng Email và Mật khẩu.
    *   **Đăng nhập:** Xác thực người dùng để truy cập vào ứng dụng.
    *   **Session:** Tự động kiểm tra trạng thái đăng nhập khi mở app.

2.  **Giao diện Video Shorts (WebView):**
    *   Sử dụng `WebView` để hiển thị nội dung trang web responsive.
    *   Giao diện mô phỏng các nút chức năng: Like, Dislike, Share, Profile.
    *   Hiển thị thông tin người dùng hiện tại (Email) lên giao diện chính.

3.  **Upload Video (Firebase Storage):**
    *   Cho phép người dùng chọn video từ thư viện điện thoại.
    *   Upload video trực tiếp lên **Firebase Cloud Storage**.
    *   Hiển thị thanh tiến trình (ProgressBar) và thông báo trạng thái upload.

## 🛠 Công Nghệ Sử Dụng

*   **Ngôn ngữ lập trình:** Java
*   **IDE:** Android Studio
*   **Hệ điều hành tối thiểu:** Android 7.0 (API 24)
*   **Backend Service (Firebase):**
    *   *Authentication:* Quản lý xác thực user.
    *   *Cloud Storage:* Lưu trữ file video mp4.
*   **Thư viện chính:**
    *   Firebase BOM (32.7.2)
    *   AndroidX Appcompat & ConstraintLayout

## ⚙️ Hướng Dẫn Cài Đặt & Chạy Ứng Dụng

Để dự án hoạt động trên máy của bạn, vui lòng thực hiện các bước cấu hình Firebase sau:

1.  **Clone/Tải dự án:** Mở dự án bằng Android Studio.
2.  **Thiết lập Firebase:**
    *   Truy cập [Firebase Console](https://console.firebase.google.com/).
    *   Tạo một dự án mới.
    *   Tải file cấu hình `google-services.json` và đặt vào thư mục `app/` của dự án.
3.  **Bật dịch vụ trên Firebase Console:**
    *   **Authentication:** Vào *Sign-in method* -> Bật *Email/Password*.
    *   **Storage:** Vào *Storage* -> *Get Started* -> Chọn *Test Mode* (để tránh lỗi quyền truy cập).
4.  **Kiểm tra Bucket URL (Quan trọng):**
    *   Kiểm tra đường dẫn Storage Bucket của bạn trên web (ví dụ: `gs://your-project.appspot.com`).
    *   Nếu khác với mặc định, cập nhật lại dòng `FirebaseStorage.getInstance("...")` trong file `UploadActivity.java`.
5.  **Chạy ứng dụng:** Nhấn nút Run (▶) trong Android Studio.

## 📝 Lưu ý
*   Đảm bảo thiết bị/máy ảo có kết nối Internet.
*   Video upload lên sẽ được lưu trong thư mục `videos/{userId}/` trên Storage.

---
*Bài tập thực hành Lập trình Android.*
