package database;

/**
 *
 * @author sameer
 */
import java.sql.*;

public class DatabaseConnection {
    
    
    private static Connection con = null;
    //private static final String DBNAME = "foobar";
    private static final String DB_USERNAME = "sameer";
    //private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "sundarban";
    private static final String URL = "jdbc:mysql://localhost/foobar";
    
    private String CATEGORY = null;
    private String SORT_BY_2 = null;
    
    public DatabaseConnection() {
        try{
            if (con == null){
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
            }
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }   
    }
    
    public void category_to_table(String category){
        if(category.equals("Books")){
            CATEGORY = "book";
            SORT_BY_2 = "title";
        }else if(category.equals("Computer Accessories")){
            CATEGORY = "computer_accessories";
            SORT_BY_2 = "brand";
        }else if(category.equals("Electronics")){
            CATEGORY = "electronics";
            SORT_BY_2 = "brand";
        }else if(category.equals("Clothing")){
            CATEGORY = "clothing";
            SORT_BY_2 = "category";
        }
    }
    
    //***************************************************************************
    //gives the list of main categories
    public ResultSet listofcategories(){
        try{
            Statement stmt = con.createStatement();   
            ResultSet rs = stmt.executeQuery("select * from categories order by name;");
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    //***************************************************************************
    public ResultSet topByCat(String cat,String SORT_BY_1,String ORDER, int no){
        if(cat!=null)
            category_to_table(cat);
        if(SORT_BY_1==null){
            SORT_BY_1="popularity";
            ORDER="desc";
        }
        try{
            Statement stmt = con.createStatement();   
            ResultSet rs = stmt.executeQuery("select * from "+CATEGORY+" order by "+ SORT_BY_1+" "+ORDER+" limit "+no+";");
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    //***************************************************************************
    public ResultSet itemByID(String cat, String id){
        if(cat!=null)
            category_to_table(cat);
        try{
            Statement stmt = con.createStatement();   
            ResultSet rs = stmt.executeQuery("select * from "+CATEGORY+" where ID="+id+";");
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public ResultSet itemBySubCat(String cat, String subcat,String SORT_BY_1,String ORDER, int no,int offset){
        if(cat!=null)
            category_to_table(cat);
        if(SORT_BY_1==null){
            SORT_BY_1="popularity";
            ORDER="desc";
        }
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from "+CATEGORY+" where category = '"+subcat+"' order by "+SORT_BY_1+" "+ORDER+", "+SORT_BY_2+" asc limit "+offset+","+no);
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    //***************************************************************************
    //gives the list of products sorted by some order with an offset on index
    public ResultSet listofproducts(String category,String SORT_BY_1,String ORDER,int no,int offset){
        if(category!=null)
            category_to_table(category);
        if(SORT_BY_1==null){
            SORT_BY_1="popularity";
            ORDER="desc";
        }
        try{
            //TODO fill all the sort bys and check order by
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from "+CATEGORY+" order by "+SORT_BY_1+" "+ORDER+", "+SORT_BY_2+" asc limit "+offset+","+no);
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    //***************************************************************************
    //gives the list of products sorted by some order with an offset on index
    public ResultSet listofsubcats(String category){
        if(category!=null)
            category_to_table(category);
        try{
           Statement stmt = con.createStatement();   
           ResultSet rs = stmt.executeQuery("select distinct category from "+CATEGORY);
           return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public void search_field(){
        
    }
    
    public int loginCheck(String username,String password){
        return 144;
    }
    
    public String useridToName(int userid){
        String name="";
        try{
           Statement stmt = con.createStatement();   
           ResultSet rs = stmt.executeQuery("select name from customer where ID = "+userid);
           rs.next();
           name = rs.getString(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return name;
    }
}

