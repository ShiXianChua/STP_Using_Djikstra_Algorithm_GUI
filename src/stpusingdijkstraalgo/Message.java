package stpusingdijkstraalgo;

public class Message {

    private Vertex source;
    private Vertex destination;

    public Message(Vertex source, Vertex destination) {
        this.destination=destination;
        this.source=source;
    }
    public Vertex getDestination(){
        return destination;
    }
    public Vertex getSource(){
        return source;
    }
    @Override
    public String toString(){
        return source.getName()+" to " +destination.getName()+"\n";
    }
}
