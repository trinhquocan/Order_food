<?php
include "ketnoi.php"; // Kết nối cơ sở dữ liệu

// Nhận dữ liệu từ form đăng nhập
$username = $_POST['username'] ?? null;
$password = $_POST['password'] ?? null;

// Kiểm tra xem có đủ thông tin không
if (!$username || !$password) {
    echo json_encode([
        'success' => false,
        'message' => 'Thiếu thông tin đăng nhập',
        'result' => []
    ]);
    exit;
}

// Kiểm tra tài khoản người dùng trong cơ sở dữ liệu
$query = 'SELECT * FROM `users` WHERE `username` = "' . mysqli_real_escape_string($conn, $username) . '"';
$data = mysqli_query($conn, $query);
$result = array();

// Nếu tìm thấy người dùng với username khớp
if (mysqli_num_rows($data) > 0) {
    // Lấy thông tin người dùng đầu tiên
    $user = mysqli_fetch_assoc($data);

    // Kiểm tra mật khẩu đã nhập với mật khẩu đã mã hóa trong cơ sở dữ liệu
    if (password_verify($password, $user['password'])) {
        $result[] = $user; // Đưa user vào mảng result
        echo json_encode([
            'success' => true,
            'message' => 'Đăng nhập thành công',
            'result' => $result
        ]);
    } else {
        echo json_encode([
            'success' => false,
            'message' => 'Mật khẩu không chính xác',
            'result' => []
        ]);
    }
} else {
    echo json_encode([
        'success' => false,
        'message' => 'Tên người dùng không chính xác',
        'result' => []
    ]);
}
?>
