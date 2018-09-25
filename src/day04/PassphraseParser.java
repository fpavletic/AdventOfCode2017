package day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class PassphraseParser {

    List<String> passphrases;

    public PassphraseParser(List<String> passphrases){
        this.passphrases = passphrases;
    }

    public int countValid(){
        int count = 0;
        for ( String passphrase : passphrases ){
            Set<String> usedWords = new HashSet<>();
            boolean isValid = true;
            for ( String word : passphrase.split(" ")){
                if ( !usedWords.add(word) ){
                    isValid = false;
                    break;
                }
            }
            if ( isValid ){
                count++;
            }
        }
        return count;
    }

    public int countValidConsideringAnagrams (){

        int count = 0;
        for ( String passphrase : passphrases ){

            Set<List<Character>> usedWords = new HashSet<>();
            boolean isValid = true;

            for ( String word : passphrase.split(" ")){
                List<Character> wordAsList = new ArrayList<>();
                for ( char c : word.toCharArray() ){
                    wordAsList.add(c);
                }
                wordAsList.sort((c1, c2) -> Character.compare(c1, c2));

                if ( !usedWords.add(wordAsList) ){
                    isValid = false;
                    break;
                }
            }
            if ( isValid ){
                count++;
            }
        }
        return count;

    }

    public static void main(String[] args) throws FileNotFoundException{

        System.out.println(new PassphraseParser(Arrays.asList(new Scanner(new File("./src/day04/passphrases.txt")).useDelimiter("\\Z").next().split("\r\n"))).countValid());
        System.out.println(String.format("%n!"));


    }
}
