import java.io.*;

public class SaveLoad {
    public static void main(String[] args) throws Exception {
        Student[] students = {
            new Student("Riya", 88, "riya@123"),
            new Student("Arjun", 75, "arjun#9"),
            new Student("Meera", 92, "meera$7")
        };
        System.out.println("Before saving:");
        for (Student s : students) System.out.println("  " + s);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            out.writeObject(students);
        }

        Student[] loaded;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.dat"))) {
            loaded = (Student[]) in.readObject();
        }
        System.out.println("After loading into fresh objects:");
        for (Student s : loaded) System.out.println("  " + s);
    }
}
