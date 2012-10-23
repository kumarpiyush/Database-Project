<%@page import="database.DatabaseConnection"%>
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/SideBar.css" />
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div id="topLevel">
            <% 
            DatabaseConnection.sanket();
            out.println("sanket rocks!!"); %>
            <!--a href = "home.html" target = "mainframe" ><b>Home</b></a></br></br>
            <a href = "about.html" target = "mainframe" ><b>About me</b></a></br></br>
            <a href = "academics.html" target = "mainframe" ><b>Academics</b></a></br></br>
            <a href = "projects.html" target = "mainframe" ><b>Projects</b></a></br></br>
            <a href = "contact.html" target = "mainframe" ><b>Contact</b></a></br-->
	</div>
    </body>
</html>
