package movies.spring.data.neo4j.services;


import movies.spring.data.neo4j.repositories.LegalpersonRepositories;

public class LegalpersonService {
    String legalpersonname;
    public LegalpersonService(String name){
        this.legalpersonname=name;
    }
    public String CompanyDao() throws Exception {
        String jsonresult=new LegalpersonRepositories(this.legalpersonname).queryLegalpersonData();
        return jsonresult.substring(0,jsonresult.length() - 1);
    }
}
