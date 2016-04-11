package danquinndesign.com.smartsweepers.utils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by danquinn on 4/10/16.
 */
public class GeneticAlgorithm {

    /** Fitness function, accepts any number of arguments and returns a fitness score */
    public interface FitnessFunction {
        int execute(ArrayList<String> arguments);
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

    public int getFitness(String chromosome) {
        return fitnessFunction.execute(decode(chromosome));
    }
}
