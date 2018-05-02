package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.repositories.CompanyRepositories;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class CompanyService {
    @Autowired
    private CompanyRepositories companyRepositories;

    public String CompanyDao(String name) throws Exception {
        JSONObject jsonresult=companyRepositories.queryCompanyData(name);
        return jsonresult.toString();
    }
    public JSONObject  HandleJson(JSONObject jsonresult){
        List<JSONObject> jsonList= (List<JSONObject>) jsonresult.get("nodes");
        List<JSONObject> jsall = new ArrayList<>();
        for (JSONObject jr :jsonList) {
            JSONObject js = new JSONObject();

            System.out.println(jr.get("company_n").getClass().toString());
            js.put("name",jr.get("company_n").toString());
            js.put("group",1);
            jsall.add(js);
        }
        JSONObject json = new JSONObject();
        json.put("success",jsall);
        System.out.println(json);
        return json;
    }
    public List<JSONObject> queryCompany(String name) throws Exception {
        List<JSONObject> result=companyRepositories.queryCompanyList(name);
        return result;
    }
}
