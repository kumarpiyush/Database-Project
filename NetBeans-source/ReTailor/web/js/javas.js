function jump_and_link(){
    //document.getElementById("target_url").innerHTML=document.URL;
    document.addtocart.target_url.value=document.URL;
    return true;
}

function isValid(){
    var x=document.forms["signUpForm"]["number"].value;
    if(x.length>15){
        alert("Phone number too long");
        return false;
    }
    for(var i=0;i<x.length;i++){
        if(x.charAt(i)>'9' || x.charAt(i)<'0'){
            alert("Phone number not valid");
            return false;
        }
    }
    var p1=document.forms["signUpForm"]["password"].value;
    var p2=document.forms["signUpForm"]["repassword"].value;
    if(p1!=p2){
        alert("passwords don't match!");
        return false;
    }
    return true;
}
