import java.util.HashMap;
import java.util.Map;

public class FNHashing<T> {
    int size;
    public FNEntry<T>[] en;
    public FNHashing(int size){
        this.size=size;
        en=new FNEntry[size];
        for(int i = 0 ; i< size ; i++){
            en[i]= (FNEntry<T>) new FNEntry<>(null ,"E");
        }

    }
    public boolean add(T dataObject , Student student) {

        int position = Math.abs(dataObject.hashCode() % size); // Using first name to calculate the position


        if (position > 0 && (((en[position]).getStatus()).equals("E"))) { // Search if the name is new
            en[position] = new FNEntry(student, "O");//Add it with the status occupied
            return true;
        }
        else {
            while (en[position].getStatus().equals("O") && position < size - 1) { // Search if the name is repetitive
                if (((Student) (en[position].getData())).getFName() .equals(dataObject)) {
                    en[position].getList().addToHead(student);// Add it to a linkList
                    return true;
                } else {
                    position++;
                }
            }
                position = findNextAvailableSlot(position); // There is no empty place and the name is not repetitive, search for a new position
                en[position] = new FNEntry(student, "O"); // Add the new name


            return false;

        }
    }
    public int findNextAvailableSlot(int currentSlot){

        while (currentSlot+1 != size && en[currentSlot].getStatus()!= "E"){
            currentSlot++;
        }


        return currentSlot;

    }
    public SLL search(T dataObject){
        int position = Math.abs(dataObject.hashCode() % size);// Using first name to calculate the position

        while (en[position].getStatus().equals("O") && position < size - 1) {
            if (((Student) (en[position].getData())).getFName().equals(dataObject)) { // The list of duplicate names is found
                return en[position].getList();  //Return the duplicate names list

            }
            else {
                position++;
            }
        }
        return null;
    }


    public String toString(){
        for (int i = 0; i<size;i++){
            if(en[i].getData()!=null){
                Student e = (Student) en[i].getData();
                System.out.println(i+"."+"["+e.getId()+" , "+e.getLName()+" , "+e.getFName()+" , "+e.getBrithDay()+" , "+e.getAcademicLevel()+"]");

            }
            else
                System.out.println(i+"."+"["+null+"]");
        }
        return "";
    }
}
