package movies.spring.data.neo4j.repositories;
import com.thinkaurelius.titan.core.attribute.Text;
import javafx.beans.binding.StringBinding;
import movies.spring.data.neo4j.connect.gremlinConnect;
import net.sf.json.JSONObject;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.structure.util.detached.DetachedVertex;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CompanyRepositories {
    Map<Object,Object> map5= new HashMap<Object,Object>();
    String companyname;
    public CompanyRepositories(String companyname){
        this.companyname=companyname;
    }
    public JSONObject queryCompanyData() throws Exception {
        gremlinConnect gl =new gremlinConnect();
        Client client=gl.connectGremlinServer();
        String shstr ="g = graph.traversal();" +
                "g.V().has('company_id','%s').as('a').in('ShareHolder').as('b').out('ShareHolder').as('c').select('a','b','c').by(id)";
        String str ="g = graph.traversal();" +
                "g.V().has('company_id','%s').as('a').in('LegalRepresent').as('b').out('LegalRepresent').as('c').select('a','b','c').by(id)";
        shstr=String.format(shstr, this.companyname);
        str=String.format(str, this.companyname);
        System.out.println(str);
        List<Result> results =client.submit(str).all().get();
        List<Result> shresults =client.submit(shstr).all().get();
        Map<Object,Object> map2= new HashMap<Object,Object>();
        List<Map<Object,Object>> nodesml = new ArrayList<Map<Object,Object>>();
        List<Map<Object,Object>> rsml = new ArrayList<Map<Object,Object>>();
        Map<String ,List<Map<Object,Object>>> fr= createJson(results,nodesml,rsml,"LegalRepresent","法人",client);
        Map<String ,List<Map<Object,Object>>> shfr= createJson(shresults,nodesml,rsml,"ShareHolder","股东",client);
        List<Map<Object,Object>> ff =shfr.get("r");
        List<Map<Object,Object>> nn =shfr.get("n");
        map2.put("nodes",nn);
        map2.put("relationships",ff);
        JSONObject jsonObject = JSONObject.fromObject(map2);
        jsonObject.put("graph",jsonObject);
        jsonObject.remove("nodes");
        jsonObject.remove("relationships");
        System.out.println(jsonObject);
        return jsonObject;
    }
    public Map<String ,List<Map<Object,Object>>> createJson(List<Result> shresults,
                           List<Map<Object,Object>> nodesml,List<Map<Object,Object>> rsml,
                           String type,String typename,Client client) throws ExecutionException, InterruptedException {
        for (Result result : shresults) {
            Map<Object,Object> map1= new HashMap<Object,Object>();
            Map<Object,Object> map3= new HashMap<Object,Object>();
            Map<Object,Object> result1 = (Map<Object, Object>)result.getObject();
            if (map5.get(result1.get("a").toString()+type)==null){
                map5.put(result1.get("a").toString()+type,"1");
                Map<Object,Object> map=queryData(result1.get("a").toString() ,client );
                Map<Object,Object> nmap=cpmap(map,result1.get("a").toString());
                nodesml.add(nmap);
            }
            if (map5.get(result1.get("b").toString()+type)==null){
                map5.put(result1.get("b").toString()+type,"1");
                Map<Object,Object> map=queryData(result1.get("b").toString() ,client );
                Map<Object,Object> nmap=cpmap(map,result1.get("b").toString());
                nodesml.add(nmap);
            }
            if (map5.get(result1.get("c").toString()+type)==null){
                map5.put(result1.get("c").toString()+type,"1");
                Map<Object,Object> map=queryData(result1.get("c").toString() ,client );
                Map<Object,Object> nmap=cpmap(map,result1.get("c").toString());
                nodesml.add(nmap);
                map3.put("id",result1.get("b").toString()+result1.get("c").toString());
                map3.put("type",type);
                map3.put("startNode",result1.get("b").toString());
                map3.put("endNode",result1.get("c").toString());
                map3.put("properties",typename);
                rsml.add(map3);
            }
            map1.put("id",result1.get("a").toString()+result1.get("b").toString());
            map1.put("type",type);
            map1.put("startNode",result1.get("a").toString());
            map1.put("endNode",result1.get("b").toString());
            map1.put("properties",typename);
            rsml.add(map1);

        }
        Map<String ,List<Map<Object,Object>>> a = new HashMap<>();
        a.put("r",rsml);
        a.put("n",nodesml);
        return a;
    }
    //company map
    public Map<Object,Object> cpmap(Map<Object,Object> map,String ids){
        Map<Object,Object> nmap=new HashMap<>();
        if (map.get("company_id")==null){
            nmap.put("lable","Person");
        } else{
            nmap.put("lable","Company");
        }
        nmap.put("id",ids);
        nmap.put("properties",map);
        System.out.println(nmap);
        return nmap;
    }
    public Map<Object,Object> queryData(String ids, Client client) throws ExecutionException, InterruptedException {
        String str ="g = graph.traversal();" +
                "g.V(%s).valueMap()";
        str=String.format(str, ids);
        Result results =client.submit(str).all().get().get(0);
        Map<Object,Object> map = (Map<Object, Object>)results.getObject();
        return map;
    }

    public List<JSONObject> queryCompanyList() throws Exception {
        List<JSONObject> resList = new ArrayList<JSONObject>();
        gremlinConnect gl =new gremlinConnect();
        Client client=gl.connectGremlinServer();
        String str ="g = graph.traversal();" +
                " g.V().has('company_n',textContainsRegex('.*%s.*')).valueMap()";
        str=String.format(str, this.companyname);
        List<Result> results =client.submit(str).all().get();
        for (Result res :results) {
            Map<Object, Object> map = (Map<Object, Object>) res.getObject();
            JSONObject jsonObject = JSONObject.fromObject(map);
//            List comids = (List) map.get("company_id");
//            String comid= (String) comids.get(0);
//            List comnames = (List) map.get("company_n");
//            String comname= (String) comnames.get(0);
//            String idname = comid +"&"+ comname;
            resList.add(jsonObject);
        }
        return resList;
    }
}
