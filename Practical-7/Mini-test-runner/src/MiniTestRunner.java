import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD)
@interface Run { }

class MathChecks {
    @Run public void additionWorks()  { if (2 + 3 != 5) throw new AssertionError("2+3 != 5"); }
    @Run public void stringLength()   { if ("java".length() != 4) throw new AssertionError("length"); }
    @Run public void failingCheck()   { throw new AssertionError("this one fails on purpose"); }
    public void notMarked()           { System.out.println("never called"); }
}

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {
        Object target = new MathChecks();
        int ran = 0, passed = 0;
        Method[] methods = target.getClass().getDeclaredMethods();
        java.util.Arrays.sort(methods, java.util.Comparator.comparing(Method::getName));
        for (Method m : methods) {
            if (!m.isAnnotationPresent(Run.class)) continue;
            ran++;
            try {
                m.invoke(target);
                passed++;
                System.out.println("PASS " + m.getName());
            } catch (java.lang.reflect.InvocationTargetException e) {
                System.out.println("FAIL " + m.getName() + " -> " + e.getCause().getMessage());
            }
        }
        System.out.println("Ran " + ran + " @Run methods, " + passed + " passed");
    }
}
