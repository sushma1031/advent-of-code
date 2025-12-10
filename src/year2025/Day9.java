package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9 {
    public long solvePart1(int[][] input) {
        long maxArea = 0, area = 0;

        for(int i=0; i<input.length; i++) {
            for(int j=i+1; j<input.length; j++) {
                if(input[i][0] == input[j][0] || input[i][1] == input[j][1]) continue;

                area = (Math.abs((long)input[i][0] - input[j][0]) + 1) * (Math.abs((long)input[i][1] - input[j][1]) + 1);

                if(area > maxArea) maxArea = area;
            }
        }
        return maxArea;
    }

    public long solvePart2() {
        return 0;
    }

    public static void main(String[] args) {
        Day9 obj = new Day9();

        File file = new File("input.txt");
        List<int[]> lines = new ArrayList<>();
        String[] coords;

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();                
                if(line.strip().length() == 0) continue;
                
                coords = line.split(",");
                lines.add(new int[]{Integer.parseInt(coords[0]), Integer.parseInt(coords[1])});

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int[][] input = lines.toArray(new int[0][]);
        
        long ans1 = obj.solvePart1(input);
        long ans2 = obj.solvePart2();

        System.out.println(ans1);
        System.out.println(ans2);
    }
}