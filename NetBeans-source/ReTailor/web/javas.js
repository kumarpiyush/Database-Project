function jump_and_link(){
    //document.getElementById("target_url").innerHTML=document.URL;
    document.addtocart.target_url.value=document.URL;
    return true;
}

function jump_and_link1(){
    //document.getElementById("target_url").innerHTML=document.URL;
    document.logout_form.target_url.value=document.URL;
    return true;
}

function jump_and_link2(){
    //document.getElementById("target_url").innerHTML=document.URL;
    document.login_form.target_url.value=document.URL;
    return true;
}

function isValid(){
    var x=document.forms["loginform"]["number"].value;
    for(var i=0;i<x.length;i++){
        if(x.charAt(i)>'9' || x.charAt(i)<'0'){
            alert("Phone number not valid");
            return false;
        }
    }
    return true;
}
