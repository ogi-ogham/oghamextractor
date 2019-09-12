package oghamepidoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class OghamUtils {

	public static final Map<String, String> oghammap = new HashMap<String, String>() {{
	    put(" ", " ");  
	    put("b",  "ᚁ");
	    put("l",  "ᚂ");
	    put( "f",  "ᚃ");
	    put( "v",  "ᚄ");
	    put( "s",  "ᚅ");
	    put( "n",  "ᚆ");
	    put( "h",  "ᚇ");
	    put( "d",  "ᚈ");
	    put( "t",  "ᚉ");
	    put( "c",  "ᚊ");
	    put("m",  "ᚋ");
	    put("g",  "ᚌ");
	    put("ng",  "ᚍ");
	    put("z",  "ᚎ");
	    put("r",  "ᚏ");
	    put("a",  "ᚐ");
	    put("o",  "ᚑ");
	    put("u",  "ᚒ");
	    put("e",  "ᚓ");
	    put("i",  "ᚔ");
	}};
	
	
	public static String translitToUnicode(String name) {
		StringBuilder result=new StringBuilder();
		for(int i=0;i<name.length();i++) {
			String curstr=name.charAt(i)+"";
			if(oghammap.containsKey(curstr.toLowerCase())) {
				result.append(oghammap.get(curstr.toLowerCase()));
			}
		}
		return result.toString();
	}
	
	
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
