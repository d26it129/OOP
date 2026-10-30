import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int marks;
    private transient String password;       // transient: skipped during serialization

    public Student(String name, int marks, String password) {
        this.name = name; this.marks = marks; this.password = password;
    }

    @Override
    public String toString() { return name + " | marks=" + marks + " | password=" + password; }
}
