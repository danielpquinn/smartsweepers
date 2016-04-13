package danquinndesign.com.smartsweepers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import danquinndesign.com.smartsweepers.neuralnet.NeuralNet;

import static junit.framework.Assert.*;

/**
 * Tests behavior of neural network
 */
public class NeuralNetworkTest {

    private NeuralNet net;

    @Before
    public void initialize() {
        net = new NeuralNet(2, 1, 1, 3);
    }

    @Test
    public void constructor() throws Exception {

        // Make sure neural net has correct number of neurons in
        // input and output layers upon creation

        NeuralNet net = new NeuralNet(2, 1, 1, 3);

        assertEquals(2, net.getLayers().get(0).getNeurons().size());
        assertEquals(1, net.getLayers().get(2).getNeurons().size());
    }

    @Test
    public void getOutput() throws Exception {

        // Check for correct output length

        double[] output = net.getOutput();

        assertEquals(1, output.length);
    }

    @Test
    public void getWeights() throws Exception {

        // Check for correct weights length

        ArrayList<Double> weights = net.getWeights();
        assertEquals(9, weights.size());
    }
}