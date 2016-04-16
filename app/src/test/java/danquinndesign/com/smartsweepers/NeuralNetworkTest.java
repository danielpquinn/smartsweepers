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

        float[] inputs = { -1, -1 };
        float[] outputs = net.getOutput(inputs);
        for (float output: outputs) { System.out.println(output); }

        inputs[0] = -0.5f;
        inputs[1] = -0.5f;
        outputs = net.getOutput(inputs);
        for (float output: outputs) { System.out.println(output); }

        inputs[0] = 0;
        inputs[1] = 0;
        outputs = net.getOutput(inputs);
        for (float output: outputs) { System.out.println(output); }

        inputs[0] = 0.5f;
        inputs[1] = 0.5f;
        outputs = net.getOutput(inputs);
        for (float output: outputs) { System.out.println(output); }

        inputs[0] = 1;
        inputs[1] = 1;
        outputs = net.getOutput(inputs);
        for (float output: outputs) { System.out.println(output); }

        System.out.println(net.Sigmoid(-1));
        System.out.println(net.Sigmoid(-0.5f));
        System.out.println(net.Sigmoid(0));
        System.out.println(net.Sigmoid(0.5f));
        System.out.println(net.Sigmoid(1));

        assertEquals(1, outputs.length);
    }

    @Test
    public void getWeights() throws Exception {

        // Check for correct weights length

        ArrayList<Float> weights = net.getWeights();
        assertEquals(13, weights.size());
    }
}