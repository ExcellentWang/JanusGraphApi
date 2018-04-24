package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.repositories.CompanyRepositories;
import org.apache.tinkerpop.gremlin.driver.Result;

import javax.json.Json;
import java.util.List;
import java.util.Map;

public class CompanyService {
    public String CompanyDao(String company) throws Exception {
        String jsonresult=new CompanyRepositories().queryCompanyData(company);
        return jsonresult.substring(0,jsonresult.length() - 1);
    }
}
