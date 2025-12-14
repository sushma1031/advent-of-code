package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day11 {
    final String OUT = "out";
    final String YOU = "you";
    final String SERVER = "svr";
    final String DAC = "dac";
    final String FFT = "fft";

    public long solvePart1(Map<String, String[]> map) {
        Map<String, Long> memo = new HashMap<>(map.keySet().size());
        return dp(YOU, map, memo);
    }

    public long solvePart2(Map<String, String[]> map) {
        Map<String, Long> memo = new HashMap<>(map.keySet().size());
        return dp2(SERVER, false, false, map, memo);
    }

    private long dp(String dev, Map<String, String[]> map, Map<String, Long> memo) {
        String[] outputs = map.get(dev);
        if (outputs.length == 1 && outputs[0].equals(OUT))
            return 1L;

        long cached = memo.getOrDefault(dev, -1L);
        if (cached != -1)
            return cached;

        long ans = 0, outAns;
        for (String o : outputs) {
            outAns = dp(o, map, memo);
            memo.put(o, outAns);
            ans += outAns;
        }
        return ans;
    }

    private long dp2(String dev, boolean seenDAC, boolean seenFFT, Map<String, String[]> map,
            Map<String, Long> memo) {
        if (dev.equals(OUT)) {
            return (seenDAC && seenFFT) ? 1 : 0;
        }

        if (dev.equals(DAC))
            seenDAC = true;
        if (dev.equals(FFT))
            seenFFT = true;

        String key = dev + "|" + (seenDAC ? 1 : 0) + "|" + (seenFFT ? 1 : 0);

        long cached = memo.getOrDefault(key, -1L);
        if (cached != -1)
            return cached;

        String[] outputs = map.get(dev);
        long ans = 0;

        for (String o : outputs) {
            ans += dp2(o, seenDAC, seenFFT, map, memo);
        }
        memo.put(key, ans);
        return ans;
    }

    public static void main(String[] args) {
        Day11 obj = new Day11();

        File file = new File("input.txt");
        Map<String, String[]> map = new HashMap<>();

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();
                if (line.strip().length() == 0)
                    continue;

                String[] components = line.split(":");
                String[] outputs = components[1].trim().split(" ");
                for (int i = 0; i < outputs.length; i++)
                    outputs[i] = outputs[i].trim();

                map.put(components[0], outputs);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        long ans1 = obj.solvePart1(map);
        long ans2 = obj.solvePart2(map);

        System.out.println(ans1);
        System.out.println(ans2);
    }
}