package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6 {
    public long solvePart1(String[][] input) {
        int[][] operands = new int[input.length - 1][];
        int[] ops = new int[input[0].length];

        for (int i = 0; i < input.length - 1; i++) {
            operands[i] = new int[input[i].length];

            for (int j = 0; j < operands[i].length; j++) {
                operands[i][j] = Integer.parseInt(input[i][j]);
            }
        }

        int opsRow = input.length - 1;
        for (int j = 0; j < input[opsRow].length; j++) {
            ops[j] = input[opsRow][j].equals("*") ? 1 : 0;
        }

        long ans = 0;
        long res;

        for (int j = 0; j < operands[0].length; j++) {
            res = ops[j];
            for (int i = 0; i < operands.length; i++) {
                if (ops[j] == 1)
                    res *= operands[i][j];
                else
                    res += operands[i][j];
            }

            ans += res;
        }
        return ans;
    }

    public long solvePart2(List<String> rawLines) {
        int rows = rawLines.size() - 1;
        int cols = rawLines.get(0).length();

        String[] opsStr = rawLines.get(rows).split("\\s+");
        int[] ops = new int[opsStr.length];

        for (int j = 0; j < ops.length; j++) {
            ops[j] = opsStr[j].equals("*") ? 1 : 0;
        }
        char[][] operands = new char[rows][cols];


        for (int r = 0; r < rows; r++) {
            String line = rawLines.get(r);
            for (int c = 0; c < cols; c++) {
                if (c < line.length()) {
                    operands[r][c] = line.charAt(c);
                } else {
                    operands[r][c] = ' ';
                }
            }
        }

        long ans = 0;
        long res;
        int val, size;
        boolean isColumnEmpty;

        for (int j = 0, opsIdx = 0; j < cols && opsIdx < ops.length;) {
            res = ops[opsIdx];
            size = 0;

            int currCol = j;

            while(currCol < cols) {
                isColumnEmpty = true;

                for (int r = 0; r < rows; r++) {
                    if (operands[r][currCol] != ' ') {
                        isColumnEmpty = false;
                        break;
                    }
                }

                if (isColumnEmpty) {
                    break;
                }
                size++;
                currCol++;
            }

            for (int k = j; k < j + size; k++) {
                val = 0;
                for (int i = 0; i < operands.length; i++) {
                    if(operands[i][k] == ' ') continue;
                    val = (val * 10) + (operands[i][k] - '0');
                }

                if (ops[opsIdx] == 1)
                    res *= val;
                else
                    res += val;
            }

            ans += res;
            j += size + 1;
            opsIdx++;
        }
        return ans;
    }

    public static void main(String[] args) {
        Day6 obj = new Day6();

        File file = new File("input.txt");
        List<String[]> lines = new ArrayList<>();
        List<String> rawLines = new ArrayList<>();

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();
                if (line.strip().length() == 0)
                    continue;

                lines.add(line.trim().split("\\s+"));
                rawLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int n = lines.size();
        String[][] input = new String[n][];

        for (int i = 0; i < n; i++)
            input[i] = lines.get(i);

        long ans1 = obj.solvePart1(input);
        long ans2 = obj.solvePart2(rawLines);

        System.out.println(ans1);
        System.out.println(ans2);
    }
}