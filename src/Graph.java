import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class Graph {
    Vertex[] vertices;
    List<Edge> edges;
    int sumVertLevel = 0;
    int maxEdges;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    /*
     *
     * Create a basic flow network. Only necessary random edges.
     *
     * */
    Graph(int number) {
        this.vertices = new Vertex[number];
        for (int i = 0; i < number; i++) {
            vertices[i] = new Vertex(i);
        }
        this.maxEdges = (this.vertices.length - 1) * this.vertices.length / 2;
        this.edges = new ArrayList<>();
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
        //Finding vertices connected to outlet
        while (temp != 0) {
            for (Edge e : this.edges)
                if (e.second == temp) {
                    temp = e.first;
                    outletConnected.add(this.vertices[e.first]);
                    break;
                }
        }
        for (Vertex v : this.vertices) {
            if (!outletConnected.contains(v))
                outletNotConnected.add(v);
        }
        //Connecting all unConnected vertices to vertices with bigger number.
        //Protects from cycle and make our graph a flow network.
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
            this.addEdge(first, second, random.nextInt(9) + 1);
        }
    }

    void addEdge(Vertex x, Vertex y, int flow) {
        this.edges.add(new Edge(x.number, y.number, flow));
        x.level += 1;
        y.level += 1;
        x.neighbors.add(y.number);
        y.neighbors.add(x.number);
        this.sumVertLevel += 2;
    }

    void addRandomEdge() {
        Random random = new Random();
        int first = random.nextInt(this.vertices.length - 1);
        int second = random.nextInt(this.vertices.length - 1 - first) + first + 1;
        if (!this.vertices[first].neighbors.contains(second))
            addEdge(this.vertices[first], this.vertices[second], random.nextInt(9) + 1);
    }

    //Print graph as list of edges
    void show() {
        System.out.print("\n\n");
        for (Vertex x : this.vertices) {
            x.show();
        }
        System.out.print("\nLIST\n\n");
        for (Edge e : this.edges) {
            e.show();
        }
        System.out.println("\nVertices number: " + this.vertices.length);
        System.out.println("Edges number: " + this.edges.size());
        System.out.println("Fill: " + ((double) (this.edges.size()) / this.maxEdges) * 100);
    }

    void fill(int mode, float number) {
        if (mode == 1) {
            while (this.edges.size() < this.maxEdges * number / 100) {
                this.addRandomEdge();
            }
        } else {
            while (this.sumVertLevel < this.vertices.length * number) {
                this.addRandomEdge();
            }
        }
    }

    //Function helper for showMatrix()
    private int[][] createMatrix() {
        int[][] temp = new int[this.vertices.length][this.vertices.length];
        for (Edge i : this.edges) {
            if (i.weight > 0) {
                temp[i.first][i.second] = i.weight;
            }
        }
        return temp;
    }

    //Adding random edges to make graph basic connected (not flow network yet)
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
            this.addEdge(first, second, random.nextInt(9) + 1);
        }
    }

    //Print graph as adjency matrix (not effective but looks better)
    //It requires to create big matrix so it's slow
    void showMatrix() {
        int[][] edgesMat = this.createMatrix();
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
                if (edgesMat[i][j] > 0) {
                    System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "| " + edgesMat[i][j] + " |" + ANSI_RESET);
                } else {
                    if (i == j) {
                        System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK + "| " + edgesMat[i][j] + " |" + ANSI_RESET);
                    } else {
                        System.out.print("| " + edgesMat[i][j] + " |");
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

    //Save graph to file as list of edges
    void save(String fileName) {
        try {
            String s = String.format("C://Users/Maciek/Documents/GitHub/graphGenerator/%s.txt", fileName);
            FileWriter myWriter = new FileWriter(s);
            for (Edge e : this.edges) {
                myWriter.write(e.first + " " + e.second + " " + e.weight);
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving graph.");
            e.printStackTrace();
        }
    }
}
