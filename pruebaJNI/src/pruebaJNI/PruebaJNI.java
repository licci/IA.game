package pruebaJNI;

import CLIPSJNI.*;


public class PruebaJNI {

	private Environment clips;
	
	private boolean legs;
	private boolean mammary;
	private boolean wings;
	private boolean pelo;

	
	
	public PruebaJNI() {
		
		clips = new Environment();
		
		clips.load("ejemploReglas.clp");
		
		legs = true;
		mammary = true;
		wings = false;
		pelo = true;
	}
	
	public static void main(String[] args) {
		
		PruebaJNI prueba = new PruebaJNI();
		
		prueba.reset();

		prueba.run();
	}

	private void run() {
		// TODO Auto-generated method stub
		
		if (legs) 	
			clips.eval("(assert (legs s))");			
		else 
			clips.eval("(assert (legs n))");
		
		if (mammary) 	
			clips.eval("(assert (mammary s))");			
		else 
			clips.eval("(assert (mammary n))");
		
		if (wings) 	
			clips.eval("(assert (wings s))");			
		else 
			clips.eval("(assert (wings n))");
		
		if (pelo) 	
			clips.eval("(assert (pelo s))");			
		else 
			clips.eval("(assert (pelo n))");
		
		clips.run();
		
		String response = clips.getInputBuffer();
		
		System.out.println(response);
	}

	private void reset() {
		// TODO Auto-generated method stub
		clips.reset();
	}
	
}
