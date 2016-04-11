package danquinndesign.com.smartsweepers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.BitSet;

import danquinndesign.com.smartsweepers.utils.GeneticAlgorithm;

import static org.junit.Assert.*;

/**
 * Tests behavior of genetic algorithm utility
 */
public class GeneticAlgorithmTest {

    GeneticAlgorithm ga;

    @Before
    public void initialize() {
        GeneticAlgorithm.FitnessFunction fitnessFunction = new GeneticAlgorithm.FitnessFunction() {
            @Override
            public int execute(ArrayList<String> arguments) {
                return 0;
            }
        };
        ga = new GeneticAlgorithm("0,1,2,3,4,5,6,7,8,9,+,-,*,/", fitnessFunction);
    }

    @Test
    public void getBitLengthToEncodeParams() throws Exception {
        assertEquals(4, ga.getBitLengthToEncodeParams());
    }

    @Test
    public void getParamMap() throws Exception {
        assertEquals("{{1, 2}=6, {0, 1, 2}=7, {2}=4, {0, 2}=5, {1}=2, {0, 1}=3, {}=0, {0}=1, {2, 3}=*, {0, 2, 3}=/, {1, 3}=+, {0, 1, 3}=-, {3}=8, {0, 3}=9}", ga.getParamMap().toString());
    }

    @Test
    public void decode() throws Exception {
        ArrayList<String> desiredResult = new ArrayList<String>();
        desiredResult.add("6");
        desiredResult.add("7");
        desiredResult.add("4");
        ArrayList<String> result = ga.decode("011011100010");
        assertEquals(desiredResult, result);
    }

    @Test
    public void getFitness() throws Exception {
        assertEquals(0, ga.getFitness("011011100010"));
    }
}