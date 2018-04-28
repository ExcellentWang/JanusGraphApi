package movies.spring.data.neo4j.repositories;
import movies.spring.data.neo4j.connect.gremlinConnect;
import net.sf.json.JSONObject;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CompanyRepositories1 {
    String companyname;
    public CompanyRepositories1(String companyname){
        this.companyname=companyname;
    }
    public JSONObject queryCompanyData() throws Exception {
        gremlinConnect gl =new gremlinConnect();
        Client client=gl.connectGremlinServer();
        String shstr ="g = graph.traversal();" +
                "g.V().has('company_n','%s').as('a').in('ShareHolder').as('b').out('ShareHolder').as('c').select('a','b','c').by(id)";
        String str ="g = graph.traversal();" +
                "g.V().has('company_n','%s').as('a').in('LegalRepresent').as('b').out('LegalRepresent').as('c').select('a','b','c').by(id)";
        shstr=String.format(shstr, this.companyname);
        str=String.format(str, this.companyname);
        System.out.print(shstr);
        System.out.print(str);
        List<Result> results =client.submit(str).all().get();
        System.out.println(results);
        List<Result> shresults =client.submit(shstr).all().get();
        Map<Object,Object> map2= new HashMap<Object,Object>();
        List<Map<Object,Object>> nodesml = new ArrayList<Map<Object,Object>>();
        List<Map<Object,Object>> rsml = new ArrayList<Map<Object,Object>>();
        Map<Object,Object> map5= new HashMap<Object,Object>();
        List fr= createJson(results,map5,nodesml,rsml,"LegalRepresent","法人",client);
        List sh= createJson(shresults,map5,nodesml,rsml,"ShareHolder","股东",client);
        fr.add(sh);
//        for (Result result : results) {
//            Map<Object,Object> map1= new HashMap<Object,Object>();
//            Map<Object,Object> map3= new HashMap<Object,Object>();
//            Map<Object,Object> result1 = (Map<Object, Object>)result.getObject();
//            if (map5.get(result1.get("a").toString()+"LegalRepresent")==null){
//                map5.put(result1.get("a").toString()+"LegalRepresent","1");
//                map=queryData(result1.get("a").toString() ,client );
//                map=cpmap(map,result1.get("a").toString());
//                nodesml.add(map);
//            }
//            if (map5.get(result1.get("b").toString()+"LegalRepresent")==null){
//                map5.put(result1.get("b").toString()+"LegalRepresent","1");
//                map=queryData(result1.get("b").toString() ,client );
//                map=cpmap(map,result1.get("b").toString());
//                nodesml.add(map);
//            }
//            if (map5.get(result1.get("c").toString()+"LegalRepresent")==null){
//                map5.put(result1.get("c").toString()+"LegalRepresent","1");
//                map=queryData(result1.get("c").toString() ,client );
//                map=cpmap(map,result1.get("c").toString());
//                nodesml.add(map);
//                map3.put("id",result1.get("b").toString()+result1.get("c").toString());
//                map3.put("type","LegalRepresent");
//                map3.put("startNode",result1.get("b").toString());
//                map3.put("endNode",result1.get("c").toString());
//                map3.put("properties","法人");
//                rsml.add(map3);
//            }
//            map1.put("id",result1.get("a").toString()+result1.get("b").toString());
//            map1.put("type","LegalRepresent");
//            map1.put("startNode",result1.get("a").toString());
//            map1.put("endNode",result1.get("b").toString());
//            map1.put("properties","法人");
//            rsml.add(map1);
//
//        }
//        for (Result result : shresults) {
//            Map<Object,Object> map1= new HashMap<Object,Object>();
//            Map<Object,Object> map3= new HashMap<Object,Object>();
//            Map<Object,Object> result1 = (Map<Object, Object>)result.getObject();
//            if (map5.get(result1.get("a").toString()+"ShareHolder")==null){
//                map5.put(result1.get("a").toString()+"ShareHolder","1");
//                map=queryData(result1.get("a").toString() ,client );
//                map=cpmap(map,result1.get("a").toString());
//                nodesml.add(map);
//            }
//            if (map5.get(result1.get("b").toString()+"ShareHolder")==null){
//                map5.put(result1.get("b").toString()+"ShareHolder","1");
//                map=queryData(result1.get("b").toString() ,client );
//                map=cpmap(map,result1.get("b").toString());
//                nodesml.add(map);
//            }
//            if (map5.get(result1.get("c").toString()+"ShareHolder")==null){
//                map5.put(result1.get("c").toString()+"ShareHolder","1");
//                map=queryData(result1.get("c").toString() ,client );
//                map=cpmap(map,result1.get("c").toString());
//                nodesml.add(map);
//                map3.put("id",result1.get("b").toString()+result1.get("c").toString());
//                map3.put("type","ShareHolder");
//                map3.put("startNode",result1.get("b").toString());
//                map3.put("endNode",result1.get("c").toString());
//                map3.put("properties","股东");
//                rsml.add(map3);
//            }
//            map1.put("id",result1.get("a").toString()+result1.get("b").toString());
//            map1.put("type","ShareHolder");
//            map1.put("startNode",result1.get("a").toString());
//            map1.put("endNode",result1.get("b").toString());
//            map1.put("properties","股东");
//            rsml.add(map1);
//
//        }
        map2.put("nodes",nodesml);
        map2.put("relationships",rsml);
        JSONObject jsonObject = JSONObject.fromObject(map2);
        jsonObject.put("graph",jsonObject);
        jsonObject.remove("nodes");
        jsonObject.remove("relationships");
        System.out.println(jsonObject);
        return jsonObject;
    }
    public List createJson(List<Result> shresults,Map<Object,Object> map5,
                           List<Map<Object,Object>> nodesml,List<Map<Object,Object>> rsml,
                           String type,String typename,Client client) throws ExecutionException, InterruptedException {
        List a = new ArrayList();
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
        a.add(rsml);
        a.add(nodesml);
        return a;
    }
    //company map
    public Map<Object,Object> cpmap(Map<Object,Object> map,String ids){
        Map<Object,Object> nmap=new HashMap<>();
        List<String> labl=new ArrayList<>();
        nmap.put("id",ids);
        nmap.put("lable",labl);
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
