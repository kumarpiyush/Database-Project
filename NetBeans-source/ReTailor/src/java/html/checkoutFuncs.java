/*
 * This file returns html segments for the page
 * checkout.jsp and thankyou.jsp
 */

/**
 *
 * @author
 * ReTailor
 */

package html;
import java.sql.ResultSet;
import java.util.*;
import javax.servlet.http.HttpSession;
import database.DatabaseConnection;
import java.sql.SQLException;
/**
 *
 * @author
 * piyush
 */
public class checkoutFuncs {
    DatabaseConnection cc=new DatabaseConnection();
    
    // returns the code to be displayed on checkout page
    public String getOrderCode(Vector<String[]> order,HttpSession session) throws SQLException{
        String ret="";
        Vector<String[]> processed_order=new Vector<String[]>();
        String[] temp=null;
        if(order!=null){
            ret+="<form name=\"addtocart\" method=\"post\" action=\"order_handler\" onsubmit=\"jump_and_link();\">";
            ret+="<input type='hidden' name='place_order' value='1'>";
            for(String[] i : order){
                ResultSet rs=cc.itemByID(i[0], i[1]);
                temp=new String[4];
                temp[0]=i[0];
                temp[1]=i[1];
                temp[2]=i[2];
                rs.next();
                temp[3]=rs.getString("price");//cost of 1 object
                processed_order.add(temp);
                
                ret+="<div id=\"buyDisplay\">";
                ret+="<table id=\"buyOuterTable\">";
                ret+="<tr id=\"buyOuterRow\">";
                ret+="<td><img src=\""+rs.getString("img_url")+"\"/ id=\"buyImage\"></td>";
                ret+="<td>";
                ret+="<table id=\"buyInnerTable\">";
                ret+="<tr><td>"+rs.getString(3)+"</td></tr>";
                ret+="<tr><td>"+rs.getString(2)+"("+rs.getString(4)+")</td></tr>";
                ret+="<tr><td>Number: "+i[2]+"</td></tr>";
                ret+="<tr><td>Cost of one item: &#8377;"+rs.getString(6)+"</td></tr>";
                ret+="<tr><td>Total cost: &#8377;"+Integer.parseInt(rs.getString(6)) * Integer.parseInt(i[2])+"</td></tr>";
                //ret+="</tr>";
                ret+="</table>\n\n";
                ret+="</td>";
                //ret+=(i[0]+" "+i[1]+" "+i[2]+"<br />"); // category, ID, count
                ret+="<td>";
                ret+="<table><tr><td>Edit order:</td></tr><tr><td><input type=\"number\" min=\"0\" name=\"product"+i+"\" value=\""+i[2]+"\"/></td></tr></table>";
                ret+="</td>";
                ret+="</tr>";
                ret+="</table>";
                ret+="</div>";
            }
            ret+="<input type=\"submit\" value=\"Confirm Edit\">";
            ret+="</form>";
            session.setAttribute("cart_array", processed_order);
            // <input type=\"number\" min=\"1\" name=\"prod_cnt\" value=\"1\">
            
            
            String login_str="Order Current Selection";
            boolean logged_in=true;
            if(session.getAttribute("userid").toString().equals("-1")){
                logged_in=false;
                login_str="Please login and proceed to order";
            }

            if(logged_in){  // put in database
                ret+="<form action='order_handler' method='post'>";
                ret+="<input type='hidden' name='place_order' value='1'>";
                ret+="<input type='submit' value='"+login_str+"'";
                return ret;
            }
            else{           // call the login page address
                // TODO : ask around for a login page
                ret+="<form action='index.jsp' method='post'>";
                ret+="<input type='submit' value='"+login_str+"'";
                return ret;
            }
        }
        else{
            ret="<h1>If you don't have money then get out!! Don't you ever bring an empty cart to me!!!!</h1>";
            // TODO : soften this
            return ret;
        }
    }
}
