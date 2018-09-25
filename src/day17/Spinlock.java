package day17;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Spinlock {

    private static final int step = 324;
    private List<Integer> current = new ArrayList<>(){{add(0);}};

    public int getAfter(int num) throws FileNotFoundException{
        int index = 0;
        for ( int i = 1; i <= num; i++ ){
            index = ( index + step ) % current.size() + 1;
            current.add(index, i);
        }
        return current.get(( index + 1 ) % current.size());
    }

    public long getAfterZero(long iterations){

        long index = 0;
        long afterZero = 0;
        for ( long i = 1; i <= iterations; i++ ){
            index = ( index + step ) % i + 1;
            if ( index == 1 ){
                afterZero = i;
            }
        }
        return afterZero;
    }


    public static void main(String[] args) throws FileNotFoundException{
        System.out.println(new Spinlock().getAfter(2017));
        long ts = System.currentTimeMillis();
        System.out.println(new Spinlock().getAfterZero(50000000));
        System.out.println(System.currentTimeMillis() - ts);
    }


}
