import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Q573 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int heightOfWell = Integer.parseInt(tokenizer.nextToken());
            if(heightOfWell == 0)
                break;
            int distanceTravelledInDay = Integer.parseInt(tokenizer.nextToken());
            int distanceLostInNight = Integer.parseInt(tokenizer.nextToken());
            int fatiguePercent = Integer.parseInt(tokenizer.nextToken());
            Snail snail = new Snail(heightOfWell, distanceTravelledInDay, distanceLostInNight, fatiguePercent);
            printSuccessOrFailure(snail.getSuccessOrFailure());
        }
    }

    private static void printSuccessOrFailure(Result result) {
        String line = "";
        line += result.isSuccess ? "success" : "failure";
        line += " on day " + result.day;
        System.out.println(line);
    }

    private static class Result {
        int day;
        boolean isSuccess;
    }

    private static class Snail {
        private int heightOfWell;
        private int distanceClimbedOnFirstDay;
        private int distanceLostInNight;
        private int day;
        private double distanceLostInFatigue;
        private double distanceSoFar;

        Snail(int heightOfWell, int distanceTravelledInDay, int distanceLostInNight, int fatiguePercent) {
            this.heightOfWell = heightOfWell;
            this.distanceClimbedOnFirstDay = distanceTravelledInDay;
            this.distanceLostInNight = distanceLostInNight;
            this.distanceLostInFatigue = fatiguePercent * distanceTravelledInDay / 100.0;
            this.day = 1;
        }

        Result getSuccessOrFailure() {
            double distanceClimbedYesterday = 0.0;
            double distanceClimbedToday;
            while(true) {
                distanceClimbedToday = getDistanceClimbedToday(distanceClimbedYesterday);
                distanceSoFar += distanceClimbedToday;
                if(distanceSoFar > heightOfWell)
                    break;
                distanceSoFar -= distanceLostInNight;
                if(distanceSoFar < 0)
                    break;
                day++;
                distanceClimbedYesterday = distanceClimbedToday;
            }
            Result result = new Result();
            result.day = day;
            result.isSuccess = distanceSoFar >= heightOfWell;
            return result;
        }

        double getDistanceClimbedToday(double previousDistance) {
            if(day == 1)
                return distanceClimbedOnFirstDay;
            double result = previousDistance - distanceLostInFatigue;
            return result < 0 ? 0.0 : result;
        }
    }
}