package infrastructure;

public class Frame {

    private double xOffset;
    private double yOffset;
    private double scale;
    private double width;
    private double height;
    private Point minPoint;
    private Point maxPoint;

    public Frame() {
        minPoint = new Point(0, 0);
        maxPoint = new Point(0, 0);
    }

    public double getxOffset() {
        return xOffset;
    }

    protected void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    protected void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public double getScale() {
        return scale;
    }

    protected void setScale(double scale) {
        this.scale = scale;
    }

    public double getWidth() {
        return width;
    }

    protected void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    protected void setHeight(double height) {
        this.height = height;
    }

    protected void updateMinAndMaxPoints() {
        minPoint.setX(computeRealX(0));
        minPoint.setY(computeRealY(getHeight()));

        maxPoint.setX(computeRealX(getWidth()));
        maxPoint.setY(computeRealY(0));
    }

    public double computePixelX(double x) {
        return x * getScale() - getxOffset();
    }

    public double computePixelY(double y) {
        return -y * getScale() + getyOffset();
    }

    public double computeRealX(double pixelX) {
        return (pixelX + getxOffset()) / getScale();
    }

    public double computeRealY(double pixelY) {
        return (-pixelY + getyOffset()) / getScale();
    }

    protected Point getMinPoint() {
        return minPoint;
    }

    protected Point getMaxPoint() {
        return maxPoint;
    }

    public double getMinX() {
        return getMinPoint().getX();
    }

    public double getMaxX() {
        return getMaxPoint().getX();
    }

    public double getMinY() {
        return getMinPoint().getY();
    }

    public double getMaxY() {
        return getMaxPoint().getY();
    }

}
