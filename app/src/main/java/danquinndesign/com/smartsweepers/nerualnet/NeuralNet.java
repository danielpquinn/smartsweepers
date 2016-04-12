package danquinndesign.com.smartsweepers.nerualnet;

import java.util.ArrayList;

/**
 * Created by danquinn on 4/12/16.
 */
public class NeuralNet {

    /** Collection of layers */
    private ArrayList<NeuronLayer> mLayers = new ArrayList();

    public NeuralNet(int numInputs, int numOutputs, int numHiddenLayers, int numNeuronsPerHiddenLayer) {

        // Generate input layer.
        // TODO: Assuming for now that the input layer should have one input per neuron...

        mLayers.add(new NeuronLayer(numInputs, 1));

        // Generate hidden layers. First hidden layer should have as many inputs
        // as the number of neurons in the input layer. Each consecutive hidden layer
        // should have as many inputs as the last hidden layer... I think.

        for (int i = 0; i < numHiddenLayers; i += 1) {
            int numLayerInputs = i == 0 ? numInputs : numNeuronsPerHiddenLayer;
            mLayers.add(new NeuronLayer(numNeuronsPerHiddenLayer, numLayerInputs));
        }

        // Generate output layer

        mLayers.add(new NeuronLayer(numOutputs, numNeuronsPerHiddenLayer));
    }

    /** Get layers */

    public ArrayList<NeuronLayer> getLayers() {
        return mLayers;
    }

    /** Get output from network */

    public double[] getOutput() {

        // First batch of outputs will be the length of inputs to the neural net.

        double[] outputs = new double[mLayers.get(0).getNeurons().size()];

        // Iterate over hidden layers

        for (int i = 1; i < mLayers.size(); i += 1) {

            double[] inputs = outputs;

            outputs = new double[mLayers.get(i).getNeurons().size()];

            for (int j = 1; j < mLayers.get(i).getNeurons().size(); j += 1) {

                // Sum up each input multiplied by it's corresponding weight

                double sum = 0;
                double[] weights = mLayers.get(i).getNeurons().get(j).getWeights();

                for (int k = 0; k < inputs.length; k += 1) {
                    sum += inputs[k] * weights[k];
                }

                // Add bias... still confused by this.
                // TODO: Verify that -1 is correct here

                sum += weights[weights.length - 1] * -1;

                // Pass sum to activation function and store result in this layer's output

                outputs[j] = Sigmoid(sum);
            }
        }

        return outputs;
    }

    /** Sigmoid function used to generate output for each neuron */

    private static double Sigmoid(double t) {
        return t / (1 + Math.pow(Math.E, t * -1));
    }

    /** Get an array of all weights in hidden layers */

    public ArrayList<Double> getWeights() {
        ArrayList<Double> weights = new ArrayList();

        for (int i = 1; i < mLayers.size(); i += 1) {

            for (int j = 0; j < mLayers.get(i).getNeurons().size(); j += 1) {

                // TODO: Do I include bias here? Leaving it out for now.

                for (int k = 0; k < mLayers.get(i).getNeurons().get(j).getWeights().length - 1; k += 1) {
                    weights.add(mLayers.get(i).getNeurons().get(j).getWeights()[k]);
                }
            }
        }

        return weights;
    }
}
