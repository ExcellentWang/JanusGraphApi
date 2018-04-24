package movies.spring.data.neo4j.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySqlRepository {

    public static List<Object> queryMysql() {

        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/home_linked";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "Dream96*";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from company_info;";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet ResultSet = statement.executeQuery(sql);
            ArrayList<Object> arrayList = new ArrayList<>();
            while(ResultSet.next()){
                    //获取stuname这列数据
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("company_n",ResultSet.getString("company_n"));
                objectObjectHashMap.put("company_addr",ResultSet.getString("company_addr"));
                objectObjectHashMap.put("company_representat",ResultSet.getString("company_representat"));
                objectObjectHashMap.put("repren_id",ResultSet.getString("repren_id"));
                objectObjectHashMap.put("sharehold",ResultSet.getString("sharehold"));
                objectObjectHashMap.put("sharehold_id",ResultSet.getString("sharehold_id"));
                arrayList.add(objectObjectHashMap);
                }

            con.close();

            return arrayList;

        } catch ( Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("成功！");

        }
       return null;
    }

}
