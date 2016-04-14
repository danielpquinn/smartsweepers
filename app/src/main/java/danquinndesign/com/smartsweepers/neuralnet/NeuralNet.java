package danquinndesign.com.smartsweepers.neuralnet;

import java.util.ArrayList;

/**
 * Created by danquinn on 4/12/16.
 */
public class NeuralNet {

    /** Collection of layers */
    private ArrayList<NeuronLayer> mLayers = new ArrayList();

    public NeuralNet(int numInputs, int numOutputs, int numHiddenLayers, int numNeuronsPerHiddenLayer) {

        // Generate input layer.

        mLayers.add(new NeuronLayer(numInputs, numInputs));

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

    public float[] getOutput(float[] inputs) {

        // First batch of outputs will be the length of inputs to the neural net.

        float[] outputs = inputs;

        // Iterate over layers

        for (int i = 0; i < mLayers.size(); i += 1) {

            ArrayList<Neuron> neurons = mLayers.get(i).getNeurons();

            inputs = outputs;

            outputs = new float[neurons.size()];

            for (int j = 0; j < neurons.size(); j += 1) {

                // Sum up each input multiplied by it's corresponding weight

                float sum = 0;
                float[] weights = neurons.get(j).getWeights();

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

    public static float Sigmoid(float a) {
        return (float)(1 / (1 + Math.pow(Math.E, 100 * a * -1)));
    }

    /** Get an array of all weights in hidden layers */

    public ArrayList<Float> getWeights() {
        ArrayList<Float> weights = new ArrayList();

        for (int i = 1; i < mLayers.size(); i += 1) {
            for (int j = 0; j < mLayers.get(i).getNeurons().size(); j += 1) {
                for (int k = 0; k < mLayers.get(i).getNeurons().get(j).getWeights().length; k += 1) {
                    weights.add(mLayers.get(i).getNeurons().get(j).getWeights()[k]);
                }
            }
        }

        return weights;
    }

    /** Set weights of all connections */

    public void setWeights(ArrayList<Float> weights) {
        int currentWeight = 0;

        for (int i = 1; i < mLayers.size(); i += 1) {
            for (int j = 0; j < mLayers.get(i).getNeurons().size(); j += 1) {
                for (int k = 0; k < mLayers.get(i).getNeurons().get(j).getWeights().length; k += 1) {
                    mLayers.get(i).getNeurons().get(j).getWeights()[k] = weights.get(currentWeight);
                    currentWeight += 1;
                }
            }
        }
    }
}
