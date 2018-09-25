package day14;

import day10.KnotTyer;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class DiskDefrag {

    String input;
    boolean[][] bits;

    public DiskDefrag(String input){
        this.input = input;
    }

    public int getUsed(){

        int used = 0;
        for ( int i = 0; i < 128; i++ ) {
            String hash = new KnotTyer(input + "-" + i).getDenseHash();
            used += new BigInteger(hash, 16).bitCount();
        }
        return used;
    }

    private void generateArray(){
        bits = new boolean[128][128];
        for ( int i = 0; i < 128; i++ ){
            String hash = new KnotTyer(input + "-" + i).getDenseHash();
            char[] row = String.format("%128s", new BigInteger(hash, 16).toString(2)).replace(' ', '0').toCharArray();
            for ( int j = 0; j < 128; j++ ){
                bits[i][j] = row[j] == '1';
            }
        }
    }

    public int getGroupCount(){

        generateArray();
        Set<String> alreadyVisited = new HashSet<>();
        int result = 0;
        for ( int i = 0; i < 128; i++ ){
            for ( int j = 0; j < 128; j++ ){
                if (expandGroup(i, j, alreadyVisited)){
                    result++;
                }
            }
        }
        return result;
    }

    private boolean expandGroup(int i, int j, Set<String> alreadyVisited){
        if ( i < 0 || i > 127 || j < 0 || j > 127 || !bits[i][j]) return false;
        if ( alreadyVisited.add(i+", "+j)){
            expandGroup(i + 1, j, alreadyVisited);
            expandGroup(i - 1, j, alreadyVisited);
            expandGroup(i, j + 1, alreadyVisited);
            expandGroup(i, j - 1, alreadyVisited);
            return true;
        }
        return false;
    }


    public static void main(String[] args){
        DiskDefrag dd = new DiskDefrag("hxtvlmkl");
//        DiskDefrag dd = new DiskDefrag("flqrgnkx");
        System.out.println(dd.getUsed());
        System.out.println(dd.getGroupCount());
    }
}
