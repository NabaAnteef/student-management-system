public class Entry<T>{
    protected Student dataObject;

    protected String status;

    public Entry(Student dataObject, String e){ //the constructor to add new entry
        this.dataObject= dataObject;
        this.status="O"; //occupied
    }
    public Entry(){ //default constructor
        this.dataObject=null;
        this.status="E"; //the default status is empty
    }

    @Override
    public String toString() {

        return "[ " + dataObject + ", " + status + " ]"; //to print the entry in the hash table
    }

    @Override
    public int hashCode() {
        return dataObject.hashCode();
    }
}
