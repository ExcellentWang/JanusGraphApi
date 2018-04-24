package tool;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

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
    }
