import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initializing the needed data structures
        // We choose 313 because it is the nearest prime number to the file size
        FNHashing<String> FNHash = new FNHashing<>(313);
        LNHashing<String> LNHash = new LNHashing<>(313);
        HashTable<Integer> IDTable = new HashTable<>(313);
        ArrayList<SLL<Student>> ALarrayList = new ArrayList<>();

        FileWriter writer = new FileWriter("students-details.csv",true);
        //Fill the data structure for academic level with information
        for (int i = 0; i < 4; i++) {
            SLL<Student> studentSLL = new SLL<>();
            ALarrayList.add(studentSLL);
        }
        //Fill the data structure with data from the file
        File file = new File("students-details.csv");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String[] object = scanner.next().split(",");
            Student student = new Student(Integer.parseInt(object[0]), object[1], object[2], object[3], object[4]);
            FNHash.add(student.getFName(), student);
            LNHash.add(student.getLName(), student);
            IDTable.insert(student, Integer.parseInt(object[0]));
            if (object[4].equals("FR")) {
                ALarrayList.get(0).addToTail(new Student(Integer.parseInt(object[0]), object[1], object[2], object[3], object[4]));
            } else if (object[4].equals("SO")) {
                ALarrayList.get(1).addToTail(new Student(Integer.parseInt(object[0]), object[1], object[2], object[3], object[4]));
            } else if (object[4].equals("JR")) {
                ALarrayList.get(2).addToTail(new Student(Integer.parseInt(object[0]), object[1], object[2], object[3], object[4]));
            } else {
                ALarrayList.get(3).addToTail(new Student(Integer.parseInt(object[0]), object[1], object[2], object[3], object[4]));
            }
        }


        // Displaying the main menu
        System.out.println("Please select a number");
        System.out.println("1. Search Student");
        System.out.println("2. Add new Student");
        System.out.println("3. Show students in academic level");
        System.out.println("4. Exit");

        // Taking the input from the user
        Scanner input = new Scanner(System.in);
        int input2 = input.nextInt();

        while (input2!= 4) { // While the user does not choose "Exit"
            if (input2 == 1) { // If user chooses Search Student

                // Display the choices of searching
                System.out.println("1-By exact student ID");
                System.out.println("2-By last name ");
                System.out.println("3-By first name ");
                System.out.println("Enter the search method you want to use (1,2,3): ");
                SLL<Student> list = new SLL<>(); //The list of duplicate data
                Student student = null;
                // Getting user choice
                int choice2 = input.nextInt();

                // Search by ID
                if (choice2 == 1) {
                    System.out.println("Enter the student id : ");
                    int id = input.nextInt(); // Getting the id from the user
                    student = IDTable.search(id); // Getting the student object from the table
                    if (student == null) { // There is no such object with the id
                        System.out.println("The ID does not exist ");
                    }
                    else { // The object exist
                        System.out.println("Your choice is : ");
                        System.out.println(student);
                    }


                }

                //Search by student last name
                else if (choice2 == 2) {
                    System.out.println("Enter the student last name : ");
                    String LName = input.next(); // Getting the last name from the user
                    list = LNHash.search(LName); // Getting the list of duplicate last names from the search method
                    if(!list.isEmpty()){ // The list exist
                        list.print(); // Print the list

                        // Ask the user to choose one student
                        System.out.println("Choose  the index of the list (Start from 1) : ");
                        int index = input.nextInt();
                        student = list.findIndex(index);
                        System.out.println("Your choice is : ");
                        System.out.println(student);//Display the user choice
                    }
                    else
                        System.out.println("The name does not exist");//There is no student with such last name

                }

                else if (choice2 == 3) { //Search by student first name
                    System.out.println("Enter the student first name : ");
                    String FName = input.next(); // Getting the first name from the user
                    list = FNHash.search(FName);// Getting the list of duplicate first names from the search method

                    if(list!=null&&!list.isEmpty()){//The list exist
                        list.print();// Print the list
                        // Ask the user to choose one student
                        System.out.println("Choose  the index of the list (Start from 1) : ");
                        int index = input.nextInt();
                        student = list.findIndex(index);
                        System.out.println("Your choice is : ");
                        System.out.println(student);
                    }
                    else
                        System.out.println("The name does not exist");

                }
                // If the student exist display menu of prossceing choices
                if (student != null) {
                    System.out.println("1- Edit the student");
                    System.out.println("2- Delete the student");
                    System.out.println("3- Return to main menu");
                    System.out.println("Enter what you want to do with the student (1,2,3): ");

                    int choice3 = input.nextInt();//Take the user input

                    // If user chooses Edit
                if (choice3 == 1) {

                    //Display editing choices
                    System.out.println("what do you want to edit?(1,2,3,4,5)");
                    System.out.println("1- Edit the student ID");
                    System.out.println("2- Edit the student first name");
                    System.out.println("3- Edit the student last name");
                    System.out.println("4- Edit the student date of birth");
                    System.out.println("5- Edit the student academic level");
                    int choice4 = input.nextInt(); // Getting the user choice


                    if (choice4 == 1) { // In case of editing the ID
                        String oldObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel(); // Get the text of the old information
                        System.out.println("Enter the new ID");
                        int newId = input.nextInt(); // Get the new id
                        //Delete the old info from the data structure
                        IDTable.delete(student);
                        list=FNHash.search(student.getFName());
                        list.delete(student);
                        list= LNHash.search(student.getLName());
                        list.delete(student);
                        deleteStudentFromArray(ALarrayList,student);
                        //Update the id
                        student.setId(newId);
                        //Fill the data structure and the file with the new info
                        IDTable.insert(student,newId);
                        addStudentFromArray(ALarrayList,student);
                        FNHash.add(student.getFName(),student);
                        LNHash.add(student.getLName(),student);
                        String newObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel();// Get the text of the new information
                        editFile(oldObject,newObject,student);
                        System.out.println("you have successfully edit the student, here is the new student details: "); // Display the secsseful message
                        System.out.println(student);

                    }

                    // In case of editing the First name
                    else if (choice4 == 2) {
                        String oldObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel();// Get the text of the old information
                        System.out.println("Enter the new first name: ");
                        String newFN = input.next();// Getting the first name from the user

                        //Delete the old info from the data structure
                        IDTable.delete(student);
                        list=FNHash.search(student.getFName());
                        list.delete(student);
                        list= LNHash.search(student.getLName());
                        list.delete(student);
                        deleteStudentFromArray(ALarrayList,student);
                        student.setFirstName(newFN);//Update the first name

                        //Fill the data structure and the file with the new info
                        IDTable.insert(student,student.getId());
                        addStudentFromArray(ALarrayList,student);
                        FNHash.add(student.getFName(),student);
                        LNHash.add(student.getLName(),student);
                        String newObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel();// Get the text of the new information
                        editFile(oldObject,newObject,student);
                        System.out.println("you have successfully edit the student, here is the new student details: "); // Display the secsseful message
                        System.out.println(student);

                    }
                    // In case of editing the Last name
                    else if (choice4 == 3) {
                        String oldObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel();// Get the text of the old information
                        System.out.println("Enter the new last name: ");
                        String newLN = input.next();// Getting the new last name from the user

                        //Delete the old info from the data structure
                        IDTable.delete(student);
                        list=FNHash.search(student.getFName());
                        list.delete(student);
                        list= LNHash.search(student.getLName());
                        list.delete(student);
                        deleteStudentFromArray(ALarrayList,student);
                        student.setLastName(newLN);//Update the last name

                        //Fill the data structure and the file with the new info
                        IDTable.insert(student,student.getId());
                        addStudentFromArray(ALarrayList,student);
                        FNHash.add(student.getFName(),student);
                        LNHash.add(student.getLName(),student);
                        String newObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel();// Get the text of the new information
                        editFile(oldObject,newObject,student);
                        System.out.println("you have successfully edit the student, here is the new student details: "); // Display the secsseful message
                        System.out.println(student);

                    }

                    // In case of editing the date of birth
                    else if (choice4 == 4) {
                        String oldObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel();// Get the text of the old information
                        System.out.println("Enter the new date of birth (DD/MM/YYYY)");
                        String newDOB = input.next();//Getting the new date of birth from the user

                        //Delete the old info from the data structure
                        IDTable.delete(student);
                        list=FNHash.search(student.getFName());
                        list.delete(student);
                        list= LNHash.search(student.getLName());
                        list.delete(student);
                        deleteStudentFromArray(ALarrayList,student);
                        student.setBrithDay(newDOB); //Update the date of birth

                        //Fill the data structure and the file with the new info
                        IDTable.insert(student,student.getId());
                        addStudentFromArray(ALarrayList,student);
                        FNHash.add(student.getFName(),student);
                        LNHash.add(student.getLName(),student);
                        String newObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel();// Get the text of the new information
                        editFile(oldObject,newObject,student);
                        System.out.println("you have successfully edit the student, here is the new student details: "); // Display the secsseful message
                        System.out.println(student);

                    }

                    // In case of editing the Academic level
                    else if (choice4 == 5) {
                        String oldObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel();// Get the text of the old information
                        System.out.println("Enter the new academic level: ");

                        // Delete the old info from the data structure
                        IDTable.delete(student);
                        deleteStudentFromArray(ALarrayList,student);
                        list=FNHash.search(student.getFName());
                        list.delete(student);
                        list= LNHash.search(student.getLName());
                        list.delete(student);

                        String newLevel = input.next(); //Getting the new academic level from the user
                        student.setAcademicLevel(newLevel);//Update the academic level
                        //Fill the data structure with the new info
                        IDTable.insert(student,student.getId());
                        addStudentFromArray(ALarrayList,student);
                        FNHash.add(student.getFName(),student);
                        LNHash.add(student.getLName(),student);
                        String newObject = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel();// Get the text of the new information
                        editFile(oldObject,newObject,student);
                        System.out.println("you have successfully edit the student, here is the new student details: "); // Display the secsseful message
                        System.out.println(student);

                    }
                }

                // If the user chooses Delete the student
                else if (choice3 == 2) {
                    String line = student.getId() + "," + student.getLName() + "," + student.getFName() + "," + student.getBrithDay() + "," + student.getAcademicLevel(); // The original text
                    String content = new String(Files.readAllBytes(Paths.get("students-details.csv"))); // Getting the content from the file
                    content = content.replace(line, "").replace(" ", "");// Replace the deleted line by empty string
                    Files.write(Paths.get("students-details.csv"), content.getBytes()); // rewrite the file with the new content
                    Files.write(Path.of("students-details.csv"), Files.lines(Paths.get("students-details.csv")).map(String::trim).filter(line2 -> !line2.isEmpty()).collect(Collectors.toList())); // Delete the empty line

                    //The list is form last name hashing
                    if(choice2 == 2){
                        list.delete(student);// Delete the student from the last name hashing
                        list = FNHash.search(student.getFName());// Getting the first name hashing
                        list.delete(student);// Delete the student from the first name hashing
                    }

                    //The list is form first name hashing
                    else if(choice2 == 3){
                        list.delete(student);// Delete the student from the first name hashing
                        list=LNHash.search(student.getLName());// Getting the last name hashing
                        list.delete(student);// Delete the student from the last name hashing
                    }
                    IDTable.delete(student); // Delete the student from ID hashing
                    deleteStudentFromArray(ALarrayList,student);
                    System.out.println("You have successfully deleted the student ");
                }

                else if (choice3 == 3) { //Return to the main menu
                    System.out.print("");
                }
            }
            }

            // If the user chooses Add a new student
            else if (input2 == 2) {

                // Getting the information from the user
                System.out.println("Enter student first name : ");
                String Fname = input.next(); // Getting the first name from the user
                System.out.println("Enter student last name : ");
                String Lname = input.next(); // Getting the last name from the user
                System.out.println("Enter student ID : ");
                String ID = input.next(); // Getting the ID from the user
                System.out.println("Enter student birth day date : ");
                String date = input.next(); // Getting the date of birth from the user
                System.out.println("Enter student academic level : ");
                String academicLevel = input.next(); //Getting the academic level from the user

                //Filling the data structure  and the file with the info
                Student student = new Student(Integer.parseInt(ID), Lname, Fname, date, academicLevel);
                FNHash.add(Fname, student);
                LNHash.add(Lname, student);
                IDTable.insert(student, Integer.parseInt(ID));
                addStudentFromArray(ALarrayList,student);

                writer.write("\n"+ID+","+Lname+","+Fname+","+date+","+academicLevel);


            }
            // If the user chooses Show students in academic level
            else if (input2==3){
                System.out.println("Enter the academic level: ");
                String AL= input.next(); // Getting the academic level from the user
                studentInTheSameLevel(AL,ALarrayList).print(); // Printing the students in one academic level
            }

            // Displaying the menu again
            System.out.println("Please select a number");
            System.out.println("1. Search Student");
            System.out.println("2. Add new Student");
            System.out.println("3. Show students in academic level");
            System.out.println("4. Exit");
            input2 = input.nextInt();
        }
        writer.close();
        System.out.println("you have successfully exit the system");






    }


    // to find the student in the level from the array
    public static SLL<Student>  studentInTheSameLevel(String level, ArrayList<SLL<Student>> ALarrayList){
        if(level.equals("FR")){
            return ALarrayList.get(0); // The first level
        }
        else if(level.equals("SO")){
            return ALarrayList.get(1); // The second level
        }
        else if(level.equals("JR")){
            return ALarrayList.get(2); // The third level
        }
        else{
            return ALarrayList.get(3); // The fourth level
        }
    }
    // A method to edit the file
    public static void editFile(String oldObject , String newObject, Student student) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("students-details.csv")));
        content = content.replace(oldObject, newObject);
        Files.write(Paths.get("students-details.csv"), content.getBytes());

    }


    public static void deleteStudentFromArray(ArrayList<SLL<Student>> ALarrayList,Student student) {
        if(student.getAcademicLevel().equals("FR")){
            ALarrayList.get(0).deleteAC(student); // Delete the student from The first level
        }
        else if(student.getAcademicLevel().equals("SO")){
            ALarrayList.get(1).deleteAC(student);  // Delete the student from The second level
        }
        else if(student.getAcademicLevel().equals("JR")){

            ALarrayList.get(2).deleteAC(student); // Delete the student from The third level
        }
        else{
            ALarrayList.get(3).deleteAC(student); // Delete the student from the fourth
        }
    }
    public static void addStudentFromArray(ArrayList<SLL<Student>> ALarrayList,Student student) {
        if(student.getAcademicLevel().equals("FR")){
            ALarrayList.get(0).addToTail(student); // Add the student to The first level
        }
        else if(student.getAcademicLevel().equals("SO")){
            ALarrayList.get(1).addToTail(student); // Add the student to The second level
        }
        else if(student.getAcademicLevel().equals("JR")){
            ALarrayList.get(2).addToTail(student); // Add the student to The third level
        }
        else{
            ALarrayList.get(1).addToTail(student); // Add the student to The fourth level
        }
    }
}
