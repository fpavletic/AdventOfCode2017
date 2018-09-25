package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InstructionProcessor {

    private String[] input;

    public InstructionProcessor(String[] input){
        this.input = input;
    }

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("([a-z]+) ([decin]{3}) ([-0-9]+) if ([a-z]+) ([=<>!]{1,2}) ([-0-9]+)");
    private static final Map<String, BiFunction<Integer, Integer, Boolean>> OPERATOR_TO_FUNCTION = new HashMap<>();
    static {
        OPERATOR_TO_FUNCTION.put(">",  (i1, i2 ) -> i1 > i2 );
        OPERATOR_TO_FUNCTION.put(">=", (i1, i2 ) -> i1 >= i2 );
        OPERATOR_TO_FUNCTION.put("<",  (i1, i2 ) -> i1 < i2 );
        OPERATOR_TO_FUNCTION.put("<=", (i1, i2 ) -> i1 <= i2 );
        OPERATOR_TO_FUNCTION.put("!=", (i1, i2 ) -> !i1.equals(i2) );
        OPERATOR_TO_FUNCTION.put("==", (i1, i2 ) -> i1.equals(i2) );
    }

    public int execute(){

        Map<String, Integer> registerToValue = new HashMap<>();

        //group 1 == target register, group 2 == instruction, group 3 == instruction argument, group 4 == condition register, group 5 == operator, group 6 == operator argument

        int highestEver = Integer.MIN_VALUE;

        for ( String line : input ){
            Matcher instructionMatcher = INSTRUCTION_PATTERN.matcher(line.trim());
            instructionMatcher.find();
            if ( OPERATOR_TO_FUNCTION.get(instructionMatcher.group(5)).apply(registerToValue.getOrDefault(instructionMatcher.group(4), 0), Integer.parseInt(instructionMatcher.group(6)))){
                registerToValue.merge(instructionMatcher.group(1), Integer.parseInt(instructionMatcher.group(3)) * (instructionMatcher.group(2).equals("inc") ? 1 : -1), (i1, i2) -> i1 + i2);
            }
            OptionalInt currentHighest = registerToValue.values().stream().mapToInt(i -> i).max();
            if ( currentHighest.isPresent() && currentHighest.getAsInt() > highestEver ) {
                highestEver = currentHighest.getAsInt();
            }

        }

        return highestEver;

    }

    public static void main(String[] args) throws FileNotFoundException{
        System.out.println(new InstructionProcessor(new Scanner(new File("./src/day08/inputOriginal.txt")).useDelimiter("\\Z").next().split("\r\n")).execute());
    }
}
