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
    boolean directed;
    boolean acyclic;
    boolean doubleWeight;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";


    // Create an empty graph.
    Graph(int number, boolean directed, boolean acyclic, boolean doubleWeight) {
        this.vertices = new Vertex[number];
        for (int i = 0; i < number; i++) {
            vertices[i] = new Vertex(i, number, !directed || acyclic);
        }
        this.maxEdges = (this.vertices.length - 1) * this.vertices.length / 2;
        this.edges = new ArrayList<>();
        this.directed = directed;
        this.acyclic = acyclic;
        this.doubleWeight = doubleWeight;
    }

    void fill(int mode, float number) {
        switch (mode) {
            case 1:
                // Network flow
                this.basicFlowNetwork();
                while (this.edges.size() < this.maxEdges * number / 100) {
                    this.addRandomEdge();
                }
                break;
            case 2:
                // Full graph
                if (this.directed) this.fullDirectedGraph();
                else fullUnDirectedGraph();
            case 3:
                // Random connected graph
                this.connect();
                if (!this.acyclic) {
                    while (this.edges.size() < this.maxEdges * number / 100) {
                        this.addRandomEdge();
                    }
                }
            case 4:
                // Random graph
                this.acyclic = false;
                while (this.edges.size() < this.maxEdges * number / 100) {
                    this.addRandomEdge();
                }
            default:
                break;
        }
    }

    void connect() {
        List<Vertex> connected = new ArrayList<>();
        connected.add(this.vertices[0]); // source
        List<Vertex> unConnected = new ArrayList<>(Arrays.asList(this.vertices).subList(1, this.vertices.length));
        this.addSomeEdges(connected, unConnected);
    }

    // Basic flow network. Only necessary random edges (source -> outlet)
    void basicFlowNetwork() {
        this.connect();
        //Here connected graph and we can go from source to any vertex

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
        addSomeEdges(outletConnected, outletNotConnected);
    }

    void fullDirectedGraph() {
        Random random = new Random();
        for (Vertex v : vertices) {
            for (Vertex w : vertices) {
                if (v.number != w.number) {
                    int secondWeight;
                    if (this.doubleWeight) secondWeight = random.nextInt(9) + 1;
                    else secondWeight = 0;
                    this.addDirectedEdge(v, w, random.nextInt(9) + 1, secondWeight);
                }
            }
        }
    }

    void fullUnDirectedGraph() {
        Random random = new Random();
        for (Vertex v : vertices) {
            for (Vertex w : vertices) {
                if (v.number < w.number) {
                    int secondWeight;
                    if (this.doubleWeight) secondWeight = random.nextInt(9) + 1;
                    else secondWeight = 0;
                    this.addDirectedEdge(v, w, random.nextInt(9) + 1, secondWeight);
                }
            }
        }
    }

    void addDirectedEdge(Vertex x, Vertex y, int flow, int secondFlow) {
        this.edges.add(new Edge(x.number, y.number, flow, secondFlow));
        x.level += 1;
        x.neighbors.add(y.number);
        x.potential.remove(Integer.valueOf(y.number));
        this.sumVertLevel += 1;
    }

    void addUndirectedEdge(Vertex x, Vertex y, int flow, int secondFlow) {
        this.edges.add(new Edge(x.number, y.number, flow, secondFlow));
        x.level += 1;
        y.level += 1;
        x.neighbors.add(y.number);
        y.neighbors.add(x.number);
        x.potential.remove(Integer.valueOf(y.number));
        y.potential.remove(Integer.valueOf(x.number));
        this.sumVertLevel += 2;
    }

    void addDirectedEdgeLast(Vertex x, Vertex y, int flow, int secondFlow) {
        this.edges.add(new Edge(x.number, y.number, flow, secondFlow));
        x.level += 1;
        x.neighbors.add(y.number);
        x.potential.remove(x.potential.size() - 1);
        this.sumVertLevel += 1;
    }

    void addUndirectedEdgeLast(Vertex x, Vertex y, int flow, int secondFlow) {
        this.edges.add(new Edge(x.number, y.number, flow, secondFlow));
        x.level += 1;
        y.level += 1;
        x.neighbors.add(y.number);
        y.neighbors.add(x.number);
        x.potential.remove(x.potential.size() - 1);
        y.potential.remove(y.potential.size() - 1);
        this.sumVertLevel += 2;
    }

    void addRandomEdge() {
        Random random = new Random();
        int first = random.nextInt(this.vertices.length - 1);
        while (this.vertices[first].potential.size() < 1) {
            first = random.nextInt(this.vertices.length - 1);
        }
        //int second = this.vertices[first].potential.get(random.nextInt(this.vertices[first].potential.size()));
        int second = this.vertices[first].potential.get(this.vertices[first].potential.size() - 1);

        int secondWeight = 0;
        if (this.doubleWeight) secondWeight = random.nextInt(9) + 1;
        if (this.directed)
            addDirectedEdgeLast(this.vertices[first], this.vertices[second], random.nextInt(9) + 1, secondWeight);
        else addUndirectedEdgeLast(this.vertices[first], this.vertices[second], random.nextInt(9) + 1, secondWeight);
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

    //Adding random edges to make graph basic connected (not flow network yet)
    private void addSomeEdges(List<Vertex> connected, List<Vertex> unConnected) {
        Random random = new Random();
        while (!unConnected.isEmpty()) {
            int randFirst = random.nextInt(connected.size());
            int randSecond = random.nextInt(unConnected.size());
            Vertex first = connected.get(randFirst);
            Vertex second = unConnected.get(randSecond);
            if (first.number >= second.number) { //TODO must be better way
                continue;
            }
            connected.add(second);
            unConnected.remove(randSecond);
            int secondWeight;
            if (this.doubleWeight) secondWeight = random.nextInt(9) + 1;
            else secondWeight = 0;
            if (this.directed) this.addDirectedEdge(first, second, random.nextInt(9) + 1, secondWeight);
            else this.addUndirectedEdge(first, second, random.nextInt(9) + 1, secondWeight);
        }
    }

    //Print graph as adjency matrix (not effective but looks more familiar)
    //It requires to create big matrix so it takes some time
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

    //Save graph to file as list of edges
    void save(String fileName) {
        try {
            String s = String.format("C://Users/Maciek/Documents/GitHub/graphGenerator/%s.txt", fileName);
            FileWriter myWriter = new FileWriter(s);
            for (Edge e : this.edges) {
                if (!this.doubleWeight) myWriter.write(e.first + " " + e.second + " " + e.weight);
                else myWriter.write(e.first + " " + e.second + " " + e.weight + " " + e.secondWeight);
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
