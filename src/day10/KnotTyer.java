package day10;

import java.lang.reflect.Array;
import java.util.*;

public class KnotTyer {

    private List<Integer> values;
    private List<Integer> lengths;

    public KnotTyer(List<Integer> lengths){
        this();
        this.lengths = lengths;
    }

    public KnotTyer(String lengths){
        this();
        this.lengths = new ArrayList<>();
        for ( char c : lengths.toCharArray() ){
            this.lengths.add((int)c);
        }
        this.lengths.add(17);
        this.lengths.add(31);
        this.lengths.add(73);
        this.lengths.add(47);
        this.lengths.add(23);
    }

    private KnotTyer(){
        values = new ArrayList<>();
        for ( int i = 0; i < 256; i++){
            values.add(i);
        }
    }

    public int getFirstTwo(){
        int index = 0;
        int skip = 0;

        for ( int length : lengths ){

            List<Integer> sublist = sublist(index, (index + length) % values.size());
            Collections.reverse(sublist);

            for ( int i = 0; i < sublist.size(); i++ ){
                values.set((index + i) % values.size(), sublist.get(i));
            }
            index += length + skip++;
            index %= values.size();
        }

        return values.get(0) * values.get(1);
    }

    public String getDenseHash(){

        int index = 0;
        int skip = 0;
        for ( int count = 0; count < 64; count++ ){
            for ( int length : lengths ){

                List<Integer> sublist = sublist(index, (index + length) % values.size());
                Collections.reverse(sublist);

                for ( int i = 0; i < sublist.size(); i++ ){
                    values.set((index + i) % values.size(), sublist.get(i));
                }
                index += length + skip++;
                index %= values.size();
            }
        }

        int[] denseHash = new int[16];
        for ( int i = 0; i < 16; i++ ){
            for ( int j = 0; j < 16; j++ ){
                denseHash[i] ^= values.get(16 * i + j );
            }
        }

        StringBuilder hexadecimalDenseHash = new StringBuilder();
        for ( int i = 0; i < 16; i++ ){
            String hexadecimal = Integer.toHexString(denseHash[i]);
            hexadecimalDenseHash.append(hexadecimal.length() == 1 ? "0" + hexadecimal : hexadecimal);
        }

        return hexadecimalDenseHash.toString();
    }

    private List<Integer> sublist (int fromIndex, int toIndex){
        if ( fromIndex <= toIndex ){
            return new ArrayList<>(values.subList(fromIndex, toIndex ));
        }
        List<Integer> sublist = new ArrayList<>(values.subList(fromIndex, values.size()));
        sublist.addAll(values.subList(0, toIndex));
        return sublist;
    }

    public static void main(String[] args){
        System.out.println(new KnotTyer(Arrays.asList(new Integer[]{165, 1, 255, 31, 87, 52, 24, 113, 0, 91, 148, 254, 158, 2, 73, 153})).getFirstTwo());
        System.out.println(new KnotTyer("165,1,255,31,87,52,24,113,0,91,148,254,158,2,73,153").getDenseHash());
//        System.out.println(new KnotTyer("").getDenseHash());
    }
}
