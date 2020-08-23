package stpusingdijkstraalgo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {
    private Point coord;
    private String name;
    private List<Edge> adjacenciesList= new ArrayList<>();
    private boolean visited;
    private Vertex predecessor;
    private int totalCostTime = Integer.MAX_VALUE;
    private int totalCostDistance = Integer.MAX_VALUE;

    public Vertex(String name) {
        this.name = name;
    }
    public Vertex(Point name) {
        this.coord = name;
    }
    public void reset(){
        totalCostTime = Integer.MAX_VALUE;
        totalCostDistance = Integer.MAX_VALUE;
        predecessor=null;
        visited=false;
    }
     public void setCoord(int x, int y){
        coord.setLocation(x, y);
    }

    public Point getCoord(){
        return coord;
    }
    public void addNeighbour(Edge edge) {
        this.adjacenciesList.add(edge);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edge> getAdjacenciesList() {
        return adjacenciesList;
    }

    public void setAdjacenciesList(List<Edge> adjacenciesList) {
        this.adjacenciesList = adjacenciesList;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Vertex getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertex predecessor) {
        this.predecessor = predecessor;
    }

    public int getTotalCostTime() {
        return totalCostTime;
    }

    public void setTotalCostTime(int totalCostTime) {
        this.totalCostTime = totalCostTime;
    }

    public int getTotalCostDistance() {
        return totalCostDistance;
    }

    public void setTotalCostDistance(int totalCostDistance) {
        this.totalCostDistance = totalCostDistance;
    }
    public void setCoord(Point coord){
        this.coord=coord;
    }

    @Override
    public String toString() {
        return this.name;
    }
     public int getX(){
        return (int) coord.getX();
    }

    public int getY(){
        return (int) coord.getY();
    }

    @Override
    public int compareTo(Vertex otherVertex) {
        if (totalCostTime == otherVertex.getTotalCostTime()) {
            return Integer.compare(totalCostDistance, otherVertex.getTotalCostDistance());
        } else {
            return Integer.compare(totalCostTime, otherVertex.getTotalCostTime());
        }
    }
    
}
