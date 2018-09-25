package day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Duet {

    private final List<String> commands;
    private int index[] = new int[]{0, 0};
    private int lastIndex[] = new int[]{-1, -1};
    private long sentCount[] = new long[]{0, 0};
    private final Map<String, Long>[] registerToValue = new HashMap[]{new HashMap<>(), new HashMap<>(){{put("p", 1l);}}};
    private final Queue<Long>[] queue = new Queue[]{new LinkedList(), new LinkedList()};


    public Duet() throws IOException{
        commands = Files.readAllLines(Paths.get("./src/day18/inputO.txt"));
    }

    public Long getFirstRecovered (){

        while ( !Arrays.equals(index, lastIndex) && indexInBounds()){

            lastIndex = Arrays.copyOf(index, 2);

            for ( int id = 0; id < 2; id++ ){
                String[] commandSplit = commands.get(index[id]).split(" ");
                switch ( commandSplit[0] ){
                    case "snd":
                        queue[1-id].add(getValue(commandSplit[1], id));
                        sentCount[id]++;
                        break;
                    case "set":
                        registerToValue[id].put(commandSplit[1], getValue(commandSplit[2], id));
                        break;
                    case "add":
                        registerToValue[id].merge(commandSplit[1], getValue(commandSplit[2], id), (i1, i2) -> i1 + i2 );
                        break;
                    case "mul":
                        if ( registerToValue[id].get(commandSplit[1]) != null ){
                            registerToValue[id].merge(commandSplit[1], getValue(commandSplit[2], id), (i1, i2) -> i1 * i2 );
                        }
                        break;
                    case "mod":
                        if ( registerToValue[id].get(commandSplit[1]) != null ) {
                            registerToValue[id].merge(commandSplit[1], getValue(commandSplit[2], id), (i1, i2) -> i1 % i2);
                        }
                        break;
                    case "rcv":
                        if ( queue[id].peek() == null ) {
                            index[id]--;
                        } else {
                            registerToValue[id].put(commandSplit[1], queue[id].remove());
                        }
                        break;
                    case "jgz":
                        if ( getValue(commandSplit[1], id) > 0 ){
                            index[id]+= getValue(commandSplit[2], id) - 1;
                        }
                        break;
                }
                index[id]++;
            }
        }
        return sentCount[1];

    }

    private boolean indexInBounds(){

        return index[0] > -1 && index[0] < commands.size() && index[1] > -1 && index[1] < commands.size();

    }

    private Long getValue(String input, int id){
        if ( Character.isLetter(input.charAt(0)) ){
            return registerToValue[id].getOrDefault(input, 0l);
        }
        return Long.parseLong(input);
    }

    public static void main(String[] args) throws IOException{
        System.out.println(new Duet().getFirstRecovered());
    }
}
