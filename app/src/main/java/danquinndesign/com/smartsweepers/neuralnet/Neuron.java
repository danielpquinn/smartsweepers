package danquinndesign.com.smartsweepers.neuralnet;

/**
 * Individual neuron
 */
public class Neuron {

    /** Weight of each incoming edge */
    private float[] mWeights;

    public Neuron(int numInputs) {

        // The last weight in the list is actually a threshold value.
        // We include it here because we want to evolve it just like
        // the other weights coming into the neuron.

        mWeights = new float[numInputs + 1];

        // Add a random weight for each input

        for (int i = 0; i < numInputs; i += 1) {
            mWeights[i] = (float)(Math.random() * 2 - 1);
        }
    }

    /** Get weights for neuron */

    public float[] getWeights() {
        return mWeights;
    }
}
