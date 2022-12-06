package domain;
import java.lang.reflect.Array;
import java.util.*;

/**
 * class for making a social network and connecting the users in a graph, from friendships
 * @param <ID> the type with which the graph will work
 */
public class Graph<ID>{
    /**
     * constructor for graph class
     */
    public Graph() {
    }

    /**
     * adjancency list of every user
     */
    private final Map<ID, List<ID>> connections = new HashMap<>();
    /**
     * the conex components
     */
    ArrayList<ArrayList<ID> > components
            = new ArrayList<>();

    /**
     * This function adds a new vertex to the graph
     * @param s the vertex which will be added to the graph
     */
    public void addVertex(ID s)
    {
        connections.put(s, new LinkedList<ID>());
    }

    /**
     * function for adding a new edge to the graph
     * @param nod1 the first vertex of the edge
     * @param nod2 the second vertex of the edge
     */
    public void addEdge(ID nod1, ID nod2){
        if(!connections.containsKey(nod1))
            addVertex(nod1);
        if(!connections.containsKey(nod2))
            addVertex(nod2);
        connections.get(nod1).add(nod2);
        connections.get(nod2).add(nod1);
    }

    /**
     * DFS algorithm to find a conex component
     * @param nod nodul de start
     * @param viz map pentru marcarea nodurilor daca sunt visited sau nu
     * @param al componenta conexa
     */
    private void DFSutil(ID nod,Map<ID, Boolean> viz,ArrayList<ID> al)
    {
        viz.put(nod,true);
        al.add(nod);
        Iterator<ID> it = connections.get(nod).iterator();
        while(it.hasNext()){
            ID next = it.next();
            if(!viz.get(next))
                DFSutil(next, viz, al);
        }
    }

    /**
     * simple dfs algorithm
     */
    public void DFS(){
        Map<ID, Boolean> visited = new HashMap<>();
        for(ID key : connections.keySet())
            visited.put(key, false);

        for(ID key : connections.keySet())
        {
            ArrayList<ID> al = new ArrayList<>();
            if(!visited.get(key)){
                DFSutil(key, visited, al);
                this.components.add(al);
            }
        }
    }


    /**
     * function to get the number of all the conex components
     * @return int value of the number of components
     */
    public int getConnectedComp(){
        return this.components.size();
    }
}
