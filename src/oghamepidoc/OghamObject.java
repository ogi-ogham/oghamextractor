package oghamepidoc;

import java.net.URLEncoder;
import java.util.Set;
import java.util.TreeSet;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Point;

public class OghamObject {

	public Point location;
	
	public String name="";
	
	public String oghamid="";
	
	public String title="";
	
	public String imagelink="";
	
	public Set<String> persons=new TreeSet<String>();
	
	public Set<Tuple<String,String>> sonOfSet=new TreeSet<Tuple<String,String>>();
	
	public Set<Tuple<String,String>> followerOfSet=new TreeSet<Tuple<String,String>>();
	
	public Set<Tuple<String,String>> descendantOfSet=new TreeSet<Tuple<String,String>>();
	
	public Set<Tuple<String,String>> nephewOfSet=new TreeSet<Tuple<String,String>>();
	
	public Set<Tuple<String,String>> tribePartOfSet=new TreeSet<Tuple<String,String>>();
	
	public static final String BASEURI="http://www.i3mainz.de/ogham#";
	
	public OntModel toRDF(OntModel model) {
		System.out.println(title);
		OntClass oghamobj=model.createClass(BASEURI+"OghamObject");
		OntClass person=model.createClass("http://xmlns.com/foaf/0.1/Person");
		OntClass geometry=model.createClass("http://www.opengis.net/ont/geosparql#Geometry");
		OntClass spatialobject=model.createClass("http://www.opengis.net/ont/geosparql#SpatialObject");
		OntClass feature=model.createClass("http://www.opengis.net/ont/geosparql#Feature");
		OntClass tribe=model.createClass("https://www.wikidata.org/wiki/Q3538737");
		OntClass wolf=model.createClass("https://www.wikidata.org/wiki/Q18498");
		wolf.addLabel("Wolf","en");
		OntClass battle=model.createClass("https://www.wikidata.org/wiki/Q178561");
		battle.addLabel("Battle","en");
		OntClass godlugh=model.createClass("https://www.wikidata.org/wiki/Q215683");
		godlugh.addLabel("God Lugh","en");
		tribe.addLabel("Tribe","en");
		feature.addSuperClass(spatialobject);
		oghamobj.addSuperClass(feature);
		geometry.addSuperClass(spatialobject);
		Individual curind;
		if(!this.oghamid.isEmpty()) {
			curind=oghamobj.createIndividual(BASEURI+URLEncoder.encode(oghamid));
		}else {
			curind=oghamobj.createIndividual(BASEURI+URLEncoder.encode(title));
		}
		ObjectProperty hasgeom=model.createObjectProperty("http://www.opengis.net/ont/geosparql#hasGeometry");
		ObjectProperty fatherrel=model.createObjectProperty("https://www.wikidata.org/wiki/Property:P22");
		fatherrel.setLabel("father", "en");
		ObjectProperty inscriptionmentions=model.createObjectProperty("https://www.wikidata.org/wiki/Property:P6568");
		inscriptionmentions.addLabel("inscriptionmentions","en");
		ObjectProperty relative=model.createObjectProperty("https://www.wikidata.org/wiki/Property:P1038");
		inscriptionmentions.addLabel("relative","en");
		ObjectProperty hasMember=model.createObjectProperty(BASEURI+"hasMember");
		ObjectProperty follows=model.createObjectProperty(BASEURI+"follows");
		ObjectProperty descendantOf=model.createObjectProperty(BASEURI+"descendantOf");
		ObjectProperty nameRelatesTo=model.createObjectProperty(BASEURI+"nameRelatesTo");
		ObjectProperty partofTribe=model.createObjectProperty("https://www.wikidata.org/wiki/Property:P463");
		partofTribe.addLabel("member of","en");
		DatatypeProperty image=model.createDatatypeProperty("https://www.wikidata.org/wiki/Property:P18");
		image.addLabel("image","en");
		DatatypeProperty oghamname=model.createDatatypeProperty(BASEURI+"nameInOgham");
		for(String perss:persons) {
			Individual persson=person.createIndividual(BASEURI+URLEncoder.encode(perss));
			if(perss.contains("CUNA")) {
				persson.addProperty(nameRelatesTo, wolf);
			}else if(perss.contains("CATTU")) {
				persson.addProperty(nameRelatesTo, battle);
			}else if(perss.contains("LUG")) {
				persson.addProperty(nameRelatesTo, godlugh);
			}
			persson.addProperty(oghamname, OghamUtils.translitToUnicode(perss));
			curind.addProperty(inscriptionmentions, persson);
		}
		for(Tuple<String,String> sonof:sonOfSet) {
			Individual son=person.createIndividual(BASEURI+URLEncoder.encode(sonof.getOne()));
			Individual father=person.createIndividual(BASEURI+URLEncoder.encode(sonof.getTwo()));
			son.addProperty(fatherrel, father);
			curind.addProperty(inscriptionmentions, son);
			curind.addProperty(inscriptionmentions, father);
		}	
		for(Tuple<String,String> partof:tribePartOfSet) {
			Individual pers=person.createIndividual(BASEURI+URLEncoder.encode(partof.getOne()));
			Individual tribee=tribe.createIndividual(BASEURI+URLEncoder.encode(partof.getTwo()));
			pers.addProperty(partofTribe, tribee);
			tribee.addProperty(hasMember, pers);
			curind.addProperty(inscriptionmentions, pers);
			curind.addProperty(inscriptionmentions, tribee);
		}
		for(Tuple<String,String> partof:nephewOfSet) {
			Individual pers=person.createIndividual(BASEURI+URLEncoder.encode(partof.getOne()));
			Individual tribee=person.createIndividual(BASEURI+URLEncoder.encode(partof.getTwo()));
			pers.addProperty(relative, tribee);
			tribee.addProperty(relative, pers);
			curind.addProperty(inscriptionmentions, pers);
			curind.addProperty(inscriptionmentions, tribee);
		}
		for(Tuple<String,String> partof:followerOfSet) {
			Individual pers=person.createIndividual(BASEURI+URLEncoder.encode(partof.getOne()));
			Individual tribee=person.createIndividual(BASEURI+URLEncoder.encode(partof.getTwo()));
			pers.addProperty(follows, tribee);
			curind.addProperty(inscriptionmentions, pers);
			curind.addProperty(inscriptionmentions, tribee);
		}
		for(Tuple<String,String> partof:this.descendantOfSet) {
			Individual pers=person.createIndividual(BASEURI+URLEncoder.encode(partof.getOne()));
			Individual tribee=person.createIndividual(BASEURI+URLEncoder.encode(partof.getTwo()));
			pers.addProperty(descendantOf, tribee);
			curind.addProperty(inscriptionmentions, pers);
			curind.addProperty(inscriptionmentions, tribee);
		}
		DatatypeProperty asWKT=model.createDatatypeProperty("http://www.opengis.net/ont/geosparql#asWKT");
		Individual geomind=geometry.createIndividual(BASEURI+URLEncoder.encode(title)+"_geom");
		curind.addProperty(hasgeom, geomind);
		curind.addProperty(image, imagelink);
		geomind.addLiteral(asWKT, location.toText());
    	return model;
	}
	
	
	public JSONObject toGeoJSONLD() {
		JSONObject geojsonobj=new JSONObject();
    	geojsonobj.put("type", "Feature");
    	JSONObject properties=new JSONObject();
    	properties.put("name", name);
    	properties.put("title", name);
    	properties.put("image", imagelink);
    	JSONArray pers=new JSONArray();
    	for(String person:this.persons) {
    		pers.put(person);
    	}
    	properties.put("persons", pers);
    	JSONArray fatherson=new JSONArray();
    	for(Tuple<String,String> person:this.sonOfSet) {
    		JSONObject fathersonrel=new JSONObject();
    		fathersonrel.put("father", person.getTwo());
    		fathersonrel.put("son", person.getOne());
    		fatherson.put(fathersonrel);
    	}
    	properties.put("fatherson", fatherson);
    	JSONObject geom=new JSONObject();
    	JSONArray coordinates=new JSONArray();
    	coordinates.put(location.getCoordinate().x);
    	coordinates.put(location.getCoordinate().y);
    	geom.put("type", "Point");
    	geom.put("coordinates", coordinates);
    	geojsonobj.put("properties", properties);
    	geojsonobj.put("geometry", geom);
    	return geojsonobj;
	}
}
