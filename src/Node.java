import java.util.Objects;

public class Node {
    private int g;
    private double h;
    private double f;
    private Node prev;
    private Point loc;


    public Node(Node prev, Point loc, int g, double h, double f) {
        this.prev = prev;
        this.loc = loc;
        this.g = g;
        this.h = h;
        this.f = f;
    }

    public Node getPrev() { return prev; }

    public Point getLoc() { return loc; }

    public double getF() { return f; }

    public int getG() { return g; }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        Node n = (Node) obj;
        return Objects.equals(loc, n.loc);
    }
}
