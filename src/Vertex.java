public class Vertex {
    int number;
    double x;
    double y;

    public Vertex(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public Vertex(int number) {
        this.number = number;
        this.x = 0;
        this.y = 0;
    }

    public Vertex() {
        this.number = 0;
        this.x = 0;
        this.y = 0;
    }

    void show() {
        String s = String.format("Number: %d | (x, y): (%f, %f)", this.number, this.x, this.y);
        System.out.println(s);
    }
}
