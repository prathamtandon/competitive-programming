import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Q154 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int[][] data = new int[110][5];
        while(!(line = reader.readLine()).equals("#")) {
            int index = 0;
            while(!line.startsWith("e")) {
                String[] allocations = line.split(",");
                for (String allocation: allocations) {
                    String[] bin = allocation.split("/");
                    data[index][colorToInt(bin[0])] = wasteToInt(bin[1]);
                }
                line = reader.readLine();
                index++;
            }
            int bestCity = -1, minChanges = Integer.MAX_VALUE;
            for (int i = 0; i < index; i++) {
                int changes = 0;
                for (int j = 0; j < index; j++) {
                    for (int k = 0; k < 5; k++) {
                        if(data[i][k] != data[j][k])
                            changes++;
                    }
                }
                if(changes < minChanges) {
                    bestCity = i + 1;
                    minChanges = changes;
                }
            }
            System.out.println(bestCity);
        }
    }

    private static int colorToInt(String color) {
        switch (color) {
            case "r":
                return 0;
            case "o":
                return 1;
            case "y":
                return 2;
            case "g":
                return 3;
            case "b":
                return 4;
            default:
                return -1;
        }
    }

    private static int wasteToInt(String waste) {
        switch (waste) {
            case "P":
                return 0;
            case "G":
                return 1;
            case "S":
                return 2;
            case "A":
                return 3;
            case "N":
                return 4;
            default:
                return -1;
        }
    }
}