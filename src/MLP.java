import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MLP {
	
	ArrayList<Layer> layers;
	float[] input;
	float[] output;
	int bias;
	
	public MLP(float[] input, int bias, int r){
		this.bias = bias;
		this .layers = new ArrayList<Layer>(3);
		layers.add(new Layer(3,input,bias, r));
		float[] l1Out = layers.get(0).initInputLayer(input, bias);
		layers.add(new Layer(5,l1Out,bias,r));
		float[] l2Out = layers.get(1).getOutputs(l1Out, bias);
		layers.add(new Layer(1,l2Out,bias,r));
		
		this.output = layers.get(2).getOutputs(l2Out, bias);
	}
	
	public float[] evaluate(float[] input){
		
		//this.layers.get(0).inputs = input;
		float[] l1Out = layers.get(0).initInputLayer(input, this.bias);
		float[] l2Out = layers.get(1).getOutputs(l1Out, this.bias);
		float[] out = layers.get(2).getOutputs(l2Out, this.bias);
		return out;
	}
	
	
	
	
private static ArrayList<float[]> getInputData() throws FileNotFoundException{
		
		System.out.println("Importing locations from file...");
		File file = new File("src/cubicData.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		ArrayList<float[]> inputs = new ArrayList<>();
		String line;
	
		try {
			while ((line = bufferedReader.readLine()) != null) {
				
				String[] words = new String[4];
				words = line.split(" ");
				float[] instance = new float[3];
				instance[0] = Float.parseFloat(words[0]);
				instance[1] = Float.parseFloat(words[1]);
				instance[2] = Float.parseFloat(words[2]);
				//instance[3] = Float.parseFloat(words[3]);
			
				inputs.add(instance);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputs;
	}
	
	
	

	private static float[] getOutputData() throws FileNotFoundException{
		
		System.out.println("Importing locations from file...");
		File file = new File("src/cubicData.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		float outputs[] = new float[100];
		String line;
		int count = 0;
		
		try {
			while ((line = bufferedReader.readLine()) != null) {
				
				String[] words = new String[4];
				words = line.split(" ");
				float instance = Float.parseFloat(words[3]);
				outputs[count] = instance;
				count ++;
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputs;	
	}
	
	
	
	
	public static float[] getErrorSample(int sample) throws IOException{
		
		if(sample > 100){
			throw new IOException("sample larger than dataset");
		}
		
		ArrayList<float[]> inputs = getInputData();
		float[] outputs = getOutputData();
		float[] errors = new float[sample];
		Random ran = new Random();
		int r =1;
		ArrayList<MLP> s = new ArrayList<MLP>(sample);	
		float bestErr = 0.0f;
		
		for(int i=0;i<sample;i++){
			//System.out.println("New Sample");
			int index = ran.nextInt((inputs.size()-1 - 0) + 1) + 0;
			//System.out.println(index);
			MLP nn = new MLP(inputs.get(index), 10,r);
			
			
			float cErr = getError(nn.output[0], outputs[index]); 
			errors[i] = cErr;
			s.add(nn);
			if(i==1){
				bestErr=cErr;
			}
			else if(cErr <= bestErr){
				bestErr = cErr;
			}
			//System.out.println(nn.output[0] +" " +getError(nn.output[0], outputs[index]) );
			
		}
		
		float[] bestWeights = new float[getAllWeights( s.get(0)).length];
		
		for(int i=0;i<s.size();i++){
			if(errors[i] == bestErr ){
				bestWeights = getAllWeights( s.get(i));
			}
		}

		float mse = getMSE(errors);
		float[] output = new float[bestWeights.length];
		output[0] = mse;
		for(int i=1;i<bestWeights.length;i++){
			output[i] = bestWeights[i];
			//System.out.println("W " + bestWeights[i]);
		}
		return output;
		
	}
	
	
	
	
	
	public static float getMSE(float[] errors){
		
		float sum =0.0f;
		for(float e : errors){
			sum += e;
			
		}
		//System.out.println(sum);
		return sum/errors.length-1;
	}
		

	
	
	
	
	
	public static float getError(float u, float d){
		float e = d-u;
		// System.out.println("DU" + e);
		return d-u;
	}
	
	public static float[] getAllWeights(MLP m){
		
		int weightsNo = 0;
		for(Layer layers : m.layers){
			for(Neuron neuron : layers.neurons){
				for(float weight : neuron.weights){
					weightsNo++;
				}
			}
		}
		
		float[] weights = new float[weightsNo];
		int count = 0;
		for(Layer layers : m.layers){
			for(Neuron neuron : layers.neurons){
				for(float weight : neuron.weights){
					weights[count] = weight;
					count++;
				}
			}
		}
		
		return weights;
	}
	
	
	
	
	public static void main(String [] args) throws IOException{
		
		/*
		ArrayList<float[]> inputs = getInputData();
		float[] outputs = getOutputData();
		int r =1;
		
		MLP nn1 = new MLP(inputs.get(1), 10,r);
		MLP nn2 = new MLP(inputs.get(4), 10,r);
		
		float[] w1 = getAllWeights(nn1);
		float[] w2 = getAllWeights(nn2);
		
		float[] s = getErrorSample(10);
		
	
		for(int i=0;i<w1.length;i++){
			System.out.println(w1[i] + " " + w2[i]);
		}
		
		System.out.println(nn1.output[0] + " : " + nn2.output[0]);
		*/
		
	

		int sample = 10;
		ArrayList<float[]> test = new ArrayList<float[]>(sample);
		for(int i=0;i<sample;i++){
			test.add(getErrorSample(sample));
		}
		
		for(int i=0;i<sample;i++){
			System.out.println("MSE: " + test.get(i)[0]);
			for(int j=1;j<test.get(i).length;j++){
				System.out.println("W"+j+" " + test.get(i)[j]);
			}
		}
		
		
		/*
		for(int i=0;i<nn.layers.size();i++){
			
			Layer cl = nn.layers.get(i);
			
			System.out.println("Layer"+i+": size="+nn.layers.get(i).layerSize);
			for(float input :nn.layers.get(i).inputs){
				System.out.println("I "+input);
			}
			for(float output :nn.layers.get(i).outputs){
				System.out.println("O "+output);
			}
			
			
		}
		
		for(float out : nn.output){
			System.out.println(out);
		}
		*/
	}
	
	
}
