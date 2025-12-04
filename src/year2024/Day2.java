package year2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
	public int solve1(int[][] data) {
        int ans = 0;
        boolean ascending = false;
        boolean safe;

        for(int i=0; i<data.length; i++) {
            if(data[i].length == 1) {
                ans++;
                continue;
            }
            ascending = data[i][0] < data[i][1];
            safe = true;
            for(int j=1; j<data[i].length && safe; j++) {
                if(data[i][j] == data[i][j-1]) safe = false;
                if(ascending) {
                    safe = data[i][j] > data[i][j-1] && data[i][j] - data[i][j-1] <= 3;
                } else {
                    safe = data[i][j] < data[i][j-1] && data[i][j-1] - data[i][j] <= 3;
                }
            }

            if(safe) ans++;
        }

        return ans;
    }

	public int solve2(int[][] data) {
        int ans = 0;

        for (int i = 0; i < data.length; i++) {
            if (isSafeWithSkip(data[i], -1))
                ans++;
            else {
                for (int skip = 0; skip < data[i].length; skip++) {
                    if (isSafeWithSkip(data[i], skip)) {
                        ans++;
                        break;
                    }
                }
            }
        }

        return ans;
    }

    private boolean isSafeWithSkip(int[] report, int skipIndex) {
        if (report.length <= 1)
            return true;

        int first = -1, second = -1;
        for (int i = 0; i < report.length; i++) {
            if (i == skipIndex)
                continue;
            if (first == -1)
                first = i;
            else if (second == -1) {
                second = i;
                break;
            }
        }

        if (second == -1)
            return true;

        boolean ascending = report[second] > report[first];

        int prev = first, diff;
        for (int i = second; i < report.length; i++) {
            if (i == skipIndex)
                continue;

            diff = report[i] - report[prev];
            if (diff == 0) return false;

            if (ascending && (diff < 0 || diff > 3))
                return false;
            if (!ascending && (diff > 0 || diff < -3))
                return false;

            prev = i;
        }

        return true;
    }

    public static void main(String[] args) {
        Day2 obj = new Day2();

        File file = new File("input.txt");
        int[][] data = new int[1000][];
        int i = 0, j;

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();

                String[] nums = line.split(" ");
                data[i] = new int[nums.length];

                for (j = 0; j < nums.length; j++) {
                    data[i][j] = Integer.parseInt(nums[j]);
                }

                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int ans1 = obj.solve1(data);
        System.out.println(ans1);

        int ans2 = obj.solve2(data);
        System.out.println(ans2);
    }
}