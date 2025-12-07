package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day7 {
    char RAY = '|';
    char SPLITTER = '^';
    char SPACE = '.';

    public long solvePart1(char[][] input) {
        long ans = 0;

        char[][] grid = deepCopy(input);

        for(int j=0; j<grid[0].length; j++) {
            if(grid[0][j] == 'S') {
                grid[0][j] = RAY;
                break;
            }
        }

        int m = grid.length - 1;
        int n = grid[0].length;

        for(int i = 1; i < m; i++) {
            for(int j=0; j < n; j++) {
                if(grid[i-1][j] == RAY) {
                    if(grid[i][j] == SPACE) grid[i][j] = RAY;
                    else if(grid[i][j] == SPLITTER) {
                        ans++;
                        grid[i][j-1] = RAY;    
                        grid[i][j+1] = RAY;    
                    }
                }
            }
        }
        return ans;
    }

    public long solvePart2(char[][] input) {
        int startPos = 0;
        long[][] memo = new long[input.length][input[0].length];

        for(int i=0; i<memo.length; i++) Arrays.fill(memo[i], -1);

        for(int j=0; j<input[0].length; j++) {
            if(input[0][j] == 'S') {
                startPos = j;
                break;
            }
        }
        return backtrack(0, startPos, input, memo);
    }

    private char[][] deepCopy(char[][] original) {
        char[][] copy = new char[original.length][original[0].length];

        for(int i=0; i<original.length; i++) {
            for(int j=0; j<original[i].length; j++) {
                copy[i][j] = original[i][j];
            }
        }

        return copy;
    }

    private long backtrack(int i, int j, char[][] input, long[][] memo) {
        if(i == input.length - 1) return 1;

        if(memo[i][j] != -1) return memo[i][j];
        
        if(input[i][j] == SPLITTER) return memo[i][j] = backtrack(i + 1, j - 1, input, memo) + backtrack(i + 1, j + 1, input, memo);
        
        return  memo[i][j] = backtrack(i + 1, j, input, memo);  
    }

    public static void main(String[] args) {
        Day7 obj = new Day7();

        File file = new File("input.txt");
        List<String> rawLines = new ArrayList<>();

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();                
                if(line.strip().length() == 0) continue;

                rawLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }

        int n = rawLines.size();
        char[][] input = new char[n][];

        for (int r = 0; r < n; r++) {
            input[r] = rawLines.get(r).toCharArray();
        }
        
        long ans1 = obj.solvePart1(input);
        long ans2 = obj.solvePart2(input);

        System.out.println(ans1);
        System.out.println(ans2);
    }
}