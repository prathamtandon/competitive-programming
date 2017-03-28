import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

class Q394 {
    private static HashMap<String, ArrayDeclaration> declarations = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
        int N = Integer.parseInt(stringTokenizer.nextToken());
        int R = Integer.parseInt(stringTokenizer.nextToken());
        ArrayDeclaration declaration;
        ArrayReference reference;
        while(N > 0) {
            declaration = readDeclaration(reader);
            declarations.put(declaration.getName(), declaration);
            N--;
        }
        while(R > 0) {
            reference = readReference(reader);
            long physicalAddress = referenceToPhysicalAddress(reference);
            printPhysicalAddress(reference, physicalAddress);
            R--;
        }
    }

    private static ArrayDeclaration readDeclaration(BufferedReader reader) throws IOException {
        StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
        String name = stringTokenizer.nextToken();
        long baseAddress = Long.parseLong(stringTokenizer.nextToken());
        long size = Long.parseLong(stringTokenizer.nextToken());
        int dimension = Integer.parseInt(stringTokenizer.nextToken());
        int numBounds = dimension;
        Bound[] bounds = new Bound[numBounds];
        int i = 0;
        while(numBounds > 0) {
            long lowerBound = Long.parseLong(stringTokenizer.nextToken());
            long upperBound = Long.parseLong(stringTokenizer.nextToken());
            bounds[i] = new Bound(lowerBound, upperBound);
            numBounds--;
            i++;
        }
        return new ArrayDeclaration(name, baseAddress, size, dimension, bounds);
    }

    private static ArrayReference readReference(BufferedReader reader) throws IOException {
        StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
        String name = stringTokenizer.nextToken();
        ArrayDeclaration declaration = declarations.get(name);
        if(declaration != null) {
            int dimension = declaration.getDimension();
            int[] indices = new int[dimension];
            int i = 0;
            while(dimension > 0) {
                indices[i] = Integer.parseInt(stringTokenizer.nextToken());
                dimension--;
                i++;
            }
            return new ArrayReference(name, indices);
        }
        throw new RuntimeException("Invalid Reference");
    }

    private static long referenceToPhysicalAddress(ArrayReference reference) {
        ArrayDeclaration declaration = declarations.get(reference.getName());
        return declaration.referenceToPhysicalAddress(reference);
    }

    private static void printPhysicalAddress(ArrayReference reference, long physicalAddress) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(reference.name);
        stringBuilder.append("[");
        int[] indices = reference.getIndices();
        for (int i = 0; i < indices.length - 1; i++) {
            stringBuilder.append(indices[i]);
            stringBuilder.append(", ");
        }
        stringBuilder.append(indices[indices.length - 1]);
        stringBuilder.append("] = ");
        stringBuilder.append(physicalAddress);
        System.out.println(stringBuilder.toString());
    }

    private static class ArrayDeclaration {
        private String name;
        private long baseAddress;
        private long size;
        private int dimension;
        private Bound[] bounds;
        private long[] sizes;

        ArrayDeclaration(String name, long baseAddress, long size, int dimension, Bound[] bounds) {
            this.name = name;
            this.baseAddress = baseAddress;
            this.size = size;
            this.dimension = dimension;
            this.bounds = bounds;
            this.sizes = this.getSizes();
        }

        long[] getSizes() {
            long[] sizes = new long[this.dimension + 1];
            sizes[sizes.length - 1] = this.size;
            int boundIndex = this.bounds.length - 1;
            long sum = 0;
            for(int i = sizes.length - 1; i > 1; i--) {
                Bound bound = this.bounds[boundIndex--];
                sizes[i-1] = sizes[i] * (bound.getUpperBound() - bound.getLowerBound() + 1);
                sum += sizes[i] * bound.getLowerBound();
            }
            sizes[0] = this.baseAddress - sizes[1] * this.bounds[boundIndex].getLowerBound() - sum;
            return sizes;
        }

        String getName() {
            return this.name;
        }

        int getDimension() {
            return this.dimension;
        }

        long referenceToPhysicalAddress(ArrayReference reference) {
            long physicalAddress = this.sizes[0];
            int[] indices = reference.getIndices();
            for (int i = 0; i < indices.length; i++) {
                physicalAddress += this.sizes[i+1] * indices[i];
            }
            return physicalAddress;
        }
    }

    private static class Bound {
        private long lowerBound;
        private long upperBound;

        Bound(long lowerBound, long upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        long getLowerBound() {
            return this.lowerBound;
        }

        long getUpperBound() {
            return this.upperBound;
        }
    }

    private static class ArrayReference {
        private String name;
        private int[] indices;

        ArrayReference(String name, int[] indices) {
            this.name = name;
            this.indices = indices;
        }

        String getName() {
            return this.name;
        }

        int[] getIndices() {
            return this.indices;
        }
    }
}