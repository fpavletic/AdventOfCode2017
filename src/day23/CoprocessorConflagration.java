package day23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoprocessorConflagration {

    private final List<String> commands;
    private int index;
    private final Map<String, Long> registerToValue = new HashMap<>();

    public CoprocessorConflagration(Path input) throws IOException{
        commands = Files.readAllLines(input);
        registerToValue.put("a", 1l);
    }

    public int countMul(){
        int mulCount = 0;

        while ( indexInBounds() ){

            String[] commandSplit = commands.get(index).split(" ");
            switch ( commandSplit[0] ){
                case "set":
                    registerToValue.put(commandSplit[1], getValue(commandSplit[2]));
                    break;
                case "sub":
                    registerToValue.merge(commandSplit[1], -getValue(commandSplit[2]), (i1, i2) -> i1 + i2 );
                    break;
                case "mul":
                    mulCount++;
                    if ( registerToValue.get(commandSplit[1]) != null ){
                        registerToValue.merge(commandSplit[1], getValue(commandSplit[2]), (i1, i2) -> i1 * i2 );
                    }
                    break;
                case "jnz":
                    if ( getValue(commandSplit[1]) != 0 ){
                        index+= getValue(commandSplit[2]) - 1;
                    }
                    break;
            }
            System.out.println(index++);

        }

        return mulCount;
    }

    private boolean indexInBounds(){

        return index > -1 && index < commands.size();

    }

    private Long getValue(String input){
        if ( Character.isLetter(input.charAt(0)) ){
            return registerToValue.getOrDefault(input, 0l);
        }
        return Long.parseLong(input);
    }

    public static void main(String[] args) throws IOException{
        System.out.println(new CoprocessorConflagration(Paths.get("./src/day23/inputO.txt")).countMul());
    }
}
