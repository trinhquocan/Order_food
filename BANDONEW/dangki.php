<?php
include "ketnoi.php";

// Nhận dữ liệu từ form đăng ký
// Nhận dữ liệu từ form
$username = $_POST['username'] ?? '';
$fullname = $_POST['fullname'] ?? '';
$email = $_POST['email'] ?? '';
$password = $_POST['password'] ?? '';
$phone = $_POST['phone'] ?? '';
$address = $_POST['address'] ?? '';
$avatar = $_POST['avatar'] ?? '';
// Gán giá trị mặc định cho role
$role = "customer";

    $query = 'SELECT * FROM `users` WHERE `email` = "'.$email.'"';
    $data = mysqli_query($conn, $query);
    $numrow = mysqli_num_rows($data);
 

    if($numrow > 0) {
        $arr = [
            'success' => false,
            'message' => "Email da ton tai"
        ];
    } else {
            $hashed_password = password_hash($password, PASSWORD_DEFAULT);
        // Thực thi truy vấn thêm người dùng mới
$query = 'INSERT INTO `users` (`username`, `fullname`, `email`, `password`, `phone`, `address`, `avatar`, `role`) 
          VALUES ("'.$username.'", "'.$fullname.'", "'.$email.'", "'.$hashed_password.'", "'.$phone.'", "'.$address.'", "'.$avatar.'", "'.$role.'")';
$data = mysqli_query($conn, $query);


if($data == true) {
    $arr = [
        'success' => true,
        'message' => "thanh cong"
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "khong thanh cong"
    ];
}
    }




print_r(json_encode($arr))

?>
