<?php
include "ketnoi.php";
$user_id = $_POST['user_id'] ?? null;
$tongtien = $_POST['tongtien'] ?? null;
$diachi = $_POST['diachi'] ?? null;
$sodienthoai = $_POST['sodienthoai'] ?? null;
$soluong = $_POST['soluong'] ?? null;
$chitiet = $_POST['chitiet'] ?? null;



$query = 'INSERT INTO `orders`( `user_id`, `tongtien`, `diachi`, `sodienthoai`, `soluong`) VALUES ("'.$user_id.'","'.$tongtien.'","'.$diachi.'","'.$sodienthoai.'","'.$soluong.'")
';
$data = mysqli_query($conn, $query);

if($data ==true) {
    $query = 'SELECT id AS iddonhang FROM `orders` WHERE `user_id` = '.$user_id.' ORDER BY id DESC LIMIT 1' ;
    $data = mysqli_query($conn, $query);

    while ($row = mysqli_fetch_assoc($data)) {
        $iddonhang = ($row);
    }
    if (!empty($iddonhang)) {
        $chitiet = json_decode($chitiet,true);
        foreach($chitiet as $key => $value) {
            $truyvan = 'INSERT INTO `orderdetails`(`order_id`, `monan_id`, `soluong`, `gia`) VALUES ('.$iddonhang["iddonhang"].','.$value["idsp"].','.$value["soluong"].','.$value["giasp"].')';
            $data = mysqli_query($conn, $truyvan);
            if($data ==true) {
                 $arr = [
                        'success' => true,
                        'message' => "thành công"
                    ];                
            }else {
                 $arr = [
                        'success' => false,
                        'message' => "không thành công"
                    ];
            }
        }
    }
} else {
    $arr = [
        'success' => false,
        'message' => "không thành công"
    ];
}
print_r(json_encode($arr));


?>




