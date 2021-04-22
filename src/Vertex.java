import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vertex {
    int number;
    int level;
    List<Integer> neighbors = new ArrayList<>();
    List<Integer> potential = new ArrayList<>();

    public Vertex(int number, int maxVertex, boolean triangle) {
        this.number = number;
        this.level = 0;
        for (int i = 0; i < maxVertex; i++) {
            if (triangle) {
                if (i > number) {
                    this.potential.add(i);
                }
            } else if (i != number) {
                this.potential.add(i);
            }
        }
        Collections.shuffle(potential);
    }

    void show() {
        String s = String.format("Number: %d, Level: %d", this.number, this.level);
        System.out.println(s);
        String temp = String.format("Neighbors(%d): ", this.neighbors.size());
        System.out.print(temp);
        for (Integer i : neighbors) {
            String x = String.format("%d ", i);
            System.out.print(x);
        }
        String temp2 = String.format("AntiNeighbors(%d): ", this.potential.size());
        System.out.print(temp2);
        for (Integer i : this.potential) {
            String x = String.format("%d ", i);
            System.out.print(x);
        }
        System.out.println();
    }
}
