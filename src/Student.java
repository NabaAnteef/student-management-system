public class Student {
    int id ;
    String firstName;
    String lastName;
    String brithDay;
    String academicLevel;
    public Student(int id , String lastName ,String firstName , String brithDay,String academicLevel){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.brithDay=brithDay;
        this.academicLevel=academicLevel;
    }
    // Getters to get the information
    public int getId(){
        return id;
    }

    public String getFName(){
        return firstName;
    }

    public String getLName(){
        return lastName;
    }
    public String getBrithDay(){
        return brithDay;
    }
    public String getAcademicLevel(){
        return academicLevel;
    }

    //Setters to edit the object
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBrithDay(String brithDay) {
        this.brithDay = brithDay;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    @Override
    public String toString() {

        return "["+ id +" , " + lastName +" , " +firstName  + " , " + brithDay + " , " + academicLevel + "]";
    }
}
