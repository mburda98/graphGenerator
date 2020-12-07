import java.util.ArrayList;
import java.util.List;

public class Vertex {
    int number;
    int level;
    List<Integer> neighbors = new ArrayList<>();

    public Vertex(int number) {
        this.number = number;
        this.level = 0;
    }

    public Vertex() {
        this.number = 0;
        this.level = 0;
    }

    void show() {
        String s = String.format("Number: %d, Level: %d", this.number, this.level);
        System.out.println(s);
        System.out.print("Neighbors: ");
        for(Integer i : neighbors){
            String x = String.format("%d ", i);
            System.out.print(x);
        }
        System.out.println();
    }
}
