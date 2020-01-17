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
	

	@GetMapping("/data/{index}")
	public Element data(@PathVariable int index) {
			return appService.printElement(index);
	}
	

	@GetMapping("/data")
	public ArrayList<Element> dataAll() {
			return appService.printElement();
	}

	@GetMapping("/{operation}/{field}")
	public Stats computation(@PathVariable("field") String field, @PathVariable("operation") String operation) throws NoSuchFieldException, SecurityException {
		if(!operation.contentEquals("sum") && !operation.contentEquals("min") && !operation.contentEquals("max") && !operation.contentEquals("devstd")) {
			throw new RuntimeException("Operatore di calcolo non valido. Operatori validi: sum, min, max, devstd");
		} else {
			return appService.calculus(field, operation);
		}

	}
	

	@GetMapping("/average/{field}")
	public Stats average(@PathVariable("field") String field) {
			return appService.avg(field);
	}
	

	@GetMapping("/metadata")
	public ArrayList<Metadata> metadata() {
			return appService.printMetadata();
	}
	

	@GetMapping("/stats/{field}")
	public ArrayList<Stats> stats(@PathVariable("field") String field) throws NoSuchFieldException, SecurityException {
			return appService.Stats(field);
	}
	
	
	@GetMapping("/uniqueElements/{field}")
	public HashMap<String, Integer> uniqueElem(@PathVariable String field)
			throws NoSuchMethodException, IllegalAccessException, RuntimeException, ReflectiveOperationException {
			return appService.counter(field);
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
	
	@GetMapping("/valueFilter/{field}/{logicOperator}/{operator1}/{value1}/{operator2}/{value2}")
	public ArrayList<Element> valueFilter(@PathVariable("field") String field, @PathVariable("operator1") String operator1,
			@PathVariable("value1") float value1, @PathVariable("logicOperator") String logicOperator,
			@PathVariable("operator2") String operator2, @PathVariable("value2") float value2) {
		return appService.multifilter(field, logicOperator, operator1, value1, operator2, value2);
	}
	

	@GetMapping("/filter/{field1}/{word}/{field2}/{operator}/{value}")
	public ArrayList<Element> filter(@PathVariable("field1") String field1, @PathVariable("word") String word, @PathVariable("field2") String field2, @PathVariable("operator") String operator,
			@PathVariable("value") float value) {
			return appService.multifilter(field1, word, field2, operator, value);
	}
	
	@PostMapping("/data")
	public ArrayList<Element> esempio(@RequestBody Element body) {	
			return appService.addBody(body);
	}
	
	@DeleteMapping("/deleteValue/{index}")
	public ArrayList<Element> deleteValue(@PathVariable("index") int index) {
			return appService.deleteVal(index);
	}

	@DeleteMapping("/delete/{field}/{operator}/{value}")
	public ArrayList<Element> delete(@PathVariable("field") String field,
			@PathVariable("operator") String operator, @PathVariable("value") float value) { 
		return appService.deleteMoreVal(field, operator, value);
			
	}		
}




