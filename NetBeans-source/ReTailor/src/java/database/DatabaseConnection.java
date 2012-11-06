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
    //private static final String DBNAME = "foobar";
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
    public ResultSet listofcategories() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from categories order by name;");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //***************************************************************************
    public ResultSet itemByID(String cat, String id) {
        if (cat != null) {
            category_to_table(cat);
        }
        try {
            PreparedStatement prepStmt = con.prepareStatement("select * from " + CATEGORY + " where ID= ?");
            prepStmt.setString(1, id);
            ResultSet rs = prepStmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public ResultSet itemBySubCat(String cat, String subcat, int sort, int no, int offset) {
        if (cat != null) {
            category_to_table(cat);
        }

        sortToSort(sort);
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + CATEGORY + " where category = '" + subcat + "' order by " + SORT_BY_1 + " " + ORDER + ", " + SORT_BY_2 + " asc limit " + offset + "," + no);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //***************************************************************************
    //gives the list of products sorted by some order with an offset on index

    public ResultSet listofproducts(String category, int sort, int no, int offset) {
        if (category != null) {
            category_to_table(category);
        }

        sortToSort(sort);
        try {
            //TODO fill all the sort bys and check order by
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + CATEGORY + " order by " + SORT_BY_1 + " " + ORDER + ", " + SORT_BY_2 + " asc limit " + offset + "," + no);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //***************************************************************************
    //gives the list of products sorted by some order with an offset on index
    public ResultSet listofsubcats(String category) {
        if (category != null) {
            category_to_table(category);
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select distinct category from " + CATEGORY);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet loginCheck(String username, String password) {
        ResultSet rs = null;
        try {
            PreparedStatement prepStmt = con.prepareStatement("select ID,name from customer where email = ? and passwd = PASSWORD(?)");
            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
            rs = prepStmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public List search_field(String search, String table, int sort,int no,int offset) throws SQLException{
        StringTokenizer st=new StringTokenizer(search);
        String[] arr= new String[st.countTokens()];
        int count=0;
        sortToSort(sort);
        while(st.hasMoreTokens()){
            arr[count++]=st.nextToken();
        }

        System.err.println(count);
        
        ResultSet book=null;
        if(table.equals("Books") || table.equals("all")){
            String query="select * from book where ";
            category_to_table("Books");
            for(int i=0;i<count;i++){
                query+="(author like ? or title like ?) and ";
            }
            query+="true order by "+SORT_BY_1+" "+ORDER+", "+SORT_BY_2+" asc limit "+offset+","+no;
            System.err.println(query);
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < count; i++) {
                System.err.println(arr[i] + " " + i);
                stmt.setString(2 * i + 1, "%" + arr[i] + "%");
                stmt.setString(2 * i + 2, "%" + arr[i] + "%");
            }
            book = stmt.executeQuery();
        }
        System.err.println(count);
        ResultSet cloth=null;
        if(table.equals("Clothing") || table.equals("all")){
            String query="select * from clothing where ";
            category_to_table("Clothing");
            for(int i=0;i<count;i++){
                if("Men".equals(arr[i])){
                    arr[i]="M";
                }
                else if("Women".equals(arr[i])){
                    arr[i]="W";
                }
                query += "(description like ? or category like ? or category2 like ?) and ";
            }
            query+="true order by "+SORT_BY_1+" "+ORDER+", "+SORT_BY_2+" asc limit "+offset+","+no;
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < count; i++) {
                stmt.setString(3 * i + 1, "%" + arr[i] + "%");
                stmt.setString(3 * i + 2, "%" + arr[i] + "%");
                stmt.setString(3 * i + 3, "%" + arr[i] + "%");
            }
            cloth = stmt.executeQuery();
        }
        ResultSet compu=null;
        if(table.equals("Computer Accessories") || table.equals("all")){
            String query="select * from computer_accessories where ";
            category_to_table("Computer Accessories");
            for(int i=0;i<count;i++){
                query+="(brand like ? or model like ? or category like ? or description like ?) and ";
            }
            query+="true order by "+SORT_BY_1+" "+ORDER+", "+SORT_BY_2+" asc limit "+offset+","+no;
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < count; i++) {
                stmt.setString(4 * i + 1, "%" + arr[i] + "%");
                stmt.setString(4 * i + 2, "%" + arr[i] + "%");
                stmt.setString(4 * i + 3, "%" + arr[i] + "%");
                stmt.setString(4 * i + 4, "%" + arr[i] + "%");
            }
            compu = stmt.executeQuery();
        }
        ResultSet elec=null;
        if(table.equals("Electronics") || table.equals("all")){
            String query="select * from electronics where ";
            category_to_table("Electronics");
            for(int i=0;i<count;i++){
                query+="(brand like ? or model like ? or category like ? or description like ?) and ";
            }
            query+="true order by "+SORT_BY_1+" "+ORDER+", "+SORT_BY_2+" asc limit "+offset+","+no;
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < count; i++) {
                stmt.setString(4 * i + 1, "%" + arr[i] + "%");
                stmt.setString(4 * i + 2, "%" + arr[i] + "%");
                stmt.setString(4 * i + 3, "%" + arr[i] + "%");
                stmt.setString(4 * i + 4, "%" + arr[i] + "%");
            }
            elec = stmt.executeQuery();
        }
        List<ResultSet> l = new LinkedList<ResultSet>();
        l.add(book);
        l.add(cloth);
        l.add(compu);
        l.add(elec);
        return l;
    }
    
    public void insertBill(int userid,Vector<String[]> data){
        int billid=0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select ID from billing order by ID desc limit 1");
            if(rs.next()){
                
            }
            //return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
