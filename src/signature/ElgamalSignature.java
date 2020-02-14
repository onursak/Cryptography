package signature;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import fieldarithmetic.Euclidean;
import fieldarithmetic.ModularExponentiation;
import fieldarithmetic.RandomGenerator;
import hash.HashGenerator;

public class ElgamalSignature {

	private Euclidean euclidean = new Euclidean();
	private ModularExponentiation modEx = new ModularExponentiation();
	private HashGenerator hashGenerator = new HashGenerator();
	
	public BigInteger[] signMessage(String message, BigInteger moduloPrime, BigInteger generator, BigInteger privateKey){
		BigInteger numericMessage = hashGenerator.hashTheMessage(message, "SHA-256");
		BigInteger r, s;
		do {
			BigInteger ephemeralKey;
			do {
				ephemeralKey = RandomGenerator.
								generateRandomNumber(BigInteger.ONE, moduloPrime.subtract(BigInteger.ONE));
			}while(!euclidean.isRelativelyPrime(ephemeralKey, moduloPrime.subtract(BigInteger.ONE)));
		
			r = modEx.applyFastModularExponentiation(generator, ephemeralKey, moduloPrime);
			BigInteger inverseEphemeralKey = euclidean.
											findMultiplicativeInverse(ephemeralKey, moduloPrime.subtract(BigInteger.ONE));
		
			s = inverseEphemeralKey.multiply( numericMessage.subtract( privateKey.multiply(r) ) )
											.mod(moduloPrime.subtract(BigInteger.ONE));
		}while(s.equals(BigInteger.ZERO));	
		return new BigInteger[] {r,s};
	}
	
	public boolean verifySignature(String message, BigInteger[] signature, BigInteger moduloPrime, 
						BigInteger generator, BigInteger publicKey){
		BigInteger numericMessage = hashGenerator.hashTheMessage(message, "SHA-256");
		BigInteger v1 = modEx.applyFastModularExponentiation(generator, numericMessage, moduloPrime);
		BigInteger publicOverR = modEx.applyFastModularExponentiation(publicKey, signature[0], moduloPrime);
		BigInteger rOverS = modEx.applyFastModularExponentiation(signature[0], signature[1], moduloPrime);
		BigInteger v2 = publicOverR.multiply(rOverS).mod(moduloPrime);

		return v1.equals(v2) ? true : false;

	}
	
	public static void main(String args[]){
		BigInteger mod = new BigInteger("23");
		BigInteger newGenerator = new BigInteger("2");
		ElgamalSignature es = new ElgamalSignature();
		
		System.out.println("Message: HelloWorld, moduloPrime: 23, generator: 2, publicKey: 2^5(mod 23) = 9" );
		System.out.println("moduloPrime: 23" );
		System.out.println("generator: 2" );
		System.out.println("privateKey: 5" );
		System.out.println("publicKey: 2^5(mod 23) = 9" );
		
		BigInteger[] result = es.signMessage("HelloWorld", mod, newGenerator, new BigInteger("5"));
		System.out.println("Signature: " + result[0] + ":" + result[1]);
		BigInteger publicKey = newGenerator.modPow(new BigInteger("5"), mod);
		System.out.println("Verification with public key: " + 
							es.verifySignature("HelloWorld", result, mod, newGenerator, publicKey));
	}


}
