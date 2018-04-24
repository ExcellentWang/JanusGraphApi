package movies.spring.data.neo4j.repositories;
import com.thinkaurelius.titan.core.attribute.Text;
import javafx.beans.binding.StringBinding;
import movies.spring.data.neo4j.connect.gremlinConnect;
import net.sf.json.JSONObject;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyRepositories {
    public String queryCompanyData(String name) throws Exception {
        gremlinConnect gl =new gremlinConnect();
        Client client=gl.connectGremlinServer();

        String str = "g = graph.traversal();" +
                "g.V().has(\"holder_name\",Text.textContains(\"%s\")).valueMap()";
        str=String.format(str, name);
        System.out.print(str);
        List<Result> results =client.submit(str).all().get();
        String jsonresult=null;
        StringBuilder sb=  new StringBuilder();

        for (Result result : results) {
            Map<Object,Object> result1 = (Map<Object, Object>)result.getObject();
            JSONObject jsonObject = JSONObject.fromObject(result1);
            jsonresult = jsonObject.toString();
            sb.append(jsonresult+"!");
            System.out.print(result1.get("holder_name"));
        }

        return sb.toString().toLowerCase();
    }
}
