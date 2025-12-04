package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

public class Day1 {
    public int solvePart1(String[] input) {
        int ans = 0, pos = 50;
        int degree, len, dir;
        
        for(String rtn: input) {
            degree = 0;
            len = rtn.length();
            dir = (rtn.charAt(0) == 'L') ? -1 : 1;

            for(int i = 1; i < len; i++) {
                degree = degree * 10 + (rtn.charAt(i) - '0');
            }

            pos = (pos + (degree * dir)) % 100;

            if(pos == 0) ans++;
        }

        return ans;
    }
    
    public int solvePart2(String[] input) {
		int ans = 0, pos = 50;
		int degree, len, dir, fullRtn;

		for (String rtn : input) {
			degree = 0;
			len = rtn.length();
			dir = (rtn.charAt(0) == 'L') ? -1 : 1;

			for (int i = 1; i < len; i++) {
				degree = degree * 10 + (rtn.charAt(i) - '0');
			}

			fullRtn = degree / 100;
			degree %= 100;
			degree *= dir;

			if (dir == 1 && pos + degree >= 100)
				fullRtn++;

			if (pos > 0 && pos + degree <= 0)
				fullRtn++;

			ans += fullRtn;
			pos = ((pos + degree) + 100) % 100;
		}

		return ans;
	}

    public static void main(String[] args) {
        Day1 obj = new Day1();

        File file = new File("../../assets/input.txt");
        List<String> list = new ArrayList<>();

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();                
                if(line.strip().length() == 0) continue;

                list.add(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        String[] input = list.toArray(new String[0]);
        System.out.println(obj.solvePart1(input));
        System.out.println(obj.solvePart2(input));
    }
}