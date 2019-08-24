package oghamepidoc;

import java.net.URLEncoder;

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
	
	public String title="";
	
	public static final String BASEURI="http://www.i3mainz.de/ogham#";
	
	public OntModel toRDF(OntModel model) {
		System.out.println(title);
		OntClass oghamobj=model.createClass(BASEURI+"OghamObject");
		OntClass geometry=model.createClass("http://www.opengis.net/ont/geosparql#Geometry");
		OntClass spatialobject=model.createClass("http://www.opengis.net/ont/geosparql#SpatialObject");
		OntClass feature=model.createClass("http://www.opengis.net/ont/geosparql#Feature");
		feature.addSuperClass(spatialobject);
		oghamobj.addSuperClass(feature);
		geometry.addSuperClass(spatialobject);
		Individual curind=oghamobj.createIndividual(BASEURI+URLEncoder.encode(title));
		ObjectProperty hasgeom=model.createObjectProperty("http://www.opengis.net/ont/geosparql#hasGeometry");
		DatatypeProperty asWKT=model.createDatatypeProperty("http://www.opengis.net/ont/geosparql#asWKT");
		Individual geomind=geometry.createIndividual(BASEURI+URLEncoder.encode(title)+"_geom");
		curind.addProperty(hasgeom, geomind);
		geomind.addLiteral(asWKT, location.toText());
    	return model;
	}
	
	
	public JSONObject toGeoJSONLD() {
		JSONObject geojsonobj=new JSONObject();
    	geojsonobj.put("type", "Feature");
    	JSONObject properties=new JSONObject();
    	properties.put("name", name);
    	properties.put("title", name);
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
