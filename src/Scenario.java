import java.math.BigInteger;

import agent.Person;
import certification.CertificationAuthority;

public class Scenario {

	public static void main (String args[]) {
		CertificationAuthority ca = new CertificationAuthority("Verisign", new BigInteger("23"), new BigInteger("2"));
		Person alice = new Person("Alice", new BigInteger("23"), new BigInteger("2"));
		Person bob = new Person("Bob", new BigInteger("23"), new BigInteger("2"));
		
		alice.learnAuthority(ca.getCertificateFileName());
		bob.learnAuthority(ca.getCertificateFileName());
		System.out.println("Alice and Bob learned the Public Key of Certification Authority. ");
		
		alice.requestCertificate(ca);
		bob.requestCertificate(ca);
		System.out.println("Alice and Bob sent the certificate request. ");
		
		alice.exchangeCertificates(bob.getCertificateFileName());
		bob.exchangeCertificates(alice.getCertificateFileName());
		System.out.println("Alice and Bob exchanged their certificates. ");
		
		alice.createCommonSecret();
		bob.createCommonSecret();
		System.out.println("Alice and Bob created common secret key according to DiffieHellman. ");
		
		String message = "Lord of the rings!";
		
		System.out.println("Message: " + message);
		
		String encryptedMessage = alice.sendMessage(message);
		System.out.println("Alice encrypted the message with AES. ");
		System.out.println("Encrypted message: " + encryptedMessage);
		
		
		String decryptedMessage = bob.takeMessage(encryptedMessage);
		System.out.println("Bob took the message and decrypted it. ");
		System.out.println("Decrypted message: " + decryptedMessage);
		
	}
}
