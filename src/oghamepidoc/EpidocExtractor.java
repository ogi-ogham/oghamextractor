package oghamepidoc;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

	boolean geo=false,title=false,foundmaqi=false,foundmucoi=false,foundkoi=false,foundanm=false,founderc=false,foundblind=false,foundeye=false;
	
	Integer maqicount=0;
    Integer mucoicount=0;
    Integer wordcounter=0;
	
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
	

	private boolean persname,photographs=true,foundnephew,founddescendant,foundfollower;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		//super.startElement(uri, localName, qName, attributes);
		switch(qName) {
		case "geo": 
			geo=true;
			break;
		case "title": 
			title=true;
			break;
		case "ref": 
			if(photographs) {
				result.imagelink=attributes.getValue("target");
			}
			break;
		case "div": 
			if(attributes.getValue("n")!=null && attributes.getValue("n").equals("photographs")) {
				photographs=true;
			}
			break;
		case "persName": 
			persname=true;
			break;
		case "w": 
			if(attributes.getValue("lemma")!=null) {
				result.words.add(attributes.getValue("lemma"));
				result.text+=attributes.getValue("lemma")+" ";
				if(wordmap.keySet().contains(attributes.getValue("lemma").toLowerCase())) {
					wordmap.get(attributes.getValue("lemma").toLowerCase()).setTwo(wordmap.get(attributes.getValue("lemma").toLowerCase()).getTwo()+1);
				}
				System.out.println(wordmap);
			}

			wordcounter++;
			if(persname) {
				result.persons.add(attributes.getValue("lemma"));
				if(this.curfatherson.getOne()!=null && this.curfatherson.getTwo()==null && foundmaqi) {
					this.curfatherson.setTwo(attributes.getValue("lemma"));
					this.result.sonOfSet.add(this.curfatherson);
					this.curfatherson=new Tuple<String,String>(null,null);
					foundmaqi=false;
				}else if(attributes.getValue("lemma")!=null && this.partoftribe.getOne()!=null && this.partoftribe.getTwo()==null && foundmucoi){
					this.partoftribe.setTwo(attributes.getValue("lemma"));
					this.result.tribePartOfSet.add(this.partoftribe);
					this.partoftribe=new Tuple<String,String>(null,null);
					foundmucoi=false;
				}else if(attributes.getValue("lemma")!=null && this.partoftribe.getOne()!=null && this.partoftribe.getTwo()==null && foundfollower){
					this.followerof.setTwo(attributes.getValue("lemma"));
					this.result.followerOfSet.add(this.followerof);
					this.followerof=new Tuple<String,String>(null,null);
					foundfollower=false;
				}else if(attributes.getValue("lemma")!=null && this.partoftribe.getOne()!=null && this.partoftribe.getTwo()==null && foundnephew){
					this.nephewof.setTwo(attributes.getValue("lemma"));
					this.result.nephewOfSet.add(this.nephewof);
					this.nephewof=new Tuple<String,String>(null,null);
					foundnephew=false;
				}else if(attributes.getValue("lemma")!=null && this.partoftribe.getOne()!=null && this.partoftribe.getTwo()==null && founddescendant){
					this.descendantof.setTwo(attributes.getValue("lemma"));
					this.result.descendantOfSet.add(this.descendantof);
					this.descendantof=new Tuple<String,String>(null,null);
					founddescendant=false;
				}else {					
					this.curfatherson=new Tuple<>(attributes.getValue("lemma"),null);
					this.partoftribe=new Tuple<>(attributes.getValue("lemma"),null);
					this.descendantof=new Tuple<>(attributes.getValue("lemma"),null);
					this.nephewof=new Tuple<>(attributes.getValue("lemma"),null);
					this.followerof=new Tuple<>(attributes.getValue("lemma"),null);
				}
			}else {
				if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("MAQI")) {
					foundmaqi=true;
					System.out.println("FOUND MAQI!!!!!!");
					maqicount++;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("MUCOI")) {
					System.out.println("FOUND MUCOI!!!!!!");
					foundmucoi=true;
                    mucoicount++;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("CELI")) {
					System.out.println("FOUND CELI!!!!!!");
					foundfollower=true;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("NETTA")) {
					System.out.println("FOUND NETTA!!!!!!");
					foundnephew=true;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("AVI")) {
					System.out.println("FOUND AVI!!!!!!");
					founddescendant=true;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("KOI")) {
					System.out.println("FOUND KOI!!!!!!");
					foundkoi=true;
					result.containskoi=true;
				}else if("formula".equals(attributes.getValue("type")) && attributes.getValue("lemma").equals("ANM")) {
					System.out.println("FOUND ANM!!!!!!");
					foundanm=true;
					result.containsanm=true;
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
		}else if(title) {
			result.title=characters;
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
		case "geo": this.geo=false;
			break;
		case "title": this.title=false;
			break;
		case "div": this.photographs=false;
			break;
		case "persName": this.persname=false;
			break;
		}
	}
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		Map<String,Tuple<Word,Integer>> wordmap=WordParser.csvToWordMap("words/words.csv");
		OntModel model=ModelFactory.createOntologyModel();
		List<OghamObject> resultList=new LinkedList<OghamObject>();
		Integer globalmaqicount=0;
        Integer globalmucoicount=0;
		for(File folder:new File("ogham3d_epidoc_files/ogham3d_epidoc_files/").listFiles()) {
			for(File file:folder.listFiles()) {
				EpidocExtractor extractor=new EpidocExtractor(wordmap);
				SAXParserFactory.newInstance().newSAXParser().parse(file, extractor);
				resultList.add(extractor.result);
				extractor.result.toRDF(model);
				globalmaqicount+=extractor.maqicount;
                globalmucoicount+=extractor.mucoicount;
                if(extractor.wordcounter==1) {
                	extractor.result.justoneword=true;
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
		
		model.write(new FileWriter("owl/epidocresult.ttl"), "TTL") ;
		
		System.out.println("Maqicount: "+globalmaqicount);
        System.out.println("Mucoicount: "+globalmucoicount);
	}
}
