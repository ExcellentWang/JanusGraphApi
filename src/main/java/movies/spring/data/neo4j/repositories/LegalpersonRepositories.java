package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.connect.gremlinConnect;
import net.sf.json.JSONObject;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class LegalpersonRepositories {


    Map<Object,Object> map5= new HashMap<Object,Object>();
    private Client client;
    public LegalpersonRepositories() throws Exception {
        this.client=new gremlinConnect().connectGremlinServer();
    }
    public JSONObject queryLegalpersonData(String name) throws Exception {
        String shstr ="g.V().hasLabel('Person').has('person_id','%s').as('a').out().as('b').in('ShareHolder').as('c').select('a','b','c').by(id).dedup()";
        String str ="g.V().hasLabel('Person').has('person_id','%s').as('a').out().as('b').in('LegalRepresent').as('c').select('a','b','c').by(id).dedup()";
        String estr ="g.V().hasLabel('Person').has('person_id','%s').as('a').out().as('b').in('Employee').as('c').select('a','b','c').by(id).dedup()";
        shstr=String.format(shstr, name);
        str=String.format(str, name);
        estr=String.format(estr, name);
        System.out.println(shstr);
        System.out.println(str);
        System.out.println(estr);
        List<Result> results =client.submit(str).all().get();
        List<Result> shresults =client.submit(shstr).all().get();
        List<Result> eresults =client.submit(estr).all().get();
        Map<Object,Object> map2= new HashMap<Object,Object>();
        List<Map<Object,Object>> nodesml = new ArrayList<Map<Object,Object>>();
        List<Map<Object,Object>> rsml = new ArrayList<Map<Object,Object>>();
        Map<String ,List<Map<Object,Object>>> fr= createJson(results,nodesml,rsml,"LegalRepresent","法人",client);
        Map<String ,List<Map<Object,Object>>> shfr= createJson(shresults,nodesml,rsml,"ShareHolder","股东",client);
        Map<String ,List<Map<Object,Object>>> mainP= createJson(eresults,nodesml,rsml,"Employee","主要人员",client);
        List<Map<Object,Object>> ff =mainP.get("r");
        List<Map<Object,Object>> nn =mainP.get("n");
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
        if (map.get("person_id")==null){
            List ids1 = (List) map.get("company_id");
            List name1 =(List) map.get("company_n");
            String id= (String) ids1.get(0);
            map.put("keyNo",id);
            map.put("name",name1.get(0));
        }else{
            List comids = (List) map.get("person_id");
            List name1 =(List) map.get("person_name");
            String comid= (String) comids.get(0);
            map.put("keyNo",comid);
            map.put("name",name1.get(0));
        }
        if (map.get("company_id")==null){
            nmap.put("label","Person");
        } else{
            nmap.put("label","Company");
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
}