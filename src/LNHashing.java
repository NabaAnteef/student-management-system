public class LNHashing<T> {
    int size;
    public FNEntry<T>[] en;
    public LNHashing(int size){
        this.size=size;
        en=new FNEntry[size];
        for(int i = 0 ; i< size ; i++){
            en[i]= (FNEntry<T>) new FNEntry<>(null ,"E");
        }

    }
    public boolean add(T dataObject , Student student){

        int position = Math.abs(dataObject.hashCode() % size); // Using the last name to calculate the position
        if(position>0&&(((en[position]).getStatus()).equals("E"))){ // Search if the last name is new
            en[position]= new FNEntry(student,"O");//Add it with the status occupied
            return true;
        }
        else {
            while (en[position].getStatus().equals("O") && position < size - 1) { // Search if the last name is repetitive
                if (((Student) (en[position].getData())).getLName().equals(dataObject)) {
                    en[position].getList().addToHead(student);// Add it to a linkList
                    return true;
                } else {
                    position++;
                }
            }
                position = findNextAvailableSlot(position);
                en[position] = new FNEntry(student, "O");

            return false;
        }
    }
    public SLL search(T dataObject){
        int position = Math.abs(dataObject.hashCode() % size);
        while (en[position].getStatus().equals("O") && position < size - 1) {
            if (((Student) (en[position].getData())).getLName().equals(dataObject)) {// The list of duplicate last names is found
                return en[position].getList(); //Return the duplicate names list
            }
            else {
                position++;
            }
        }
        return null;
    }
    public int findNextAvailableSlot(int currentSlot){

        while (currentSlot+1 != size && en[currentSlot].getStatus()!= "E"){
            currentSlot++;
        }

        return currentSlot;

    }

    public String toString(){
        for (int i = 0; i<size;i++){
            if(en[i].getData()!=null){
                Student e = (Student) en[i].getData();

               System.out.println(i+"."+"["+e.getFName()+" , "+e.getLName()+" , "+e.getId()+" , "+e.getBrithDay()+" , "+e.getAcademicLevel()+" , "+en[i].getStatus()+"]");

            }
            else
                System.out.println(i+"."+"["+null+" , "+en[i].getStatus()+"]");
        }
        return "";
    }
}
