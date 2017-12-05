/* Com3 Server Side Programming Assesment One
 * Jeremiah Cotter
 * 
 * Rev 1.
 */
package model;

import java.io.Serializable;

public class IndexBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String checked = "cem";
	private String calculation = "None Performed";
	
	public String getChecked() {
		return checked;
	}
	
	public void setChecked(String surface) {
		checked = surface;
	}

	public String getCalculation() {
		return calculation;
	}

	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}
}