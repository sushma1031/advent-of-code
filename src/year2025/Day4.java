package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4 {
    private char[][] input;

    public long solvePart1(char[][] input) {
        int ans = 0;
        this.input = input;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (input[i][j] != '@')
                    continue;

                if (checkAdjCells(i, j))
                    ans++;
            }
        }
        return ans;
    }

    public long solvePart2(char[][] input) {
        int ans = 0;
        this.input = input;
        int removed;

        do {
            removed = 0;
            for (int i = 0; i < input.length; i++) {
                for (int j = 0; j < input[0].length; j++) {
                    if (input[i][j] != '@')
                        continue;

                    if (checkAdjCells(i, j)) {
                        removed++;
                        input[i][j] = '.';
                    }
                }
            }

            ans += removed;
        } while (removed != 0);
        return ans;
    }

    private boolean checkAdjCells(int i, int j) {
        int count = 0;

        boolean hasTop = i > 0;
        boolean hasBottom = i < input.length - 1;
        boolean hasLeft = j > 0;
        boolean hasRight = j < input[0].length - 1;

        if (hasTop)
            count += (input[i - 1][j] == '@') ? 1 : 0;
        if (hasBottom)
            count += (input[i + 1][j] == '@') ? 1 : 0;
        if (hasLeft)
            count += (input[i][j - 1] == '@') ? 1 : 0;
        if (hasRight)
            count += (input[i][j + 1] == '@') ? 1 : 0;

        if (count == 4)
            return false;

        if (hasTop && hasLeft)
            count += (input[i - 1][j - 1] == '@') ? 1 : 0;
        if (hasTop && hasRight)
            count += (input[i - 1][j + 1] == '@') ? 1 : 0;
        if (hasBottom && hasLeft)
            count += (input[i + 1][j - 1] == '@') ? 1 : 0;
        if (hasBottom && hasRight)
            count += (input[i + 1][j + 1] == '@') ? 1 : 0;

        return count < 4;
    }

    public static void main(String[] args) {
        Day4 obj = new Day4();

        File file = new File("input.txt");
        List<String> lines = new ArrayList<>();

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();
                if (line.strip().length() == 0)
                    continue;

                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int n = lines.size();
        char[][] input = new char[n][n];
        for (int i = 0; i < n; i++) {
            input[i] = lines.get(i).toCharArray();
        }
        long ans1 = obj.solvePart1(input);
        long ans2 = obj.solvePart2(input);

        System.out.println(ans1);
        System.out.println(ans2);
    }
}