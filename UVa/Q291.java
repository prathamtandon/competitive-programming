import java.util.ArrayList;

class Q291 {
    private static ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
    public static void main(String[] args) {
        ArrayList<Integer> t = new ArrayList<>();
        t.add(1);
        t.add(2);
        t.add(4);
        adj.add(t);
        t = new ArrayList<>();
        t.add(0);
        t.add(2);
        t.add(4);
        adj.add(t);
        t = new ArrayList<>();
        t.add(0);
        t.add(1);
        t.add(3);
        t.add(4);
        adj.add(t);
        t = new ArrayList<>();
        t.add(2);
        t.add(4);
        adj.add(t);
        t = new ArrayList<>();
        t.add(0);
        t.add(1);
        t.add(2);
        t.add(3);
        adj.add(t);
        boolean[][] visited = new boolean[5][5];
        StringBuilder path = new StringBuilder("1");
        canDraw(0, visited, path);
    }

    private static boolean canDraw(int s, boolean[][] visited, StringBuilder path) {
        if(allVisited(visited))
            return true;
        //noinspection Convert2streamapi
        for(int i : adj.get(s)) {
            if(!visited[s][i]) {
                visited[s][i] = true;
                visited[i][s] = true;
                path.append(i+1);
                if(canDraw(i, visited, path)) {
                    System.out.println(path);
                }
                visited[s][i] = false;
                visited[i][s] = false;
                path.deleteCharAt(path.length() - 1);
            }
        }
        return false;
    }

    private static boolean allVisited(boolean[][] visited) {
        int visitedEdges = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(visited[i][j])
                    visitedEdges++;
            }
        }
        return visitedEdges == 16;
    }
}