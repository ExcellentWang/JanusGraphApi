package movies.spring.data.neo4j.repositories

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Property
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.janusgraph.core.JanusGraph
import org.janusgraph.core.JanusGraphFactory
import org.janusgraph.core.attribute.Text
import org.janusgraph.core.schema.JanusGraphManagement
import org.junit.Test
import sun.util.resources.cldr.en.CalendarData_en_AS

class test {
    def ioo(){
        JanusGraph graph = JanusGraphFactory.open('/home/wang/graphbase/janusgraph-0.2.0-hadoop2/conf/janusgraph-cassandra-es.properties');
//        JanusGraph graph = JanusGraphFactory.open('inmemory');
        JanusGraphManagement management = graph.openManagement();

        GraphTraversalSource g = graph.traversal()
      def v = g.addV().property("ha",'wo')
        def properties1 = v.properties()
        println properties1
        def properties = v.getProperties()
        println properties;
        println v;

    }
    def mytext(){
        JanusGraph graph = JanusGraphFactory.open('/home/wang/graphbase/janusgraph-0.2.0-hadoop2/conf/janusgraph-cassandra-es.properties');
//        JanusGraph graph = JanusGraphFactory.open('inmemory');
        JanusGraphManagement management = graph.openManagement();

        GraphTraversalSource g = graph.traversal()
        g.V().has('shareholder_id', Text.textContains('%s')).as('A').V().has('holder_id', Text.textContains('%s')).addE('股东').from('A');
        g.E().hasLabel("法定代表人").count()
        g.E().hasLabel("法定代表人").drop()
        g.V().hasLabel("company").limit(8).valueMap()
        g.V().has("company_n",Text.textContains("司")).values()
        g.E().groupCount()
    }
    @Test
    public void tt(){
        ioo()
    }
    @Test
    public void bb() throws Exception {
        String name="交通银行股份有限公司太平洋信用卡中心中山分中心";
        new CompanyRepositories(name).queryCompanyList();
    }
    @Test
    public void aa() throws Exception {
        String name="firm_f6175a3dadbdd73c8e13099ea9b65b35";
        new CompanyRepositories(name).queryCompanyData();
    }
    @Test
    public void cc() throws Exception {
        String name="pl_pa4a3f7b26687ffa46e2bad3e40e230f";
        new LegalpersonRepositories().queryLegalpersonData(name);
    }
    @Test
    public void vv(){
        Map<Object,Object> map5= new HashMap<Object,Object>();
        if (map5.get("a")==null){
            println "1111"
        }


    }
}
