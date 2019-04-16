package anrix.model.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Faculty implements Serializable {
    @Override
    public String toString() {
        return nameOfFaculty;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public String getNameOfFaculty() {
        return nameOfFaculty;
    }

    public void setNameOfFaculty(String nameOfFaculty) {
        this.nameOfFaculty = nameOfFaculty;
    }

    public ArrayList<Group> groups = new ArrayList<>();
    public String nameOfFaculty;
}
