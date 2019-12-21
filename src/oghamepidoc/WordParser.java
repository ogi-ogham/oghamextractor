package oghamepidoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class WordParser {
	
	Map<String,Set<String>> wordToVariants=new TreeMap<>();
	
	
	
	
	public static Map<String,Tuple<Word,Integer>> csvToWordMap(String fileName) throws FileNotFoundException, IOException {
		FileReader fileReader = new FileReader(new File(fileName));
		CSVParserBuilder csvParserBuilder = new CSVParserBuilder();
	    CSVParser parser = csvParserBuilder.withSeparator(',').build();
	    CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();
	    String[] lineContent;
	    Map<String,Tuple<Word,Integer>> words=new TreeMap<>();
	    while ((lineContent = reader.readNext()) != null) {
	    	String vars="";
	    	if(!lineContent[5].isEmpty() && lineContent[5].contains("["))
	    		vars=lineContent[5].replace("[","").replace("]","");
	    	System.out.println(vars);
	    	String[] variants=vars.split("\\|");
	    	Word word=new Word(lineContent[0].toLowerCase(),lineContent[3],lineContent[1],lineContent[2],lineContent[4]);
	    	if(lineContent[0]!=null) {
	    		System.out.println(lineContent[0]);
	    		words.put(lineContent[0].toLowerCase(),new Tuple<Word,Integer>(word,0));
	    	}
	    	for(String var:variants) {
	    		System.out.println(var);
	    		if(!var.isEmpty())
	    			words.put(var.toLowerCase(),new Tuple<Word,Integer>(word,0));
	    	}
	    }
	   return words;
	}
}
