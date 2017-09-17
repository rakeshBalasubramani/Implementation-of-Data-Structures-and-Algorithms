package cs6301.g38;
import java.util.ArrayList;
import java.util.Stack;


public class TopologicalSort {
    
    private int E, V;
    private ArrayList<ArrayList<Integer>> al;
    private boolean marked[];
    private Stack<Integer> stack;
    
    TopologicalSort(int V){
        this.V = V;
        al = new ArrayList<>();
        marked = new boolean[V];
        stack = new Stack<>();
        for(int i=0;i<V;++i){
            al.add(new ArrayList<>());
        }
    }
    
    
    public void dfs(int vertex){ 
        if(!marked[vertex]){
            marked[vertex] = true;
            for(int v:al.get(vertex)){
                dfs(v);
            }
            stack.push(vertex);
        }
    }
    
    public void addEdge(int from, int to){
        al.get(from).add(to);
    }
    
    public static void main(String[] args){
        //7 vertices in example digraph
        TopologicalSort ts = new TopologicalSort(4);
        ts.addEdge(0, 1);
        ts.addEdge(1, 2);
        ts.addEdge(2, 3);
        
        ts.addEdge(3, 0);

        
        for(int i=0;i<ts.V;++i) ts.dfs(i);
        
        System.out.println("Topological Order : ");
        while(!ts.stack.isEmpty()){
            System.out.print(ts.stack.pop()+" ");
        }
    }
}
