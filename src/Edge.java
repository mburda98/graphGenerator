public class Edge {
    int first;
    int second;
    int weight;

    public Edge(int first, int second, int weight){
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    public Edge(int first, int second){
        this.first = first;
        this.second = second;
        this.weight = 0;
    }

    void show(){
        String s = String.format("Edge: %d ---%d---> %d", this.first, this.weight, this.second);
        System.out.println(s);
    }

}
