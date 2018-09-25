package day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ChecksumCalculator {

    List<int[]> rows;

    public ChecksumCalculator(List<int[]> rows) {
        this.rows = rows;
    }

    public int calculateUsingMinAndMax(){

        int checksum = 0;
        for ( int[] row : rows ){
            checksum += Arrays.stream(row).max().getAsInt() - Arrays.stream(row).min().getAsInt();
        }
        return checksum;

    }

    public int calculateUsingEvenDivision(){

        int checksum = 0;
        for ( int[] row : rows ){
            for ( int i = 0; i < row.length; i++ ){
                final int current = row[i];
                List<Integer> matches = Arrays.stream(row).filter(n -> n != current && n % current == 0).boxed().collect(Collectors.toList());
                if (matches.size() != 0 ){
                    checksum += matches.get(0) / current;
                    break;
                }
            }
        }
        return checksum;

    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner input = new Scanner(new File("./src/day02/input.txt"));
        List<int[]> rows = new ArrayList<>();
        while ( input.hasNextLine() ){
            rows.add(Arrays.stream(input.nextLine().split("\t")).mapToInt(Integer::parseInt).toArray());
        }

        System.out.println(new ChecksumCalculator(rows).calculateUsingMinAndMax());
        System.out.println(new ChecksumCalculator(rows).calculateUsingEvenDivision());

    }

}
