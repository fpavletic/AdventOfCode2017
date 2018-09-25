package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DigitalPlumber {

    private Map<String, List<String>> programToDirectConnections = new HashMap<>();

    public DigitalPlumber(String[] input){
        Arrays.stream(input).forEach(s -> {
            String[] split = s.split("<->");
            programToDirectConnections.put(split[0].trim(), Arrays.asList(split[1].trim().split(", ")));
        });
    }

    public int getGroupCount(){

        List<String> keys = new ArrayList<>(programToDirectConnections.keySet());
        int count = 0;
        while ( keys.size() != 0 ){
            count++;
            keys.removeAll(getGroup(keys.get(0)));
        }
        return count;
    }

    public Set<String> getGroup(String programID){

        Set<String> allConnections;
        allConnections = new HashSet<>();
        allConnections.add(programID);
        recursiveFill(programToDirectConnections.get(programID), allConnections);
        return allConnections;

    }

    private void recursiveFill(List<String> programs, Set<String> allConnections){
        for ( String program : programs ){
            if ( allConnections.add(program) ){
                recursiveFill(programToDirectConnections.get(program), allConnections);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException{
        DigitalPlumber digP = new DigitalPlumber(new Scanner(new File("./src/day12/input.txt")).useDelimiter("\\Z").next().split("\r\n"));
        System.out.println(digP.getGroup("0").size());
        System.out.println(digP.getGroupCount());
    }

}
