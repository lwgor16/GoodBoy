package my.edu.tarc.goodboy;

public class Organization {
    private String organizationID;
    private String organizationName;
    private String organizationLocation;
    private String organizationOwner;
    private String organizationPhoneNumber;

    public Organization(String organizationID, String organizationName, String organizationLocation, String organizationOwner, String organizationPhoneNumber) {
        this.organizationID = organizationID;
        this.organizationName = organizationName;
        this.organizationLocation = organizationLocation;
        this.organizationOwner = organizationOwner;
        this.organizationPhoneNumber = organizationPhoneNumber;
    }

    public Organization() {

    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationLocation() {
        return organizationLocation;
    }

    public void setOrganizationLocation(String organizationLocation) {
        this.organizationLocation = organizationLocation;
    }

    public String getOrganizationOwner() {
        return organizationOwner;
    }

    public void setOrganizationOwner(String organizationOwner) {
        this.organizationOwner = organizationOwner;
    }

    public String getOrganizationPhoneNumber() {
        return organizationPhoneNumber;
    }

    public void setOrganizationPhoneNumber(String organizationPhoneNumber) {
        this.organizationPhoneNumber = organizationPhoneNumber;
    }
}
