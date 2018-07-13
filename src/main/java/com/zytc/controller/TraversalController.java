package com.zytc.controller;

import com.zytc.domain.Vertax;
import com.zytc.services.JanusGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mark Angrish
 */
@RestController
@RequestMapping("/traversal")
public class TraversalController {

    @Autowired
    private JanusGraphService janusGraphService;

    @GetMapping("/graph")
    public Map<String, Object> graph(@RequestParam(value = "limit", required = false) Integer limit) {
        Map<String, Object> a = new HashMap<String, Object>();
        a.put("a", "bbbbbbbbbbbbbbbbbbbbb");
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

    public Map<String, Object> addVertax(@RequestBody List<Vertax> vertaxList) throws InterruptedException {
        boolean vertax = janusGraphService.createVertax(vertaxList);
        if (vertax) {
            Map<String, Object> success = new HashMap<String, Object>();
            success.put("msg", "Execute Successfully ");
            return success;
        }
        Map<String, Object> failed = new HashMap<String, Object>();
        failed.put("msg", "Execute Failed ");
        return failed;

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
