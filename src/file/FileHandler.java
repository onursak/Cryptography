package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import certification.Certificate;
import certification.CertificateRequest;

public class FileHandler {
	
	public static void writeToFile(String fileName, Object object) {
		
		try {
			File file = new File(fileName);
			PrintWriter pw = new PrintWriter(file);
			pw.print(object);
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
	}
	
	private static BigInteger[] parseLineToBigInteger(String s) {
		String[] splitted = s.split(" ");
		return new BigInteger[] {new BigInteger(splitted[0]), new BigInteger(splitted[1])};
	}
	
	public static Object readFromFile(String fileName) {
		ArrayList<String> lines = new ArrayList<>();
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		// if line size equal to 3 we can understand that this object must be certificate request, otherwise certificate
		if(lines.size() == 3) {
			return new CertificateRequest(lines.get(0), 
					new BigInteger(lines.get(1)), 
					parseLineToBigInteger(lines.get(2)));
		}
		CertificateRequest cr = new CertificateRequest(lines.get(1), 
				new BigInteger(lines.get(2)), 
				parseLineToBigInteger(lines.get(3)));
		return new Certificate(cr, lines.get(0), parseLineToBigInteger(lines.get(4)));
	}
	

}
