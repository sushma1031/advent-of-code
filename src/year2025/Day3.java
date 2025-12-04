package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day3 {
    public int solvePart1(String[] banks) {
        int ans = 0;
        int len, tens, ones, tensPtr;
        int num;

        for (String bank : banks) {
            tensPtr = 0;
            tens = ones = 0;
            int i = 0;
            len = bank.length() - 1;

            while (i < len) {
                num = bank.charAt(i) - '0';
                if (num > tens) {
                    tens = num;
                    tensPtr = i;
                    ones = bank.charAt(i + 1) - '0';
                } else if (num > ones) {
                    ones = num;
                }

                i++;
            }

            num = bank.charAt(len) - '0';
            if (num > tens && tensPtr != len - 1) {
                tens = ones > tens ? ones : tens;
                ones = num;
            } else if (num > ones) {
                ones = num;
            }

            ans += (tens * 10 + ones);
        }
        return ans;
    }

    public long solvePart2(String[] banks) {
        long ans = 0;

        for (String bank : banks) {
            int[] idxs = new int[12];
            int prevIdx = -1;
            int len = bank.length();
            int max = 0;
            for (int x = 11; x >= 0; x--) {
                max = 0;
                for (int i = prevIdx + 1; i < len - x; i++) {
                    int num = bank.charAt(i) - '0';
                    if(num > max) {
                        max = num;
                        idxs[x] = i;
                    }
                }
                prevIdx = idxs[x];
            }

            long val = 0;
            for(int x = 11; x >= 0; x--) {
                val = val * 10 +(bank.charAt(idxs[x]) - '0');
            }
            ans += val;
        }
        return ans;
    }

    public static void main(String[] args) {
        Day3 obj = new Day3();
        String[] input = new String[200];
        File file = new File("../../assets/input.txt");

        int i = 0;
        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();
                if (line.strip().length() == 0)
                    continue;

                input[i++] = line;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }

        int ans1 = obj.solvePart1(input);
        long ans2 = obj.solvePart2(input);

        System.out.println(ans1);
        System.out.println(ans2);
    }
}