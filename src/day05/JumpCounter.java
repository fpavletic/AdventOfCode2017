package day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class JumpCounter {

    int[] jumps;

    public JumpCounter(String[] jumps){
        this.jumps = Arrays.stream(jumps).mapToInt(Integer::parseInt).toArray();
    }

    public int count(){

        int steps = 0;
        int index = 0;

        while (  index >= 0 && index < jumps.length ){
            int tmp = jumps[index];
            jumps[index] = jumps[index] > 2 ? jumps[index] - 1 : jumps[index] + 1;
            index += tmp;
            steps++;
        }

        return steps;

    }

    public static void main(String[] args) throws FileNotFoundException{
        long startTime = System.currentTimeMillis();
        System.out.println(new JumpCounter(new Scanner(new File("./src/day05/input.txt")).useDelimiter("\\Z").next().split("\r\n")).count());
        long endTime = System.currentTimeMillis();
        System.out.format("Time = %d", (endTime - startTime));
    }


}
