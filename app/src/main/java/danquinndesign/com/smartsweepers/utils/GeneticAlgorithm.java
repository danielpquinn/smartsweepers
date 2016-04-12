package danquinndesign.com.smartsweepers.utils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by danquinn on 4/10/16.
 */
public class GeneticAlgorithm {

    /** Rate at which two chromosomes will swap their bits */
    static final double CROSSOVER_RATE = 0.7;

    /** Rate at which mutation occurs */
    static final double MUTATION_RATE = 0.001;

    /** Fitness function, accepts any number of arguments and returns a fitness score */
    public interface FitnessFunction {
        double execute(ArrayList<String> arguments);
    }

    /** Fitness function parameters */
    public String[] params;

    /** Mapping of parameters to their binary representation */
    public Map<BitSet, String> paramMap;

    /** Function used to guage fitness of a chromosome */
    public FitnessFunction fitnessFunction;

    /** Accepts comma separated list of possible parameters and a fitness function */

    public GeneticAlgorithm(String params, FitnessFunction fitnessFunction) {
        this.params = params.split(",");
        this.paramMap = getParamMap();
        this.fitnessFunction = fitnessFunction;
    }

    /** Generate a mapping of parameters to encodings */

    public Map<BitSet, String> getParamMap() {
        Map<BitSet, String> paramMap = new HashMap<>();
        int bitLength = getBitLengthToEncodeParams();

        // Generate a unique encoding for each param and add it to map

        for (int i = 0; i < this.params.length; i += 1) {
            BitSet bits = new BitSet(bitLength);

            // Generate next bits permutation

            for (int j = 0; j < bitLength; j += 1) {
                if (((i >> j) & 1) == 0) {
                    bits.set(j, false);
                } else {
                    bits.set(j, true);
                }
            }

            paramMap.put(bits, this.params[i]);
        }

        return paramMap;
    }

    /** Figure out how many bits are needed to encode params */

    public int getBitLengthToEncodeParams() {

        // There's probably a smarter way to do this but this will work for now

        int bitLength = 0;

        while (Math.pow(bitLength, 2) < this.params.length) {
            bitLength += 1;
        }

        return bitLength;
    }

    /** Decode a message */

    public ArrayList<String> decode(String message) {
        int bitLength = getBitLengthToEncodeParams();
        int messageLength = message.length() / bitLength;

        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < messageLength; i += 1) {
            String part = message.substring(i * bitLength, i * bitLength + bitLength);
            BitSet bits = new BitSet(bitLength);
            for (int j = 0; j < part.length(); j += 1) {
                bits.set(j, part.charAt(j) == '0' ? false : true);
            }
            if (this.paramMap.get(bits) != null) {
                result.add(this.paramMap.get(bits));
            }
        }

        return result;
    }

    /** Get the fitness of a chromosome */

    public double getFitness(String chromosome) {
        return fitnessFunction.execute(decode(chromosome));
    }

    /** Randomly generate a population */

    public ArrayList<String> generatePopulation(int size, int paramLength) {
        int bitLength = getBitLengthToEncodeParams();
        ArrayList<String> population = new ArrayList<>();

        for (int i = 0; i < size; i += 1) {
            String message = "";
            for (int j = 0; j < paramLength; j += 1) {
                for (int k = 0; k < bitLength; k += 1) {
                    message += Math.random() < 0.5 ? "0" : "1";
                }
            }
            population.add(message);
        }

        return population;
    }

    /** Evolve a population */

    public ArrayList<String> evolve(ArrayList<String> population) {
        ArrayList<String> newPopulation = new ArrayList(population.size());

        class MemberComparator implements Comparator<String> {
            private GeneticAlgorithm ga;

            public MemberComparator(GeneticAlgorithm ga) {
                this.ga = ga;
            }

            public int compare(String member1, String member2) {
                double f1 = ga.getFitness(member1);
                double f2 = ga.getFitness(member2);
                if (f2 < f1) { return -1; }
                if (f2 > f1) { return 1; }
                return 0;
            }
        }

        // Sort population by fitness

        Collections.sort(population, new MemberComparator(this));

        for (int i = 0; i < population.size() / 2; i += 1) {
            String member1 = population.get((int)Math.floor(Math.random() * population.size() / (Math.random() + 1)));
            String member2 = population.get((int)Math.floor(Math.random() * population.size() / (Math.random() + 1)));

            // Swap bits after random point if crossover rate is exceeded

            if (Math.random() < CROSSOVER_RATE) {
                for (int j = (int)Math.floor(Math.random() * member1.length()); j < member1.length() - 1; j += 1) {
                    char temp = member1.charAt(j);
                    member1 = member1.substring(0, j) + member2.charAt(j) + member1.substring(j + 1);
                    member2 = member2.substring(0, j) + temp + member2.substring(j + 1);
                }
            }

            // Mutate

            member1 = mutate(member1);
            member2 = mutate(member2);

            newPopulation.add(member1);
            newPopulation.add(member2);
        }

        return newPopulation;
    }

    /** Mutate a chromosome */

    static String mutate(String chromosome) {

        for (int i = 0; i < chromosome.length(); i += 1) {
            if (Math.random() < MUTATION_RATE) {
                chromosome = chromosome.substring(0, i) + (chromosome.charAt(i) == '0' ? "1" : "0") + chromosome.substring(i + 1);
            }
        }

        return chromosome;
    }

    /** Evolve until a member has a fitness score of one */

    public ArrayList<String> solve(int maxTries, int populationSize, int parameterLength) {
        ArrayList<String> population = generatePopulation(populationSize, parameterLength);
        ArrayList<String> solution = null;
        double maxFitness = 0;
        int tries = 0;


        // Evolve a population until we run out of tries or find an individual with a fitness of 1

        while (maxFitness < 1 && tries < maxTries) {
            population = evolve(population);
            maxFitness = getFitness(population.get(0));
            tries += 1;
        }

        if (maxFitness == 1) {
            solution = decode(population.get(0));
        }

        return solution;
    }

    public ArrayList<String> solve() {
        return solve(100, 10, 5);
    }
}
