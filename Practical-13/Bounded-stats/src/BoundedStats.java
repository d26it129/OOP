public class BoundedStats {

    static <N extends Number> double average(N[] values) {
        double sum = 0;
        for (N v : values) sum += v.doubleValue();
        return sum / values.length;
    }

    public static void main(String[] args) {
        System.out.println("Integer[] avg = " + average(new Integer[] { 10, 20, 30 }));
        System.out.println("Double[]  avg = " + average(new Double[] { 1.5, 2.5, 3.5 }));
        System.out.println("Long[]    avg = " + average(new Long[] { 100L, 200L }));
        // average(new String[] {"a"});   // rejected: String is not a subclass of Number
    }
}
