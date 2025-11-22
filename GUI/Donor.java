import java.io.Serializable;

public class Donor extends User implements Serializable {
    private String id; // short unique id e.g. BD001
    private String bloodGroup;
    private String location;
    private String mobile;

    public Donor(String id, String username, String email, String password,
            String bloodGroup, String location, String mobile) {
        super(username, email, password);
        this.id = id;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getRole() {
        return "DONOR";
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public String getMobile() {
        return mobile;
    }

    // storage format: id|username|email|password|blood|location|mobile
    public String toDataString() {
        return String.join("|", id, getUsername(), getEmail(), getPassword(), bloodGroup, location, mobile);
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Blood: %s | Location: %s | Mobile: %s",
                id, getUsername(), bloodGroup, location, mobile);
    }
}