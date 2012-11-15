<?php
    $pconnect=mysql_connect("127.0.0.1","root","55piyushh");
    if(!$pconnect){
       echo "Database connection error1\n";
       die(mysql_error());
    }
    $pdb=mysql_select_db("retailor", $pconnect);
    if(!$pdb){
       echo "Database connection error2\n";
       die();
    }
?>

<?php
    function generateRandomString($length = 10) {
        $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $randomString = '';
        for ($i = 0; $i < $length; $i++) {
            $randomString .= $characters[rand(0, strlen($characters) - 1)];
        }
        return $randomString;
    }

    function getCost($table,$id){
        global $pconnect;
        $query="select price from " . $table . " where ID=" . $id;
        $result=mysql_query($query, $pconnect);
        if(!$result){
            echo "error 1";
        }
        
        while($row=mysql_fetch_array($result)){
            $i=$row['price'];
            return $i;
        }
    }

    function tableSize($table){
        global $pconnect;
        $query="select count(*) as size from " . $table;
        $result=mysql_query($query, $pconnect);
        if(!$result){
            echo "error 2 " . $table;
        }
        
        while($row=mysql_fetch_array($result)){
            $i=$row['size'];
            return $i;
        }
    }

    // the variables
    $max_custs=tableSize("customer");
    $max_books=tableSize("book");
    $max_elecs=tableSize("electronics");
    $max_comps=tableSize("computer_accessories");
    $max_cloth=tableSize("clothing");

    $types=array("book","electronics","computer_accessories","clothing");
    $map=array("book"=>"Books","electronics"=>"Electronics","computer_accessories"=>"Computer Accessories","clothing"=>"Clothing");
    $sizes=array("book"=>$max_books,"electronics"=>$max_elecs,"computer_accessories"=>$max_comps,"clothing"=>$max_cloth);

    $max_quant=5;

    // not get order number
    for($order_no=1;$order_no<=1000;$order_no++){
        echo "doing $order_no <br />";
        //$time="SELECT FROM_UNIXTIME(RAND() * (1351708200 - 1230748200) + 1230748200)";
        $customer=rand(1,$max_custs);
        
        $quant=rand(1,$max_quant);
        $cost=0;
        for($j=0;$j<$quant;$j++){
            $prod=$types[rand(0,3)];
            $prodid=rand(0,$sizes[$prod]-1);
            $prod_quant=rand(1,3);
            $prod_cost=getCost($prod,$prodid);
            $cost+=($prod_quant*$prod_cost);
            $query="INSERT INTO bill_details values ({$order_no}, \"" . $map[$prod] . "\",{$prodid},{$prod_quant},{$prod_cost})";
            echo $query . "<br />";

            $result=mysql_query($query,$pconnect);
        }
        $query="INSERT INTO billing values ({$order_no}, {$customer},{$cost},FROM_UNIXTIME(RAND() * (1351708200 - 1230748200) + 1230748200))";
        $result=mysql_query($query,$pconnect);
    }

?>

<?php
    mysql_close($pconnect);
    //update billing,(select @i:=@i+1 as ind,billing.bill_date as dt1 from billing order by bill_date) as tbl1 set bill_date=tbl1.dt1 where ID=tbl1.ind ;
?>
