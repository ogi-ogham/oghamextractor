package oghamepidoc;

public class Tuple<T,T2> implements Comparable{
    T one;
    T2 two;
    String unitURI;
	
    public Tuple(T one, T2 two){
        this.one=one;
        this.two=two;
    }
    
    public Tuple(T one, T2 two,String unitURI){
        this.one=one;
        this.two=two;
        this.unitURI=unitURI;
    }
    
    
    @Override
    public int compareTo(Object o) {
        Tuple t=(Tuple) o;
        if(t.two== this.two && this.one==t.one)
            return 0;
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tuple)
            return this.one.equals(((Tuple)obj).one) && this.two.equals(((Tuple)obj).two);
        return false;
    }

    public T getOne(){
        return one;
    }

    public void setOne(final T one) {
        this.one = one;
    }

    public T2 getTwo(){
        return two;
    }

    public void setTwo(final T2 two) {
        this.two = two;
    }

    @Override
    public String toString() {
        return "["+this.one+","+this.two+"]";
    }

	public String getUnit() {
		return this.unitURI;
	}
}
