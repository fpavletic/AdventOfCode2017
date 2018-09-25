package day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ASeriesOfTubes {

    private static final int VERTICAL = 124;
    private static final int HORIZONTAL = 45;
    private static final int CROSS = 43;
    private static final int WHITESPACE = 32;

    private int x = -1, y = 0;
    private int[][] alg;
    
    private StringBuilder output = new StringBuilder();
    private int steps = 1;

    public ASeriesOfTubes (Path input) throws IOException{
        List<int[]> tmp = new ArrayList<>();
        for( String line : Files.readAllLines(input)){
            if ( x == -1 ) x = line.indexOf((char)VERTICAL);
            tmp.add(line.chars().toArray());
        }
        alg = tmp.stream().toArray(int[][]::new);
    }

    public void run(){
        int direction = 0; //0 -> down, 1 -> left, 2 -> up, 3 -> right
        while ( direction != -1 ){
            do {
                updatePosition(direction);
                addLetter();
            } while ( alg[y][x] != CROSS && alg[y][x] != WHITESPACE);
            direction = updateDirection(direction);
        }
    }

    private int updateDirection(int currentDirection){
        //go left
        if ( currentDirection != 3 && x != 0 && ( alg[y][x - 1] == HORIZONTAL || Character.isLetter((char)alg[y][x - 1]))) {
            return 1;
        }
        //go right
        if ( currentDirection != 1 && x < alg[y].length - 1 && ( alg[y][x + 1] == HORIZONTAL || Character.isLetter((char)alg[y][x + 1]))){
            return 3;
        }
        //go up
        if ( currentDirection != 0 && y != 0 && ( alg[y - 1][x] == VERTICAL || Character.isLetter((char)alg[y - 1][x]))){
            return 2;
        }
        //go down
        if ( currentDirection != 2 && y < alg.length - 1 &&( alg[y + 1][x] == VERTICAL || Character.isLetter((char)alg[y + 1][x]))){
            return 0;
        }
        //end
        return -1;
    }

    private void addLetter(){
        if ( alg[y][x] > 64 && alg[y][x] < 93 ){
            output.append((char) alg[y][x]);
        }
    }

    private void updatePosition(int direction){
        switch ( direction ){
            case 0:
                y++;
                break;
            case 1:
                x--;
                break;
            case 2:
                y--;
                break;
            case 3:
                x++;
                break;
        }
        steps++;
    }

    public String getPositionMarkers(){
        return output.toString();
    }

    public int getSteps(){
        return steps;
    }

    public static void main(String[] args) throws IOException{
        ASeriesOfTubes asot = new ASeriesOfTubes(Paths.get("./src/day19/input.txt"));
        asot.run();
        System.out.println(asot.getPositionMarkers());
        System.out.println(asot.getSteps());
    }
}
