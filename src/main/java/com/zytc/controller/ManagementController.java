package com.zytc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zytc.domain.Edge;
import com.zytc.domain.Vertax;
import com.zytc.services.JanusGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mark Angrish
 */
@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private JanusGraphService janusGraphService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @GetMapping("/")
    public Map<Object, Object> testMutiProcess(){
        taskExecutor.execute(() -> {
            try {
                boolean vertax = janusGraphService.createVertax(new ArrayList<>());
                System.out.println(vertax);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("rrr","iii");
        return objectObjectHashMap;
    }

    @GetMapping("/graph")
    public Map<String, Object> graph(@RequestParam(value = "limit", required = false) Integer limit) {
        Map<String, Object> a = new HashMap<String, Object>();
        a.put("a", "bbbbbbbbbbbbbbbbbbbbb");

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(() -> {
                try {
                    System.out.println(index);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        return a;
//        return movieService.graph(limit == null ? 100 : limit);
    }

    /**
     * @param
     * @return
     * @RequestMapping(value = "/stuffs", method = RequestMethod.POST)
     * public String invoices(@RequestBody Map<String, Object> [] stuffs) {
     * <p>
     * return "Hooray";
     * }
     */

//	@PostMapping(value = "/addvertax",produces="application/json")
    @RequestMapping(value = {"/addvertax", ""}, method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    public Map<String, Object> addVertax(@RequestBody List<Vertax> vertaxList) {
//        boolean vertax = janusGraphService.createVertax(vertaxList);

        Map<String, Object> success = new HashMap<String, Object>();
        success.put("msg", "Execute Successfully");
        return success;
//        return movieService.graph(limit == null ? 100 : limit);
    }

    @RequestMapping(value = {"/addedge", ""}, method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    public Map<String, Object> addEdge(@RequestBody Edge edge) {

        taskExecutor.execute(() -> {
            try {
                boolean edgeRes = janusGraphService.createEdge(edge);
                System.out.println(edgeRes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Map<String, Object> success = new HashMap<String, Object>();
        success.put("msg", "Execute in Running ");
        return success;


//        return movieService.graph(limit == null ? 100 : limit);
    }


//    @GetMapping("/companyList")
//    public List<JSONObject> companyLsit(@RequestParam("name") String name) throws Exception {
//        List<JSONObject> result =companyService.queryCompany(name);
//        return result;
//    }
//    @GetMapping("/companyGraph")
//    public String company(@RequestParam("ids") String ids) throws Exception {
//        MysqlxDatatypes.Scalar.String jsonResult =companyService.CompanyDao(ids);
//        return jsonResult;
//    }
//    @GetMapping("/legalpersonGraph")
//    public String legalperson(@RequestParam("name") String name) throws Exception {
//        String jsonResult =legalpersonService.LegalpersonDao(name);
//        return jsonResult;
//    }
//    @GetMapping("/shareholderGraph")
//    public String shareholder(@RequestParam("name") String name) throws Exception{
//        CompanyService cs = new CompanyService();
//        String jsonResult =cs.CompanyDao(name);
//        return jsonResult;
//    }
}
