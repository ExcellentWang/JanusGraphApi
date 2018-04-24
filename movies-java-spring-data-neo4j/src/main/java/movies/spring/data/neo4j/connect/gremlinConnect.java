package movies.spring.data.neo4j.connect;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;

public class gremlinConnect {
    String filename="D:\\study\\movies-java-spring-data-neo4j_20180423\\movies-java-spring-data-neo4j\\src\\main\\resources\\conf\\gremlin.yaml";
    public Client connectGremlinServer() throws Exception {
        try {
            this.getClass().getClassLoader().getResources(filename);
            Cluster cluster = Cluster.open(filename);
            Client client = cluster.connect();
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
