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
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sameer
 */
public class pageRender {

    DatabaseConnection cc = new DatabaseConnection();
    int noOfProducts = 12;
    int noOfGroups = 4;
    int offset = 0;

    private String getSiteBrand() {
        String page = "<div id=\"siteBrand\">";
        page += "<a href=\"index.jsp\"><img style=\"margin-left:40px;\" src=\"images/retailor-logo.png\"/></a>";
        page += "</div>";
        return page;
    }

    private String getLoginHandler(HttpSession session) {
        int userid = -1;
        try {
            userid = (Integer) (session.getAttribute("userid"));
        } catch (Exception e) {
            userid = -1;
            session.setAttribute("userid", -1);
        }
        String page = "";
        if (userid < 0) {
            page += "\n\n<form action=\"HtmlPages\" name=\"login_form\" method=\"post\" onsubmit=\"HtmlPages\">\n";
            page += "Email: <input type=\"text\" name=\"username\" />\n";
            page += "Password: <input type=\"password\" name=\"password\" />\n";
            page += "<input type=\"submit\" value=\"Login\"/>\n";
            page += "</form>\n\n";
            Vector<String[]> crt=(Vector<String[]>)session.getAttribute("cart_array");
            page += "<a href=\"checkout.jsp\"> <input type='button' value=\"Checkout ("+(crt==null?"0":crt.size())+")\" /></a>";
        }
        else {
            page += "\n<table style=\"position: relative; float: right; margin-right: 50px;\">\n\t<tr>\n\t\t<td>\n\t\t\t<span >Hi <a href=\"profile.jsp?id=" + userid + "\">" + session.getAttribute("name").toString() + "!</a></span>\n\t\t\t</td>\n\t\t\t";
            page += "<td><form action=\"HtmlPages\" name=\"logout_form\" method=\"post\" onsubmit=\"HtmlPages\">\n";
            page += "<input type=\"hidden\" name=\"logoutflag\" value=\"1\"/>\n";
            page += "<input type=\"submit\" value=\"Logout\"/>\n";
            page += "</form>\n\t\t</td>\n\t</tr></table>";
            Vector<String[]> crt=(Vector<String[]>)session.getAttribute("cart_array");
            page += "<a href=\"checkout.jsp\"> <input type='button' value=\"Checkout ("+(crt==null?"0":crt.size())+")\" /></a>";
        }
        return page;
    }

    public String getSubCatsDropDown(String cat) throws SQLException {
        String page = "<ul>\n";
        ResultSet rs = cc.listofsubcats(cat);
        while (rs.next()) {
            page += "<li><a href = \"index.jsp?cat=" + cat + "&subcat=";
            page += rs.getString(1) + "\">" + rs.getString(1) + "</a></li>\n";
        }
        page += "</ul>\n";
        return page;
    }

    public String getHeader(HttpSession session) throws SQLException {
        String page = "";
        page += "<div id=\"topLeft\">" + getSiteBrand() + "</div>";
        page += "\n" + "<div id=\"topRight\">" + getLoginHandler(session);
        page += "\n" + getSearch() + "</div>" + "\n";

        return page;
    }

    public String getSideBar() throws SQLException {
        String page = "";
        //page="<div id=\"topLevel\">";
        ResultSet rs = cc.listofcategories();
        page += "<ul class=\"nav\">\n";
        while (rs.next()) {
            page += "<li class=\"dropdown\" ><a href = \"index.jsp?cat=" + rs.getString(1) + "\" >";
            page += rs.getString(1);
            page += "</a>";
            page += getSubCatsDropDown(rs.getString(1));
            page += "</li>\n";
        }
        page += "</ul>\n";
        //page+="<ul class=\"nav\">\n<li><a href=\"#\">Home</a></li>\n<li class=\"dropdown\">\n<a href=\"#\">Work</a>\n<ul><li><a href=\"#\">Sublink</a></li><li><a href=\"#\">Sublink</a></li></ul></li><li><a href=\"#\">Portofolio</a></li><li><a href=\"#\">About</a></li><li><a href=\"#\">Contact</a></li></ul>";
        return page;
    }

