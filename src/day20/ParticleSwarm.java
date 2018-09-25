package day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParticleSwarm {

    private static final Pattern particlePattern = Pattern.compile("p=<([-0-9]+),([-0-9]+),([-0-9]+)>, v=<([-0-9]+),([-0-9]+),([-0-9]+)>, a=<([-0-9]+),([-0-9]+),([-0-9]+)>");

    private Map<Integer, Function<Double, Double>> particleToDistanceFunction = new HashMap<>();
    private Map<Integer, Function<Double, Point>> particleToPointFunction = new HashMap<>();

    private static final int X = 0, Y = 1, Z = 2;

    public ParticleSwarm(Path input) throws IOException{
        int index = 0;
        for ( String particle : Files.readAllLines(input)){
            Matcher particleMatcher = particlePattern.matcher(particle);
            particleMatcher.find();

            long pos[] = new long[]{Long.parseLong(particleMatcher.group(1)), Long.parseLong(particleMatcher.group(2)), Long.parseLong(particleMatcher.group(3))};
            long vel[] = new long[]{Long.parseLong(particleMatcher.group(4)), Long.parseLong(particleMatcher.group(5)), Long.parseLong(particleMatcher.group(6))};
            long ace[] = new long[]{Long.parseLong(particleMatcher.group(7)), Long.parseLong(particleMatcher.group(8)), Long.parseLong(particleMatcher.group(9))};

            particleToDistanceFunction.put(index, t ->
                    Math.abs( pos[X] + t * ( vel[X] + 0.5 * ace[X] ) + t * t * 0.5 * ace[X]) + // X axis
                    Math.abs( pos[Y] + t * ( vel[Y] + 0.5 * ace[Y] ) + t * t * 0.5 * ace[Y]) + // Y axis
                    Math.abs( pos[Z] + t * ( vel[Z] + 0.5 * ace[Z] ) + t * t * 0.5 * ace[Z])   // Z axis
            );
            particleToPointFunction.put(index, t -> new Point(
                    Math.abs( pos[X] + t * ( vel[X] + 0.5 * ace[X] ) + t * t * 0.5 * ace[X]), // X axis
                    Math.abs( pos[Y] + t * ( vel[Y] + 0.5 * ace[Y] ) + t * t * 0.5 * ace[Y]), // Y axis
                    Math.abs( pos[Z] + t * ( vel[Z] + 0.5 * ace[Z] ) + t * t * 0.5 * ace[Z])  // Z axis
            ));
            index++;
        }
    }

    public int getMinDistParticle (double time){
        double minDist = Double.MAX_VALUE;
        int minDistParticle = -1;
        for ( Map.Entry<Integer, Function<Double, Double>> particle : particleToDistanceFunction.entrySet()){
            double tmp = particle.getValue().apply(time);
            if ( tmp < minDist ){
                minDist = tmp;
                minDistParticle = particle.getKey();
            }
        }
        return minDistParticle;
    }

    public int countAfterCollisions(double time){
        for ( double i = 0; i < time; i++ ){

            Map<Point, Set<Integer>> pointToParticles = new HashMap<>();
            System.out.println("Time: " + i + ", size: " + particleToPointFunction.size());

            for ( int j = 0; j < particleToPointFunction.size(); j++ ){
                Function<Double, Point> pointFunction = particleToPointFunction.get(j);
                if ( pointFunction == null ) continue;
                Point tmp = pointFunction.apply(i);
                if ( pointToParticles.get(tmp) == null ){
                    pointToParticles.put(tmp, new HashSet<>());
                }
                pointToParticles.get(tmp).add(j);
            }

            if ( pointToParticles.size() != particleToPointFunction.size() ) {
                for ( Set<Integer> point : pointToParticles.values() ) {
                    if ( point.size() > 1 ) {
                        particleToPointFunction.keySet().removeAll(point);
                    }
                }
            }
        }

        return particleToPointFunction.size();
    }

    public static void main(String[] args) throws IOException{
        ParticleSwarm ps = new ParticleSwarm(Paths.get("./src/day20/inputO.txt"));
        System.out.println(ps.getMinDistParticle(10000000));
        System.out.println(ps.countAfterCollisions(Double.MAX_VALUE));
    }

    private class Point {

        private long x, y, z;

        public Point(long x, long y, long z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point(double x, double y, double z){
            this.x = (long)x;
            this.y = (long)y;
            this.z = (long)z;
        }

        @Override
        public boolean equals(Object o){
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            Point point = (Point) o;

            if ( x != point.x ) return false;
            if ( y != point.y ) return false;
            return z == point.z;
        }

        @Override
        public int hashCode(){
            int result = (int) (x ^ (x >>> 32));
            result = 31 * result + (int) (y ^ (y >>> 32));
            result = 31 * result + (int) (z ^ (z >>> 32));
            return result;
        }

        @Override
        public String toString(){
            return  x + ", " + y + ", " + z;
        }
    }


}
