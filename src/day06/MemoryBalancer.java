package day06;

import java.lang.reflect.Array;
import java.util.*;

public class MemoryBalancer {

    private int[] memoryBanks;

    public MemoryBalancer(int[] memoryBanks){
        this.memoryBanks = memoryBanks;
    }

    public int countIterations (){

        Map<String, Integer> statesToAppearanceIteration = new HashMap<>();

        int iterations = 0;
        for ( int index = findFullest(); statesToAppearanceIteration.merge(Arrays.toString(memoryBanks), iterations, (i, j) -> i) == iterations; index = findFullest() ){
            int value = memoryBanks[findFullest()];
            for ( memoryBanks[index] = 0; value > 0; value--){
                memoryBanks[(++index)%memoryBanks.length]++;
            }
            iterations++;
        }

        return iterations - statesToAppearanceIteration.get(Arrays.toString(memoryBanks)).intValue();
    }

    private int findFullest(){
        int fullestBankIndex = 0;
        for ( int i = 0; i < memoryBanks.length; i++ ){
            if ( memoryBanks[fullestBankIndex] < memoryBanks[i] ){
                fullestBankIndex = i;
            }
        }
        return fullestBankIndex;
    }

    public static void main(String[] args){
        System.out.println(new MemoryBalancer(new int[]{10, 3, 15, 10, 5, 15, 5, 15, 9, 2, 5, 8, 5, 2, 3, 6}).countIterations());
    }
}
