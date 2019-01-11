package my.edu.tarc.goodboy;

import java.util.Calendar;

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
    private int dayInDatabase;

    public Dog(String dogId, String dogBreed, String dogColor, String dogCondition, String dogOrganization, String dogGender, int dogAge, String dogSize) {
        this.dogId = dogId;
        this.dogBreed = dogBreed;
        this.dogColor = dogColor;
        this.dogCondition = dogCondition;
        this.dogOrganization = dogOrganization;
        this.dogGender = dogGender;
        this.dogAge = dogAge;
        this.dogSize = dogSize;
        this.dayInDatabase = 0;
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

    public int getDayInDatabase() {
        return dayInDatabase;
    }

    public void setDayInDatabase(String dayInDatabase) {

        int year = Integer.parseInt(dayInDatabase.substring(0, 3));
        int month = Integer.parseInt(dayInDatabase.substring(5, 6));
        int date = Integer.parseInt(dayInDatabase.substring(8, 9));

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDate = Calendar.getInstance().get(Calendar.DATE);

        if (currentYear == year) {
            if (currentMonth == month) {
                this.dayInDatabase = currentDate - date;
            }
            else {
                if (currentDate >= currentDate) {
                    this.dayInDatabase = currentDate - date + ((currentMonth - month) * 30);
                }
                else {
                    this.dayInDatabase = 30 - (date = currentDate) + ((currentMonth - month - 1) * 30);
                }
            }
        }
        else {
            if (currentMonth >= month) {
                if (currentDate >= date) {
                    this.dayInDatabase = currentDate - date + ((currentMonth - month) * 30) + ((currentYear - year) * 365);
                }
                else {
                    this.dayInDatabase = 30 - (date = currentDate) + ((currentMonth - month - 1) * 30) + ((currentYear - year) * 365);
                }
            }
            else {
                if (currentDate >= currentDate) {
                    this.dayInDatabase = currentDate - date + ((currentMonth - month) * 30) + ((currentYear - year - 1) * 365) ;
                }
                else {
                    this.dayInDatabase = 30 - (date = currentDate) + ((currentMonth - month - 1) * 30) + ((currentYear - year - 1) * 365);
                }
            }
        }

    }

    public void setDogId(String dogId) {
        this.dogId = dogId;
    }
}
