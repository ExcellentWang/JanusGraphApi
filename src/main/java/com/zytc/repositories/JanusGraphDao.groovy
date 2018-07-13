package com.zytc.repositories

import com.zytc.domain.Edge
import com.zytc.domain.Vertax
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
import org.springframework.stereotype.Repository

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

@Repository
class JanusGraphDao {
    JanusGraph graph = null
    GraphTraversalSource g = null
    ManagementSystem mgmt = null
//    jdbc连接对象
    Connection conn = null
//    jdbc连接配置
//    String driver = "com.mysql.jdbc.Driver"
//    String url = "jdbc:mysql://192.168.1.214:4000/spiderData"
//    String username = "dbuser"
//    String password = "dbuserDev123"


    JanusGraphDao() {

        this.graph = JanusGraphFactory.open(
                '/home/wang/IdeaProjects/GraphApi/src/main/resources/conf/janusgraph-cassandra-es-server.properties')
        this.g = graph.traversal()

    }

    Connection getMyslqCon(String url,String username,String password){
        try {
            Class.forName("com.mysql.jdbc.Driver") //classLoader,加载对应驱动
            this.conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace()
        } catch (SQLException e) {
            e.printStackTrace()
        }
        return this.conn
    }
    //    节点schema声明
    boolean createSchema(List<String> labelList, List<String> propertyList, List<Map> indexList) {
        for (String label : labelList) {
            this.mgmt = (ManagementSystem) graph.openManagement()

            if (this.mgmt.getVertexLabel(label) == null) {
                this.mgmt.makeVertexLabel(label).make()
            }

            for (String property : propertyList) {
                if (this.mgmt.getPropertyKey(property) == null) {
                    this.mgmt.makePropertyKey(property).dataType(String.class).make()
                }
            }

            for (String index : indexList) {
                def indexkey1 = String.format("%s_%s_mixed_index", label, index)
                if (!this.mgmt.getGraphIndex(indexkey1)) {

                    mgmt.buildIndex(indexkey1, Vertex.class).addKey(mgmt.getPropertyKey(index)).indexOnly(mgmt.getVertexLabel(label)).buildMixedIndex('search')
                }
                def indexkey2 = String.format("%s_%s_composite_index", label, index)
                if (!this.mgmt.getGraphIndex(indexkey2)) {

                    mgmt.buildIndex(indexkey2, Vertex.class).addKey(mgmt.getPropertyKey(index)).indexOnly(mgmt.getVertexLabel(label)).buildCompositeIndex()
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
    //    添加节点
    boolean addVertax(List<Map> dataList, Vertax vertax) {

        for (String s : vertax.getLabelList()) {
            for (Map map : dataList) {

                def script4addV = "g.addV('%s')%s.next()"
                def buffer = new StringBuffer()

                for (String s1 : vertax.getProperty()) {
                    def script4property = ".property('%s','%s')"
                    def script4property_result = String.format(script4property, s1, map.get(s1).toString().replaceAll("[\n,\r]", ""))
                    buffer.append(script4property_result)
                }

                def script4addV_result = String.format(script4addV, s, buffer.toString())
                println(script4addV_result)
                Expression e = new JexlEngine().createExpression(script4addV_result)
                JexlContext jc = new MapContext()
                jc.set('g', this.g)
                def evaluate = e.evaluate(jc)
                println(evaluate)
            }
        }
        this.g.tx().commit()

    }
    //添加关系
    void addEdge(Edge edge) {
        String url = String.format("jdbc:mysql://%s:%s/%s",edge.getDbHost(),edge.getDbPort(),edge.getDbBase())
        def con = getMyslqCon(url,edge.getDbUser(),edge.getDbPass())


    }

}
