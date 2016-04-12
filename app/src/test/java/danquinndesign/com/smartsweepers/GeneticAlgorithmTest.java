package danquinndesign.com.smartsweepers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import danquinndesign.com.smartsweepers.geneticalgorithm.GeneticAlgorithm;

import static org.junit.Assert.*;

/**
 * Tests behavior of genetic algorithm utility
 */
public class GeneticAlgorithmTest {

    public GeneticAlgorithm ga;

    // Based on problem described here: http://www.ai-junkie.com/ga/intro/gat3.html
    // Find a combination of integers and operators that equal 42. For example: 6 + 5 * 4 / 2 + 1

    public GeneticAlgorithm.FitnessFunction fitnessFunction = new GeneticAlgorithm.FitnessFunction() {
        @Override
        public double execute(ArrayList<String> args) {
            int total = -1;
            int lastInt = -1;
            String lastOperation = "";
            String lastType = "";
            int alternations = 0;
            double score;

            for (int i = 0; i < args.size(); i += 1) {
                if (total == -1) {
                    try {
                        total = Integer.parseInt(args.get(i));
                    } catch (NumberFormatException e) {
                        // Ignore
                    }
                }
            }

            for (int i = 0; i < args.size(); i += 1) {
                int currentInt = -1;
                String currentOperation = "";

                try {
                    currentInt = Integer.parseInt(args.get(i));
                    if (lastType == "string") { alternations += 1; }
                    lastType = "int";
                } catch (NumberFormatException e) {
                    currentOperation = args.get(i);
                    if (lastType == "int") { alternations += 1; }
                    lastType = "string";
                }

                if (currentOperation != "") {
                    lastOperation = currentOperation;
                } else if (lastOperation != "" && currentInt != -1) {
                    lastInt = currentInt;
                }

                if (lastOperation != "" && lastInt != -1) {
                    switch (lastOperation) {
                        case "+":
                            total += lastInt;
                            break;
                        case "-":
                            total -= lastInt;
                            break;
                        case "*":
                            total *= lastInt;
                            break;
                        default:
                            if (lastInt > 0) { total /= lastInt; }
                            break;
                    }
                    lastInt = -1;
                    lastOperation = "";
                }
            }

            score = (42d - Math.abs(42d - (double)total)) / 42d;

            // Give a higher score to sequences that contain alternations

            score += (1 - score) * (alternations / args.size());

            return score;
        }
    };

    @Before
    public void initialize() {
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
        assertEquals(0.23809523809523808, ga.getFitness("101001011010"), 0);
        assertEquals(1d, ga.getFitness("1001010110010101100101011001010101010110"), 0);
    }

    @Test
    public void generatePopulation() throws Exception {
        ArrayList<String> population = ga.generatePopulation(10, 5);
        assertEquals(10, population.size());
    }

    @Test
    public void solve() throws Exception {
        ArrayList<String> solution = ga.solve(50, 200, 7);
        System.out.println(solution);
        assertEquals(1, 1);
    }
}