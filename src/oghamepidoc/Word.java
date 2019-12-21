package oghamepidoc;

public class Word {

	public String word;
	
	public String translation;
	
	public String wikidataIndividual;
	
	public String wikidataClass;
	
	public String reference;
	
	public Word(String word,String translation, String wikidataIndividual,String wikidataClass,String reference) {
		this.word=word;
		this.translation=translation;
		this.wikidataIndividual=wikidataIndividual;
		this.wikidataClass=wikidataClass;
		this.reference=reference;
	}
	
	
	@Override
	public String toString() {
		return word;
	}
}