    public String getElem(String category, String id, String arg1, String arg2, String mrp, String price, String img_url) {
        String page = "";
        page += "<div id=\"entry\">";
        page += "<a href=\"index.jsp?cat=" + category + "&id=" + id + "\">";
        page += "<img class=\"product_img\" src=\"" + img_url + "\" alt=\"Image Missing\" /></a><br/>";
        page += "<a href=\"index.jsp?cat=" + category + "&id=" + id + "\">";
        page += arg1;
        page += "</a>";
        page += "<br/>";
        page += arg2;
        page += "<br/>";
        page += "<strike>";
        page += mrp;
        page += "</strike>&nbsp;&nbsp;";
        page += price;
        page += "</div>";
        return page;
    }

    public String organiseResult(ResultSet rs, String cat) throws SQLException {
        String page = "";
        if ("Books".equals(cat)) {
            while (rs.next()) {
                page += getElem(cat, rs.getString(1), rs.getString("title"), rs.getString("author"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page += "\n";
            }
        } else if ("Clothing".equals(cat)) {
            while (rs.next()) {
                page += getElem(cat, rs.getString(1), rs.getString("category"), rs.getString("category2"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page += "\n";
            }
        } else if ("Electronics".equals(cat)) {
            while (rs.next()) {
                page += getElem(cat, rs.getString(1), rs.getString("model"), rs.getString("category"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page += "\n";
            }
        } else if ("Computer Accessories".equals(cat)) {
            while (rs.next()) {
                page += getElem(cat, rs.getString(1), rs.getString("model"), rs.getString("category"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page += "\n";
            }
        }
        page += "\n";
        return page;
    }

    public String getPrevNextLinks(Map<String, String[]> mm) {
        String page = "";
        String nextURL = "index.jsp?";
        String prevURL = "index.jsp?";
        int offset = 0;
        boolean offset_found = false;
        for (Map.Entry<String, String[]> itr : mm.entrySet()) {
            String key = itr.getKey();
            if (key.equals("offset")) {
                offset_found = true;
                String[] arr = itr.getValue();
                offset = Integer.parseInt(arr[0]);
                nextURL += "&offset=" + (offset + noOfProducts);
                if (offset - noOfProducts > 0) {
                    prevURL += "&offset=" + (offset - noOfProducts);
                }
            } else {
                nextURL += "&" + key + "=";
                prevURL += "&" + key + "=";
                String[] arr = itr.getValue();
                nextURL += arr[0];
                prevURL += arr[0];
            }
        }
        if (!offset_found) {
            nextURL += "&offset=" + noOfProducts;
        }
        page += "<br/><br/><br/><div id=\"prev_next\">";
        page += " <a href=\"" + prevURL + "\">Prev</a>";
        /* for(int i=0; i<5; i++){
         page+=" <a href=\"\">" + (i+1) + "</a>";
         }*/
        page += " <a href=\"" + nextURL + "\">Next</a>";
        page += "</p>";
        page += "</div>";
        return page;
    }

    private String getOptionForSortBy(String current_option, String index, String text, String URL, boolean is_sorted) {
        String sortby = "\t\t\t<option ";
        if (!index.equals("4")) {
            if (is_sorted && current_option.equals(index)) {
                sortby += " selected";
            }
        } else {
            if (!is_sorted || (is_sorted && current_option.equals("4"))) {
                sortby += " selected";
            }
        }
        sortby += " value=";
        String URL1 = URL + "&sort=" + index;
        sortby = sortby + "\"" + URL1 + "\"";
        sortby += ">" + text + "</option>\n\t";
        return sortby;
    }

    public String getSortBy(Map<String, String[]> mm) {
        String sortby = "";
        String URL = "index.jsp?";
        String current_option = "";
        boolean is_sorted = false;
        for (Map.Entry<String, String[]> itr : mm.entrySet()) {
            String key = itr.getKey();
            String[] arr = itr.getValue();
            if (key.equals("sort")) {
                is_sorted = true;
                current_option = arr[0];
            } else {
                if (!key.equals("offset")) {
                    URL += "&" + key + "=";
                    URL += arr[0];
                }
            }
        }
        sortby += "<div  class=\"sortSelect\" ><table><tr><td>Sort :</td>\n\t<td><select name=\"Sort By\" onchange=\"location = this.options[this.selectedIndex].value;\">\n\t";
        sortby += getOptionForSortBy(current_option, "1", "Increasing Price", URL, is_sorted);
        sortby += getOptionForSortBy(current_option, "2", "Decreasing Price", URL, is_sorted);
        sortby += getOptionForSortBy(current_option, "3", "Increasing Popularity", URL, is_sorted);
        sortby += getOptionForSortBy(current_option, "4", "Decreasing Popularity", URL, is_sorted);
        sortby += "\t\t\t\t</select></td></tr></table></div>";
        return sortby;
    }

    public String getSearch(String searchQuery, String table, int sort, int no, int off_set) throws SQLException {
        String page = "";
        List<ResultSet> l;
        if (table.equals("all")) {
            l = cc.search_field(searchQuery, table, sort, no, off_set / noOfGroups);
        } else {
            l = cc.search_field(searchQuery, table, sort, noOfGroups * no, off_set);
        }
        ResultSet book, compu, cloth, elec;
        book = l.get(0);
        cloth = l.get(1);
        compu = l.get(2);
        elec = l.get(3);
        int count = 0;
        page += "<div id=\"collectedEntry\">\n";
        if (book != null) {
            while (book.next()) {
                //System.err.println(book.getString(2));

                page += getElem("Books", book.getString(1), book.getString("title"), book.getString("author"), book.getString("mrp"), book.getString("price"), book.getString("img_url"));
                page += "\n";
                count++;
            }
        }
        if (compu != null) {
            while (compu.next()) {
                //System.err.println(compu.getString(7));
                page += getElem("Computer Accessories", compu.getString(1), compu.getString(2), compu.getString("category"), compu.getString("mrp"), compu.getString("price"), compu.getString("img_url"));
                page += "\n";
                count++;
            }
        }
        if (cloth != null) {
            while (cloth.next()) {
                //System.err.println(cloth.getString(2));
                page += getElem("Clothing", cloth.getString(1), cloth.getString("category"), cloth.getString(3), cloth.getString("mrp"), cloth.getString("price"), cloth.getString("img_url"));
                page += "\n";
                count++;
            }
        }
        if (elec != null) {
            while (elec.next()) {
                //System.err.println(elec.getString(2));
                page += getElem("Electronics", elec.getString(1), elec.getString("model"), elec.getString("category"), elec.getString("mrp"), elec.getString("price"), elec.getString("img_url"));
                page += "\n";
                count++;
            }
        }
        if (count == 0) {
            page += "<p>Sorry, no results found :(</p>";
        }
        page += "</div>";
        return page;
    }

    public String getMainPage(String cat, String id, String subcat, String searchQuery, String table, int sort, int off_set) throws SQLException {
        String page = "";
        if (searchQuery != null) {
            page += getSearch(searchQuery, table, sort, noOfProducts / noOfGroups, off_set);
        } else {
            page += "<div id=\"collectedEntry\">\n";
            if (cat == null) {
                ResultSet rs = cc.listofcategories();
                while (rs.next()) {
                    ResultSet rs2 = cc.listofproducts(rs.getString(1), sort, noOfProducts / noOfGroups, 0);
                    page += organiseResult(rs2, rs.getString(1));
                    page += "\n";
                }
            } else {
                if (id == null && subcat == null) {
                    ResultSet rs2 = cc.listofproducts(cat, sort, noOfProducts, off_set);
                    page += organiseResult(rs2, cat);
                    page += "\n";
                } else if (id != null) {
                    ResultSet rs2 = cc.itemByID(cat, id);
                    while (rs2.next()) {
                        page += "<table  class=\"product_detail\">\n";
                        page += "<tr>";
                        page += "<td><img class=\"detail_img\" src=\"" + rs2.getString("img_url") + "\"/></td>\n";
                        page += "<td>";
                        page += "<div class = \"product_description\" ><p>";
                        page += rs2.getString(2);
                        page += "</p>\n<p>";
                        page += rs2.getString(3);
                        page += "</p>\n<p>";
                        page += rs2.getString(4);
                        page += "</p>\n<p>";
                        page += "<strike>";
                        page += rs2.getString(5);
                        page += "</strike>&nbsp;&nbsp;";
                        page += rs2.getString(6);
                        page += "</p>\n<p>";
                        page += rs2.getString(7);
                        page += "</p>\n";
                        page += "</div>\n</td>";
                        page += "</tr>\n";
                        page += "</table>\n";

                        // now the add to cart part
                        page += "<form name=\"addtocart\" method=\"post\" action=\"order_handler\">\n";
                        // the product details
                        page += "<input type=\"hidden\" name=\"cat\" value=\"" + cat + "\">";
                        page += "<input type=\"hidden\" name=\"id\" value=\"" + id + "\">";

                        page += "Number: <input type=\"number\" min=\"1\" name=\"prod_cnt\" value=\"1\">";
                        page += "<input type=\"submit\" value=\"Add to Cart\">";
                        page += "</form>";

                    }
                } else {
                    ResultSet rs2 = cc.itemBySubCat(cat, subcat, sort, noOfProducts, off_set);
                    page += organiseResult(rs2, cat);
                    page += "\n";
                }
            }
            page += "</div>";
        }
        return page;
    }

    private String getSearch() throws SQLException {
        String page = "";
        page += "<div id=\"search\">";
        page += "<form action=\"index.jsp\" name=\"frmLogin\" method=\"get\">";
        page += "Search:";
        page += "<input type=\"text\" name=\"mainSearch\">";
        page += "<select name=\"table\">";
        page += "<option value=\"all\">All</option>";
        ResultSet rs = cc.listofcategories();
        while (rs.next()) {
            page += "<option value=\"" + rs.getString(1) + "\">" + rs.getString(1) + "</option>";
        }
        page += "</select>";
        page += "<input type=\"submit\" value=\"Search\">";
        page += "</form>";
        page += "</div>";
        return page;
    }

    public String getUserDetails(int ID, boolean bill, String specificBill) throws SQLException {
        String page = "";
        String id = "" + ID;
        page += "<div id=\"user\">";
        if (!bill) {
            page += "<table class=\"userDetails\">\n";
            ResultSet rs = cc.getUserDetails(id);
            if (!rs.next()) {
                page += "<tr><td>Severe Error Occured</td></tr>";
                page += "<tr><td>No details found!!</td></tr>";
            } else {
                page += "<tr><td>Name:</td><td>" + rs.getString("name") + "</td></tr>";
                page += "<tr><td>Email:</td><td>" + rs.getString("email") + "</td></tr>";
                page += "<tr><td>Phone no:</td><td>" + rs.getString("phone_number") + "</td></tr>";
                page += "<tr><td>Address:</td><td>" + rs.getString("address") + "</td></tr>";
            }
            page += "</table>";
            page += "<br/>See your previous transactions <a href=\"profile.jsp?id=" + id + "&bill=true\">here</a>.<br/>";
        }
        else{
            if(specificBill==null){
                page += "<table class=\"userDetails\">\n";
                ResultSet rs = cc.getBillDetails(id);
                while(rs.next()){
                    page += "<tr><td>Bill ID:</td><td><a href=\"profile.jsp?id="+id+"&bill=true&specific="+rs.getString("ID")+"\">"+rs.getString("ID")+"</a></td></tr>";
                    page += "<tr><td>Bill Date:</td><td>"+rs.getString("date")+"</td></tr>";
                    page += "<tr><td>Bill Cost:</td><td>"+rs.getString("total_cost")+"</td></tr>";
                }
                page += "</table>";
            }
            else{
                page+="<p>Bill details of Bill No. "+specificBill+"</p>";
                page += "<table class=\"userDetails\">\n";
                ResultSet rs = cc.getSpecificBillDetails(specificBill);
                while(rs.next()){
                    page += "<tr><td>Product Type:</td><td>"+rs.getString("prod_type") +"</td></tr>";
                    page += "<tr><td>Product ID:</td><td>"+rs.getString("prod_id")+"</td></tr>";
                    page += "<tr><td>Quantity:</td><td>"+rs.getString("quantity")+"</td></tr>";
                    page += "<tr><td>Cost:</td><td>"+rs.getString("cost")+"</td></tr>";
                }
                page += "</table>";
            }
        }
        page += "</div>";
        return page;
    }
}