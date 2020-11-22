import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Graph {
    Vertex[] vertices;
    int[][] edges;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    Graph(Vertex[] vertices, int[][] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    Graph(int number) {
        this.vertices = new Vertex[number];
        for (int i = 0; i < number; i++) {
            vertices[i] = new Vertex(i);
        }
        this.edges = new int[number][number];
        //Empty graph
        List<Vertex> connected = new ArrayList<>();
        connected.add(this.vertices[0]); // source
        List<Vertex> unConnected = new ArrayList<>(Arrays.asList(this.vertices).subList(1, this.vertices.length));
        this.addSomeEdges(connected, unConnected);
        //Here connected graph but still not flow network
        List<Vertex> outletConnected = new ArrayList<>();
        List<Vertex> outletNotConnected = new ArrayList<>();
        int temp = this.vertices.length - 1;
        outletConnected.add(this.vertices[temp]);
        while (temp != 0) {
            for (int i = 0; i < temp; i++) {
                if (this.edges[i][temp] > 0) {
                    temp = i;
                    outletConnected.add(this.vertices[temp]);
                    break;
                }
            }
        }
        for (Vertex v : this.vertices) {
            if (!outletConnected.contains(v))
                outletNotConnected.add(v);
        }
        Random random = new Random();
        while (!outletNotConnected.isEmpty()) {
            int randFirst = random.nextInt(outletNotConnected.size());
            int randSecond = random.nextInt(outletConnected.size());
            Vertex first = outletNotConnected.get(randFirst);
            Vertex second = outletConnected.get(randSecond);
            if (first.number >= second.number) {
                continue;
            }
            outletConnected.add(first);
            outletNotConnected.remove(randFirst);
            this.addEdge(first.number, second.number, random.nextInt(9) + 1);
        }
    }

    void addEdge(int x, int y, int flow) {
        this.edges[x][y] = flow;
    }

    void show() {
        for (Vertex x : this.vertices) {
            x.show();
        }
        System.out.print("\nMATRIX\n\n\t");
        for (Vertex x : this.vertices) {
            System.out.print("| " + x.number + " |");
        }
        System.out.print("\n\t");
        for (int i = 0; i < this.vertices.length; i++) {
            System.out.print("-----");
        }
        System.out.print("\n");
        for (int i = 0; i < this.vertices.length; i++) {
            if (i < 10) {
                System.out.print(" " + i + "  ");
            } else {
                System.out.print(" " + i + " ");
            }
            for (int j = 0; j < this.vertices.length; j++) {
                if (this.edges[i][j] > 0) {
                    System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "| " + this.edges[i][j] + " |" + ANSI_RESET);
                } else {
                    if (i == j) {
                        System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK + "| " + this.edges[i][j] + " |" + ANSI_RESET);
                    } else {
                        System.out.print("| " + this.edges[i][j] + " |");
                    }
                }
                if (j > 9) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n\t");
            for (int k = 0; k < this.vertices.length; k++) {
                if (k < 10) {
                    System.out.print("-----");
                } else {
                    System.out.print("------");
                }
            }
            System.out.print("\n");
        }
    }

    private void addSomeEdges(List<Vertex> connected, List<Vertex> unConnected) {
        Random random = new Random();
        while (!unConnected.isEmpty()) {
            int randFirst = random.nextInt(connected.size());
            int randSecond = random.nextInt(unConnected.size());
            Vertex first = connected.get(randFirst);
            Vertex second = unConnected.get(randSecond);
            if (first.number >= second.number) {
                continue;
            }
            connected.add(second);
            unConnected.remove(randSecond);
            this.addEdge(first.number, second.number, random.nextInt(9) + 1);
        }
    }

    void save() {
        try {
            FileWriter myWriter = new FileWriter("test.txt");
            for(int i=0; i<this.vertices.length; i++){
                for(int j=0; j<this.vertices.length; j++){
                    myWriter.write(this.edges[i][j] + ",");
                }
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
