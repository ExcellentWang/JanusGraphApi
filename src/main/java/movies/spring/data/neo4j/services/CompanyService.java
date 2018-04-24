package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.repositories.CompanyRepositories;
import org.apache.tinkerpop.gremlin.driver.Result;

import javax.json.Json;
import java.util.List;
import java.util.Map;

public class CompanyService {
    String companyname;
    public CompanyService(String name){
        this.companyname=name;
    }
    public String CompanyDao() throws Exception {
        String jsonresult=new CompanyRepositories(this.companyname).queryCompanyData();
        return jsonresult.substring(0,jsonresult.length() - 1);
    }
}
