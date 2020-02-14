package exchange;
import java.math.BigInteger;

import fieldarithmetic.ModularExponentiation;

public class DiffieHellman {
	private ModularExponentiation modEx = new ModularExponentiation();
	
	public BigInteger exchangeKeys(BigInteger moduloPrime, BigInteger generator, BigInteger publicKey, BigInteger privateKey) {
		BigInteger result = modEx.applyFastModularExponentiation(publicKey, privateKey, moduloPrime);
		return result;
	}

	public static void main(String args[]) {
		DiffieHellman dh = new DiffieHellman();
		
		System.out.println("Alice and Bob's moduloPrime: 23 , generator: 2");
		System.out.println("Alice's privateKey: 5 , publicKey: 9");
		System.out.println("Bob's privateKey: 4 , publicKey: 16");
		
		BigInteger secret1 = dh.exchangeKeys(new BigInteger("23"), new BigInteger("2"), new BigInteger("16"), new BigInteger("5"));
		BigInteger secret2 = dh.exchangeKeys(new BigInteger("23"), new BigInteger("2"), new BigInteger("9"), new BigInteger("4"));
		
		System.out.println("Alice's secret value: " + secret1);
		System.out.println("Bob's secret value: " + secret2);

	}
}
