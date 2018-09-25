package day15;

public class DuelingGenerators {

    private long genOne = 591, genTwo = 393;
    private static final long genOneMult = 16807, genTwoMult = 48271, primaryDivider = 2147483647;

    int getCount ( int iterations, boolean iterate ){
        int count = 0;
        for ( int i = 0; i < iterations; i++){

            genOne = getGenValue(genOne, genOneMult, iterate, 4);
            genTwo = getGenValue(genTwo, genTwoMult, iterate, 8);

            if ( (genOne & 0xFFFF) == (genTwo & 0xFFFF) ){
                count++;
            }

        }
        return count;

    }

    private static long getGenValue ( long current, long mult, boolean iterate, int secondaryDivider ){
        do {
            current = (current * mult) % primaryDivider;
        } while ( iterate && (current & secondaryDivider - 1) != 0 );
        return current;
    }

    public static void main(String[] args){
        DuelingGenerators dg = new DuelingGenerators();
        long timeS = System.currentTimeMillis();
        System.out.println(dg.getCount(40000000, false));
        System.out.println(System.currentTimeMillis() - timeS);
        dg = new DuelingGenerators();
        timeS = System.currentTimeMillis();
        System.out.println(dg.getCount(5000000, true));
        System.out.println(System.currentTimeMillis() - timeS);
    }

}
