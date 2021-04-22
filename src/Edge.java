public class Edge {
    int first;
    int second;
    int weight;
    int secondWeight;

    public Edge(int first, int second, int weight, int secondWeight){
        this.first = first;
        this.second = second;
        this.weight = weight;
        this.secondWeight = secondWeight;
    }

    void show(){
        String s = String.format("Edge:\t%d\t---%d--->\t%d", this.first, this.weight, this.second);
        System.out.println(s);
    }
}
