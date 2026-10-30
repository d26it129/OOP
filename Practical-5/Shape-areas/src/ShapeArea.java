abstract class Shape {
    abstract double area();
    abstract String name();
}

class Circle extends Shape {
    private double r;
    Circle(double r) { this.r = r; }
    double area() { return Math.PI * r * r; }
    String name() { return "Circle(r=" + r + ")"; }
}

class Rectangle extends Shape {
    private double w, h;
    Rectangle(double w, double h) { this.w = w; this.h = h; }
    double area() { return w * h; }
    String name() { return "Rectangle(" + w + "x" + h + ")"; }
}

class Triangle extends Shape {
    private double b, h;
    Triangle(double b, double h) { this.b = b; this.h = h; }
    double area() { return 0.5 * b * h; }
    String name() { return "Triangle(b=" + b + ",h=" + h + ")"; }
}

public class ShapeArea {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(3), new Rectangle(4, 5), new Triangle(6, 7), new Circle(1.5) };
        double total = 0;
        Shape largest = shapes[0];
        for (Shape s : shapes) {
            double a = s.area();
            total += a;
            if (a > largest.area()) largest = s;
            System.out.printf("%-22s area = %8.2f   running total = %8.2f%n", s.name(), a, total);
        }
        System.out.printf("Largest: %s (%.2f)%n", largest.name(), largest.area());
    }
}
