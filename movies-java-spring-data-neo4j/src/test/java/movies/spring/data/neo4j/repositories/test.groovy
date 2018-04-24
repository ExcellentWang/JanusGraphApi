package movies.spring.data.neo4j.repositories

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Property
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.janusgraph.core.JanusGraph
import org.janusgraph.core.JanusGraphFactory
import org.janusgraph.core.schema.JanusGraphManagement
import org.junit.Test

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
    @Test
    public void tt(){
        ioo()
    }
}
