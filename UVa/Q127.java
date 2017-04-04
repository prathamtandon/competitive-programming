import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

class Q127 {
    private static Stack<String>[] piles;
    private static int numberOfPiles;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while(!(line = reader.readLine()).equals("#")) {
            String secondLine = reader.readLine();
            int lineNumber = 1;
            numberOfPiles = 0;
            piles = (Stack<String>[]) new Stack[52];
            while(lineNumber < 3) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                int card = 1;
                while (card < 27) {
                    String nextCard = tokenizer.nextToken();
                    int movedIndex = -1;
                    if(canMoveToKthPreviousPile(nextCard, numberOfPiles, 3)) {
                        Stack<String> pile = piles[numberOfPiles - 3];
                        pile.push(nextCard);
                        movedIndex = numberOfPiles - 3;
                    } else if(canMoveToKthPreviousPile(nextCard, numberOfPiles, 1)) {
                        Stack<String> pile = piles[numberOfPiles - 1];
                        pile.push(nextCard);
                        movedIndex = numberOfPiles - 1;
                    } else {
                        Stack<String> pile = new Stack<>();
                        pile.push(nextCard);
                        piles[numberOfPiles] = pile;
                        numberOfPiles++;
                    }
                    if(movedIndex != -1) {
                        shuffleCards(movedIndex, movedIndex + 3);
                    }
                    card++;
                }
                line = secondLine;
                lineNumber++;
            }
            printRemainingPiles();
        }
    }

    private static void printRemainingPiles() {
        String suffix = numberOfPiles == 1 ? " pile " : " piles ";
        StringBuilder builder = new StringBuilder(numberOfPiles + suffix + "remaining: ");
        for (int i = 0; i < numberOfPiles - 1; i++) {
            builder.append(piles[i].size());
            builder.append(" ");
        }
        builder.append(piles[numberOfPiles - 1].size());
        System.out.println(builder.toString());
    }

    private static boolean canMoveToKthPreviousPile(String card, int cardIndex, int offset) {
        return cardIndex >= offset && isMatch(card, piles[cardIndex - offset].peek());
    }

    private static boolean isMatch(String first, String second) {
        char[] firstChars = first.toCharArray();
        char[] secondChars = second.toCharArray();
        return firstChars[0] == secondChars[0] || firstChars[1] == secondChars[1];
    }

    private static void shuffleCards(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            if(i < 0 || i >= numberOfPiles)
                return;
            String top = piles[i].peek();
            if(canMoveToKthPreviousPile(top, i, 3)) {
                top = piles[i].pop();
                if(piles[i].isEmpty()) {
                    fillGap(i);
                    numberOfPiles--;
                }
                piles[i - 3].push(top);
                shuffleCards(i - 3, i);
            } else if(canMoveToKthPreviousPile(top, i, 1)) {
                top = piles[i].pop();
                if(piles[i].isEmpty()) {
                    fillGap(i);
                    numberOfPiles--;
                }
                piles[i - 1].push(top);
                shuffleCards(i - 1, i + 2);
            }
        }
    }

    private static void fillGap(int index) {
        for (int i = index; i < numberOfPiles - 1; i++) {
            piles[i] = piles[i + 1];
        }
    }
}