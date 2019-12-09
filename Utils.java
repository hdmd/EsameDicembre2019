package it.univpm.esameDicembre.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import it.univpm.esameDicembre.Model.Element;
import it.univpm.esameDicembre.Model.Metadata;

public class Utils {


	public static void JSONDecode(String url, String fileName) {

		boolean a = true;
		try {
			
			
			URLConnection openConnection = new URL(url).openConnection();
			InputStream in = openConnection.getInputStream();
			
			 String data = "";
			 String line = "";
			 try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
				   System.out.println( line );
			   }
			 } finally {
			   in.close();
			 }
			JSONObject obj = (JSONObject) JSONValue.parseWithException(data); 
			JSONObject objI = (JSONObject) (obj.get("result"));
			JSONArray objA = (JSONArray) (objI.get("resources"));
			
			for(Object o: objA){
			    if ( o instanceof JSONObject ) {
			        JSONObject o1 = (JSONObject)o; 
			        String format = (String)o1.get("format");
			        String urlD = (String)o1.get("url");
			        if(format.equals("http://publications.europa.eu/resource/authority/file-type/TSV") && a) {
			        	download(urlD, "file.tsv");
			        	a = false;
			        }
			    }
			}
			System.out.println( "OK" );
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void download(String url, String fileName) throws Exception {
	    /*try (InputStream in = URI.create(url).toURL().openStream()) {
	        Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);*/
		
			HttpURLConnection openConnection = (HttpURLConnection) new URL(url).openConnection();
			openConnection.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			InputStream in = openConnection.getInputStream();
			try {
				if (openConnection.getResponseCode() >= 300 && openConnection.getResponseCode() < 400) {
					download(openConnection.getHeaderField("Location"), fileName);
					in.close();
					openConnection.disconnect();
					return;
				}
				Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("File size " + Files.size(Paths.get(fileName)));
			} finally {
				in.close();
			}
		}	    
	
	public static void tsvParse(ArrayList<Element> v, ArrayList<Metadata> header, String fileName) throws IOException {
		
		boolean metadataFlag = false;

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
				
			while ((line = br.readLine()) != null) {
				String[] values = line.split(","); 
				
				String[] geo = values[values.length - 1].split("\t");
				
				int rigaLength = ((values.length - 1)+(geo.length)); 
				
				String[] riga = new String[rigaLength];
				int k;
				for ( k = 0 ; k < rigaLength; k++) {
					if (k<(values.length)-1) { 
						for (int i = 0; i < ((values.length)-1); i++) 
							riga[k]=values[i]; 
					} else 
						for (int j = 0; j < geo.length; j++)
							riga[k]=geo[j];
					if (k<(values.length-1)) {
						riga[k]=values[k];
										
					} else {
						riga[k]=geo[k-(values.length-1)];
						
					  } 
				}
				
				if( k < 38) {
					String[] a = new String[38];
					String b = "0";
					int i;
					for ( i = 0; i < riga.length ; i ++) {
						a[i] = riga[i];
					}
					riga = a;
					for ( i = 25; i < riga.length; i++)
						riga[i] = b;
				}
				if (!metadataFlag) {
					for (int p = 0; p < riga.length; p++) {
						String attribute = riga[p];
						header.add(new Metadata(attribute));
					}
				}
				
				if (metadataFlag) {
					int j = 0;
					int lineaLength = riga.length;
					String[] linea = new String[lineaLength];
					String s = "0";
					for (int p = 0; p < riga.length; p++)
					{ 	
						linea[j] = riga[p];
						if((riga[p].contains(":")))
						{
							linea[j] = s;
							
						} else
							if((riga[p].contains("u") || riga[p].contains("d"))) {
								linea[j] = riga[p].substring(0, riga[p].length() - 1);
								
							}
					j++;	
					}
					v.add(new Element(
							linea[0], 
							linea[1], 
							linea[2], 
							linea[3], 
							linea[4], 
							Integer.parseInt(linea[5]), 
							Float.parseFloat(linea[6]), 
							Float.parseFloat(linea[7]), 
							Float.parseFloat(linea[8]), 
							Float.parseFloat(linea[9]), 
							Float.parseFloat(linea[10]),
							Float.parseFloat(linea[11]),
							Float.parseFloat(linea[12]), 
							Float.parseFloat(linea[13]), 
							Float.parseFloat(linea[14]),
							Float.parseFloat(linea[15]), 
							Float.parseFloat(linea[16]), 
							Float.parseFloat(linea[17]), 
							Float.parseFloat(linea[18]), 
							Float.parseFloat(linea[19]), 
							Float.parseFloat(linea[20]), 
							Float.parseFloat(linea[21]),	
							Float.parseFloat(linea[22]), 
							Float.parseFloat(linea[23]), 
							Float.parseFloat(linea[24]), 
							Float.parseFloat(linea[25]),	
							Float.parseFloat(linea[26]), 
							Float.parseFloat(linea[27]), 
							Float.parseFloat(linea[28]), 
							Float.parseFloat(linea[29]),	
							Float.parseFloat(linea[30]), 
							Float.parseFloat(linea[31]), 
							Float.parseFloat(linea[32]), 
							Float.parseFloat(linea[33]),	
							Float.parseFloat(linea[34]), 
							Float.parseFloat(linea[35]), 
							Float.parseFloat(linea[36]), 
							Float.parseFloat(linea[37]))); 
					}
				metadataFlag = true;
			}

			br.close();

			}catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
