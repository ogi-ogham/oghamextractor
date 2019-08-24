package oghamepidoc;

import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Point;

public class OghamObject {

	public Point location;
	
	public String name="";
	
	
	public JSONObject toGeoJSONLD() {
		JSONObject geojsonobj=new JSONObject();
    	geojsonobj.put("type", "Feature");
    	JSONObject properties=new JSONObject();
    	properties.put("name", name);
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
