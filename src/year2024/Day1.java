package year2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day1 {
	public int solve1(int[] list1, int[] list2) {
        Arrays.sort(list1);
        Arrays.sort(list2);

        int sum = 0;

        for (int i = 0; i < list1.length; i++) {
            sum += Math.abs(list1[i] - list2[i]);
        }

        return sum;
    }

	public int solve2(int[] list1, int[] list2) {
        Map<Integer, Integer> freq = new HashMap<>();
        for(int num: list2) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        int simScore = 0;
        for(int num: list1) {
            simScore += (num * freq.getOrDefault(num, 0));
        }

        return simScore;
    }

    public static void main(String[] args) {
        Day1 obj = new Day1();
        int[] list1 = new int[1000];
        int[] list2 = new int[1000];

        File file = new File("input.txt");
        int i = 0;
        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();
                
                if(line.length() == 0) continue;

                String[] nums = line.split("   ");
                
                list1[i] = Integer.parseInt(nums[0]);
                list2[i] = Integer.parseInt(nums[1]);

                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int ans1 = obj.solve1(list1, list2);
        System.out.println(ans1);

        int ans2 = obj.solve2(list1, list2);
        System.out.println(ans2);
    }
}