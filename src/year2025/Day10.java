package year2025;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class Node {
    char[] state;
    int steps, btnsCount;
    boolean[] visited;

    Node(char[] state, int steps, boolean[] visited) {
        this.state = state;
        this.steps = steps;
        this.btnsCount = this.state.length;
        this.visited = visited;
    }
}

public class Day10 {
    public long solvePart1(char[][] lights, int[][][] buttons) {
        int ans = 0, localAns;
        int N = lights.length;
        int lenLights, lenBtns;
        
        for(int c=0; c<N; c++) {
            lenLights = lights[c].length;
            lenBtns = buttons[c].length;
            localAns = lenBtns;
            char[] INITIAL_STATE = new char[lenLights];
            Arrays.fill(INITIAL_STATE, '.');
            
            Queue<Node> queue = new ArrayDeque<>();

            for(int i=0; i<buttons[c].length; i++) {
                Node node = new Node(Arrays.copyOf(INITIAL_STATE, lenLights), 1, new boolean[lenBtns]);
                for(int num: buttons[c][i]) {
                    node.state[num] = (node.state[num] == '#' ? '.' : '#');
                }
                node.visited[i] = true;

                queue.offer(node);
            }

            while(!queue.isEmpty()) {
                Node n = queue.poll();

                if(Arrays.equals(n.state, lights[c])) {
                    localAns = n.steps;
                    break;
                }

                for(int i=0; i<buttons[c].length; i++) {
                    if(n.visited[i]) continue;
                    Node node = new Node(Arrays.copyOf(n.state, lenLights), n.steps + 1, Arrays.copyOf(n.visited, lenBtns));
                    for(int num: buttons[c][i]) {
                        node.state[num] = (node.state[num] == '#' ? '.' : '#');
                    }
                    node.visited[i] = true;

                    queue.offer(node);
                }
            }

            ans += localAns;
        }

        return ans;
    }

    public long solvePart2() {
        return 0;
    }

    public static void main(String[] args) {
        Day10 obj = new Day10();

        File file = new File("input.txt");
        List<char[]> lightsList = new ArrayList<>();
        List<String[]> buttonsList = new ArrayList<>();

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String line = fsc.nextLine();                
                if(line.strip().length() == 0) continue;

                String[] components = line.split(" ");

                lightsList.add(components[0].substring(1, components[0].length() - 1).toCharArray());
                buttonsList.add(Arrays.copyOfRange(components, 1, components.length - 1));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        char[][] lights = lightsList.toArray(new char[0][]);
        int n = buttonsList.size();
        int[][][] buttons = new int[n][][];

        for(int i=0; i<n; i++) {
            String[] btns = buttonsList.get(i);
            buttons[i] = new int[btns.length][];

            for(int j=0; j<btns.length; j++) {
                String[] l = btns[j].substring(1, btns[j].length() - 1).split(",");
                buttons[i][j] = new int[l.length];

                for(int k=0; k<l.length; k++) {
                    buttons[i][j][k] = Integer.parseInt(l[k]);
                }
            }
        }
        
        long ans1 = obj.solvePart1(lights, buttons);
        long ans2 = obj.solvePart2();

        System.out.println(ans1);
        System.out.println(ans2);
    }
}