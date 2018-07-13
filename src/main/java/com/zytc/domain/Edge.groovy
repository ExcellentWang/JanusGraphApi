package com.zytc.domain

class Edge {


    String dbBase = ""
    String dbHost = ""
    String dbUser = ""
    String dbPass = ""
    String dbPort = ""
    String tableName = ""
    String fromColumn = ""
    String toColumn = ""
    Map fromV = null
    Map toV = null
    String edgeLabel = ""
    List property = null

    Edge() {
    }

    String getDbBase() {
        return dbBase
    }

    void setDbBase(String dbBase) {
        this.dbBase = dbBase
    }

    String getDbHost() {
        return dbHost
    }

    void setDbHost(String dbHost) {
        this.dbHost = dbHost
    }

    String getDbUser() {
        return dbUser
    }

    void setDbUser(String dbUser) {
        this.dbUser = dbUser
    }

    String getDbPass() {
        return dbPass
    }

    void setDbPass(String dbPass) {
        this.dbPass = dbPass
    }

    String getDbPort() {
        return dbPort
    }

    void setDbPort(String dbPort) {
        this.dbPort = dbPort
    }

    String getTableName() {
        return tableName
    }

    void setTableName(String tableName) {
        this.tableName = tableName
    }

    String getFromColumn() {
        return fromColumn
    }

    void setFromColumn(String fromColumn) {
        this.fromColumn = fromColumn
    }

    String getToColumn() {
        return toColumn
    }

    void setToColumn(String toColumn) {
        this.toColumn = toColumn
    }

    Map getFromV() {
        return fromV
    }

    void setFromV(Map fromV) {
        this.fromV = fromV
    }

    Map getToV() {
        return toV
    }

    void setToV(Map toV) {
        this.toV = toV
    }

    String getEdgeLabel() {
        return edgeLabel
    }

    void setEdgeLabel(String edgeLabel) {
        this.edgeLabel = edgeLabel
    }

    List getProperty() {
        return property
    }

    void setProperty(List property) {
        this.property = property
    }
}

