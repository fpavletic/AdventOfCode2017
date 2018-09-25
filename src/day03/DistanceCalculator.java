package day03;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DistanceCalculator {

    int num;
    Map<Point, Integer> coordinatesToValue = new HashMap<>();

    public DistanceCalculator(int num) {
        this.num = num;
    }

    public int calculateDistance(){
        int level = 1, tmp = num -1;

        while ( tmp > level * 8 ){
            tmp -= level * 8;
            level++;
        }

        return level + ( Math.abs(level - tmp % (level * 2) ));

    }

    public int firstLarger() {

        coordinatesToValue.put(new Point(0, 0), 1);

        int lastLevel = 0;

        int x = 0;
        int y = 0;
        int dir = 3;

        while ( true ){

            switch ( dir ){
                case 0: //up
                    y++;
                    if ( y == lastLevel )
                        dir = 1;
                    break;
                case 1: //left
                    x--;
                    if ( x == -lastLevel )
                        dir = 2;
                    break;
                case 2: //down
                    y--;
                    if ( y == -lastLevel )
                        dir = 3;
                    break;
                case 3: //right
                    x++;
                    if ( x == lastLevel +1 ) {
                        dir = 0;
                        lastLevel++;
                    }
                    break;
            }

            System.out.format("(%d, %d)\n", x, y);
            int value = sumNeighbourValues(x, y);
            if ( value > num ){
                return value;
            }
            coordinatesToValue.put(new Point(x, y), value);

        }

    }

    private Integer sumNeighbourValues(int i, int j){
        int sum = 0;
        //left
        sum += coordinatesToValue.getOrDefault(new Point(i -1, j -1), 0);
        sum += coordinatesToValue.getOrDefault(new Point(i -1, j), 0);
        sum += coordinatesToValue.getOrDefault(new Point(i -1, j +1), 0);
        //centre
        sum += coordinatesToValue.getOrDefault(new Point(i, j -1), 0);
        sum += coordinatesToValue.getOrDefault(new Point(i, j +1), 0);
        //right
        sum += coordinatesToValue.getOrDefault(new Point(i +1, j -1), 0);
        sum += coordinatesToValue.getOrDefault(new Point(i +1, j), 0);
        sum += coordinatesToValue.getOrDefault(new Point(i +1, j +1), 0);
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(new DistanceCalculator(368078).calculateDistance());
    }

}
