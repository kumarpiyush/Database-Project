/*
 * This file returns html segments for the page
 * index.jsp, checkout.jsp, profie.jsp and thankyou.jsp
 */
package html;

/**
 *
 * @author ReTailor
 */
import database.DatabaseConnection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class pageRender {

    DatabaseConnection cc = new DatabaseConnection();
    int noOfProducts = 12;
    int noOfGroups = 4;
    private final String TABLE1 = "Books";
    private final String TABLE2 = "Computer Accessories";
    private final String TABLE3 = "Electronics";
    private final String TABLE4 = "Clothing";
    int offset = 0;

    //***************************************************************************
    //retruns the site brand
    private String getSiteBrand() {
        String page = "";
        page += "<div class=\"container pull-left\" style=\"padding-left: 50px\" >";
        page += "<a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">";
        page += "<span class=\"icon-bar\"></span>";
        page += "<span class=\"icon-bar\"></span>";
        page += "<span class=\"icon-bar\"></span>";
        page += "</a>";
        page += "<a class=\"brand\" href=\"index.jsp\">Re-Tailor</a>";
        page += "</div>";

        return page;
    }

    //***************************************************************************
    //returns the login box
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

            page += " <a style=\"color: white;\" href=\"#myModal\"  data-toggle=\"modal\">LogIn</a>";
            Vector<String[]> crt = (Vector<String[]>) session.getAttribute("cart_array");
            //page += "<a href=\"checkout.jsp\"> <input type='button' value=\"Checkout (" + (crt == null ? "0" : crt.size()) + ")\" /></a>";
            page += "<a style=\"margin-left: 20px;\" href=\"checkout.jsp\"> <button class=\"btn\" type=\"submit\">Checkout (" + (crt == null ? "0" : crt.size()) + ")</button> </a>";
        } else {
            Vector<String[]> crt = (Vector<String[]>) session.getAttribute("cart_array");
            page += "<table style=\"margin-top: -5px;\"><tr><td>";
            page += "Hi <a style=\"color: white;\" href=\"profile.jsp?id=" + userid + "\">" + session.getAttribute("name").toString() + "!</a>";
            page += "</td><td>";
            //page += "<a href=\"checkout.jsp\"> <input type='button' value=\"Checkout (" + (crt == null ? "0" : crt.size()) + ")\" /></a>";
            page += "<a style=\"margin-left: 10px;\" href=\"checkout.jsp\"> <button class=\"btn\" type=\"submit\">Checkout (" + (crt == null ? "0" : crt.size()) + ")</button> </a>";
            page += "</td><td>";
            page += "<form style=\"margin: 0 0 0 0\" action=\"HtmlPages\" name=\"logout_form\" method=\"post\" onsubmit=\"HtmlPages\">\n";
            page += "<input type=\"hidden\" name=\"logoutflag\" value=\"1\"/>\n";
            page += "<button class=\"btn\" type=\"submit\" value=\"Logout\">Logout</button>\n";
            page += "</form>";
            page += "</td></tr></table>\n";

        }
        return page;
    }

    public String getSubCatsDropDown(String cat) throws SQLException {

        String page = "<ul class=\"dropdown-menu \" role=\"menu\" aria-labelledby=\"dLabel\">\n";

        ResultSet rs = cc.listofsubcats(cat);
        page += "<li><a href = \"index.jsp?cat=" + cat + "\"> All </a></li>\n";
        while (rs.next()) {
            page += "<li><a href = \"index.jsp?cat=" + cat + "&subcat=";
            page += rs.getString(1) + "\">" + rs.getString(1) + "</a></li>\n";
        }
        page += "</ul>\n";
        return page;
    }

    public String getHeader(HttpSession session) throws SQLException {
        String page = "";
        page += "<div class=\"navbar navbar-inverse navbar-fixed-top\" style=\"height: 70px;\"><div class=\"navbar-inner\">";
        page += getSiteBrand();

        page += "<div class=\"container \">";
        page += "<div class=\"nav pull-right\" style=\"position: absolute; margin-left:600px\">";

        page += "<ul>";
        page += "<table ><tr><td>";
        page += getSearch();
        page += "</td><td>";
        page += "\n" + getLoginHandler(session);
        page += "</td></tr></table>";
        page += "</ul>";

        page += "</div></div>";
        page += "</div></div>";


        return page;
    }

    public String getSideBar() throws SQLException {
        String page = "";
        //page="<div id=\"topLevel\">";
        ResultSet rs = cc.listofcategories();
        page += "<div class=\"span3 bs-docs-sidebar sidebar-info\" style=\"margin-top:50px; \">\n";
        page += "<ul class=\"nav nav-list bs-docs-sidenav affix\" style=\"padding-top: 20px;  padding-bottom: 40px; border: 1px solid #021A40;\">\n";
        while (rs.next()) {
            page += "<li class=\"dropdown\" style=\"padding-top: 20px;\"><a href = \"index.jsp?cat=" + rs.getString(1) + "\" class=\"dropdown\" data-toggle=\"dropdown\">\n";
            page += rs.getString(1) + "<b class=\"caret\"></b>\n";
            page += "</a>";
            page += getSubCatsDropDown(rs.getString(1));
            page += "</li>\n";
        }
        page += "</ul></div>\n";
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
        page += "<strike>&#8377;";
        page += mrp;
        page += "</strike>&nbsp;&nbsp;&#8377;";
        page += price;
        page += "</div>";
        return page;
    }

    public String organiseResult(ResultSet rs, String cat) throws SQLException {
        String page = "";
        if (!rs.next()) {
            return "<p>Sorry, no results found :(</p>\n";
        }
        if (TABLE1.equals(cat)) {
            do {
                page += getElem(cat, rs.getString(1), rs.getString("title"), rs.getString("author"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page += "\n";
            } while (rs.next());
        } else if (TABLE4.equals(cat)) {
            do {
                page += getElem(cat, rs.getString(1), rs.getString("category"), rs.getString("category2"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page += "\n";
            } while (rs.next());
        } else if (TABLE3.equals(cat)) {
            do {
                page += getElem(cat, rs.getString(1), rs.getString("model"), rs.getString("category"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page += "\n";
            } while (rs.next());
        } else if (TABLE2.equals(cat)) {
            do {
                page += getElem(cat, rs.getString(1), rs.getString("model"), rs.getString("category"), rs.getString("mrp"), rs.getString("price"), rs.getString("img_url"));
                page += "\n";
            } while (rs.next());
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
        page += "<ul class=\"pager\" id=\"prev_next\">";
        page += " <li class=\"previous\"><a href=\"" + prevURL + "\">&larr; Previous</a></li>";
        page += " <li class=\"next\"><a href=\"" + nextURL + "\">Next &rarr;</a>";
        page += "</ul>";
        return page;
    }

    private String getOptionForSortBy(String current_option, String index, String text, String URL, boolean is_sorted) {
        String sortby = "\t\t\t<li> ";
        sortby += "<a href=\"";
        String URL1 = URL + "&sort=" + index;
        sortby += URL1 + "\"";
        sortby += ">" + text + "</a></li>\n\t";
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
        sortby += "<div class=\"container\"><div  class=\"btn-group\" id=\"sortSelect\" ><a class=\"btn dropdown-toggle\" data-toggle=\"dropdown\">Sort By <span class=\"caret\"></span></a>\n<ul class=\"dropdown-menu\">";
        sortby += getOptionForSortBy(current_option, "1", "Increasing Price", URL, is_sorted);
        sortby += getOptionForSortBy(current_option, "2", "Decreasing Price", URL, is_sorted);
        sortby += getOptionForSortBy(current_option, "3", "Increasing Popularity", URL, is_sorted);
        sortby += getOptionForSortBy(current_option, "4", "Decreasing Popularity", URL, is_sorted);
        sortby += "\t\t\t\t</ul></div></div>";
        return sortby;
    }

    public String getSearch(String searchQuery, String table, int sort, int no, int off_set) throws SQLException {
        String page = "";
        ResultSet l;
        l = cc.search_field(searchQuery, table, sort, no, off_set);
        int count = 0;
        page += "<div id=\"collectedEntry\">\n";
        if (table.equals(TABLE1)) {
            while (l.next()) {
                page += getElem(TABLE1, l.getString(1), l.getString("title"), l.getString("author"), l.getString("mrp"), l.getString("price"), l.getString("img_url"));
                page += "\n";
                count++;
            }
        }
        if (table.equals(TABLE2)) {
            while (l.next()) {
                page += getElem(TABLE2, l.getString(1), l.getString(2), l.getString("category"), l.getString("mrp"), l.getString("price"), l.getString("img_url"));
                page += "\n";
                count++;
            }
        }
        if (table.equals(TABLE4)) {
            while (l.next()) {
                page += getElem(TABLE4, l.getString(1), l.getString("category"), l.getString(3), l.getString("mrp"), l.getString("price"), l.getString("img_url"));
                page += "\n";
                count++;
            }
        }
        if (table.equals(TABLE3)) {
            while (l.next()) {
                page += getElem(TABLE3, l.getString(1), l.getString("model"), l.getString("category"), l.getString("mrp"), l.getString("price"), l.getString("img_url"));
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

    private String getSearch() throws SQLException {
        String page = "";
        page += "<form class=\"navbar-search \" action=\"index.jsp\" name=\"search\" method=\"get\">";
        page += "<input class=\"search-query span2\" placeholder=\"Search\" type=\"text\" name=\"mainSearch\"/>";
        page += "<select name=\"table\" class=\"controls\" style=\"margin-left: 15px; margin-right: 15px; margin-top: 5px; width: 170px; \">";

        ResultSet rs = cc.listofcategories();
        while (rs.next()) {
            page += "<option value=\"" + rs.getString(1) + "\">" + rs.getString(1) + "</option>";
        }
        page += "</select>";

        page += "</form>";

        return page;
    }

    public String getModal() {
        String modal = "";
        modal += "<div id=\"myModal\" class=\"modal hide fade\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">";
        modal += "<div class=\"modal-header\">";
        modal += "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>";
        modal += "<h3 id=\"myModalLabel\">Log in</h3>";
        modal += "</div>";
        modal += "<div class=\"modal-body\">";
        modal += "\n<div class=\"container\" style=\"margin-top: 100px; width: 450px; padding: 30px 30px 30px 30px;  border:1px solid #021a40;\">";
        modal += "\n<form class=\"form-signin\"  action=\"HtmlPages\" name=\"login_form\" method=\"post\" onsubmit=\"HtmlPages\">";
        modal += "\n<input name=\"username\" class=\"input-block-level\" type=\"email\" placeholder=\"Email address\" >";
        modal += "\n<input name=\"password\" class=\"input-block-level\" type=\"password\" placeholder=\"Password\">";
        modal += "\n<div><p></p></div>";
        modal += "\n<button class=\"btn btn-large btn-info\" type=\"submit\" value=\"Login\">Log In</button>";
        modal += "\n</form></div>\n\n";
        modal += "\n<a style=\"margin-left: 73%; position: absolute;top: 73%;\" href = \"SignUp.jsp\" ><button class=\"btn btn-large btn-info\" >Sign Up</button></a>";

        modal += "</div>";
        modal += "</div>";
        return modal;
    }

    public String getMainPage(String cat, String id, String subcat, String searchQuery, String table, int sort, int off_set) throws SQLException {
        String page = "";
        if (searchQuery != null) {
            page += getSearch(searchQuery, table, sort, noOfProducts, off_set);
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
                    if (!rs2.next()) {
                        page += "<p>Sorry, no results found :(</p>\n";
                    } else {
                        do {
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
                            page += "<strike>&#8377;";
                            page += rs2.getString(5);
                            page += "</strike>&nbsp;&nbsp;&#8377;";
                            page += rs2.getString(6);
                            page += "</p>\n<p>";
                            page += rs2.getString(7);
                            page += "</p>\n";
                            page += "</div>\n</td>";
                            page += "</tr>\n";
                            page += "</table>\n";

                            // now the add to cart part
                            page += "<form name=\"addtocart\" method=\"post\" action=\"order_handler\" onsubmit=\"jump_and_link();\">\n";
                            // the product details
                            page += "<input type=\"hidden\" name=\"cat\" value=\"" + cat + "\">";
                            page += "<input type=\"hidden\" name=\"id\" value=\"" + id + "\">";
                            page += "<input type=\"hidden\" name=\"target_url\" value=''>";

                            page += "Number: <input type=\"number\" min=\"1\" name=\"prod_cnt\" value=\"1\">";
                            page += "<input type=\"submit\" value=\"Add to Cart\">";
                            page += "</form>";
                        } while (rs2.next());
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

    public String getUserDetails(int ID, boolean bill, String specificBill) throws SQLException {
        String page = "";
        String id = "" + ID;
        page += "<div"
                + " id=\"user\">";
        if (!bill) {
            page += "<table class=\"table table-bordered userDetails\" >\n";
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
            page += "<br/><p style=\"margin-left: 250px;\">See your previous transactions <a href=\"profile.jsp?id=" + id + "&bill=true\">here</a>.</p>";
        } else {
            ResultSet rs = cc.getBillDetails(id);
            if (specificBill == null) {
                while (rs.next()) {
                    page += "<table class=\"table  table-bordered userDetails\">\n";
                    page += "<tr><td>Bill ID: </td><td><a href=\"profile.jsp?id=" + id + "&bill=true&specific=" + rs.getString("ID") + "\">" + rs.getString("ID") + "</a></td></tr>";
                    page += "<tr><td>Bill Date: </td><td>" + rs.getString(4) + "</td></tr>";
                    page += "<tr><td>Bill Cost: </td><td> &#8377;" + rs.getString("total_cost") + "</td></tr>";
                    page += "</table>";
                }
            } else {
                boolean hacker = false;
                while (rs.next()) {
                    if (rs.getString(1).equals(specificBill)) {
                        hacker = true;
                    }
                }
                if (hacker) {
                    ResultSet rs2 = cc.getSpecificBillDetails(specificBill);
                    if (rs2.next()) {
                        page += "<p style=\"margin-left: 250px;\" >Bill details of Bill No. " + specificBill + "</p>";

                        do {
                            page += "<table class=\"table table-bordered userDetails\" >\n";
                            page += "<tr><td>Product Type: </td><td>" + rs2.getString("prod_type") + "</td></tr>";
                            page += "<tr><td colspan=\"2\">View the item: <a href=\"index.jsp?cat=" + rs2.getString("prod_type") + "&id=" + rs2.getString("prod_id") + "\">click here</a>.</td></tr>";
                            page += "<tr><td>Quantity: </td><td>" + rs2.getString("quantity") + "</td></tr>";
                            page += "<tr><td>Cost of one item: </td><td> &#8377;" + rs2.getString("cost") + "</td></tr>";
                            page += "</table>";
                        } while (rs2.next());
                    }
                } else {
                    page += "<p style=\"margin-left: 250px;\">Dont try to be smart. There is no such bill in your account</p>";
                }
            }
        }
        page += "</div>";
        return page;
    }
}