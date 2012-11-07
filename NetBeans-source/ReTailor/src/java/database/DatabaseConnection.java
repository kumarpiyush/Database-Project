package database;

/**
 *
 * @author sameer
 */
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class DatabaseConnection {

    private Connection con = null;
    private static final String DBNAME = "foobar";
    private static final String DB_USERNAME = "sameer";
    //private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "sundarban";
    private static final String URL = "jdbc:mysql://localhost/foobar";
    private String CATEGORY = null;
    private String SORT_BY_1 = "popularity";
    private String SORT_BY_2 = null;
    private String ORDER = "desc";

    public DatabaseConnection() {
        try {
            if (con == null) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void category_to_table(String category) {
        if (category.equals("Books")) {
            CATEGORY = "book";
            SORT_BY_2 = "title";
        } else if (category.equals("Computer Accessories")) {
            CATEGORY = "computer_accessories";
            SORT_BY_2 = "brand";
        } else if (category.equals("Electronics")) {
            CATEGORY = "electronics";
            SORT_BY_2 = "brand";
        } else if (category.equals("Clothing")) {
            CATEGORY = "clothing";
            SORT_BY_2 = "category";
        }
    }

    //***************************************************************************
    //gives the listimport java.sql.ResultSet; of main categories
    public ResultSet listofcategories() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from categories order by name;");
        return rs;
    }

    //***************************************************************************
    public ResultSet itemByID(String cat, String id) throws SQLException {
        if (cat != null) {
            category_to_table(cat);
        }
        PreparedStatement prepStmt = con.prepareStatement("select * from " + CATEGORY + " where ID= ?");
        prepStmt.setString(1, id);
        ResultSet rs = prepStmt.executeQuery();
        return rs;
    }

    private void sortToSort(int sort) {
        if (sort == 1) {
            SORT_BY_1 = "price";
            ORDER = "asc";
        } else if (sort == 2) {
            SORT_BY_1 = "price";
            ORDER = "desc";
        } else if (sort == 4) {
            SORT_BY_1 = "popularity";
            ORDER = "asc";
        } else {
            SORT_BY_1 = "popularity";
            ORDER = "desc";
        }
    }

    public ResultSet itemBySubCat(String cat, String subcat, int sort, int no, int offset) throws SQLException {
        if (cat != null) {
            category_to_table(cat);
        }
        sortToSort(sort);
        PreparedStatement prepStmt = con.prepareStatement("select * from " + CATEGORY + " where category = ? and quantity > 0 order by " + SORT_BY_1 + " " + ORDER + ", " + SORT_BY_2 + " asc limit " + offset + "," + no);
        prepStmt.setString(1, subcat);
        ResultSet rs = prepStmt.executeQuery();
        return rs;
    }
    //***************************************************************************
    //gives the list of products sorted by some order with an offset on index

    public ResultSet listofproducts(String category, int sort, int no, int offset) throws SQLException {
        if (category != null) {
            category_to_table(category);
        }
        sortToSort(sort);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from " + CATEGORY + " where quantity > 0 order by " + SORT_BY_1 + " " + ORDER + ", " + SORT_BY_2 + " asc limit " + offset + "," + no);
        return rs;
    }

    //***************************************************************************
    //gives the list of products sorted by some order with an offset on index
    public ResultSet listofsubcats(String category) throws SQLException {
        if (category != null) {
            category_to_table(category);
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select distinct category from " + CATEGORY);
        return rs;
    }

    public ResultSet loginCheck(String username, String password) throws SQLException {
        ResultSet rs = null;
        PreparedStatement prepStmt = con.prepareStatement("select ID,name from customer where email = ? and passwd = PASSWORD(?)");
        prepStmt.setString(1, username);
        prepStmt.setString(2, password);
        rs = prepStmt.executeQuery();
        return rs;
    }

    public ResultSet search_field(String search, String table, int sort, int no, int offset) throws SQLException {
        StringTokenizer st = new StringTokenizer(search);
        String[] arr = new String[st.countTokens()];
        int count = 0;
        sortToSort(sort);
        while (st.hasMoreTokens()) {
            arr[count++] = st.nextToken();
        }

        if (table.equals("Books")) {
            String query = "select * from book where ";
            category_to_table("Books");
            for (int i = 0; i < count; i++) {
                query += "(author like ? or title like ?) and ";
            }
            query += "quantity > 0 order by " + SORT_BY_1 + " " + ORDER + ", " + SORT_BY_2 + " asc limit " + offset + "," + no;
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < count; i++) {
                stmt.setString(2 * i + 1, "%" + arr[i] + "%");
                stmt.setString(2 * i + 2, "%" + arr[i] + "%");
            }
            return stmt.executeQuery();
        }else if (table.equals("Clothing")){
            String query = "select * from clothing where ";
            category_to_table("Clothing");
            for (int i = 0; i < count; i++) {
                String gender = "A";
                if ("Men".equalsIgnoreCase(arr[i])) {
                    gender = "M";
                } else if ("Women".equalsIgnoreCase(arr[i])) {
                    gender = "W";
                } else if ("Kids".equalsIgnoreCase(arr[i]) || "Kid".equalsIgnoreCase(arr[i]) || "Children".equalsIgnoreCase(arr[i])) {
                    gender = "K";
                }
                query += "(description like ? or category = '" + gender + "' or category2 like ?) and ";
            }
            query += "quantity > 0 order by " + SORT_BY_1 + " " + ORDER + ", " + SORT_BY_2 + " asc limit " + offset + "," + no;
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < count; i++) {
                stmt.setString(2 * i + 1, "%" + arr[i] + "%");
                stmt.setString(2 * i + 2, "%" + arr[i] + "%");
            }
            return stmt.executeQuery();
        }else if (table.equals("Computer Accessories")) {
            String query = "select * from computer_accessories where ";
            category_to_table("Computer Accessories");
            for (int i = 0; i < count; i++) {
                query += "(brand like ? or model like ? or category like ? or description like ?) and ";
            }
            query += "quantity > 0 order by " + SORT_BY_1 + " " + ORDER + ", " + SORT_BY_2 + " asc limit " + offset + "," + no;
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < count; i++) {
                stmt.setString(4 * i + 1, "%" + arr[i] + "%");
                stmt.setString(4 * i + 2, "%" + arr[i] + "%");
                stmt.setString(4 * i + 3, "%" + arr[i] + "%");
                stmt.setString(4 * i + 4, "%" + arr[i] + "%");
            }
            return stmt.executeQuery();
        }else if (table.equals("Electronics")) {
            String query = "select * from electronics where ";
            category_to_table("Electronics");
            for (int i = 0; i < count; i++) {
                query += "(brand like ? or model like ? or category like ? or description like ?) and ";
            }
            query += "quantity > 0 order by " + SORT_BY_1 + " " + ORDER + ", " + SORT_BY_2 + " asc limit " + offset + "," + no;
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < count; i++) {
                stmt.setString(4 * i + 1, "%" + arr[i] + "%");
                stmt.setString(4 * i + 2, "%" + arr[i] + "%");
                stmt.setString(4 * i + 3, "%" + arr[i] + "%");
                stmt.setString(4 * i + 4, "%" + arr[i] + "%");
            }
            return stmt.executeQuery();
        }
        return null;
    }

    // function to put the ordered things in the database
    public String storeOrders(String userid, Vector<String[]> data) throws SQLException {
        int billid = 1;
        /*for (int i = 0; i < data.size(); i++) {
            int q1 = Integer.parseInt(data.elementAt(i)[2]);
            int q2 = quantityOfItemID(data.elementAt(i)[0], data.elementAt(i)[1]);
            if (q1 - q2 > 0) {
                return -2;
            }
        }*/
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select ID from billing order by ID desc limit 1");
        if (rs.next()) {
            billid = rs.getInt(1) + 1;
        }
        
        float total_price = 0;
        for (int i = 0; i < data.size(); i++) {
            int q1 = Integer.parseInt(data.elementAt(i)[2]);
            updateQuantityOfItemID(data.elementAt(i)[0], data.elementAt(i)[1], q1);
            total_price = Float.parseFloat(data.elementAt(i)[3]);
        }
        
        PreparedStatement pS = con.prepareStatement("INSERT into billing Values ( ?, ?, ?, now())");
        pS.setInt(1, billid);
        pS.setString(2, userid);
        pS.setFloat(3, total_price);
        pS.executeUpdate();
        
        for (int i = 0; i < data.size(); i++) {
            PreparedStatement pS2 = con.prepareStatement("INSERT into bill_details Values ( ?, ?, ?, ?, ?)");
            pS2.setInt(1, billid);
            pS2.setString(2, data.elementAt(i)[0]);
            pS2.setString(3, data.elementAt(i)[1]);
            pS2.setString(4, data.elementAt(i)[2]);
            pS2.setString(5, data.elementAt(i)[3]);
            pS2.executeUpdate();
        }
        //return rs;
        
        return String.valueOf(billid);
    }

    //
    public int quantityOfItemID(String cat, String id) throws SQLException {
        int quantity = 0;
        if (cat != null) {
            category_to_table(cat);
        }
        PreparedStatement prepStmt = con.prepareStatement("select quantity from " + CATEGORY + " where ID= ?");
        prepStmt.setString(1, id);
        ResultSet rs = prepStmt.executeQuery();
        if (rs.next()) {
            quantity = rs.getInt(1);
        }
        return quantity;
    }

    public float priceOfItemID(String cat, String id) throws SQLException {
        float price = -2;
        if (cat != null) {
            category_to_table(cat);
        }
        PreparedStatement prepStmt = con.prepareStatement("select price from " + CATEGORY + " where ID= ?");
        prepStmt.setString(1, id);
        ResultSet rs = prepStmt.executeQuery();
        if (rs.next()) {
            price = rs.getInt(1);
        }
        return price;
    }

    public void updateQuantityOfItemID(String cat, String userid, int quantity) throws SQLException {
        if (cat != null) {
            category_to_table(cat);
        }
        PreparedStatement prepStmt = con.prepareStatement("Update " + CATEGORY + " set quantity = quantity - ? where ID = ?");
        prepStmt.setInt(1, quantity);
        prepStmt.setString(2, userid);
        prepStmt.executeUpdate();
    }

    public ResultSet getUserDetails(String id) throws SQLException {
        PreparedStatement prepStmt = con.prepareStatement("select * from customer where id = ?");
        prepStmt.setString(1, id);
        return prepStmt.executeQuery();
    }

    public ResultSet getBillDetails(String id) throws SQLException {
        PreparedStatement prepStmt = con.prepareStatement("select * from billing where customer_id = ?");
        prepStmt.setString(1, id);
        return prepStmt.executeQuery();
    }

    public ResultSet getSpecificBillDetails(String specificBill) throws SQLException {
        PreparedStatement prepStmt = con.prepareStatement("select ID,customer_id,total_cost,DATE_FORMAT(bill_date,'%b %d %Y %h:%i %p') from bill_details where bill_id = ?");
        prepStmt.setString(1, specificBill);
        return prepStmt.executeQuery();
    }
    
    // returns transaction summary
    public String[] getSpecificBillSummary(String specificBill) throws SQLException{
        Statement stmt = con.createStatement();
        ResultSet rs=stmt.executeQuery("select * from billing where ID = "+specificBill);
        String[] details=new String[3];
        details[0]=rs.getString(1);
        details[1]=rs.getString(3);
        details[2]=rs.getString(4);
        
        return details;
    }
}
