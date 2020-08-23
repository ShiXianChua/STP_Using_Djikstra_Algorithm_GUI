package stpusingdijkstraalgo;

public class Edge {

    private int time;
    private int distance;
    private int lifetime;
    private Vertex startVertex;
    private Vertex targetVertex;

    public Edge(int time, int distance,int lifetime, Vertex startVertex, Vertex targetVertex) {
        this.time = time;
        this.distance = distance;
        this.lifetime = lifetime;
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
    }
    public Edge( Vertex startVertex, Vertex targetVertex) {
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
    }
   public boolean hasVertex(Vertex vertex){
        return startVertex==vertex || targetVertex==vertex;
    }

    public boolean equals(Edge edge) {
        return (startVertex ==edge.getStartVertex() && targetVertex ==edge.targetVertex) ;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }
    public void reduceLifetime() {
        this.lifetime--;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    public void setTargetVertex(Vertex targetVertex) {
        this.targetVertex = targetVertex;
    }
    public String toString(){
        return "Synapse: from "+startVertex+" to "+targetVertex;
    }
    public String showStatus(){
        return "Distance : "+distance+"\n"+"Time : "+time+"\n"+"Lifetime : "+lifetime;
    }
}
