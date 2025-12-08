package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class UnionFind {
    int[] parents;
    int distinct;

    UnionFind(int n) {
        parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        distinct = n;
    }

    public int find(int x) {
        int tmp = x;
        while (x != parents[x]) {
            parents[x] = parents[parents[x]];
            x = parents[x];
        }
        parents[tmp] = x;
        return x;
    }

    public void union(int i, int j) {
        int iParent = find(i);
        int jParent = find(j);

        if (iParent != jParent) distinct--;

        if (iParent < jParent) {
            parents[jParent] = iParent;
        } else {
            parents[iParent] = jParent;
        }
    }
}

public class Day8 {
    public long solvePart1(int[][] input, int limit) {
        Map<Long, List<int[]>> map = new TreeMap<>(); // distance -> (point i, point j)
        UnionFind uf = new UnionFind(input.length);

        long dist;
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                dist = Math.round(Math.pow(input[i][0] - input[j][0], 2)
                        + Math.pow(input[i][1] - input[j][1], 2)
                        + Math.pow(input[i][2] - input[j][2], 2));
                
                if(!map.containsKey(dist)) map.put(dist, new ArrayList<>());
                map.get(dist).add(new int[]{i, j});
            }
        }
        int conn = 0; 
        for(List<int[]> points: map.values()) {
            for(int[] p: points) {
                uf.union(p[0], p[1]);
                conn++;
                if(conn == limit) break;
            }
            if(conn == limit) break;
        }

        int[] freq = new int[input.length];

        for(int i=0; i<uf.parents.length; i++) freq[uf.find(i)]++;
        Arrays.sort(freq);

        return freq[freq.length - 1] * freq[freq.length - 2] * freq[freq.length - 3];
    }

    public long solvePart2(int[][] input) {
        Map<Long, List<int[]>> map = new TreeMap<>(); // distance -> (point i, point j)
        UnionFind uf = new UnionFind(input.length);

        long dist;
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                dist = Math.round(Math.pow(input[i][0] - input[j][0], 2)
                        + Math.pow(input[i][1] - input[j][1], 2)
                        + Math.pow(input[i][2] - input[j][2], 2));
                
                if(!map.containsKey(dist)) map.put(dist, new ArrayList<>());
                map.get(dist).add(new int[]{i, j});
            }
        }

        boolean breakLoop = false;
        long prod = 0;
        for(List<int[]> points: map.values()) {
            for(int[] p: points) {
                uf.union(p[0], p[1]);
                if(uf.distinct == 1) {
                    prod = (long)input[p[0]][0] * input[p[1]][0];
                    breakLoop = true;
                    break;
                }
            }
            if(breakLoop) break;
        }

        return prod;
    }

    public static void main(String[] args) {
        Day8 obj = new Day8();

        File file = new File("input.txt");
        List<String> lines = new ArrayList<>();

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();
                if (line.strip().length() == 0)
                    continue;

                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int n = lines.size();
        int[][] input = new int[n][3];

        for (int i = 0; i < n; i++) {
            String[] nums = lines.get(i).split(",");

            for (int j = 0; j < 3; j++)
                input[i][j] = Integer.parseInt(nums[j]);
        }

        long ans1 = obj.solvePart1(input, 1000);
        long ans2 = obj.solvePart2(input);

        System.out.println(ans1);
        System.out.println(ans2);
    }
}