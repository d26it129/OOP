public class Driver {
    public static void main(String[] args) {
        Point[] pts = { new Point(1, 2), new Point(3, 4), new Point(1, 2),
                        new Point(5, 6), new Point(3, 4) };
        int distinct = 0;
        for (int i = 0; i < pts.length; i++) {
            boolean seenBefore = false;
            for (int j = 0; j < i; j++) {
                if (pts[i].equals(pts[j])) { seenBefore = true; break; }
            }
            System.out.println(pts[i] + (seenBefore ? "  repeat" : "  new"));
            if (!seenBefore) distinct++;
        }
        System.out.println("Distinct: " + distinct);
    }
}
