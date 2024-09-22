package oghamepidoc;

import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

public class EpidocExtractor extends DefaultHandler2 {

	boolean geo=Boolean.FALSE,title=Boolean.FALSE,titlest=Boolean.FALSE,foundmaqi=Boolean.FALSE,foundmucoi=Boolean.FALSE,foundkoi=Boolean.FALSE,foundanm=Boolean.FALSE,founderc=Boolean.FALSE,foundblind=Boolean.FALSE,foundeye=Boolean.FALSE;
	
	Integer maqicount= (Integer) 0;
    Integer mucoicount= (Integer) 0;
    Integer wordcounter= (Integer) 0;
	
	GeometryFactory fac=new GeometryFactory();
	
	OghamObject result=new OghamObject();
	
	Tuple<String,String> curfatherson=new Tuple<String,String>(null,null);
	
	Tuple<String,String> partoftribe=new Tuple<String,String>(null,null);
	
	Tuple<String,String> followerof=new Tuple<String,String>(null,null);
	
	Tuple<String,String> nephewof=new Tuple<String,String>(null,null);
	
	Tuple<String,String> descendantof=new Tuple<String,String>(null,null);
	
	Map<String,Tuple<Word,Integer>> wordmap;
	
	public EpidocExtractor(Map<String,Tuple<Word,Integer>> wordmap) {
		this.wordmap=wordmap;
	}
	

