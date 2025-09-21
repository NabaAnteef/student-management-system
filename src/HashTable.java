public class HashTable<T> {
    private Entry<T>[] array;
    int size;

    //to make the hash table
    HashTable(int size) {
        this.size = size;
        array = new Entry[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Entry(); // before adding anything the hash table will have the default Entry()
        }
    }

    // to find the next available index that is not occupied
    public int findNextAvailableSlot(int currentSlot) {
        int nextSlot = (currentSlot + 1) % size; //it finds the next index using linear propping
        while (!array[nextSlot].status.equals("E")) {
            nextSlot = (nextSlot + 1) % size; //linear propping
            if (nextSlot == currentSlot) { //we reach the end of the table
                return -1;
            }
        }
        return nextSlot;
    }

    //it takes the student object from the table and add it to the hash table depending on the id
    public boolean insert(Student dataObject, int id) {
        int index = id %size;
        if (array[index].status.equals("E") || array[index].status.equals("D")) {
            array[index].dataObject = dataObject; //add the student to the index
            array[index].status = "O"; // change the status
            return true;
        }
        index = findNextAvailableSlot(index); //if the first index is occupied
        if (index != -1) {
            array[index].dataObject = dataObject; //add the student to the index
            array[index].status = "O"; // change the status
            return true;
        }
        return false; //the table is full and there is no place
    }

    //to find the index of a student with specific id
    public int find(Student dataObject) {
        int index = dataObject.getId() % size;
        int startIndex = index; // we start with the index and check if we find the object or no and then change the index if we do not find it
        while (array[index] != null) {
            if (array[index].status.equals("O") && array[index].dataObject.equals(dataObject)) { // check the status is O and the object is the same as the dataObject
                return index;
            }
            index = (index + 1) % size; // linear propping
            if (index == startIndex) {
                break;
            }
        }
        return -1;
    }

    // it searches for the student and then return it
    public Student search(int id) {
        int index = id % size;
        int startIndex = index;
        while (array[index] != null) {
            if (array[index].status.equals("O") && array[index].dataObject.getId()==id) {  // check the status is O and the id is the same as the dataObject id
                return array[index].dataObject; //it returns the student
            }
            index = (index + 1) % size; //linear propping
            if (index == startIndex) {
                break;
            }
        }
        return null;
    }

    // to delete the student from the table
    public boolean delete(Student dataObject) {
        if (find(dataObject) != -1) {
            array[find(dataObject)].status = "D"; //change the status
            return true;
        }
        return false; // student not found
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(i).append(": ").append(array[i].toString()).append("\n");
        }
        return sb.toString();
    }
}