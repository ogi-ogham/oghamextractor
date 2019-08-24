package oghamepidoc;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class OghamUtils {

	public static JSONObject createGeoJSON(List<OghamObject> objects) {
		JSONObject geojsonresult=new JSONObject();
	    geojsonresult.put("type", "FeatureCollection");
	    geojsonresult.put("name", "OghamCollection");
	    JSONArray features=new JSONArray();
	    geojsonresult.put("features", features);
		for(OghamObject obj:objects) {
			features.put(obj.toGeoJSONLD());
		}
		return geojsonresult;
	}
	
}
