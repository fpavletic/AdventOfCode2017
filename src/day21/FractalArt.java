package day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FractalArt {



    private Map<String, String> oldToNew = new HashMap<>();

    public FractalArt(Path source) throws IOException{

        for ( String line : Files.readAllLines(source) ) {
            String[] split = line.split(" => ");
            String n = split[1];
            for ( String o : getVariations(split[0])){
                oldToNew.put(o, n);
            }
        }
    }

    private Set<String> getVariations(String rule){
        Set<String> variations = new HashSet<>();
        for ( int i = 0; i < 4; i++ ){
            variations.add(rule);
            String[] rows = rule.split("/");
            variations.add(flipHorizontal(rows));
            variations.add(flipVertical(rows));
            rule = rotate(rows);
        }
        return variations;
    }

    private String rotate(String[] rows){
        int n = rows.length;
        char[][] result = new char[n][n];
        for ( int i = 0; i < n; i++ ){
            for ( int j = 0; j < n; j++ ){
                result[i][j] = rows[n - j - 1].charAt(i);
            }
        }
        return Arrays.stream(result).map(String::new).collect(Collectors.joining("/"));
    }

    private String flipVertical(String[] rows){
        int n = rows.length;
        String[] result = new String[n];
        for ( int i = 0; i < n; i++ ){
            result[i] = new StringBuilder(rows[i]).reverse().toString();
        }
        return Arrays.stream(result).collect(Collectors.joining("/"));
    }

    private String flipHorizontal(String[] rows){ //NOT TESTED
        int n = rows.length;
        String[] result = new String[n];
        for ( int i = 0; i < n; i++ ){
            result[i] = rows[n - i -1];
        }
        return Arrays.stream(result).collect(Collectors.joining("/"));
    }

    public long run(int iterations){
        String pattern = ".#./..#/###";
        for ( int i = 0; i < iterations; i++ ){
            List<String> squares = breakPattern(pattern);
            for ( int j = 0; j < squares.size(); j++ ){
                squares.set(j, oldToNew.get(squares.get(j)));
            }
            pattern = buildPattern(squares);
        }
        return pattern.chars().filter(i -> (char)i == '#').count();
    }

    private String buildPattern(List<String> squares){
        int n = (int)Math.sqrt(squares.size());
        int m = squares.get(0).split("/").length;
        StringBuilder[] rows = new StringBuilder[n * m];
        for ( int i = 0; i < squares.size(); i++ ){
            String[] squareRows = squares.get(i).split("/");
            for ( int j = 0; j < squareRows.length; j++ ){
                int index = i / n * m + j;
                if ( rows[index] == null ){
                    rows[index] = new StringBuilder();
                }
                rows[index].append(squareRows[j]);
            }
        }
        return Arrays.stream(rows).map(sb -> sb.toString()).collect(Collectors.joining("/"));
    }

    private List<String> breakPattern ( String pattern ){
        String[] rows = pattern.split("/");
        int n = rows.length;
        int m = n % 2 == 0 ? 2 : 3;
        List<String> result = new ArrayList<>();
        for ( int i = 0; i < n; i+=m ) {
            for ( int j = 0; j < n; j+=m ) {
                StringBuilder current = new StringBuilder();
                for ( int k = 0; k < m; k++ ) {
                    current.append(rows[i + k].substring(j, j + m));
                    if ( k != m - 1 ) current.append('/');
                }
                result.add(current.toString());
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException{
        System.out.println(new FractalArt(Paths.get("./src/day21/inputO.txt")).run(18));
    }
}
