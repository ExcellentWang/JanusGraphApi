package com.zytc.repositories

import com.zytc.domain.Edge
import com.zytc.domain.Node
import org.apache.commons.jexl2.Expression
import org.apache.commons.jexl2.JexlContext
import org.apache.commons.jexl2.JexlEngine
import org.apache.commons.jexl2.MapContext
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.janusgraph.core.JanusGraph
import org.janusgraph.core.JanusGraphFactory
import org.janusgraph.core.schema.SchemaAction
import org.janusgraph.core.schema.SchemaStatus
import org.janusgraph.graphdb.database.management.ManagementSystem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

@Repository
class JanusGraphManageDao {
    JanusGraph graph = null
    GraphTraversalSource g = null
    ManagementSystem mgmt = null

    @Autowired
    StringRedisTemplate stringRedisTemplate


    JanusGraphManageDao() {

        this.graph = JanusGraphFactory.open(
                '/home/wang/IdeaProjects/GraphApi/src/main/resources/conf/janusgraph-cassandra-es-server.properties')
        this.g = graph.traversal()

    }

    //    节点schema声明
    boolean createSchema(List<String> labelList, List<String> propertyList, List<Map> indexList) {
        for (String label : labelList) {
            this.mgmt = (ManagementSystem) graph.openManagement()

            if (this.mgmt.getNodeLabel(label) == null) {
                this.mgmt.makeNodeLabel(label).make()
            }

            for (String property : propertyList) {
                if (this.mgmt.getPropertyKey(property) == null) {
                    this.mgmt.makePropertyKey(property).dataType(String.class).make()
                }
            }

            for (String index : indexList) {
                def indexkey1 = String.format("%s_%s_mixed_index", label, index)
                if (!this.mgmt.getGraphIndex(indexkey1)) {

                    mgmt.buildIndex(indexkey1, Node.class).addKey(mgmt.getPropertyKey(index)).indexOnly(mgmt.getNodeLabel(label)).buildMixedIndex('search')
                }
                def indexkey2 = String.format("%s_%s_composite_index", label, index)
                if (!this.mgmt.getGraphIndex(indexkey2)) {

                    mgmt.buildIndex(indexkey2, Node.class).addKey(mgmt.getPropertyKey(index)).indexOnly(mgmt.getNodeLabel(label)).buildCompositeIndex()
                }
            }

            this.mgmt.commit()

            this.mgmt = (ManagementSystem) graph.openManagement()

            for (String index : indexList) {
                def indexkey1 = String.format("%s_%s_composite_index", label, index)
                mgmt.awaitGraphIndexStatus(graph, indexkey1).status(SchemaStatus.REGISTERED, SchemaStatus.ENABLED).call()
                def indexkey2 = String.format("%s_%s_mixed_index", label, index)
                mgmt.awaitGraphIndexStatus(graph, indexkey2).status(SchemaStatus.REGISTERED, SchemaStatus.ENABLED).call()

            }
            this.mgmt.commit()

            this.mgmt = (ManagementSystem) graph.openManagement()

            for (String index : indexList) {
                def indexkey1 = String.format("%s_%s_composite_index", label, index)
                mgmt.updateIndex(mgmt.getGraphIndex(indexkey1), SchemaAction.REINDEX).get()
                def indexkey2 = String.format("%s_%s_mixed_index", label, index)
                mgmt.updateIndex(mgmt.getGraphIndex(indexkey2), SchemaAction.REINDEX).get()

            }
            this.mgmt.commit()
        }

//        for(Map index : indexList){
//
//        }


        return false

    }
    //    节点schema声明，所有proerty建立index
    boolean createSchema1(Node node) {
        try {
            this.mgmt = (ManagementSystem) graph.openManagement()

            if (this.mgmt.getVertexLabel(node.getLabel()) == null) {
                this.mgmt.makeVertexLabel(node.getLabel()).make()
            }

            for (String property : node.getProperty()) {
                if (this.mgmt.getPropertyKey(property) == null) {
                    this.mgmt.makePropertyKey(property).dataType(String.class).make()
                }
            }

            for (String index : node.getProperty()) {
                def indexkey1 = String.format("%s_%s_mixed_index", node.getLabel(), index)
                if (!this.mgmt.getGraphIndex(indexkey1)) {

                    mgmt.buildIndex(indexkey1, Vertex.class).addKey(mgmt.getPropertyKey(index)).indexOnly(mgmt.getVertexLabel(node.getLabel())).buildMixedIndex('search')
                }
                def indexkey2 = String.format("%s_%s_composite_index", node.getLabel(), index)
                if (!this.mgmt.getGraphIndex(indexkey2)) {

                    mgmt.buildIndex(indexkey2, Vertex.class).addKey(mgmt.getPropertyKey(index)).indexOnly(mgmt.getVertexLabel(node.getLabel())).buildCompositeIndex()
                }
            }

            this.mgmt.commit()

            this.mgmt = (ManagementSystem) graph.openManagement()

            for (String index : node.getProperty()) {
                def indexkey1 = String.format("%s_%s_composite_index", node.getLabel(), index)
                mgmt.awaitGraphIndexStatus(graph, indexkey1).status(SchemaStatus.REGISTERED, SchemaStatus.ENABLED).call()
                def indexkey2 = String.format("%s_%s_mixed_index", node.getLabel(), index)
                mgmt.awaitGraphIndexStatus(graph, indexkey2).status(SchemaStatus.REGISTERED, SchemaStatus.ENABLED).call()
            }
            this.mgmt.commit()

            this.mgmt = (ManagementSystem) graph.openManagement()

            for (String index : node.getProperty()) {
                def indexkey1 = String.format("%s_%s_composite_index", node.getLabel(), index)
                mgmt.updateIndex(mgmt.getGraphIndex(indexkey1), SchemaAction.REINDEX).get()
                def indexkey2 = String.format("%s_%s_mixed_index", node.getLabel(), index)
                mgmt.updateIndex(mgmt.getGraphIndex(indexkey2), SchemaAction.REINDEX).get()
            }
            this.mgmt.commit()
            return true
        } catch (Exception e) {
            println(e)
            return false
        }

    }
    //    添加节点
    String addNode(Node node, List<Map> dataMap) {
        for (Map map : dataMap) {
//验证redis
            Boolean member = stringRedisTemplate.opsForSet().isMember(String.format("%s_%s", node.getDataSource(), node.getTableName()), map.toString())
            if (member) {
                continue
            }
            def script4addV = "g.addV('%s')%s.next()"
            def buffer = new StringBuffer()

            for (String s1 : node.getProperty()) {
                def script4property = ".property('%s','%s')"
                def script4property_result = String.format(script4property, s1, map.get(s1).toString().replaceAll("[\n,\r]", ""))
                buffer.append(script4property_result)
            }

            def script4addV_result = String.format(script4addV, Node.getLabel(), buffer.toString())
            println(script4addV_result)
            Expression e = new JexlEngine().createExpression(script4addV_result)
            JexlContext jc = new MapContext()
            jc.set('g', this.g)
            String evaluate = e.evaluate(jc)
//加入redis
            stringRedisTemplate.opsForSet().add(String.format("%s_%s", node.getDataSource(), node.getTableName()), map.toString())
            println(evaluate)


        }
        this.g.tx().commit()
    }
    //    添加关系
    boolean addEdge(Edge edge) {
        String url = String.format("jdbc:mysql://%s:%s/%s", edge.getDbHost(), edge.getDbPort(), edge.getDbBase())

    }

}
