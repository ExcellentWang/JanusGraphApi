package movies.spring.data.neo4j.repositories

import org.junit.Test

class gremlin {
    @Test
    public void ow() {
        StringBuffer s = new StringBuffer();
        s.append("JanusGraphManagement management = graph.openManagement(); ");
        s.append("boolean created = false; ");

        // naive check if the schema was previously created
        s.append(
                "if (management.getRelationTypes(RelationType.class).iterator().hasNext()) { management.rollback(); created = false; } else { ");

        // properties
        s.append("PropertyKey name = management.makePropertyKey(\"name\").dataType(String.class).make(); ");
        s.append("PropertyKey age = management.makePropertyKey(\"age\").dataType(Integer.class).make(); ");
        s.append("PropertyKey time = management.makePropertyKey(\"time\").dataType(Integer.class).make(); ");
        s.append("PropertyKey reason = management.makePropertyKey(\"reason\").dataType(String.class).make(); ");
        s.append("PropertyKey place = management.makePropertyKey(\"place\").dataType(Geoshape.class).make(); ");

        // vertex labels
        s.append("management.makeVertexLabel(\"titan\").make(); ");
        s.append("management.makeVertexLabel(\"location\").make(); ");
        s.append("management.makeVertexLabel(\"god\").make(); ");
        s.append("management.makeVertexLabel(\"demigod\").make(); ");
        s.append("management.makeVertexLabel(\"human\").make(); ");
        s.append("management.makeVertexLabel(\"monster\").make(); ");

        // edge labels
        s.append("management.makeEdgeLabel(\"father\").multiplicity(Multiplicity.MANY2ONE).make(); ");
        s.append("management.makeEdgeLabel(\"mother\").multiplicity(Multiplicity.MANY2ONE).make(); ");
        s.append("management.makeEdgeLabel(\"lives\").signature(reason).make(); ");
        s.append("management.makeEdgeLabel(\"pet\").make(); ");
        s.append("management.makeEdgeLabel(\"brother\").make(); ");
        s.append("management.makeEdgeLabel(\"battled\").make(); ");

        // composite indexes
        s.append("management.buildIndex(\"nameIndex\", Vertex.class).addKey(name).buildCompositeIndex(); ");

        // mixed indexes

        s.append("management.buildIndex(\"vAge\", Vertex.class).addKey(age).buildMixedIndex(); ");
        s.append("management.buildIndex(\"eReasonPlace\", Edge.class).addKey(reason).addKey(place).buildMixedIndex(); ");


        s.append("management.commit(); created = true; }");
        def string = s.toString()

        println string
        String ss = "JanusGraphManagement management = graph.openManagement();" +
                " boolean created = false;" +
                " if (management.getRelationTypes(RelationType.class).iterator().hasNext()) { management.rollback();" +
                " created = false;" +
                " } else { PropertyKey name = management.makePropertyKey(\"name\").dataType(String.class).make();" +
                " PropertyKey age = management.makePropertyKey(\"age\").dataType(Integer.class).make();" +
                " PropertyKey time = management.makePropertyKey(\"time\").dataType(Integer.class).make();" +
                " PropertyKey reason = management.makePropertyKey(\"reason\").dataType(String.class).make();" +
                " PropertyKey place = management.makePropertyKey(\"place\").dataType(Geoshape.class).make();" +
                " management.makeVertexLabel(\"titan\").make();" +
                " management.makeVertexLabel(\"location\").make();" +
                " management.makeVertexLabel(\"god\").make();" +
                " management.makeVertexLabel(\"demigod\").make();" +
                " management.makeVertexLabel(\"human\").make();" +
                " management.makeVertexLabel(\"monster\").make();" +
                " management.makeEdgeLabel(\"father\").multiplicity(Multiplicity.MANY2ONE).make();" +
                " management.makeEdgeLabel(\"mother\").multiplicity(Multiplicity.MANY2ONE).make();" +
                " management.makeEdgeLabel(\"lives\").signature(reason).make();" +
                " management.makeEdgeLabel(\"pet\").make();" +
                " management.makeEdgeLabel(\"brother\").make();" +
                " management.makeEdgeLabel(\"battled\").make();" +
                " management.buildIndex(\"nameIndex\", Vertex.class).addKey(name).buildCompositeIndex();" +
                " management.buildIndex(\"vAge\", Vertex.class).addKey(age).buildMixedIndex();" +
                " management.buildIndex(\"eReasonPlace\", Edge.class).addKey(reason).addKey(place).buildMixedIndex();" +
                " management.commit();" +
                " created = true;" +
                " }"

    }
}
