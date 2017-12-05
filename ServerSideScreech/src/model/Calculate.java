/* Com3 Server Side Programming Assesment One
 * Jeremiah Cotter
 * 
 * Rev 1.
 */
package model;

public class Calculate {
	private String length;
	private int type;
	private double answer;
	private final String[] SURFACE_S = {"Portland Cement", "Asphalt", "Gravel", "Ice", "Snow"};
	private final double[] SURFACE_D = {0.9, 0.75, 0.5, 0.15, 0.3};

	/** Constructor - Sets the surface and stopping length
	 * 
	 * @param length of skid marks
	 * @param surface type
	 */
	public Calculate(String l, String s) {
		length = l;
		switch(s) {
		case "cem":
			type = 0;
			break;
		case "asph":
			type = 1;
			break;
		case "grav":
			type = 2;
			break;
		case "ice":
			type = 3;
			break;
		case "snow":
			type = 4;
			break;
		}
		calculate();
	}
	
	/** Calculates starting speed
	 * 
	 */
	public void calculate() {
		Double distance = Double.parseDouble(length);
		answer = Math.sqrt(30*distance*getSurfaceD());
	}

	/** Gets the length entered
	 * 
	 * @return
	 */
	public String getLength() {
		if(length==null)
			return "";
		return length;
	}
	
	/** Rounds the answer to a whole number and returns it.
	 * 
	 * @return
	 */
	public String getAnswer() {
		answer = Math.round(answer);
		if (answer==0.00)
			return "";
		else return (int)answer + " MPH";
	}

	/** Gets the surface type and returns the string
	 * 
	 * @return
	 */
	public String getSurfaceS() {
		if(type<0||type>5)
			return "error";
		return SURFACE_S[type];
	}

	/** Gets the surface type and returns the double for the calculation
	 * 
	 * @return
	 */
	public double getSurfaceD() {
		if(type<0||type>5)
			return 0.0;
		return SURFACE_D[type];
	}

	/** Gets the type to return as an int for the radio buttons
	 * 
	 */
	public int getSurfaceR() {
		return type;
	}
}