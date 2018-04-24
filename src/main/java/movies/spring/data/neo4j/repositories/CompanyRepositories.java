package movies.spring.data.neo4j.repositories;
import com.thinkaurelius.titan.core.attribute.Text;
import javafx.beans.binding.StringBinding;
import movies.spring.data.neo4j.connect.gremlinConnect;
import net.sf.json.JSONObject;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyRepositories {
    String companyname;
    public CompanyRepositories(String companyname){
        this.companyname=companyname;
    }
    public JSONObject queryCompanyData() throws Exception {
        gremlinConnect gl =new gremlinConnect();
        Client client=gl.connectGremlinServer();

//        String str = "g = graph.traversal();" +
//                "g.V().has('company_n','%s').in('法定代表人').out('法定代表人').valueMap()";
        String str = "g = graph.traversal();" +
                "g.V().has('company_n','%s').in('ShareHold').out('ShareHold').valueMap()";
        str=String.format(str, this.companyname);
        System.out.print(str);
        List<Result> results =client.submit(str).all().get();
//        String jsonresult;
//        StringBuilder sb=  new StringBuilder();
        Map<Object,Object> map2= new HashMap<Object,Object>();
        List<Map<Object,Object>> ml = new ArrayList<Map<Object,Object>>();
        for (Result result : results) {
            Map<Object,Object> result1 = (Map<Object, Object>)result.getObject();
            ml.add(result1);
            System.out.print(result1.get("holder_name"));
        }
        map2.put("nodes",ml);
        JSONObject jsonObject = JSONObject.fromObject(map2);
        System.out.println(jsonObject);
        return jsonObject;
    }
}
