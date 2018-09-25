package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PermutationPromenade {

    private String programs = "abcdefghijklmnop";
//    private String programs = "abcde";
    private File input;

    public PermutationPromenade(File input){
        this.input = input;
    }

    private static final Pattern command = Pattern.compile("([sxp])([0-9]+|[a-p]).?([0-9]+|[a-p])?");
    private static final Map<String, BiFunction<String, Integer[], String>> nameToFunction = new HashMap<>(){{
        put("s", (String s, Integer[] i) -> s.substring(s.length() - i[0]) + s.substring(0, s.length() - i[0]) );
        put("x", (String s, Integer[] i) -> {
            if ( i[0].equals(i[1])) return s;
            char[] tmp = s.toCharArray();
            tmp[i[0]] ^= tmp[i[1]];
            tmp[i[1]] ^= tmp[i[0]];
            tmp[i[0]] ^= tmp[i[1]];
            return new String(tmp);
        });
        put("p", (String s, Integer[] i) -> {
            s = s.replace((char)i[0].intValue(), 'z');
            s = s.replace((char)i[1].intValue(), (char)i[0].intValue());
            s = s.replace('z', (char)i[1].intValue());
            return s;
        } );
    }};

    public String permutate () throws FileNotFoundException{
        Map<Character, Character> pTransform = new HashMap<>();
        Scanner scanner = new Scanner(input).useDelimiter(",");
        while ( scanner.hasNext() ){
            Matcher m = command.matcher(scanner.next());
            if ( !m.find() ){
                System.err.println("ERROR UNABLE TO MATCH");
            }
            if ( m.group(1).equals("p")){

                char c1 = m.group(2).toCharArray()[0], c2 = m.group(3).toCharArray()[0], tmp = pTransform.getOrDefault(c2, c2);
                pTransform.put(c2, pTransform.getOrDefault(c1, c1));
                pTransform.put(c1, tmp);

                programs = nameToFunction.get(m.group(1)).apply(programs, new Integer[]{(int)c1, (int)c2});
            } else {
                programs = nameToFunction.get(m.group(1)).apply(programs, new Integer[]{Integer.parseInt(m.group(2)), m.group(3) != null ? Integer.parseInt(m.group(3)) : 0 });
            }
        }
        pTransform = pTransform.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        scanner.close();
        char[] programsArray = programs.toCharArray();
        for ( int i = 0; i < programs.length(); i++){
            programsArray[i] = pTransform.get(programsArray[i]);
        }
        return programs;
    }

    public static void main(String[] args) throws FileNotFoundException{
//        System.out.println(new PermutationPromenade(new Scanner(new File("./src/day16/input.txt")).useDelimiter(",")).permutate());
        long ts = System.currentTimeMillis();
        System.out.println(new PermutationPromenade(new File("./src/day16/inputO.txt")).permutate());
        System.out.println((System.currentTimeMillis() - ts) / 1000.0);
    }

}
