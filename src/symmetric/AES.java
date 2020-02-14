package symmetric;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	private Cipher cipher;
	
	public AES(){
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
	}
	
	public String encryptTheMessage(String message, SecretKey key) {
		try {
			byte[] plainText = message.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherText = cipher.doFinal(plainText);
			String encodedString = Base64.getEncoder().encodeToString(cipherText);
			return encodedString;
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public String decryptTheMessage(String message, SecretKey key) {
		try {
			byte[] cipherText = Base64.getDecoder().decode(message);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plainText = cipher.doFinal(cipherText);
			return new String(plainText);
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	public static void main(String args[]) {
		KeyGenerator kg;
		SecretKey sk;
		try {
			kg = KeyGenerator.getInstance("AES");
			kg.init(256);
			sk = kg.generateKey();
			
			String message = "Lost is the best tv series that I ever watched!";
			System.out.println("Message is: "+message);

			String encrypted = new AES().encryptTheMessage(message, sk);
			System.out.println("Enrypted message is: "+encrypted);
			String decrypted = new AES().decryptTheMessage(encrypted, sk);
			System.out.println("Decrypted message is: "+decrypted);

		
		} catch (NoSuchAlgorithmException e) {
			
		}

	}
	
	
}
