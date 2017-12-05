/* Com3 Server Side Programming Assesment One
 * Jeremiah Cotter
 * 
 * Rev 1.
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class FileOutput {

	private static FileOutput fileOut;
	private static final File file = new File(System.getProperty("user.home") + "/Desktop/hiscore.txt");
	private int hiscore = 0;

	/** private constructor to used for singleton pattern
	 * 
	 */
	private FileOutput() {
		try {
			if(!file.createNewFile()) {
				Scanner sc = new Scanner(file);
				sc.next();sc.next();
				hiscore = sc.nextInt();
				sc.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** synchronised write file method so only one user can write at a time. Stops a user over writing another user with the wrong answer.
	 * 
	 * @param answer
	 * @param createTime
	 */
	public synchronized void writeFile(String answer, Date createTime){
		answer = answer.substring(0, answer.indexOf(' '));
		int speed = Integer.parseInt(answer);
		if(speed>hiscore)
			try {
				PrintWriter writer = new PrintWriter(file);
				writer.println("Top Speed: " + speed);
				writer.println("Session Details: " + createTime);
				writer.close();
				hiscore = speed;
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/** returns static class or creates it if required.
	 * 
	 * @return
	 */
	public static FileOutput getFile() {
		FileOutput temp;
		if(fileOut == null)
			temp = new FileOutput();
		else 
			temp = fileOut;
		return temp;
	}
}