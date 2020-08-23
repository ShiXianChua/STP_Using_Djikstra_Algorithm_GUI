package stpusingdijkstraalgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class STPUsingDijkstraAlgo {
    
    public STPUsingDijkstraAlgo(){
        
    }

    public void computeShortestPaths(Vertex sourceVertex, Vertex destination) {

        sourceVertex.setTotalCostTime(0);
        sourceVertex.setTotalCostDistance(0);
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(sourceVertex);
        sourceVertex.setVisited(true);

        while (!priorityQueue.isEmpty()) {
            // Getting the minimum distance vertex from priority queue
            Vertex actualVertex = priorityQueue.poll();

            actualVertex.getAdjacenciesList().forEach((edge) -> {
                if (edge.getLifetime() > 0) {
                    Vertex v = edge.getTargetVertex();
                    if (!v.isVisited()) {
                        int newTotalCostTime = actualVertex.getTotalCostTime() + edge.getTime();
                        int newTotalCostDistance = actualVertex.getTotalCostDistance() + edge.getDistance();

                        if (newTotalCostTime < v.getTotalCostTime()) {
                            priorityQueue.remove(v);
                            v.setTotalCostTime(newTotalCostTime);
                            v.setTotalCostDistance(newTotalCostDistance);
                            v.setPredecessor(actualVertex);
                            priorityQueue.add(v);
                            //if newTotalCost for 2 routes are the same, use distance code here
                        } else if (newTotalCostTime == v.getTotalCostTime()) {
                            if (newTotalCostDistance < v.getTotalCostDistance()) {
                                priorityQueue.remove(v);
                                v.setTotalCostTime(newTotalCostTime);
                                v.setTotalCostDistance(newTotalCostDistance);
                                v.setPredecessor(actualVertex);
                                priorityQueue.add(v);
                            }
                        }
                    }
                }
            });
            actualVertex.setVisited(true);
        }
        
    }

    public List<Vertex> getShortestPathTo(Vertex destinationVertex) {
        List<Vertex> shortestPath = new ArrayList<>();
        for (Vertex vertex = destinationVertex; vertex != null; vertex = vertex.getPredecessor()) {
            shortestPath.add(vertex);
        }
        Collections.reverse(shortestPath);
        reduceLife(shortestPath);
        return shortestPath;
    }

    public void reduceLife(List<Vertex> shortestpath) {
        for (int i = 0; i < shortestpath.size() - 1; i++) {
            for (int j = 0; j < shortestpath.get(i).getAdjacenciesList().size(); j++) {
                Vertex now = shortestpath.get(i);
                Vertex next = now.getAdjacenciesList().get(j).getTargetVertex();
                if (next.equals(shortestpath.get(i + 1))) {
                    if (now.getAdjacenciesList().get(j).getLifetime() > 0) {
                        now.getAdjacenciesList().get(j).reduceLifetime();
                        break;
                    }

                }

            }
        }
    }

    public boolean isVertexReachable(Vertex v) {
        return v.getTotalCostTime() != Integer.MAX_VALUE;
    }

    public void reset(ArrayList<Vertex> vertexList) {
        vertexList.forEach((e) -> {
            e.reset();
        });
    }

}
