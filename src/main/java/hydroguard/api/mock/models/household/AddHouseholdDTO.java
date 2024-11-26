package hydroguard.api.mock.models.household;

import java.util.List;

public class AddHouseholdDTO {
    private String name;
    private String city;
    private List<AddUserDTO> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<AddUserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<AddUserDTO> members) {
        this.members = members;
    }
}