	private Boolean persname=Boolean.FALSE,photographs=Boolean.TRUE,foundnephew=Boolean.FALSE,founddescendant=Boolean.FALSE,foundfollower=Boolean.FALSE;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		//super.startElement(uri, localName, qName, attributes);
		switch(qName) {
		case "geo": 
			geo=Boolean.TRUE;
			break;
		case "titleStmt":
			titlest=Boolean.TRUE;
			break;
		case "title": 
			title=Boolean.TRUE;
			if(titlest)
				result.title="";
			System.out.println("IN TITLE");
			break;
		case "ref": 
			if(photographs) {
				result.imagelink=attributes.getValue("target");
			}
			break;
		case "div": 
			if(attributes.getValue("n")!=null && attributes.getValue("n").equals("photographs")) {
				photographs=Boolean.TRUE;
			}
			break;
		case "persName": 
			persname=Boolean.TRUE;
			break;
		case "w": 
			if(attributes.getValue("lemma")!=null) {
				result.words.add(attributes.getValue("lemma"));
				result.text+=attributes.getValue("lemma")+" ";
				if(wordmap.keySet().contains(attributes.getValue("lemma").toLowerCase())) {
					wordmap.get(attributes.getValue("lemma").toLowerCase()).setTwo(Integer.valueOf(wordmap.get(attributes.getValue("lemma").toLowerCase()).getTwo()+1));
				}
				//System.out.println(wordmap);
			}

			wordcounter++;
			if(persname) {
				if(attributes.getValue("lemma")!=null)
					result.persons.add(attributes.getValue("lemma"));
				if(this.curfatherson.getOne()!=null && this.curfatherson.getTwo()==null && foundmaqi) {
					this.curfatherson.setTwo(attributes.getValue("lemma"));
					this.result.sonOfSet.add(this.curfatherson);
					this.curfatherson=new Tuple<String,String>(null,null);
					foundmaqi=Boolean.FALSE;
				}else if(attributes.getValue("lemma")!=null && this.partoftribe.getOne()!=null && this.partoftribe.getTwo()==null && foundmucoi){
					this.partoftribe.setTwo(attributes.getValue("lemma"));
					this.result.tribePartOfSet.add(this.partoftribe);
					this.partoftribe=new Tuple<String,String>(null,null);
					foundmucoi=Boolean.FALSE;
				}else if(attributes.getValue("lemma")!=null && this.partoftribe.getOne()!=null && this.partoftribe.getTwo()==null && foundfollower){
					this.followerof.setTwo(attributes.getValue("lemma"));
					this.result.followerOfSet.add(this.followerof);
					this.followerof=new Tuple<String,String>(null,null);
					foundfollower=Boolean.FALSE;
				}else if(attributes.getValue("lemma")!=null && this.partoftribe.getOne()!=null && this.partoftribe.getTwo()==null && foundnephew){
					this.nephewof.setTwo(attributes.getValue("lemma"));
					this.result.nephewOfSet.add(this.nephewof);
					this.nephewof=new Tuple<String,String>(null,null);
					foundnephew=Boolean.FALSE;
				}else if(attributes.getValue("lemma")!=null && this.partoftribe.getOne()!=null && this.partoftribe.getTwo()==null && founddescendant){
					this.descendantof.setTwo(attributes.getValue("lemma"));
					this.result.descendantOfSet.add(this.descendantof);
					this.descendantof=new Tuple<String,String>(null,null);
					founddescendant=Boolean.FALSE;
				}else {					
					this.curfatherson=new Tuple<>(attributes.getValue("lemma"),null);
					this.partoftribe=new Tuple<>(attributes.getValue("lemma"),null);
					this.descendantof=new Tuple<>(attributes.getValue("lemma"),null);
					this.nephewof=new Tuple<>(attributes.getValue("lemma"),null);
					this.followerof=new Tuple<>(attributes.getValue("lemma"),null);
				}
			}else {
				if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("MAQI")) {
					foundmaqi=Boolean.TRUE;
					System.out.println("FOUND MAQI!!!!!!");
					maqicount++;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("MUCOI")) {
					System.out.println("FOUND MUCOI!!!!!!");
					foundmucoi=Boolean.TRUE;
                    mucoicount++;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("CELI")) {
					System.out.println("FOUND CELI!!!!!!");
					foundfollower=Boolean.TRUE;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("NETTA")) {
					System.out.println("FOUND NETTA!!!!!!");
					foundnephew=Boolean.TRUE;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("AVI")) {
					System.out.println("FOUND AVI!!!!!!");
					founddescendant=Boolean.TRUE;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("KOI")) {
					System.out.println("FOUND KOI!!!!!!");
					foundkoi=Boolean.TRUE;
					result.containskoi= (Boolean) true;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("ANM")) {
					System.out.println("FOUND ANM!!!!!!");
					foundanm=Boolean.TRUE;
					result.containsanm= (Boolean) true;
				}
			}
			break;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String characters=new String(ch,start,length);
		if(geo) {
			String[] geospl=characters.split(",");
			Coordinate coord=new Coordinate(Double.valueOf(geospl[0].trim()), Double.valueOf(geospl[1].trim()));
			result.location=fac.createPoint(coord);
		}else if(title && titlest) {
			System.out.println("TITLE: "+characters);
			result.title+=characters;
			if(result.title.contains(".")) {
				result.oghamid=result.title.substring(0,result.title.indexOf('.'));
			}
		}
	}	
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		//super.endElement(uri, localName, qName);
		switch(qName) {
		case "geo": this.geo=Boolean.FALSE;
			break;
		case "titleStmt":
			this.titlest=Boolean.FALSE;
			break;
		case "title": this.title=Boolean.FALSE;
			result.title=result.title.trim();
			System.out.println("FINAL TITLE: "+ result.title);
			break;
		case "div": this.photographs=Boolean.FALSE;
			break;
		case "persName": this.persname=Boolean.FALSE;
			break;
		}
	}
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, FileNotFoundException, CsvValidationException {
		Map<String,Tuple<Word,Integer>> wordmap=WordParser.csvToWordMap("words/words.csv");
		OntModel model=ModelFactory.createOntologyModel();
		List<OghamObject> resultList=new LinkedList<OghamObject>();
		Integer globalmaqicount= (Integer) 0;
        Integer globalmucoicount= (Integer) 0;
		for(File folder:new File("ogham3d_epidoc_files/ogham3d_epidoc_files/").listFiles()) {
			for(File file:folder.listFiles()) {
				EpidocExtractor extractor=new EpidocExtractor(wordmap);
				SAXParserFactory.newInstance().newSAXParser().parse(file, extractor);
				resultList.add(extractor.result);
				extractor.result.toRDF(model);
				globalmaqicount+=extractor.maqicount;
                globalmucoicount+=extractor.mucoicount;
                if(extractor.wordcounter==1) {
                	extractor.result.justoneword= (Boolean) true;
                }
			}
		}
		JSONObject listresult=OghamUtils.createGeoJSON(resultList);
		BufferedWriter writer=new BufferedWriter
			    (new OutputStreamWriter(new FileOutputStream("docs/data/oghamireland.js"), StandardCharsets.UTF_8));
		writer.write("var oghamireland="+listresult.toString(2));
		writer.close();
		writer=new BufferedWriter(new FileWriter(new File("result.json")));
		writer.write(listresult.toString(2));
		writer.close();
		File directory = new File(String.valueOf("owl"));

		if (!directory.exists()) {
			directory.mkdir();
		}
		
		model.write(new FileWriter("owl/epidocresult.ttl"), "TTL") ;
		
		System.out.println("Maqicount: "+globalmaqicount);
        System.out.println("Mucoicount: "+globalmucoicount);
	}
}
