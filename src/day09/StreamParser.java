package day09;

import day08.InstructionProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StreamParser {

    char[] input;

    public StreamParser(char[] input){
        this.input = input;
    }

    public long calculateScore (){
        int depth = 0;
        long score = 0;
        boolean isInTrashMode = false;
        for ( int index = 0; index < input.length; index++ ){

            switch ( input[index] ){
                case '{':
                    if ( isInTrashMode ) break;
                    score+=++depth;
                    break;
                case '}':
                    if ( isInTrashMode ) break;
                    depth--;
                    break;
                case '!':
                    index++;
                    break;
                case '<':
                    if ( isInTrashMode ) break;
                    isInTrashMode = true;
                    break;
                case '>':
                    isInTrashMode = false;
                    break;
            }
        }
        return score;
    }

    public static void main(String[] args) throws FileNotFoundException{
        
        String input = new Scanner(new File("./src/day09/input.txt")).useDelimiter("\\Z").next();
        input = input.replaceAll("!.", "");
        System.out.println(input.length() - input.replaceAll("<.*?>", "<>").length());

        System.out.println(new StreamParser(input.toCharArray()).calculateScore());
    }
}
