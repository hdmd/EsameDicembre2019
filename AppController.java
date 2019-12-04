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
	
}
