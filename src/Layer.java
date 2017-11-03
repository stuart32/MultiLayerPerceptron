import java.util.ArrayList;
import java.util.Random;

public class Layer {
	float[] outputs;
	float[]inputs;
	ArrayList<Neuron> neurons;
	int layerSize;
	
	public Layer(int size, float[] inputs, int bias, int r){
		
		this.neurons = new ArrayList<Neuron>(size);
		this.layerSize = size;
		for(int i=0; i<size; i++){
			
			this.neurons.add(new Neuron(inputs, r));
		}
		
	}
	
	public float[] getOutputs(float[] inputs, int bias){
		
		float[] out = new float[this.layerSize];
		
		for(int i=0;i<this.layerSize;i++){
			neurons.get(i).setActivation(inputs, bias);
			out[i] = neurons.get(i).setActivation(inputs, bias);
		}
		return out;
	} 
	
public float[] initInputLayer(float[] inputs, int bias){
		
		float[] out = new float[this.layerSize];
		
		for(int i=0;i<this.layerSize;i++){
			neurons.get(i).setInputActivation(inputs, bias);
			out[i]= neurons.get(i).activation;
		}
		return out;
	} 
	

}
