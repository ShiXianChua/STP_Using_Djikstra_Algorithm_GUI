package stpusingdijkstraalgo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph {

    private ArrayList<Vertex> vertexList;
    private ArrayList<Edge> edgeList;
    private ArrayList<Message> messageList;
    private STPUsingDijkstraAlgo algo = new STPUsingDijkstraAlgo();
    private boolean solved = false;
    private Vertex source;
    private Vertex destination;

    public Graph() {
        edgeList = new ArrayList<>();
        vertexList = new ArrayList<>();
        messageList = new ArrayList<>();
    }

    public void addEdge(Edge e) {
        edgeList.add(e);
        vertexList.forEach(v->{
            if (v.equals(e.getStartVertex())){
                v.addNeighbour(e);
            }
                
        });
    }

    public ArrayList<Edge> getEdges() {
        return edgeList;
    }

    public void addVertex(Vertex v) {
        vertexList.add(v);
        v.getAdjacenciesList().forEach(e -> {
            edgeList.add(e);
        });
    }

    public void deleteVertex(Vertex delete) {
        for (Vertex vertex : vertexList){
            if(vertex.equals(delete)){
                vertexList.remove(vertex);
                return;
            }
        }
    }

    public ArrayList<Vertex> getVertices() {
        return vertexList;
    }

    public void addMessasge(Vertex source, Vertex destination) {
        Message m = new Message(source, destination);
        messageList.add(m);
    }

    public Message getMessage() {
        return messageList.remove(0);
    }
    public ArrayList<Message> getMessages() {
        return messageList;
    }

    public List<Vertex> runAlgo() {
        algo.reset(vertexList);
        Message m= messageList.remove(0);
        this.source=m.getSource();
        this.destination=m.getDestination();
        algo.computeShortestPaths(source,destination);
        setSolved(true);
        return algo.getShortestPathTo(m.getDestination());
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isSolved() {
        return solved;
    }

    public void deleteNode(Vertex vertex) {
        List<Edge> delete = new ArrayList<>();
        for (Edge edge : edgeList) {
            if (edge.hasVertex(vertex)) {
                delete.add(edge);
            }
        }
        for (Edge edge : delete) {
            edgeList.remove(edge);
        }
        vertexList.remove(vertex);
    }

    public boolean containsEdge(Edge e) {
        for (int i = 0; i < edgeList.size(); i++) {
            if (e.equals(edgeList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean containsVertex(String name) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (name.equals(vertexList.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    public Vertex getVertex(String name) {
        if (containsVertex(name)) {
            for (int i = 0; i < vertexList.size(); i++) {
                if (name.equals(vertexList.get(i).getName())) {
                    return vertexList.get(i);
                }
            }
        }
        return null;
    }

    public String printMessages() {
        String s = "";
        s = messageList.stream().map((m) -> m.toString()).reduce(s, String::concat);
        return s;
    }
    public boolean hasMessage(){
        return !messageList.isEmpty();
    }
    public void clear(){
        vertexList.clear();
        edgeList.clear();
        messageList.clear();
        
    }
    public boolean isSource(Vertex vertex){
        return vertex == messageList.get(0).getSource();
    }

    public boolean isDestination(Vertex node){
        return node == messageList.get(0).getDestination();
    }
    public int getShortestTime(){
        return destination.getTotalCostTime();
    }
    public int getShortestDistance(){
        return destination.getTotalCostDistance();
    }

}
