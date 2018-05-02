package movies.spring.data.neo4j.services;


import movies.spring.data.neo4j.repositories.CompanyRepositories;
import movies.spring.data.neo4j.repositories.LegalpersonRepositories;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegalpersonService {
    @Autowired
    private LegalpersonRepositories legalpersonRepositories;

    public String LegalpersonDao(String name) throws Exception {
        JSONObject jsonresult=legalpersonRepositories.queryLegalpersonData(name);
        return jsonresult.toString();
    }
}
