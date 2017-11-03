import java.util.Random;

public class Neuron {
	
	float[] weights;
	float activation;
	
	public Neuron(float[] inputs, int r){
		
		weights = new float[inputs.length+1];
		weights[0] = 1;
		for(int i=1; i<weights.length; i++){
			weights[i] = getRandInRange(-5,5,r);
		}
		
		
	
	}
	
	
	public float setActivation(float[] inputs, int bias){
		
		//System.out.println("Setting activation");
		
		float[] i_buffer = new float[weights.length];
		i_buffer[0] = bias;
		float sum = 0.0f;
		
		
		
		for(int i=1; i<i_buffer.length; i++){
			i_buffer[i] = inputs[i-1];
		}
		
		
		
		
		for(int i=0;i<i_buffer.length-1; i++){
			
			//System.out.println("Sumb: "+sum);
			sum =+ i_buffer[i]*weights[i];
			//System.out.println("Suma: "+sum + " input: " + i_buffer[i] + " Weight: " + weights[i]);
			
			
		}
		
		//System.out.println(sum);
		
		return (float)(1/( 1 + Math.pow(Math.E,(-1*sum))));
				
		
	}
	
	public float getActivationSigmoid(){
		return (float)(1/( 1 + Math.pow(Math.E,(-1*activation))));
	}
	
	public void setInputActivation(float[] inputs, int bias){
		//System.out.println("Setting input activation");
		for(int i=0;i<inputs.length;i++){
			this.activation= inputs[i];
		}
	}

	
	private static float getRandInRange(int min, int max, int r) {
		
	

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random ran = new Random();
		float random = min + ran.nextFloat()* (max - min);
		return random;
	}
	
	public float[] getWeights(){
		return weights;
	}
	public float getWeightI(int i){
		return weights[i];
	}

}
