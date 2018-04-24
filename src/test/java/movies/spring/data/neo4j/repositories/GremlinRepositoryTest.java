package movies.spring.data.neo4j.repositories;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Result;
//dupliaca import
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static movies.spring.data.neo4j.repositories.MySqlRepository.queryMysql;


//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;

public class GremlinRepositoryTest {
    private String filename = "/home/wang/IdeaProjects/movies-java-spring-data-neo4j/src/main/resources/conf/gremlin.yaml";


    private Map<String, String> querydict = new HashMap<String, String>();

    private Client client = null;

    public Map<String, String> getQuerydict() {
        querydict.put("traversal", "g = graph.traversal();");
        //get reprensent id from janusgraph
        querydict.put("getRepId", "g.V().where(hasLabel('representative')).values('chair_id');");

        //get share holder Id from janusgraph
        querydict.put("getShaId", "g.V().where(hasLabel('share_holder')).values('shareholder_id');");
        //add share hold relationship
        querydict.put("addShaRel",
                "g.V().has('shareholder_id', Text.textContains('%s')).as('A')" +
                        ".V().has('holder_id', Text.textContains('%s')).addE('股东').from('A');");
        //add legal reprentative relationship
        querydict.put("addLegRe", "g.V().has('chair_id', Text.textContains('%s')).as('A')" +
                ".V().has('repren_id', Text.textContains('%s')).addE('法定代表人').from('A');");
        //add chair man for janusgraph
        querydict.put("addRep", "g = graph.traversal();" +
                "g.addV(T.label,'representative').property('chair_name','%s')" +
                ".property('chair_id','%s');");
        querydict.put("", "");
        querydict.put("", "");
        querydict.put("", "");
        querydict.put("", "");
        querydict.put("", "");

        return querydict;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public GremlinRepositoryTest() throws Exception {
        setClient(connectGremlinServer());
    }

    //get connetion of gremlin
    public Client connectGremlinServer() throws Exception {
        try {
            Cluster cluster = Cluster.open(filename);
            Client client = cluster.connect();
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //    execute Gremlin
//    public List<Result> client.submit()(String gremlin) throws ExecutionException, InterruptedException .all().get(){
//        List<Result> results = client.submit(gremlin).all().get();
//        return results;
//    }


    // create gremlin schemal
//    private String creatSchema() {
//        StringBuffer creatCause = new StringBuffer();
//        //Edge Labels
////        creatCause.append("mgmt = graph.openManagement()\n" +
////                //法人关系
////                "legal_representative = mgmt.makeEdgeLabel('legal_representative').multiplicity(MULTI).make()\n" +
////                //股东关系
////                "share_holder = mgmt.makeEdgeLabel('share_holder').multiplicity(MULTI).make()\n" +
////                "mgmt.commit()\n");
//        //property
//        creatCause.append("mgmt = graph.openManagement()\n" +
//                "com_name = mgmt.makePropertyKey('company_n').dataType(Object.class).cardinality(Cardinality.LIST).make()\n" +
//                "com_name = mgmt.makePropertyKey('chair_name').dataType(Object.class).cardinality(Cardinality.LIST).make()\n" +
//                "com_name = mgmt.makePropertyKey('company_addr').dataType(Object.class).cardinality(Cardinality.LIST).make()\n" +
//                "com_name = mgmt.makePropertyKey('company_repres').dataType(Object.class).cardinality(Cardinality.LIST).make()\n" +
//                "com_name = mgmt.makePropertyKey('repren_id').dataType(Object.class).cardinality(Cardinality.LIST).make()\n" +
//                "com_name = mgmt.makePropertyKey('sharehold_id').dataType(Object.class).cardinality(Cardinality.LIST).make()\n" +
//                "com_name = mgmt.makePropertyKey('sharehold').dataType(Object.class).cardinality(Cardinality.LIST).make()\n" +
//                "mgmt.commit()\n");
//        //VertexLabel
//        creatCause.append("mgmt = graph.openManagement()\n" +
//                //公司
//                " company = mgmt.makeVertexLabel('company').make()\n" +
//                //法人
//                " representative = mgmt.makeVertexLabel('representative').make()\n" +
//                //股东
//                " shareholder = mgmt.makeVertexLabel('shareholder').make()\n" +
//                "mgmt.commit()\n");
//
//        return creatCause.toString();
//    }

    //create index of vertax in which index locate at it's property

//    private String creatIndex() {
//        StringBuffer creatCause = new StringBuffer();
//        String s = "g.tx().rollback()\n" +
//                "mgmt = graph.openManagement()\n" +
//                "mgmt.buildIndex('company_nLable', Vertex.class).addKey('company_n').buildCompositeIndex()\n" +
//                "mgmt.buildIndex('chair_nameLable', Vertex.class).addKey('chair_name').buildCompositeIndex()\n" +
//                "mgmt.buildIndex('company_addrLable', Vertex.class).addKey('company_addr').buildCompositeIndex()\n" +
//                "mgmt.buildIndex('company_represLable', Vertex.class).addKey('company_repres').buildCompositeIndex()\n" +
//                "mgmt.buildIndex('repren_idLable', Vertex.class).addKey('repren_id').buildCompositeIndex()\n" +
//                "mgmt.buildIndex('sharehold_idLable', Vertex.class).addKey('sharehold_id').buildCompositeIndex()\n" +
//                "mgmt.buildIndex('shareholdLable', Vertex.class).addKey('sharehold').buildCompositeIndex()\n" +
//                "mgmt.commit()\n";
//
//
//        return null;
//    }

    //query add company vertax
    private List<Result> addComany() throws Exception {
//
        List<Object> nodeList = queryMysql();

        List<Object> sublist = nodeList.subList(0, 3000);
        ArrayList<String> arrayList = new ArrayList<>();

        List<Result> results = null;

        try {
            for (Object map : sublist) {
                Map<String, String> datamap = (Map<String, String>) map;
                String str = "g = graph.traversal();" +
                        "g.addV(T.label,'company')" +
                        ".property('company_n','%s')" +
                        ".property('company_addr','%s')" +
                        ".property('company_representat','%s')" +
                        ".property('repren_id','%s')" +
                        ".property('holder_id','%s')" +
                        ".property('holder_name','%s');";

                str = String.format(str, CommonUtil.replaceSpecStr(datamap.get("company_n")),
                        CommonUtil.replaceSpecStr(datamap.get("company_addr")),
                        CommonUtil.replaceSpecStr(datamap.get("company_representat")),
                        datamap.get("repren_id"),
                        datamap.get("sharehold_id"),
                        CommonUtil.replaceSpecStr(datamap.get("sharehold"))
                );

                results = client.submit(str).all().get();
            }
        } finally {
            client.close();
        }
        return results;
    }

    //query add representative
    private List<Result> addChair() throws Exception {
        List<Object> nodeList = queryMysql();
        List<Object> sublist = nodeList.subList(0, 3000);

        List<Result> results = null;
        try {
            for (Object map : sublist) {
                Map<String, String> datamap = (Map<String, String>) map;
                String str = getQuerydict().get("addRep");
                if (datamap.get("repren_id").length() < 7) {
                    continue;
                }
                str = String.format(str, CommonUtil.replaceSpecStr(datamap.get("company_representat")),
                        datamap.get("repren_id")
                );
                results = client.submit(str).all().get();
            }
        } finally {
            client.close();
        }
        return results;
    }

    //query add Shareholder
    private List<Result> addShareHolder() throws ExecutionException, InterruptedException {
        List<Object> nodeList = queryMysql();


        List<Object> sublist = nodeList.subList(0, 3000);
        ArrayList<String> arrayList = new ArrayList<>();
//        stringBuffer.append("g = graph.traversal()\n");

        List<Result> results = null;
        try {
            for (Object map : sublist) {

                Map<String, String> datamap = (Map<String, String>) map;
                String[] shareholds = datamap.get("sharehold").split(",");
                String[] sharehold_ids = datamap.get("sharehold_id").split(",");

                try {
                    for (int i = 0; i < sharehold_ids.length; i++) {
                        String str = "g = graph.traversal();" +
                                "g.addV(T.label,'share_holder')" +
                                ".property('shareholder_id','%s')" +
                                ".property('holder_name','%s');";

                        str = String.format(str, sharehold_ids[i], shareholds[i]);

                        results = client.submit(str).all().get();
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } finally {
            client.close();
        }
        return results;
    }


    //Add relationship of reprsentative
    private List<Result> addLagRela() throws Exception {
        List<Result> resultslist = null;
        String getRepId = getQuerydict().get("getRepId");
        List<Result> dataresult = client.submit(getRepId).all().get();

        try {

            for (Result result : dataresult) {
                Object chairId = result.getObject();

                String str = getQuerydict().get("addLegRe");
                String repren_id1 = "";
                if (chairId.toString().length() > 15) {
                    repren_id1 = chairId.toString();
                } else {
                    continue;
                }

                String repren_id = repren_id1;
                str = String.format(str, repren_id, repren_id);
                resultslist = client.submit(str).all().get();
            }
        } finally {
            client.close();
        }
        return resultslist;
    }

    private List<Object> addShareRela() throws Exception {
        List<Object> resultslist = null;
        String query = "";
        String getShaId = getQuerydict().get("getShaId");
        List<Result> dataresult = client.submit(getShaId).all().get();
        try {
            for (Result result : dataresult) {
                Object holderId = result.getObject();
                String str = getQuerydict().get("addShaRel");

                if (holderId.toString().length() > 15) {
                    str = String.format(str, holderId, holderId);
                } else {
                    continue;
                }

                List<Result> results = client.submit(str).all().get();

            }
        } finally {
            client.close();
        }
        return resultslist;
    }

    //Add shareHolder relation ship
    private void addShaRela() throws Exception {
        List<Object> resultslist = null;
        String query = "";
        List<Result> dataresult = client.submit(query).all().get();
        StringBuffer stringBuffer = new StringBuffer();
        List<Result> results2 = dataresult.subList(1, 200);
        for (Result result : results2) {
            System.out.println(result);
            Map resultMap = (Map) result.getObject();

            String str = "g = graph.traversal()\n" +
                    "g.V().has('repren_id', within('%s')).as('A')" +// 节点一
                    ".V().has('chair_id', within('%s')).addE('法人').from('A')\n" //
                    ;
            String repren_id1 = "";
            if (resultMap.get("holder_id") != null) {
                repren_id1 = resultMap.get("holder_id").toString();
            } else if (resultMap.get("chair_id").toString() != null) {
                repren_id1 = resultMap.get("chair_id").toString();
            } else {
                continue;
            }

            String repren_id = CommonUtil.replaceSpecStr(repren_id1);
            str = String.format(str, repren_id, repren_id);
            stringBuffer.append(str);
//            stringBuffer.append("g.tx().commit()\n");
            String insert = stringBuffer.toString();
            System.out.println(insert);

            try {
                List<Result> results = client.submit(insert).all().get();
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * 声明schemal
     *
     * @throws Exception
     */
//    @Test
//    public void stateSchemal() throws Exception {
//        String gremlinQuery = creatSchema();
//        System.out.println(gremlinQuery);
//
//        List<Result> results1 = client.submit(gremlinQuery).all().get();
//
//        System.out.println(results1);
//    }

    /**
     * 添加企业Node
     */
    @Test
    public void addComNode() throws Exception {
        addComany();

//
    }

    /**
     * 添加企业法人
     */
    @Test
    public void addChairNode() throws Exception {
        addChair();
    }

    /**
     * Create shareholder relationship
     */
    @Test
    public void addShaNode() throws Exception {
        addShareHolder();
    }

    /**
     * Create reprentative relationship
     */
    @Test
    public void addRelationRep() throws Exception {
        addLagRela();
    }

    /**
     * 添加股东
     */
    @Test
    public void addRelationSha() throws Exception {
        addShareRela();
    }
//    public void addindex() {
//        creatIndex();
//    }
}



