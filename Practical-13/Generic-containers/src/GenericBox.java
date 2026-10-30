class Box<T> {
    private T value;
    Box(T value) { this.value = value; }
    T get() { return value; }
    void set(T value) { this.value = value; }
}

class Pair<A, B> {
    private final A first; private final B second;
    Pair(A first, B second) { this.first = first; this.second = second; }
    A getFirst() { return first; }
    B getSecond() { return second; }
    @Override public String toString() { return "(" + first + ", " + second + ")"; }
}

public class GenericBox {

    static <T> T firstOf(T[] arr) { return arr[0]; }

    public static void main(String[] args) {
        Box<Integer> intBox = new Box<>(42);
        Box<String> strBox = new Box<>("hello");
        int n = intBox.get();                 // no cast needed
        String s = strBox.get();
        System.out.println("Box<Integer> = " + n + ", Box<String> = " + s);

        Pair<String, Integer> p = new Pair<>("Riya", 88);
        System.out.println("Pair = " + p + "  first=" + p.getFirst() + " second=" + p.getSecond());

        System.out.println("firstOf(Integer[]) = " + firstOf(new Integer[] { 7, 8, 9 }));
        System.out.println("firstOf(String[])  = " + firstOf(new String[] { "x", "y" }));
        // intBox.set("text");   // compile-time error: type safety without casts
    }
}
