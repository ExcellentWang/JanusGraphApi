package movies.spring.data.neo4j.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.services.CompanyService;
import movies.spring.data.neo4j.services.MovieService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Angrish
 */
@RestController
@RequestMapping("/")
public class MovieController {

	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

    @GetMapping("/movie")
    public Movie findByTitle(@RequestParam String title) {
        return movieService.findByTitle(title);
    }

    @GetMapping("/movies")
    public Collection<Movie> findByTitleLike(@RequestParam String title) {
        return movieService.findByTitleLike(title);
    }

    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		Map<String,Object> a = new HashMap<String,Object>();
		a.put("a","bbbbbbbbbbbbbbbbbbbbb");
		return a;
//        return movieService.graph(limit == null ? 100 : limit);
	}
    @GetMapping("/company")
    public String company(@RequestParam("name") String name) throws Exception {
        CompanyService cs = new CompanyService(name);
        String jsonResult =cs.CompanyDao();
        return jsonResult;
    }
    @GetMapping("/legalperson")
    public String legalperson(@RequestParam("name") String name) throws Exception {
        CompanyService cs = new CompanyService(name);
        String jsonResult =cs.CompanyDao();
        return jsonResult;
    }
    @GetMapping("/shareholder")
    public String shareholder(@RequestParam("name") String name) throws Exception{
        CompanyService cs = new CompanyService(name);
        String jsonResult =cs.CompanyDao();
        return jsonResult;
    }
}
