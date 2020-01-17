package it.univpm.esameDicembre.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.esameDicembre.Model.Element;
import it.univpm.esameDicembre.Model.Metadata;
import it.univpm.esameDicembre.Model.Stats;
import it.univpm.esameDicembre.Service.AppService;


@RestController
public class AppController {
	
	@Autowired
	AppService appService; 
	
	
	@GetMapping("/data")
	public ArrayList<Element> data() {
		return appService.printdata();
	}

	
	@GetMapping("/metadata")
	public ArrayList<Metadata> metadata() {
		return appService.printMetadata();
	}
	
	
	@GetMapping("/data/{index}")
	public Element element(@PathVariable int index) {
		return appService.printElement(index);
	}
	
	
	@GetMapping("/{operation}/{field}")
	public Stats operation(@PathVariable("field") String field, @PathVariable("operation") String operation) {
		return appService.calculus(field, operation);
	}
	
	@GetMapping("/average/{field}")
	public Stats average(@PathVariable("field") String field) {
		return appService.avg(field);
	}
	
	@GetMapping("/stats/{field}")
	public ArrayList<Stats> stats(@PathVariable("field") String field) {
		return appService.Statistics(field);
	}
	
	@GetMapping("/uniqueElements/{field}")
	public HashMap<String, Integer> uniqueElem(@PathVariable String field)
			throws NoSuchMethodException, IllegalAccessException, RuntimeException, ReflectiveOperationException {
		return appService.counter(field);
	}
	
	
	@GetMapping("/filter/{field1}/{word}/{field2}/{operator}/{value}")
	public ArrayList<Element> filter(@PathVariable("field1") String field1, @PathVariable("word") String word, @PathVariable("field2") String field2, @PathVariable("operator") String operator,
			@PathVariable("value") float value) {
		return appService.multifilter(field1, word, field2, operator, value);
	}
	
	@GetMapping("/stringFilter/{field}/{word}")
	public ArrayList<Element> stringFilter(@PathVariable("field") String field, @PathVariable("word") String word) {
		return appService.filter(field, "=", word);
	}
	
	@GetMapping("/valueFilter/{field}/{operator}/{value}")
	public ArrayList<Element> valueFilter(@PathVariable("field") String field,
			@PathVariable("operator") String operator, @PathVariable("value") float value) {
		return appService.filter(field, operator, value);
	}
	
	@GetMapping("/valueFilter/{field}/{operator1}/{value1}/{logicOperator}/{operator2}/{value2}")
	public ArrayList<Element> valueFilter(@PathVariable("field") String field, @PathVariable("operator1") String operator1,
			@PathVariable("value1") float value1, @PathVariable("logicOperator") String logicOperator,
			@PathVariable("operator2") String operator2, @PathVariable("value2") float value2) {
		return appService.multifiltervalue(field, logicOperator, operator1, value1, operator2, value2);
	}
	
	
	@PostMapping("/data")
	public ArrayList<Element> insert(@RequestBody Element body) {
		return appService.addBody(body);
	}
	
	
	@DeleteMapping("/deleteElement/{index}")
	public ArrayList<Element> deleteValue(@PathVariable int index) {
		return appService.deleteVal(index);
	}

	
	@DeleteMapping("/deletefilter/{field}/{operator}/{value}")
	public ArrayList<Element> delete(@PathVariable("field") String field,
			@PathVariable("operator") String operator, @PathVariable("value") float value) {
			return appService.deleteMoreVal(field, operator, value);
	}	
}




