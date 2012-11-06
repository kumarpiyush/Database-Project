/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package html;

import database.DatabaseConnection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sameer
 */
public class pageRender {
    DatabaseConnection cc=new DatabaseConnection();
    int noOfProducts=9;
    int offset=0;
    
    private String getSiteBrand() {
        String page="<div id=\"siteBrand\">";
        page+="<a href=\"index.jsp\"><img style=\"margin-left:40px;\" src=\"images/retailor-logo.png\"/></a>";
        page+="</div>";
        return page;
    }

    private String getLoginHandler(HttpSession session){
        int userid=-1;
        try{
            userid=(Integer)(session.getAttribute("userid"));
        }catch(Exception e){
            userid=-1;
            session.setAttribute("userid",-1);
        }
        String page="";
        System.err.println("ERR 1 "+userid);
        if(userid<0){
            page+="\n\n<form action=\"HtmlPages\" name=\"login_form\" method=\"post\" onsubmit=\"HtmlPages\">\n";
            page+="Email: <input type=\"text\" name=\"username\" />\n";
            page+="Password: <input type=\"password\" name=\"password\" />\n";
            page+="<input type=\"submit\" value=\"Login\"/>\n";
            page+="</form>\n\n";
        }
        else{
            page+="\n<table style=\"position: relative; float: right; margin-right: 50px;\">\n\t<tr>\n\t\t<td>\n\t\t\t<span >Hi "+cc.useridToName(userid)+"!</span>\n\t\t\t</td>\n\t\t\t";
            page+="<td><form action=\"HtmlPages\" name=\"logout_form\" method=\"post\" onsubmit=\"HtmlPages\">\n";
            page+="<input type=\"submit\" value=\"Logout\"/>\n";
            page+="</form>\n\t\t</td>\n\t</tr></table>";
        }
        return page;
    }
    
    public String getSubCatsDropDown(String cat) throws SQLException{
        String page = "<ul>\n";
        ResultSet rs = cc.listofsubcats(cat);
        while(rs.next()){
            page+="<li><a href = \"index.jsp?cat="+cat+"&subcat=";
            page+= rs.getString(1)+"\">"+rs.getString(1)+"</a></li>\n";
        }
        page+="</ul>\n";
        return page;
    }
    
    public String getHeader(HttpSession session) throws SQLException{
        String page="";
        page+="<div id=\"topLeft\">"+getSiteBrand()+"</div>";
        page +="\n"+"<div id=\"topRight\">"+getLoginHandler(session);
        page+="\n"+ getSearch()+"</div>"+"\n";
        
        return page;
    }
    
    public String getSideBar() throws SQLException{
        String page="";
        //page="<div id=\"topLevel\">";
        ResultSet rs=cc.listofcategories();
        page+="<ul class=\"nav\">\n";
        while(rs.next()){
            page+="<li class=\"dropdown\" ><a href = \"index.jsp?cat="+rs.getString(1)+"\" >";
            page+=rs.getString(1);
            page+="</a>";
            page+=getSubCatsDropDown(rs.getString(1));
            page+="</li>\n";
        }
        page+="</ul>\n";
        //page+="<ul class=\"nav\">\n<li><a href=\"#\">Home</a></li>\n<li class=\"dropdown\">\n<a href=\"#\">Work</a>\n<ul><li><a href=\"#\">Sublink</a></li><li><a href=\"#\">Sublink</a></li></ul></li><li><a href=\"#\">Portofolio</a></li><li><a href=\"#\">About</a></li><li><a href=\"#\">Contact</a></li></ul>";
        return page;
    }
    
    public String getElem(String category,String id,String arg1,String arg2,String mrp, String price, String img_url){
        String page="";
        page+="<div id=\"entry\">";
        page+="<a href=\"index.jsp?cat="+category+"&id="+id+"\">";
        page+="<img class=\"product_img\" src=\""+img_url+"\" alt=\"Image Missing\" /></a><br/>";
        page+="<a href=\"index.jsp?cat="+category+"&id="+id+"\">";
        page+=arg1;
        page+="</a>";
        page+="<br/>";
        page+=arg2;
        page+="<br/>";
        page+="<strike>";
        page+=mrp;
        page+="</strike>&nbsp;&nbsp;";
        page+=price;
        page+="</div>";
        return page;
    }
    
