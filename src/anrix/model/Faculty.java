package anrix.model;

import java.util.ArrayList;

public class Faculty {
    @Override
    public String toString() {
        return nameOfFaculty;
    }

    public ArrayList<Group> groups = new ArrayList<>();
    public String nameOfFaculty;
}
