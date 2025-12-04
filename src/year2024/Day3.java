package year2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3 {
    public int solve1(List<String> input) {
        int ans = 0;

        for (String s : input) {
            char[] str = s.toCharArray();
            int i = 0, j, k;
            int[] nums = new int[2];
            boolean valid = false;

            while (i < str.length - 4) {
                if (!(str[i] == 'm' && str[i + 1] == 'u' && str[i + 2] == 'l' && str[i + 3] == '(')) {
                    i++;
                    continue;
                }

                j = i + 4;
                k = 0;
                nums[0] = nums[1] = 0;
                valid = false;
                while (j < str.length) {
                    if (Character.isDigit(str[j]))
                        nums[k] = nums[k] * 10 + (str[j] - '0');
                    else if (str[j] == ',' && k == 0 && nums[0] > 0)
                        k = 1;
                    else if (str[j] == ')' && k == 1 && nums[1] > 0) {
                        valid = true;
                        break;
                    } else {
                        // reset
                        nums[0] = nums[1] = 0;
                        break;
                    }
                    j++;
                }
                if (valid)
                    ans += nums[0] * nums[1];
                i = j + 1;
            }
        }

        return ans;
    }

    public int solve2(List<String> input) {
        int ans = 0;
        for (String s : input) {
            char[] str = s.toCharArray();
            int i = 0, j, k;
            int[] nums = new int[2];
            boolean valid = false;
            boolean enabled = true;
            int limit = str.length - 4;

            while (i < limit) {
                if (str[i] == 'd' && str[i + 1] == 'o') {
                    if (i + 6 < str.length &&
                            str[i + 2] == 'n' && str[i + 3] == '\'' && str[i + 4] == 't' &&
                            str[i + 5] == '(' && str[i + 6] == ')') {

                        enabled = false;
                        i += 7;

                    } else if (i + 3 < str.length &&
                            str[i + 2] == '(' && str[i + 3] == ')') {

                        enabled = true;
                        i += 4;
                    } else {
                        i++;
                    }
                    continue;
                }
                if (!(str[i] == 'm' && str[i + 1] == 'u' && str[i + 2] == 'l' && str[i + 3] == '(')) {
                    i++;
                    continue;
                }
                if (!enabled) {
                    i += 4;
                    continue;
                }

                j = i + 4;
                k = 0;
                nums[0] = nums[1] = 0;
                valid = false;
                while (j < str.length) {
                    if (Character.isDigit(str[j]))
                        nums[k] = nums[k] * 10 + (str[j] - '0');
                    else if (str[j] == ',' && k == 0 && nums[0] > 0)
                        k = 1;
                    else if (str[j] == ')' && k == 1 && nums[1] > 0) {
                        valid = true;
                        break;
                    } else {
                        // reset
                        nums[0] = nums[1] = 0;
                        break;
                    }
                    j++;
                }
                if (valid)
                    ans += nums[0] * nums[1];
                i = j + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Day3 obj = new Day3();

        File file = new File("input.txt");
        List<String> input = new ArrayList<>();

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();
                if (line.strip().length() == 0)
                    continue;

                input.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int ans1 = obj.solve1(input);
        System.out.println(ans1);

        int ans2 = obj.solve2(input);
        System.out.println(ans2);
    }
}