import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Q10901 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FerryTransport ferry;
        int cases = Integer.parseInt(reader.readLine());
        while(cases > 0) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int capacity = Integer.parseInt(tokenizer.nextToken());
            int crossingTime = Integer.parseInt(tokenizer.nextToken());
            int numArrivals = Integer.parseInt(tokenizer.nextToken());
            ferry = new FerryTransport(capacity, crossingTime, numArrivals);
            while (numArrivals > 0) {
                tokenizer = new StringTokenizer(reader.readLine());
                int arrivalTime = Integer.parseInt(tokenizer.nextToken());
                String location = tokenizer.nextToken();
                ferry.addToSchedule(arrivalTime, location);
                numArrivals--;
            }
            int[] journeyTimes = ferry.getJourneyTimes();
            for(int time: journeyTimes)
                System.out.println(time);
            cases--;
            if(cases > 0)
                System.out.println();
        }
    }

    private static class Car {
        int arrivalTime;
        String location;

        Car(int arrivalTime, String location) {
            this.arrivalTime = arrivalTime;
            this.location = location;
        }
    }

    private static class FerryTransport {
        private static final String LEFT = "left";
        private static final String RIGHT = "right";
        private int globalTime;
        private String ferryLocation;
        private int capacity;
        private int crossingTime;
        private Car[] arrivalOrder;
        private Queue<Car> leftQueue;
        private Queue<Car> rightQueue;
        private HashMap<Car, Integer> journeyTimes;
        private int index;

        FerryTransport(int capacity, int crossingTime, int numArrivals) {
            this.capacity = capacity;
            this.crossingTime = crossingTime;
            this.arrivalOrder = new Car[numArrivals];
            this.journeyTimes = new HashMap<>();
            leftQueue = new LinkedList<>();
            rightQueue = new LinkedList<>();
            this.globalTime = 0;
            this.index = 0;
            this.ferryLocation = LEFT;
        }

        void addToSchedule(int arrivalTime, String location) {
            Car car = new Car(arrivalTime, location);
            if(location.equals(LEFT))
                leftQueue.add(car);
            else
                rightQueue.add(car);
            this.arrivalOrder[index++] = car;
        }


        private void loadAtGivenLocation(String location) {
            int loadedCount = 0;
            Queue<Car> queue = location.equals(LEFT) ? leftQueue : rightQueue;
            while(!queue.isEmpty() && queue.peek().arrivalTime <= this.globalTime && loadedCount < this.capacity) {
                this.journeyTimes.put(queue.remove(), this.globalTime + this.crossingTime);
                loadedCount++;
            }
        }

        private void handleCarsAtGivenLocation(String location) {
            if(ferryLocation.equals(location)) {
                loadAtGivenLocation(location);
                this.globalTime += this.crossingTime;
                flipFerryLocation();
            } else {
                this.globalTime += this.crossingTime;
                loadAtGivenLocation(location);
                this.globalTime += this.crossingTime;
            }
        }

        private boolean areCarsWaitingAtLocation(String location) {
            Queue<Car> queue = location.equals(LEFT) ? leftQueue : rightQueue;
            return !queue.isEmpty() && queue.peek().arrivalTime <= this.globalTime;
        }

        private void simulateFerryIdleTime() {
            if(!leftQueue.isEmpty() && !rightQueue.isEmpty())
                this.globalTime = Math.min(leftQueue.peek().arrivalTime, rightQueue.peek().arrivalTime);
            else if(!leftQueue.isEmpty())
                this.globalTime = leftQueue.peek().arrivalTime;
            else if(!rightQueue.isEmpty())
                this.globalTime = rightQueue.peek().arrivalTime;
        }

        int[] getJourneyTimes() {
            while(!leftQueue.isEmpty() && !rightQueue.isEmpty()) {
                if(areCarsWaitingAtLocation(ferryLocation)) {
                    handleCarsAtGivenLocation(ferryLocation);
                } else if(areCarsWaitingAtLocation(getOppositeLocation(ferryLocation))) {
                    handleCarsAtGivenLocation(getOppositeLocation(ferryLocation));
                } else {
                    simulateFerryIdleTime();
                }
            }
            while(!leftQueue.isEmpty()) {
                if(areCarsWaitingAtLocation(LEFT))
                    handleCarsAtGivenLocation(LEFT);
                else
                    simulateFerryIdleTime();
            }
            while(!rightQueue.isEmpty()) {
                if(areCarsWaitingAtLocation(RIGHT))
                    handleCarsAtGivenLocation(RIGHT);
                else
                    simulateFerryIdleTime();
            }
            int[] result = new int[this.arrivalOrder.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = this.journeyTimes.get(this.arrivalOrder[i]);
            }
            return result;
        }

        void flipFerryLocation() {
            if(this.ferryLocation.equals(LEFT))
                this.ferryLocation = RIGHT;
            else
                this.ferryLocation = LEFT;
        }

        String getOppositeLocation(String location) {
            if(location.equals(LEFT))
                return RIGHT;
            return LEFT;
        }
    }
}