    public String organiseResult(ResultSet rs, String cat) throws SQLException{
        String page="";
        if("Books".equals(cat)){
            while(rs.next()){
                page+=getElem(cat, rs.getString(1), rs.getString("title"), rs.getString("author"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page+="\n";
            }
        }
        else if("Clothing".equals(cat)){
            while(rs.next()){
                page+=getElem(cat, rs.getString(1), rs.getString("category"), rs.getString("category2"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page+="\n";
            }
        }
        else if("Electronics".equals(cat)){
            while(rs.next()){
                page+=getElem(cat, rs.getString(1), rs.getString("model"), rs.getString("category"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page+="\n";
            }
        }
        else if("Computer Accessories".equals(cat)){
            while(rs.next()){
                page+=getElem(cat, rs.getString(1), rs.getString("model"), rs.getString("category"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page+="\n";
            }
        }
        page+="\n";
        return page;
    }
    
    public String getPrevNextLinks(){
        String page="";
        page+="<br/><br/><br/><div id=\"prev_next\">";
        page+="<p><a href=\"\">Prev</a>";
        for(int i=0; i<5; i++){
            page+=" <a href=\"\">" + (i+1) + "</a>";
        }
        page+=" <a href=\"\">Next</a>";
        page+="</p>";
        page+="</div>";
        return page;
    }
    
    public String getMainPage(String cat,String id,String subcat, String searchQuery, String table) throws SQLException{
        String page="";
        if(searchQuery!=null){
            List<ResultSet> l=cc.search_field(searchQuery, table);
            ResultSet book,compu,cloth,elec;
            book=l.get(0);
            cloth=l.get(1);
            compu=l.get(2);
            elec=l.get(3);
            int count=0;
            page+="<div id=\"collectedEntry\">\n";
            if(book!=null){
                while(book.next()){
                    //System.err.println(book.getString(2));
                    page+=getElem("Books", book.getString(1), book.getString("title"), book.getString("author"), book.getString("mrp"), book.getString("price"), book.getString("img_url"));
                    page+="\n";
                    count++;
                    if(count%3==0 && count>0){
                        page+="</div>\n<div id=\"collectedEntry\">\n";
                    }
                }
            }
            if(compu!=null){
                while(compu.next()){
                    //System.err.println(compu.getString(7));
                    page+=getElem("Computer Accessories", compu.getString(1), compu.getString(2), compu.getString("category"), compu.getString("mrp"), compu.getString("price"), compu.getString("img_url"));
                    page+="\n";
                    count++;
                    if(count%3==0 && count>0){
                        page+="</div>\n<div id=\"collectedEntry\">\n";
                    }
                }
            }
            if(cloth!=null){
                while(cloth.next()){
                    //System.err.println(cloth.getString(2));
                    page+=getElem("Clothing", cloth.getString(1), cloth.getString("category"), cloth.getString(3), cloth.getString("mrp"), cloth.getString("price"), cloth.getString("img_url"));
                    page+="\n";
                    count++;
                    if(count%3==0 && count>0){
                        page+="</div>\n<div id=\"collectedEntry\">\n";
                    }
                }
            }
            if(elec!=null){
                while(elec.next()){
                    //System.err.println(elec.getString(2));
                    page+=getElem("Electronics", elec.getString(1), elec.getString("model"), elec.getString("category"), elec.getString("mrp"), elec.getString("price"), elec.getString("img_url"));
                    page+="\n";
                    count++;
                    if(count%3==0 && count>0){
                        page+="</div>\n<div id=\"collectedEntry\">\n";
                    }
                }
            }
            if(count==0){
                page+="<p>Sorry, no results found :(</p>";
            }
            page+="</div>";
        }
        else{
            page+="<div id=\"collectedEntry\">\n";
            if(cat==null){
                ResultSet rs=cc.listofcategories();
                while(rs.next()){
                    ResultSet rs2=cc.topByCat(rs.getString(1),null,null,3);
                    page+=organiseResult(rs2, rs.getString(1));
                    page+="\n";
                }
            }
            else{
                if(id==null && subcat==null){
                    ResultSet rs2=cc.listofproducts(cat,null,null,noOfProducts,offset);
                    page+=organiseResult(rs2, cat);
                    page+="\n";
                }
                else if(id!=null){
                    ResultSet rs2=cc.itemByID(cat, id);
                    while(rs2.next()){
                        page+="<table  class=\"product_detail\">\n";
                        page+="<tr>";
                        page+="<td><img class=\"detail_img\" src=\""+rs2.getString("img_url")+"\"/></td>\n";
                        page+="<td>";
                        page+="<div class = \"product_description\" ><p>";
                        page+=rs2.getString(2);
                        page+="</p>\n<p>";
                        page+=rs2.getString(3);
                        page+="</p>\n<p>";
                        page+=rs2.getString(4);
                        page+="</p>\n<p>";
                        page+="<strike>";
                        page+=rs2.getString(5);
                        page+="</strike>&nbsp;&nbsp;";
                        page+=rs2.getString(6);
                        page+="</p>\n<p>";
                        page+=rs2.getString(7);
                        page+="</p>\n";
                        page+="</div>\n</td>";
                        page+="</tr>\n";
                        page+="</table>\n";
                        page+="<div id=\"order_product\"><p><a href=\"http://www.amazon.com\">Order Now !</a></p></div>";

                    }
                }
                else{
                    System.err.println(subcat);
                    ResultSet rs2=cc.itemBySubCat(cat, subcat,null,null, noOfProducts, offset);
                    page+=organiseResult(rs2, cat);
                    page+="\n";
                }
            }
            page+="</div>";
        }
        return page;
    }
    
    private String getSearch() throws SQLException {
        String page="";
        page+="<div id=\"search\">";
        page+="<form action=\"index.jsp\" name=\"frmLogin\" method=\"get\">";
        page+="Search:";
        page+="<input type=\"text\" name=\"mainSearch\">";
        page+="<select name=\"table\">";
        page+="<option value=\"all\">All</option>";
        ResultSet rs=cc.listofcategories();
        while(rs.next()){
            page+="<option value=\""+rs.getString(1)+"\">"+rs.getString(1)+"</option>";
        }
        page+="</select>";
        page+="<input type=\"submit\" value=\"Search\">";
        page+="</form>";
        page+="</div>";
        return page;
    }
}
