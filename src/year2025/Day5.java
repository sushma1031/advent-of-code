package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day5 {
    public long solvePart1(String[] ranges, long[] ingredients) {
        long[][] rngs = new long[ranges.length][2];
        String[] range;

        int ans = 0;

        for (int i = 0; i < ranges.length; i++) {
            range = ranges[i].split("-");
            rngs[i][0] = Long.parseLong(range[0]);
            rngs[i][1] = Long.parseLong(range[1]);
        }

        Arrays.sort(rngs, (a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));

        List<long[]> merged = new ArrayList<>();

        long[] current = rngs[0];
        for (int i = 1; i < rngs.length; i++) {
            if (current[1] >= rngs[i][0] - 1) {
                current[1] = Math.max(current[1], rngs[i][1]);
            } else {
                merged.add(current);
                current = rngs[i];
            }
        }
        merged.add(current);

        long[][] mergedRngs = merged.toArray(new long[0][]);

        int low, high, mid;
        for (long num : ingredients) {
            low = 0;
            high = mergedRngs.length - 1;

            while (low <= high) {
                mid = (low + high) / 2;

                if (mergedRngs[mid][0] <= num && mergedRngs[mid][1] >= num) {
                    ans++;
                    break;
                }

                if (mergedRngs[mid][0] > num)
                    high = mid - 1;
                else
                    low = mid + 1;
            }
        }

        return ans;
    }

    public long solvePart2(String[] ranges) {
        long ans = 0;
        String[] range;
        long[][] rngs = new long[ranges.length][2];

        for (int i = 0; i < ranges.length; i++) {
            range = ranges[i].split("-");
            rngs[i][0] = Long.parseLong(range[0]);
            rngs[i][1] = Long.parseLong(range[1]);
        }

        Arrays.sort(rngs, (a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));


        long[] current = rngs[0];
        for (int i = 1; i < rngs.length; i++) {
            if (current[1] >= rngs[i][0] - 1) {
                current[1] = Math.max(current[1], rngs[i][1]);
            } else {
                ans += current[1] - current[0] + 1;
                current = rngs[i];
            }
        }
        ans += current[1] - current[0] + 1;
        

        return ans;
    }

    public static void main(String[] args) {
        Day5 obj = new Day5();

        File file = new File("input.txt");
        List<String> input1 = new ArrayList<>();
        List<Long> input2 = new ArrayList<>();

        boolean ingredientsStart = false;

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();

                if (line.strip().length() == 0) {
                    if (!ingredientsStart)
                        ingredientsStart = true;
                    continue;
                }

                if (ingredientsStart)
                    input2.add(Long.parseLong(line));
                else
                    input1.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        String[] ranges = input1.toArray(new String[0]);

        int n = input2.size();
        long[] ingredients = new long[n];
        for (int i = 0; i < n; i++)
            ingredients[i] = input2.get(i);

        long ans1 = obj.solvePart1(ranges, ingredients);
        long ans2 = obj.solvePart2(ranges);

        System.out.println(ans1);
        System.out.println(ans2);
    }
}