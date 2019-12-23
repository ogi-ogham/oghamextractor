package oghamepidoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CSVExtractor {

	public static OntModel csvToTTL(String fileName,Integer[] idcoll,String classs,String namespace,Map<String, Tuple<Word, Integer>> words,OntModel model) throws FileNotFoundException, IOException {
		List<Integer> idcol=Arrays.asList(idcoll);
		FileReader fileReader = new FileReader(new File(fileName));
		CSVParserBuilder csvParserBuilder = new CSVParserBuilder();
	    CSVParser parser = csvParserBuilder.withSeparator('|').build();
	    CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();
	    String[] lineContent;
	    OntClass cls=model.createClass(namespace+classs);
	    Boolean firstline=true;
	    List<String> cols=new LinkedList<String>();
	    while ((lineContent = reader.readNext()) != null) {
	    	int i=0;
	    	System.out.println(lineContent.length);
	    	if(firstline) {
	            for (String e : lineContent) {
	            	cols.add(e);
	            }
	    		firstline=false;
	    	}else {
	    		String curid="";
	    		for(Integer idc:idcol) {
	    			if(idc<lineContent.length) {
	    				curid+=lineContent[idc]+"_";
	    			}
	    		}
	    		if(!curid.isEmpty())
	    			curid=curid.substring(0,curid.length()-1);
		    	Individual ind=cls.createIndividual(namespace+curid.replace(" ","_").replace("/","_"));
		    	if(idcol.contains(1) && idcol.contains(2) && idcol.contains(3) && idcol.contains(4) && !idcol.contains(5)) {
		    			ind.addProperty(model.createObjectProperty(namespace+"translationOfReading"), model.createClass(namespace+"Stone").createIndividual(namespace+lineContent[0].replace(" ","_")+"_"+lineContent[1].replace(" ","_")+"_"+lineContent[2].replace(" ","_")+"_"+lineContent[3].replace(" ","_")+"_"+lineContent[4].replace(" ","_")));
		    			ind.addProperty(model.createObjectProperty(namespace+"foundInInscription"), model.createClass(namespace+"Stone").createIndividual(namespace+lineContent[0].replace(" ","_")+"_"+lineContent[1].replace(" ","_")+"_"+lineContent[2].replace(" ","_")+"_"+lineContent[3].replace(" ","_")));
		    			ind.addProperty(model.createObjectProperty(namespace+"foundOnStone"), model.createClass(namespace+"Stone").createIndividual(namespace+lineContent[0].replace(" ","_")+"_"+lineContent[1].replace(" ","_")));
		    			ind.addProperty(model.createObjectProperty(namespace+"foundAtSite"), model.createClass(namespace+"Site").createIndividual(namespace+lineContent[0]));
		    	}
		    	if(idcol.contains(1) && idcol.contains(2) && idcol.contains(3) && !idcol.contains(4) && !idcol.contains(5)) {
	    			ind.addProperty(model.createObjectProperty(namespace+"foundInInscription"), model.createClass(namespace+"Stone").createIndividual(namespace+lineContent[0].replace(" ","_")+"_"+lineContent[1].replace(" ","_")+"_"+lineContent[2].replace(" ","_")+"_"+lineContent[3].replace(" ","_")));
	    			ind.addProperty(model.createObjectProperty(namespace+"foundOnStone"), model.createClass(namespace+"Stone").createIndividual(namespace+lineContent[0].replace(" ","_")+"_"+lineContent[1].replace(" ","_")));
	    			ind.addProperty(model.createObjectProperty(namespace+"foundAtSite"), model.createClass(namespace+"Site").createIndividual(namespace+lineContent[0]));
		    	}
		    	if(idcol.contains(1) && !idcol.contains(2) && !idcol.contains(3) && !idcol.contains(4) && !idcol.contains(5)) {
	    			ind.addProperty(model.createObjectProperty(namespace+"foundOnStone"), model.createClass(namespace+"Stone").createIndividual(namespace+lineContent[0].replace(" ","_")+"_"+lineContent[1].replace(" ","_")));
	    			ind.addProperty(model.createObjectProperty(namespace+"foundAtSite"), model.createClass(namespace+"Site").createIndividual(namespace+lineContent[0]));
		    	}
		    	if(idcol.contains(0) && !idcol.contains(1) && !idcol.contains(2) && !idcol.contains(3) && !idcol.contains(4) && !idcol.contains(5)) {
	    			ind.addProperty(model.createObjectProperty(namespace+"foundAtSite"), model.createClass(namespace+"Site").createIndividual(namespace+lineContent[0]));
		    	}
		    	/*if(idcol.contains(1) && !idcol.contains(2) && !idcol.contains(3)) {
		    		if(1<lineContent.length) {
		    			ind.addProperty(model.createObjectProperty(namespace+"hasStone"), model.createClass(namespace+"Stone").createIndividual(namespace+lineContent[0].replace(" ","_")+"_"+lineContent[1].replace(" ","_")));
		    		ind.addProperty(model.createObjectProperty(namespace+"hasSite"), model.createClass(namespace+"Site").createIndividual(namespace+lineContent[35].substring(0,lineContent[35].indexOf('/'))));
		    		}
		    	}else if(idcol.contains(1) && idcol.contains(2) && !idcol.contains(3)) {
		    		if(2<lineContent.length)
		    			ind.addProperty(model.createObjectProperty(namespace+"hasInscription"), model.createClass(namespace+"Inscription").createIndividual(namespace+lineContent[0]+"_"+lineContent[1]+"_"+lineContent[2]));
		    	}else if(idcol.contains(35)) {
		    		if(35<lineContent.length)
		    			ind.addProperty(model.createObjectProperty(namespace+"hasSite"), model.createClass(namespace+"Site").createIndividual(namespace+lineContent[35].substring(0,lineContent[35].indexOf('/'))));
		    	}*/
	            for (String e : lineContent) {
	            	
	            	if(cols.get(i).equalsIgnoreCase("EXPANSION")) {
	            		for(String word:words.keySet()) {
	            			if(e.contains(word)) {
	            				OntClass clss=model.createClass(words.get(word).getOne().wikidataClass);
	            				ind.addProperty(model.createObjectProperty(namespace+"contains"), clss.createIndividual(words.get(word).getOne().wikidataIndividual));
	            			}
	            		}	
	            	}
	                if(!idcol.contains(i) && !e.equals("NULL") && !e.isEmpty()) {
	                	ind.addProperty(model.createDatatypeProperty(namespace+cols.get(i).replace(" ","_")), e);
	                }
	                i++;
	                
	            }
	    	}
	    }
	      return model;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Map<String, Tuple<Word, Integer>> words=WordParser.csvToWordMap("words/words.csv");
		String filename="site";
		OntModel model=ModelFactory.createOntologyModel();
		csvToTTL("data/"+filename+".csv",new Integer[]{0},"Site","http://ogham.link/cisp/",words,model);
		//res.write(new FileWriter(new File("res/"+filename+".ttl")), "TTL") ;
		filename="stone";
		csvToTTL("data/"+filename+".csv",new Integer[]{35},"Stone","http://ogham.link/cisp/",words,model);
		//res.write(new FileWriter(new File("res/"+filename+".ttl")), "TTL") ;
		filename="inscrip";
		csvToTTL("data/"+filename+".csv",new Integer[]{0,1,2},"Inscription","http://ogham.link/cisp/",words,model);
		//res.write(new FileWriter(new File("res/"+filename+".ttl")), "TTL") ;
		filename="reading";
		csvToTTL("data/"+filename+".csv",new Integer[]{0,1,2,3},"Reading","http://ogham.link/cisp/",words,model);
		//res.write(new FileWriter(new File("res/"+filename+".ttl")), "TTL") ;
		filename="translat";
		csvToTTL("data/"+filename+".csv",new Integer[]{0,1,2,3,4},"Translation","http://ogham.link/cisp/",words,model);
		model.write(new FileWriter(new File("res/"+filename+".ttl")), "TTL") ;
	}
	
}

