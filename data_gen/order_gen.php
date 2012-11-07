<?php
    $pconnect=mysql_connect("127.0.0.1","root","55piyushh");
    if(!$pconnect){
       echo "Database connection error1\n";
       die(mysql_error());
    }
    $pdb=mysql_select_db("foobar", $pconnect);
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
    /*
    $q=$_POST['q'];
    if($q=='a'){ 
        $d=$_POST['d'];
        $e=$_POST['e'];
        
        $query="INSERT INTO calender (date, event) VALUES ('{$d}', '{$e}')";
        $result=mysql_query($query, $pconnect);
        if(!$result){
            echo "error";
        }
    }
    
    else if($q=='t'){
        $d=$_POST['d'];
        $query="SELECT * FROM `calender` WHERE `date`='" . $d . "'";
        $result=mysql_query($query, $pconnect);
        if(!$result){
            echo "error";
        }
        
        while($row=mysql_fetch_array($result)){
            $i=$row['id'];
            $e=$row['event'];
            echo "<img src=\"files/cross.png\" style=\"cursor:pointer;\" onclick=\"delete_event({$i});\"> {$e} <br>";
        }
    }
    
    else if($q=='d'){
        $i=$_POST['i'];
        
        $query="DELETE FROM `pdatabase`.`calender` WHERE `calender`.`id` = {$i}";
        $result=mysql_query($query,$pconnect);
        if(!$result){
            echo "error";
        }
    }
     */
?>

<?php
    mysql_close($pconnect);
?>
