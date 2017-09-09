import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Uva196 {
    private static String[][] sheet = new String[1000][18300];
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer;
        int tc = Integer.parseInt(reader.readLine());
        for (int i = 0; i < tc; i++) {
            String line = reader.readLine();
            if(line.length() == 0)
                line = reader.readLine();
            tokenizer = new StringTokenizer(line);
            int cols = Integer.parseInt(tokenizer.nextToken());
            int rows = Integer.parseInt(tokenizer.nextToken());
            for (int j = 0; j < rows; j++) {
                tokenizer = new StringTokenizer(reader.readLine());
                for (int k = 0; k < cols; k++) {
                    sheet[j][k] = tokenizer.nextToken();
                }
            }
            StringBuilder result = new StringBuilder();
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    if(sheet[j][k].startsWith("="))
                        sheet[j][k] = evaluate(sheet[j][k].substring(1));
                    result = result.append(sheet[j][k]).append(" ");
                }
                result = new StringBuilder(result.toString().trim());
                result = result.append("\n");
            }
            System.out.print(result);
        }
    }

    private static String evaluate(String expr)
    {
        String[] terms = expr.split("\\+");
        long ans = 0;
        for(String term: terms)
        {
            Point p = convertToPoint(term);
            if(sheet[p.x][p.y].startsWith("="))
                sheet[p.x][p.y] = evaluate(sheet[p.x][p.y].substring(1));
            ans += Long.parseLong(sheet[p.x][p.y]);
        }
        return String.valueOf(ans);
    }

    private static Point convertToPoint(String term)
    {
        int idx = getFirstDigitIdx(term);
        String rowId = term.substring(idx);
        int r = Integer.parseInt(rowId) - 1;
        int c = -1;
        int p = 0;
        for(int i = idx - 1; i >= 0; i--)
        {
            c += (term.charAt(i) - 'A' + 1) * (int)Math.pow(26, p++);
        }
        return new Point(r, c);
    }

    private static int getFirstDigitIdx(String expr)
    {
        int idx = 0;
        for (int i = 0; i < expr.length(); i++) {
            if(Character.isDigit(expr.charAt(i)))
            {
                idx = i;
                break;
            }
        }
        return idx;
    }
}