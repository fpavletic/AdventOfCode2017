package day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeBuilder {

    static Pattern programPattern = Pattern.compile("([a-zA-Z]+) \\(([0-9]+)\\)( -> ([ ,a-zA-Z]+))?");

    String[] programs;

    public TreeBuilder(String[] programs){
        this.programs = programs;
    }

    private int balanceTree(){

        Map<String, Node> nameToNode = new HashMap<>();
        for ( String program : programs ){

            Matcher programMatcher = programPattern.matcher(program);
            if ( !programMatcher.find() ){
                continue;
            }

            if ( !nameToNode.containsKey(programMatcher.group(1)) ){
                nameToNode.put(programMatcher.group(1), new Node(programMatcher.group(1), Integer.parseInt(programMatcher.group(2))));
            } else {
                nameToNode.get(programMatcher.group(1)).weight = Integer.parseInt(programMatcher.group(2));
            }

            if ( programMatcher.group(4) != null ){
                for ( String child : programMatcher.group(4).trim().replaceAll(", ", " ").split(" ")){
                    if ( !nameToNode.containsKey(child) ){
                        nameToNode.put(child, new Node(child, -1));
                    }
                    Node childNode = nameToNode.get(child);
                    nameToNode.get(programMatcher.group(1)).children.add(childNode);
                }
            }
        }

        Node lastRoot = null;
        Node root = nameToNode.get("ahnofa");
        Node newRoot;
        while ( (newRoot = findDifferent(root)) != null ){
            lastRoot = root;
            root = newRoot;
        }

        int regularSubTreeWeight = lastRoot.children.get(0) == root ? lastRoot.children.get(1).getSubtreeWeight() : lastRoot.children.get(0).getSubtreeWeight();
        root.weight = root.weight + regularSubTreeWeight - root.getSubtreeWeight();

        return root.weight;
    }


    private Node findDifferent ( Node parent ){
        Map<Integer, List<Node> > subtreeWeightToNode = new HashMap<>();

        for ( Node child : parent.children ){

            int subtreeWeight = child.getSubtreeWeight();

            if ( !subtreeWeightToNode.containsKey(subtreeWeight)){
                subtreeWeightToNode.put(subtreeWeight, new ArrayList<>() );
            }

            subtreeWeightToNode.get(subtreeWeight).add(child);
        }

        for ( List<Node> nodes : subtreeWeightToNode.values() ){

            if ( nodes.size() == 1 ){
                return nodes.get(0);
            }
        }

        return null;
    }

    private String getBottom(){
        Set<String> left = new HashSet<>();
        Set<String> right = new HashSet<>();
        for ( String program : programs ){
            Matcher programMatcher = programPattern.matcher(program);
            if (!programMatcher.find()){
                continue;
            }
            left.add(programMatcher.group(1));
            if ( programMatcher.group(4) != null ){
                right.addAll(Arrays.asList(programMatcher.group(4).trim().replaceAll(", ", " ").split(" ")));
            }
        }
        left.removeAll(right);
        return new ArrayList<String>(left).get(0);
    }

    public static void main(String[] args) throws FileNotFoundException{
        new TreeBuilder(new Scanner(new File("./src/day07/input.txt")).useDelimiter("\\Z").next().split("\r\n")).balanceTree();
    }

    private class Node {

        private String name;
        private int weight;
        private List<Node> children = new ArrayList<>();
        private int subtreeWeight = 0;

        public Node(String name, int weight){
            this.name = name;
            this.weight = weight;
        }

        int getSubtreeWeight(){

            int sum = weight;
            for ( Node child : children ){
                sum += child.getSubtreeWeight();
            }
            return sum;

        }
    }

}
