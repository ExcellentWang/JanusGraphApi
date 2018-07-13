package com.zytc.services;

import java.util.*;

import com.zytc.domain.Edge;
import com.zytc.domain.Vertax;
import com.zytc.repositories.JanusGraphDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JanusGraphService  {

    @Autowired
    JanusGraphDao janusGraphDao;
    public boolean createVertax(List<Vertax> vertaxList) throws InterruptedException {
        for (int i = 0 ;i< 9;i++){
        System.out.println(UUID.randomUUID());
        }
        return true;
    }

    public boolean createEdge(Edge edge)throws InterruptedException {
        System.out.println(edge);
        janusGraphDao.addEdge(edge);
        return true;
    }


}
