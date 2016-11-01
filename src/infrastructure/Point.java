package infrastructure;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double calculateDistance(Point point) {
        return Math.sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
    }

    public Point subtract(Point a) {
        return new Point(x - a.x, y - a.y);
    }

    public Point normal() {
        return new Point(-y, x);
    }

    public Point unit() {
        double m = getMagnitude();
        return new Point(x / m, y / m);
    }

    public Point multiply(double d) {
        return new Point(x * d, y * d);
    }

    public Point opposite() {
        return new Point(-x, -y);
    }
}
