<?php
include "ketnoi.php";

// $query = "SELECT * FROM `mon_an_ban_chay` ORDER BY MAMONAN DESC";
$query = "SELECT * FROM `mon_an_ban_chay` WHERE `LOAI` = 2 LIMIT 0,5";

$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data))
{
    $result[] = ($row);

}

if(!empty($result))
{
    $arr = [
        'success' => true,
        'message' => "thanh cong",
        'result' => $result
    ];
}else{
    $arr = [
        'success' => false,
        'message' => "khong thanh cong",
        'result' => $result
    ];
}
    

print_r(json_encode($arr));

?>




