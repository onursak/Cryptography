package hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
	public byte[] hashTheMessage(String message) {
		byte[] bytesOfMessage = message.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hashedMessage = md.digest(bytesOfMessage);
		return hashedMessage;
	}
	
	public BigInteger hashTheMessage(String message, String algorithm) {
		byte[] bytesOfMessage = message.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hashedMessage = md.digest(bytesOfMessage);
		return new BigInteger(1,hashedMessage);
	}

}
