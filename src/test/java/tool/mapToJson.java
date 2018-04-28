package tool;

import java.util.*;

import net.sf.json.JSONObject;
import org.junit.Test;

public class mapToJson {

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("msg", "yes");//map里面装有yes
        System.out.println(map);
        JSONObject jsonObject = JSONObject.fromObject(map);
        System.out.println("输出的结果是：" + jsonObject);
        //3、将json对象转化为json字符串
        String result = jsonObject.toString();
        System.out.println(result);
    }

    @Test
    public void test1() {
        List<Map<Object, Object>> al = new ArrayList<Map<Object, Object>>();

        Map<Object, Object> jsonOne = new HashMap<Object, Object>();
        Map<Object, Object> jsonTwo = new HashMap<Object, Object>();
        Map<Object, Object> jsonThree = new HashMap<Object, Object>();
        jsonOne.put("name", "kewen");

        jsonTwo.put("name", "kewen");
        jsonThree.put("name", "kewen");
        al.add(jsonOne);
        al.add(jsonTwo);
        al.add(jsonThree);
        Map<Object, Object> map2 = new HashMap<Object, Object>();
        map2.put("node", al);
        System.out.println(map2);
        JSONObject jsonObject = JSONObject.fromObject(map2);
        System.out.println(jsonObject.toString());
        List<Map<Object, Object>> a = (List<Map<Object, Object>>) jsonObject.get("node");
        for (Map<Object, Object> o : a) {
            System.out.println(o.get("name"));
        }

    }
    @Test
    public void tst2(){
//        Vector ls = new Vector();
////        ArrayList<JSONObject> ls = new ArrayList<JSONObject>();
//        JSONObject jsonOne = new JSONObject();
//        JSONObject jsonOne1 = new JSONObject();
//        JSONObject jsont = new JSONObject();
//        jsonOne.put("name", "kewen");
//        jsonOne.put("name1", "kewen1");
//        ls.add(jsonOne);
//        jsonOne.put("name1", "kewen2");
//        ls.add(jsonOne);
//        jsonOne1.put("nodes",ls);
//        System.out.println(jsonOne1);
        Map<String,List>a=new HashMap<>();
        List b= new ArrayList();
        a.put("a",b);
        System.out.println(a.get("a").getClass().toString());
    }
}
