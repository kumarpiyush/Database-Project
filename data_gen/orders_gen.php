<?php
    $pconnect=mysql_connect("127.0.0.1","root","55piyushh");
    if(!$pconnect){
       echo "Database connection error1\n";
       die(mysql_error());
    }
    $pdb=mysql_select_db("retailor", $pconnect);
    if(!$pdb){
       echo "Database connection error2";
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
        $query="select price from " . $table . " where ID=" . $id;
        $result=mysql_query($query, $pconnect);
        if(!$result){
            echo "error";
        }
        
        while($row=mysql_fetch_array($result)){
            $i=$row['price'];
            echo $i;
        }
    }

    getCost("book","12");
    // the variables
    $max_custs=0;
    $max_books=0;
    $max_elecs=0;
    $max_comps=0;
    $max_cloth=0;

    //$query=


?>

<?php
    mysql_close($pconnect);
?>
