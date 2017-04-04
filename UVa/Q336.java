import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Q336 {
    private static int testCase = 1;
    public static void main(String[] args) throws IOException {
        Graph network;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numConnections;
        String line;
        while(true) {
            if((line = reader.readLine()).trim().equals(""))
                continue;
            numConnections = Integer.parseInt(line);
            if(numConnections == 0)
                break;
            network = new Graph();
            while(numConnections > 0) {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
                while(tokenizer.hasMoreTokens()) {
                    int u = Integer.parseInt(tokenizer.nextToken());
                    int v = Integer.parseInt(tokenizer.nextToken());
                    network.addEdge(u, v);
                    numConnections--;
                }
            }
            boolean isQueryEnd = false;
            while(true) {
                if(isQueryEnd)
                    break;
                line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line);
                while(tokenizer.hasMoreTokens()) {
                    int startNode = Integer.parseInt(tokenizer.nextToken());
                    int ttl = Integer.parseInt(tokenizer.nextToken());
                    if(startNode != 0) {
                        int numUnreachable = network.getUnreachableNodesCount(startNode, ttl);
                        System.out.println("Case " + testCase + ": " + numUnreachable + " nodes not reachable from " +
                                "node " + startNode + " with TTL = " + ttl + ".");
                        testCase++;
                    } else {
                        isQueryEnd = true;
                    }
                }
            }
        }
    }

    private static class Graph {
        private int V;
        private HashMap<Integer, ArrayList<Integer>> adj;

        private static class Node {
            private int id;
            private int ttl;

            Node(int id, int ttl) {
                this.id = id;
                this.ttl = ttl;
            }

            int getId() {
                return id;
            }

            int getTtl() {
                return ttl;
            }
        }

        Graph() {
            adj = new HashMap<>();
        }

        void addEdge(int u, int v) {
            if(!adj.containsKey(u)) {
                adj.put(u, new ArrayList<>());
                this.V++;
            }
            if(!adj.containsKey(v)) {
                adj.put(v, new ArrayList<>());
                this.V++;
            }
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int getUnreachableNodesCount(int startNode, int ttl) {
            ArrayList<Integer> visited = new ArrayList<>();
            Queue<Node> queue = new LinkedList<>();
            visited.add(startNode);
            if(!adj.containsKey(startNode)) {
                return this.V;
            }
            if(ttl <= 0) {
                return this.V - 1;
            }
            //noinspection Convert2streamapi
            for(int neighbour: this.adj.get(startNode))
                queue.add(new Node(neighbour, ttl));

            while(!queue.isEmpty()) {
                Node head = queue.remove();
                int currentTtl = head.getTtl();
                if(visited.contains(head.getId()))
                    continue;
                visited.add(head.getId());
                currentTtl--;
                if(currentTtl > 0) {
                    if(!adj.containsKey(head.getId()))
                        continue;
                    for(int neighbour: this.adj.get(head.getId())) {
                        if(!visited.contains(neighbour)) {
                            queue.add(new Node(neighbour, currentTtl));
                        }
                    }
                }
            }
            return this.V - visited.size();
        }
    }
}