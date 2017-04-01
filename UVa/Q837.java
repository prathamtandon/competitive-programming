import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Q837 {
    private static LineSegment[] segments;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numCases = Integer.parseInt(reader.readLine());
        while(numCases > 0) {
            reader.readLine();
            int numSegments = Integer.parseInt(reader.readLine());
            segments = new LineSegment[numSegments];
            double[] temp = new double[numSegments * 2];
            int index = 0, tIndex = 0;
            while(numSegments > 0) {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
                double x1 = Double.parseDouble(tokenizer.nextToken());
                tokenizer.nextToken();
                double x2 = Double.parseDouble(tokenizer.nextToken());
                tokenizer.nextToken();
                double transparencyCoeff = Double.parseDouble(tokenizer.nextToken());
                LineSegment segment = new LineSegment(x1, x2, transparencyCoeff);
                segments[index++] = segment;
                temp[tIndex++] = x1;
                temp[tIndex++] = x2;
                numSegments--;
            }
            Arrays.sort(temp);
            int numProjections = temp.length + 1;
            System.out.println(numProjections);
            System.out.println(String.format("-inf %1$.3f 1.000", temp[0]));
            for (int i = 0; i < temp.length - 1; i++) {
                double low = temp[i];
                double high = temp[i+1];
                System.out.println(String.format("%1$.3f %2$.3f %3$.3f", low, high, getPercentageForProjectedSegment
                        (low, high) ));
            }
            System.out.println(String.format("%1$.3f +inf 1.000", temp[temp.length - 1]));
            if(numCases > 1)
                System.out.println();
            numCases--;
        }
    }

    private static double getPercentageForProjectedSegment(double low, double high) {
        double result = 1.0;
        for(LineSegment segment : segments) {
            if(segment.getX1() <= low && segment.getX2() >= high) {
                result *= segment.getTransparencyCoeff();
            }
        }
        return result;
    }

    private static class LineSegment {
        private double x1;
        private double x2;
        private double transparencyCoeff;

        LineSegment(double x1, double x2, double transparencyCoeff) {
            this.x1 = Math.min(x1, x2);
            this.x2 = Math.max(x1, x2);
            this.transparencyCoeff = transparencyCoeff;
        }

        double getX1() {
            return x1;
        }

        double getX2() {
            return x2;
        }

        double getTransparencyCoeff() {
            return transparencyCoeff;
        }
    }
}