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

    $alpha=fopen("passwds.txt","w");
    if(!$alpha){
        echo "Oh boy!";
        die();
    }
    for($i=1;$i<=500;$i++){
        $pass=generateRandomString();
        fprintf($alpha,"%d %s\n",$i,$pass);
        echo "inserting for " . $i . "<br />";
        $query="UPDATE customer set passwd=PASSWORD(\"" . $pass . "\") WHERE ID=" . $i . ";";
        $result=mysql_query($query,$pconnect);
        /*if($result){
            echo "didn't work :-/ <br />";
        }*/
    }
    fclose($alpha);
?>

<?php
    mysql_close($pconnect);
?>
