package it.univpm.esameDicembre;

import java.io.IOException;
import java.util.ArrayList;

//import com.app.service.FilterUtils;

import it.univpm.esameDicembre.Model.Element;
import it.univpm.esameDicembre.Model.Metadata;
import it.univpm.esameDicembre.Utils.Calculator;
import it.univpm.esameDicembre.Utils.Utils;

public class test {

	public static void main(String[] args) throws IOException {
		
		String url ="http://data.europa.eu/euodp/data/api/3/action/package_show?id=3h1d8YdPl3KCgkeQNbjkA";
		String fileName = "file.tsv";
		
		ArrayList<Element> v = new ArrayList<Element>();
		ArrayList<Metadata> header = new ArrayList<Metadata>();
		
		Utils.JSONDecode(url, fileName);
		Utils.tsvParse(v, header, fileName);
		
	}

}
