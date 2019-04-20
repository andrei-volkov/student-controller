package anrix.model.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Faculty implements Serializable {
    public String name;
    public ArrayList<Group> groups = new ArrayList<>();

    public Faculty() {}

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
