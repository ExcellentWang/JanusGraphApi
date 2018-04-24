package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.repositories.ShareholderRepositories;

public class ShareholderService {
    String shareholdername;
    public ShareholderService(String name){
        this.shareholdername=name;
    }
    public String CompanyDao() throws Exception {
        String jsonresult=new ShareholderRepositories(this.shareholdername).queryLegalpersonData();
        return jsonresult.substring(0,jsonresult.length() - 1);
    }
}
