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
	
	public String text="";
	
	public Set<String> persons=new TreeSet<String>();
	
	public Set<String> words=new TreeSet<String>();
	
	public Set<Tuple<String,String>> sonOfSet=new TreeSet<Tuple<String,String>>();
	
	public Set<Tuple<String,String>> followerOfSet=new TreeSet<Tuple<String,String>>();
	
	public Set<Tuple<String,String>> descendantOfSet=new TreeSet<Tuple<String,String>>();
	
	public Set<Tuple<String,String>> nephewOfSet=new TreeSet<Tuple<String,String>>();
	
	public Set<Tuple<String,String>> tribePartOfSet=new TreeSet<Tuple<String,String>>();
	
	Boolean containskoi=(Boolean)false,containsanm=(Boolean)false,justoneword=(Boolean)false;
	
	public static final String BASEURI="http://www.i3mainz.de/ogham#";
	
	public OntModel toRDF(OntModel model) {
		System.out.println(title);
		OntClass oghamobj=model.createClass(BASEURI+"OghamObject");
		oghamobj.addLabel("Ogham Object","en");
		OntClass dictionary=model.createClass("http://www.w3.org/ns/lemon/lime#Lexicon");
		dictionary.addLabel("Lexicon","en");
		OntClass character=model.createClass("http://www.w3.org/ns/lemon/ontolex#Character");
		character.addLabel("Character","en");
		OntClass lexicalSense=model.createClass("http://www.w3.org/ns/lemon/ontolex#LexicalSense");
		lexicalSense.addLabel("Lexical Sense","en");
		OntClass word=model.createClass("http://www.w3.org/ns/lemon/ontolex#Word");
		word.addLabel("Word","en");
		OntClass person=model.createClass("http://xmlns.com/foaf/0.1/Person");
		person.addLabel("Person","en");
		OntClass geometry=model.createClass("http://www.opengis.net/ont/geosparql#Geometry");
		geometry.addLabel("Geometry","en");
		OntClass point=model.createClass("http://www.opengis.net/ont/sf#Point");
		point.addLabel("Point","en");
		OntClass spatialobject=model.createClass("http://www.opengis.net/ont/geosparql#SpatialObject");
		spatialobject.addLabel("Spatial Object","en");
		OntClass feature=model.createClass("http://www.opengis.net/ont/geosparql#Feature");
		feature.addLabel("Feature","en");
		OntClass tribe=model.createClass("http://www.wikidata.org/entity/Q3538737");
		tribe.addLabel("Tribe","en");
		OntClass oghamletter=model.createClass("http://www.wikidata.org/entity/Q41812345");
		oghamletter.addLabel("Ogham Letter","en");
        OntClass oghamword=model.createClass("http://www.wikidata.org/entity/Q67384733");
		oghamword.addLabel("Ogham Word","en");
		OntClass nomenclature=model.createClass("http://www.wikidata.org/entity/Q67382150");
		nomenclature.addLabel("Nomenclature Word","en");
		OntClass formular=model.createClass("http://www.wikidata.org/entity/Q67381377");
		formular.addLabel("Formular Word","en");
        Individual maqi=formular.createIndividual("http://www.wikidata.org/entity/Q67381254");
		maqi.addLabel("MAQI","en");
		OntClass wolf=model.createClass("http://www.wikidata.org/entity/Q18498");
        wolf.addLabel("Wolf","en");
        Individual cuna=nomenclature.createIndividual("http://www.wikidata.org/entity/Q67382235");
		cuna.addLabel("CUNA","en");
        OntClass cow=model.createClass("http://www.wikidata.org/entity/Q830");
		cow.addLabel("Cow","en");
        OntClass heaven=model.createClass("http://www.wikidata.org/entity/Q527");
		heaven.addLabel("Heaven","en");
        Individual erc=nomenclature.createIndividual("http://www.wikidata.org/entity/Q67382360");
		erc.addLabel("ERC","en");
		OntClass battle=model.createClass("http://www.wikidata.org/entity/Q178561");
		battle.addLabel("Battle","en");
        Individual cattu=nomenclature.createIndividual("http://www.wikidata.org/entity/Q67383338");
		cattu.addLabel("CATTU","en");
		OntClass godlugh=model.createClass("http://www.wikidata.org/entity/Q215683");
		godlugh.addLabel("God Lugh","en");
        OntClass lug=model.createClass("http://www.wikidata.org/entity/Q67383482");
		lug.addLabel("God Lugh","en");
		feature.addSuperClass(spatialobject);
		oghamobj.addSuperClass(feature);
		geometry.addSuperClass(spatialobject);
		point.addSuperClass(geometry);
		nomenclature.addSuperClass(oghamword);
		formular.addSuperClass(oghamword);
		oghamword.addSuperClass(word);
		Individual curind;
		if(!this.oghamid.isEmpty()) {
			curind=oghamobj.createIndividual(BASEURI+URLEncoder.encode(oghamid));
		}else {
			curind=oghamobj.createIndividual(BASEURI+URLEncoder.encode(title));
		}
		ObjectProperty hasgeom=model.createObjectProperty("http://www.opengis.net/ont/geosparql#hasGeometry");
		hasgeom.addLabel("has Geometry","en");
		ObjectProperty fatherrel=model.createObjectProperty("http://www.wikidata.org/prop/direct/P22");
		fatherrel.setLabel("father", "en");
		ObjectProperty inscriptionmentions=model.createObjectProperty("http://www.wikidata.org/prop/direct/P6568");
		inscriptionmentions.addLabel("inscription mentions","en");
		ObjectProperty relative=model.createObjectProperty("http://www.wikidata.org/prop/direct/P1038");
		relative.addLabel("relative","en");
		ObjectProperty entry=model.createObjectProperty("http://www.w3.org/ns/lemon/lime#entry");
		entry.addLabel("entry","en");
		ObjectProperty sense=model.createObjectProperty("http://www.w3.org/ns/lemon/ontolex#sense");
		sense.addLabel("sense","en");
		ObjectProperty contains=model.createObjectProperty("http://www.w3.org/ns/lemon/ontolex#contains");
		contains.addLabel("contains","en");
		ObjectProperty reference=model.createObjectProperty("http://www.w3.org/ns/lemon/ontolex#reference");
		reference.addLabel("reference","en");
		ObjectProperty hasMember=model.createObjectProperty(BASEURI+"hasMember");
		hasMember.addLabel("has member","en");
		ObjectProperty follows=model.createObjectProperty(BASEURI+"follows");
		follows.addLabel("follows","en");
		ObjectProperty descendantOf=model.createObjectProperty(BASEURI+"descendantOf");
		descendantOf.addLabel("descendant of","en");
		ObjectProperty nameRelatesTo=model.createObjectProperty(BASEURI+"nameRelatesTo");
		nameRelatesTo.addLabel("name relates to","en");
        ObjectProperty definedInWikidata=model.createObjectProperty(BASEURI+"definedInWikidata");
		definedInWikidata.addLabel("defined in Wikidata","en");
		ObjectProperty partofTribe=model.createObjectProperty("http://www.wikidata.org/prop/direct/P463");
		partofTribe.addLabel("part of tribe","en");
		DatatypeProperty image=model.createDatatypeProperty("http://www.wikidata.org/prop/direct/P18");
		image.addLabel("image","en");
		DatatypeProperty transliteration=model.createDatatypeProperty("http://www.w3.org/ns/lemon/ontolex#transliteration");
		transliteration.addLabel("transliteration","en");
		DatatypeProperty script=model.createDatatypeProperty("http://www.w3.org/ns/lemon/ontolex#writtenRep");
		script.addLabel("written representation","en");
		Individual oghamdict=dictionary.createIndividual(BASEURI+"OghamDictionary");
		oghamdict.addLabel("Ogham Dictionary","en");
		for(String woord:words) {
			Individual wordd=word.createIndividual(BASEURI+URLEncoder.encode(woord));
			wordd.addLabel("Word: "+woord+" ("+OghamUtils.translitToUnicode(woord)+")","en");
			if(OghamUtils.nomenclature.contains(woord)) {
				wordd.addRDFType(nomenclature);
			}
			if(OghamUtils.formular.contains(woord)) {
				wordd.addRDFType(formular);
			}
			for(int i=0;i<woord.length();i++) {
				String curstr=woord.charAt(i)+"";
				if(OghamUtils.oghammap.containsKey(curstr.toLowerCase())) {
					Individual chara=character.createIndividual(BASEURI+OghamUtils.oghammap.get(curstr.toLowerCase())+"_character");
					chara.addLabel("Character: "+OghamUtils.oghammap.get(curstr.toLowerCase()),"en");
					chara.addRDFType(oghamletter);
					wordd.addProperty(contains, chara);
					chara.addProperty(transliteration, curstr.toUpperCase());
					chara.addProperty(script, OghamUtils.oghammap.get(curstr.toLowerCase()));
				}
			}
			wordd.addProperty(transliteration, woord);
			wordd.addProperty(script, OghamUtils.translitToUnicode(woord));
			oghamdict.addProperty(entry, wordd);
		}
		for(String perss:persons) {
			Individual persson=word.createIndividual(BASEURI+URLEncoder.encode(perss));
			persson.addLabel("Person Word: "+perss,"en");
			Individual personsense=person.createIndividual(BASEURI+URLEncoder.encode(perss)+"_person");
			persson.addLabel("Person: "+perss,"en");
			if(perss.contains("CUNA")) {
				personsense.addProperty(nameRelatesTo, wolf);
                personsense.addProperty(definedInWikidata, cuna);
			}else if(perss.contains("CATTU")) {
				personsense.addProperty(nameRelatesTo, battle);
                personsense.addProperty(definedInWikidata, cattu);
			}else if(perss.contains("LUG")) {
				personsense.addProperty(nameRelatesTo, godlugh);
                personsense.addProperty(definedInWikidata, lug);
			} else if(perss.contains("ERC")) {
				personsense.addProperty(nameRelatesTo, heaven);
                personsense.addProperty(nameRelatesTo, cow);
                personsense.addProperty(definedInWikidata, erc);
			}
			Individual lexsense=lexicalSense.createIndividual(BASEURI+URLEncoder.encode(perss)+"_sense");
			lexsense.addLabel(perss+" Sense","en");
			persson.addProperty(sense, lexsense);

			lexsense.addProperty(reference, personsense);
			curind.addProperty(inscriptionmentions, persson);
		}
		for(Tuple<String,String> sonof:sonOfSet) {
			Individual son=person.createIndividual(BASEURI+URLEncoder.encode(sonof.getOne())+"_person");
			Individual father=person.createIndividual(BASEURI+URLEncoder.encode(sonof.getTwo())+"_person");
			son.addProperty(fatherrel, father);
			curind.addProperty(inscriptionmentions, son);
			curind.addProperty(inscriptionmentions, father);
		}	
		for(Tuple<String,String> partof:tribePartOfSet) {
			Individual pers=person.createIndividual(BASEURI+URLEncoder.encode(partof.getOne())+"_person");
			Individual tribee=tribe.createIndividual(BASEURI+URLEncoder.encode(partof.getTwo()));
			pers.addProperty(partofTribe, tribee);
			tribee.addProperty(hasMember, pers);
			curind.addProperty(inscriptionmentions, pers);
			curind.addProperty(inscriptionmentions, tribee);
		}
		for(Tuple<String,String> partof:nephewOfSet) {
			Individual pers=person.createIndividual(BASEURI+URLEncoder.encode(partof.getOne())+"_person");
			Individual tribee=person.createIndividual(BASEURI+URLEncoder.encode(partof.getTwo())+"_person");
			pers.addProperty(relative, tribee);
			tribee.addProperty(relative, pers);
			curind.addProperty(inscriptionmentions, pers);
			curind.addProperty(inscriptionmentions, tribee);
		}
		for(Tuple<String,String> partof:followerOfSet) {
			Individual pers=person.createIndividual(BASEURI+URLEncoder.encode(partof.getOne())+"_person");
			Individual tribee=person.createIndividual(BASEURI+URLEncoder.encode(partof.getTwo())+"_person");
			pers.addProperty(follows, tribee);
			curind.addProperty(inscriptionmentions, pers);
			curind.addProperty(inscriptionmentions, tribee);
		}
		for(Tuple<String,String> partof:this.descendantOfSet) {
			Individual pers=person.createIndividual(BASEURI+URLEncoder.encode(partof.getOne())+"_person");
			Individual tribee=person.createIndividual(BASEURI+URLEncoder.encode(partof.getTwo())+"_person");
			pers.addProperty(descendantOf, tribee);
			curind.addProperty(inscriptionmentions, pers);
			curind.addProperty(inscriptionmentions, tribee);
		}
		DatatypeProperty asWKT=model.createDatatypeProperty("http://www.opengis.net/ont/geosparql#asWKT");
		Individual geomind=point.createIndividual(BASEURI+URLEncoder.encode(title)+"_geom");
		curind.addProperty(hasgeom, geomind);
		curind.addProperty(image, imagelink);
		geomind.addLabel(title+" Geometry","en");
		geomind.addLiteral(asWKT, model.createTypedLiteral(location.toText(),"http://www.opengis.net/ont/geosparql#wktLiteral"));
    	return model;
	}
	
	
	public JSONObject toGeoJSONLD() {
		JSONObject geojsonobj=new JSONObject();
    	geojsonobj.put("type", "Feature");
    	JSONObject properties=new JSONObject();
    	properties.put("name", name);
    	properties.put("id", oghamid);
    	properties.put("title", title);
    	properties.put("image", imagelink);
    	properties.put("text",text);
    	properties.put("textogham",OghamUtils.translitToUnicode(text));
    	boolean containsWolfName=false,containsBattleName=false,containsGodLughName=false,containsCowName=false,containsBlind=false,containsEye=false;
    	for(String perss:persons) {
			if(perss.contains("CUNA")) {
				containsWolfName=true;
			}else if(perss.contains("CATTU")) {
				containsBattleName=true;
			}else if(perss.contains("LUG")) {
				containsGodLughName=true;
			} else if(perss.contains("ERC")) {
				containsCowName=true;
			} else if(perss.contains("DALAGNI")) {
				containsBlind=true;
			} else if(perss.contains("DERCMASOC")) {
				containsEye=true;
			}
		}
		properties.put("containsWolfName", containsWolfName);
		properties.put("containsBattleName", containsBattleName);
		properties.put("containsGodLughName", containsGodLughName);
		properties.put("containsCowName", containsCowName);
		properties.put("containsBlind", containsBlind);
		properties.put("containsEye", containsEye);
		properties.put("containsHereIs", containskoi);
		properties.put("containsName", containsanm || justoneword);
		JSONArray pers=new JSONArray();
    	for(String person:this.persons) {
    		JSONObject obj=new JSONObject();
    		obj.put("person", person);
    		obj.put("personogham", OghamUtils.translitToUnicode(person));
    		pers.put(obj);
    	}
    	properties.put("persons", pers);
    	JSONArray fatherson=new JSONArray();
    	for(Tuple<String,String> person:this.sonOfSet) {
    		JSONObject fathersonrel=new JSONObject();
    		fathersonrel.put("father", person.getTwo());
    		fathersonrel.put("fatherogham", OghamUtils.translitToUnicode(person.getTwo()));
    		fathersonrel.put("son", person.getOne());
    		fathersonrel.put("sonogham", OghamUtils.translitToUnicode(person.getOne()));
    		fatherson.put(fathersonrel);
    	}
    	
    	properties.put("fatherson", fatherson);
    	JSONArray tribes=new JSONArray();
    	for(Tuple<String,String> person:this.tribePartOfSet) {
    		JSONObject triberel=new JSONObject();
    		triberel.put("person", person.getTwo());
    		triberel.put("personogham", OghamUtils.translitToUnicode(person.getTwo()));
    		triberel.put("tribe", person.getOne());
    		triberel.put("tribeogham", OghamUtils.translitToUnicode(person.getOne()));
    		tribes.put(triberel);
    	}	
    	properties.put("tribes", tribes);
    	JSONArray descendants=new JSONArray();
    	for(Tuple<String,String> person:this.descendantOfSet) {
    		JSONObject triberel=new JSONObject();
    		triberel.put("person", person.getTwo());
    		triberel.put("personogham", OghamUtils.translitToUnicode(person.getTwo()));
    		triberel.put("descendant", person.getOne());
    		triberel.put("descendantogham", OghamUtils.translitToUnicode(person.getOne()));
    		descendants.put(triberel);
    	}	
    	properties.put("descendants", descendants);
    	JSONArray followers=new JSONArray();
    	for(Tuple<String,String> person:this.followerOfSet) {
    		JSONObject triberel=new JSONObject();
    		triberel.put("person", person.getTwo());
    		triberel.put("personogham", OghamUtils.translitToUnicode(person.getTwo()));
    		triberel.put("follower", person.getOne());
    		triberel.put("followerogham", OghamUtils.translitToUnicode(person.getOne()));
    		followers.put(triberel);
    	}	
    	properties.put("followers", followers);
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
