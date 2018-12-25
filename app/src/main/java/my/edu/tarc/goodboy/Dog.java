package my.edu.tarc.goodboy;

public class Dog
{
    private String dogId;
    private String dogBreed;
    private String dogColor;
    private String dogCondition;
    private String dogOrganization;
    private String dogGender;
    private int dogAge;
    private String dogSize;

    public Dog(String dogId, String dogBreed, String dogColor, String dogCondition, String dogOrganization, String dogGender, int dogAge, String dogSize) {
        this.dogId = dogId;
        this.dogBreed = dogBreed;
        this.dogColor = dogColor;
        this.dogCondition = dogCondition;
        this.dogOrganization = dogOrganization;
        this.dogGender = dogGender;
        this.dogAge = dogAge;
        this.dogSize = dogSize;
    }

    public Dog() {
    }

    public String getDogSize() {
        return dogSize;
    }

    public void setDogSize(String dogSize) {
        this.dogSize = dogSize;
    }

    public int getDogAge() {
        return dogAge;
    }

    public void setDogAge(int dogAge) {
        this.dogAge = dogAge;
    }

    public String getDogGender() {
        return dogGender;
    }

    public void setDogGender(String dogGender) {
        this.dogGender = dogGender;
    }

    public String getDogOrganization() {
        return dogOrganization;
    }

    public void setDogOrganization(String dogOrganization) { this.dogOrganization = dogOrganization; }

    public String getDogCondition() {
        return dogCondition;
    }

    public void setDogCondition(String dogCondition) {
        this.dogCondition = dogCondition;
    }

    public String getDogColor() {
        return dogColor;
    }

    public void setDogColor(String dogColor) {
        this.dogColor = dogColor;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public String getDogId() {
        return dogId;
    }

    public void setDogId(String dogId) {
        this.dogId = dogId;
    }
}
