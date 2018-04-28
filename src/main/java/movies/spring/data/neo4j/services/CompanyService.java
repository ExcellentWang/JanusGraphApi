package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.repositories.CompanyRepositories;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.junit.Test;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompanyService {
    String companyname;
    public CompanyService(String name){

        this.companyname=name;
    }

    public String CompanyDao() throws Exception {
        JSONObject jsonresult=new CompanyRepositories(this.companyname).queryCompanyData();
        JSONObject jsonR=HandleJson(jsonresult);
        return jsonR.toString();
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
    public List<JSONObject> queryCompany() throws Exception {
        List<JSONObject> result=new CompanyRepositories(this.companyname).queryCompanyList();
        return result;
    }
}
