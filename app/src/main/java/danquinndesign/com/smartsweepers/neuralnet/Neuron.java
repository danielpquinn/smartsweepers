package danquinndesign.com.smartsweepers.neuralnet;

/**
 * Created by danquinn on 4/12/16.
 */
public class Neuron {

    /** Weight of each incoming edge */
    private double[] mWeights;

    public Neuron(int numInputs) {

        // The last weight in the list is actually a threshold value.
        // We include it here because we want to evolve it just like
        // the other weights coming into the neuron.

        mWeights = new double[numInputs + 1];

        // Add a random weight for each input

        for (int i = 0; i < numInputs; i += 1) {
            mWeights[i] = Math.random();
        }
    }

    /** Get weights for neuron */

    public double[] getWeights() {
        return mWeights;
    }
}
