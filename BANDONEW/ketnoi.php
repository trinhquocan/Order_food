<?php
$servername = "localhost";
$user = "root";
$pass = "";
$database = "data_onl";

// Kết nối tới MySQL bằng mysqli
$conn = mysqli_connect("localhost:3307", "root", "", "data_onl");


// Kiểm tra kết nối
if (!$conn)
 {
    die("Kết nối thất bại: " . mysqli_connect_error());
}

// Thiết lập mã hóa tiếng Việt
mysqli_set_charset($conn, "utf8");

// echo "Kết nối thành công!";
?>
