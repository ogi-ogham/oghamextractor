package oghamepidoc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

	boolean geo=false,title=false;
	
	GeometryFactory fac=new GeometryFactory();
	
	OghamObject result=new OghamObject();
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		switch(qName) {
		case "geo": 
			geo=true;
			break;
		case "title": 
			title=true;
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
		}
	}	
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		switch(qName) {
		case "geo": this.geo=false;
			break;
		case "title": this.title=false;
			break;
		}
	}
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		OntModel model=ModelFactory.createOntologyModel();
		List<OghamObject> resultList=new LinkedList<OghamObject>();
		for(File folder:new File("ogham3d_epidoc_files/ogham3d_epidoc_files/").listFiles()) {
			for(File file:folder.listFiles()) {
				EpidocExtractor extractor=new EpidocExtractor();
				SAXParserFactory.newInstance().newSAXParser().parse(file, extractor);
				resultList.add(extractor.result);
				extractor.result.toRDF(model);
			}
		}
		JSONObject listresult=OghamUtils.createGeoJSON(resultList);
		BufferedWriter writer=new BufferedWriter(new FileWriter(new File("result.json")));
		writer.write(listresult.toString(2));
		writer.close();
		model.write(new FileWriter("result.ttl"), "TTL") ;
	}
}
