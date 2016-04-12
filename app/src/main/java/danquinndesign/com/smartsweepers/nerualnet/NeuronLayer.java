package danquinndesign.com.smartsweepers.nerualnet;

import java.util.ArrayList;

/**
 * Created by danquinn on 4/12/16.
 */
public class NeuronLayer {

    /** List of neurons */
    private ArrayList<Neuron> mNeurons = new ArrayList();

    public NeuronLayer(int numNeurons, int inputsPerNeuron) {

        // Add neurons to layer?

        for (int i = 0; i < numNeurons; i += 1) {
            mNeurons.add(new Neuron(inputsPerNeuron));
        }
    }

    /** Get neurons in layer */

    public ArrayList<Neuron> getNeurons() {
        return mNeurons;
    }
}
