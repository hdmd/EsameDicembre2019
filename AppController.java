package it.univpm.esameDicembre.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import it.univpm.esameDicembre.Model.Element;
import it.univpm.esameDicembre.Model.Metadata;
import it.univpm.esameDicembre.Model.Stats;
import it.univpm.esameDicembre.Controller.appService;


@RestController
public class AppController {
	
	@Autowired
	appService appService; 
	
	@GetMapping("/data/{indice}")
	public Element data(@PathVariable int indice) {
		return appService.printElement(indice);
	}
	
	@GetMapping("/data")
	public ArrayList<Element> dataAll() {
		return appService.printElement();
	}
	
	@GetMapping("/{operazione}/{nome_param}")
	public Stats sum(@PathVariable("nome_param") String nome_param, @PathVariable("operazione") String operazione) {
		return appService.calcolo(nome_param, operazione);
	}
	
	@GetMapping("/avg/{nome_param}")
	public Stats avg(@PathVariable("nome_param") String nome_param) {
		return appService.avg(nome_param);
	}
	
	@GetMapping("/metadata")
	public ArrayList<Metadata> metadata() {
		return appService.printMetadata();
	}
	
	@GetMapping("/stats/{nome_param}")
	public ArrayList<Stats> stats(@PathVariable("nome_param") String nome_param) {
		return appService.Stats(nome_param);
	}

	@GetMapping("/elementiunici/{nome_param}")
	public HashMap<String, Integer> uniqueElem(@PathVariable String nome_param)
			throws NoSuchMethodException, IllegalAccessException, RuntimeException, ReflectiveOperationException {
		return appService.counter(nome_param);
	}
	
	@GetMapping("/filtroStringa/{field}/{value}")
	public ArrayList<Element> stringFilter(@PathVariable("field") String field, @PathVariable("value") String value) {
		return appService.filter(field, "eq", value);
	}
	
	@GetMapping("/filtroValore/{field}/{operator}/{value}")
	public ArrayList<Element> valueFilter(@PathVariable("field") String field,
			@PathVariable("operator") String operator, @PathVariable("value") float value) {
		return appService.filter(field, operator, value);
	}
	
	@GetMapping("/filtroValore/{field}/{logicOperator}/{operator1}/{value1}/{operator2}/{value2}")
	public ArrayList<Element> valueFilter(@PathVariable("field") String field, @PathVariable("operator1") String operator1,
			@PathVariable("value1") float value1, @PathVariable("logicOperator") String logicOperator,
			@PathVariable("operator2") String operator2, @PathVariable("value2") float value2) {
		return appService.multifilter(field, logicOperator, operator1, value1, operator2, value2);
	}
	
	@GetMapping("/filtro/{field}/{value1}/{operator2}/{value2}")
	public ArrayList<Element> filter(@PathVariable("field") String field, @PathVariable("value1") String value1, @PathVariable("operator2") String operator2,
			@PathVariable("value2") float value2) {
		return appService.multifilter(field, value1, operator2, value2);
	}
	
}
