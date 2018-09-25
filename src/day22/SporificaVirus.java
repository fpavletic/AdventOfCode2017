package day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SporificaVirus {

    private Map<Integer, Consumer<Point>> directionToMove = new HashMap<>(){{
        put(0, p -> p.y--);
        put(1, p -> p.x++);
        put(2, p -> p.y++);
        put(3, p -> p.x--);
    }};

    private int len = -1;

    private Map<Point, Integer> infected = new HashMap<>();

    public SporificaVirus(Path input) throws IOException{

        final int[] index = new int[]{0};
        List<String> lines = Files.readAllLines(input);
        len = lines.get(0).length();
        lines.stream().forEach(s -> s.chars().forEach(i -> { if ( i == 35 ) infected.put(new Point(index[0] % len, index[0] / len), 2); index[0]++; }));

    }

    public int run(int iterations){
        int infectCount = 0;
        int direction = 0;
        Point position = new Point(len /2, len /2 );
        for ( int i = 0; i < iterations; i++ ){
            direction = getDirection(direction, infected.getOrDefault(position, 0));
            infected.merge(new Point(position), 1, ( i1, i2 ) -> ( i1 + i2 ) % 4 );
            if ( infected.getOrDefault(position, 0).equals(2)) {
                infectCount++;
            }
            directionToMove.get(direction)
                    .accept(position);
        }
        return infectCount;
    }

    private int getDirection ( int direction, int infectionLevel ){
        switch ( infectionLevel ){
            case 0: // clean
                return direction > 0 ? direction - 1 : 3;
            case 1: // weakened
                return direction;
            case 2: // infected
                return ++direction % 4;
            case 3: // flagged
                return direction > 1 ? direction - 2 : direction + 2;

        }
        return -1;
    }


    public static void main(String[] args) throws IOException{
        System.out.println(new SporificaVirus(Paths.get("./src/day22/inputO.txt")).run(10000000));
    }


    private static class Point {
        int x, y;

        public Point(Point p){
            this(p.x, p.y);
        }

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o){
            if ( o == null || getClass() != o.getClass() ) return false;

            Point point = (Point) o;

            if ( x != point.x ) return false;
            return y == point.y;
        }

        @Override
        public int hashCode(){
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString(){
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
