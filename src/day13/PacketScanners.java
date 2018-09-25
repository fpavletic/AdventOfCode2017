package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PacketScanners {

    Map<Integer, Integer> layerToDepth = new HashMap<>();

    public PacketScanners(String[] input){
        for ( String line : input ){
            String[] lineSplit = line.split(": ");
            layerToDepth.put(Integer.parseInt(lineSplit[0]), Integer.parseInt(lineSplit[1]));
        }
    }

    public int getEscapeDelay() {
        for ( int delay = 0; true; delay++ ){
            if ( getThreatLevel(delay) == null ){
                return delay;
            }
        }
    }

    public Integer getThreatLevel (int start ){
        Integer threatLevel = null;
        for ( Integer layer : layerToDepth.keySet()){
            if ( getCurrentDepth(layer + start, layerToDepth.get(layer)) == 0){
                if ( threatLevel == null ){
                    threatLevel = layer * layerToDepth.get(layer);
                } else {
                    threatLevel += layer * layerToDepth.get(layer);
                }
            }
        }
        return threatLevel;
    }

    private int getCurrentDepth(Integer time, Integer maxDepth){
        int tmp = time % ( 2 * ( maxDepth - 1 ));
        if ( tmp >= maxDepth ){
            return 2 * ( maxDepth - 1 ) - tmp;
        } else {
            return tmp;
        }
    }

    public static void main(String[] args) throws FileNotFoundException{
        PacketScanners ps = new PacketScanners(new Scanner(new File("./src/day13/inputO.txt")).useDelimiter("\\Z").next().split("\r\n"));
        System.out.println(ps.getThreatLevel(0));
        System.out.println(ps.getEscapeDelay());
    }
}
