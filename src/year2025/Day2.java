package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
    public long solvePart1(String[] idRanges) {
        long ans = 0;
        String[] bounds;
        long num, last, tenPow, limit;
        int len;

        for(String range: idRanges) {
            bounds = range.split("-");
            len = bounds[0].length();
            num = Long.parseLong(bounds[0]);
            last = Long.parseLong(bounds[1]);
            limit = (long) Math.pow(10, len);
            
            while(num <= last) {
                if((len & 1) == 0) {
                    tenPow = (long) Math.pow(10, (len / 2));
                    if(num % tenPow == num / tenPow) ans += num;
                }
                num++;

                if(num == limit) {
                    len++;
                    limit *= 10;
                }
            }
        }
        return ans;
    }
    
    public long solvePart2(String[] idRanges) {
        long ans = 0;
        String[] bounds;
        long num, last;
        String numStr, substr;
        int len;

        for (String range : idRanges) {
            bounds = range.split("-");
            len = bounds[0].length();
            num = Long.parseLong(bounds[0]);
            last = Long.parseLong(bounds[1]);

            while (num <= last) {
                numStr = String.valueOf(num);
                len = numStr.length();

                for (int i = 1; i <= (len / 2); i++) {
                    if (len % i == 0) {
                        substr = numStr.substring(0, i);
                        if (substr.repeat(len / i).equals(numStr)) {
                            ans += num;
                            break;
                        }
                    }
                }
                num++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Day2 obj = new Day2();

        File file = new File("../../assets/input.txt");
        String content = "";
        String[] input;

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();                
                if(line.strip().length() == 0) continue;

                content += line;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        input = content.split(",");

        long startTime = System.currentTimeMillis(); 
        
        long ans1 = obj.solvePart1(input);
        long ans2 = obj.solvePart2(input);
        
        long duration = System.currentTimeMillis() - startTime;
        System.out.printf("Execution time: %d ms\n", duration);

        System.out.println(ans1);
        System.out.println(ans2);
    }
}