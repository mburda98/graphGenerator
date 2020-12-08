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

    void show() {
        String s = String.format("Number: %d, Level: %d", this.number, this.level);
        System.out.println(s);
        String temp = String.format("Neighbors(%d): ", this.neighbors.size());
        System.out.print(temp);
        for(Integer i : neighbors){
            String x = String.format("%d ", i);
            System.out.print(x);
        }
        System.out.println();
    }
}
