package com.zytc.domain

import org.springframework.context.annotation.Bean

import java.beans.beancontext.BeanContext

class Vertax {

    String dbName = ""
    String dataSource = "spiderData"
    String tableName = ""
    String vertaxName = ""
    List<String> label = null
    List<String> property = null
    List<String> index = null

    Vertax() {

    }

    Vertax(String dataSource, String tableName, List labelList, List property, List index) {
        this.dataSource = dataSource
        this.tableName = tableName
        this.labelList = labelList
        this.property = property
        this.index = index
    }

    String getDbName() {
        return dbName
    }

    void setDbName(String dbName) {
        this.dbName = dbName
    }

    List getLabel() {
        return label
    }

    void setLabel(List label) {
        this.label = label
    }

    String getVertaxname() {
        return vertaxName
    }

    void setVertaxname(String vertaxName) {
        this.vertaxName = vertaxName
    }
    String getDataSource() {
        return dataSource
    }

    void setDataSource(String dataSource) {
        this.dataSource = dataSource
    }

    String getTableName() {
        return tableName
    }

    void setTableName(String tableName) {
        this.tableName = tableName
    }

    List getLabelList() {
        return labelList
    }

    void setLabelList(List labelList) {
        this.labelList = labelList
    }

    List getProperty() {
        return property
    }

    void setProperty(List property) {
        this.property = property
    }

    List getIndex() {
        return index
    }

    void setIndex(List index) {
        this.index = index
    }
}