package fieldarithmetic;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ModularExponentiation {
	private Euclidean euclidean = new Euclidean();
	private Fermat fermat = new Fermat();
	private Euler euler = new Euler();
	
	/**
	 * This method for performing repeated squaring for given number and exponent which is multiply of 2.
	 */
	public BigInteger applySquaring(BigInteger a, int exponent, BigInteger mod) {
		
		BigInteger result = a;
	
		int initialExponent = 1;
		
		while(initialExponent < exponent){
			
			result = result.modPow(BigInteger.TWO, mod);
			
			if(result.compareTo(BigInteger.ONE) == 0) {
				return BigInteger.ONE;
			}
			
			initialExponent *= 2;
		}
		
		return result;
	}
	
	/**
	 * This method for performing binary decomposition and applying repeated squaring method with decomposed exponent.
	 */
	public BigInteger applyBinaryDecomposition(BigInteger a, BigInteger exponent, BigInteger mod) {
		
		BigInteger result = BigInteger.ONE;
		
		for(int i=0; i < exponent.bitLength(); i++) {
			if(exponent.testBit(i) == true) {
				result = result.multiply(applySquaring(a, (int) Math.pow(2, i), mod));
				
			}
		
		}
		
		return result.mod(mod);
	}
	
	/**
	 * This method for performing fast modular exponentiation for large bases and exponents.
	 */
	public BigInteger applyFastModularExponentiation(BigInteger base, BigInteger exponent, BigInteger mod) {
		
		BigInteger finalResult = BigInteger.ZERO;
		
		//If number mod number is prime number, then we can apply fermat's little theorem
		if(fermat.isPrimeNumber(mod, 1000) == true) {
			
			BigInteger reducedExponent = exponent.mod(mod.subtract(BigInteger.ONE));
			BigInteger reducedBase = base.mod(mod);
			finalResult = applyBinaryDecomposition(reducedBase, reducedExponent, mod);
			
		}
		
		//If number mod number is not prime number, then we can apply euler's phi function
		else if(fermat.isPrimeNumber(mod, 1000) == false && 
							euclidean.isRelativelyPrime(base.subtract(BigInteger.ONE), mod)) {
	
			long eulerPhiResult = euler.applyEulerPhiFunction(mod);
			BigInteger reducedBase = base.mod(mod);
			BigInteger reducedExponent = exponent.mod(BigInteger.valueOf(eulerPhiResult));
			finalResult = applyBinaryDecomposition(reducedBase, reducedExponent, mod);
		}
		
		//If the case is not the both of above, we can apply repeated squaring with binary decomposition
		else {
	
			finalResult = applyBinaryDecomposition(base, exponent, mod);
		
		}
		
		return finalResult;
	
	}
	
	public void runTheScenario() {
		
		System.out.println("Modular Exponentiation result for 5^121242653 (mod 11) : " + 
				 			applyFastModularExponentiation(new BigInteger("5"), 
			                  new BigInteger("121242653"), new BigInteger("11")));
	
		System.out.println("Modular Exponentiation result for 343281^327847324 (mod 39) : " + 
							applyFastModularExponentiation(new BigInteger("343281"), 
						      new BigInteger("327847324"), new BigInteger("39")));
	
		System.out.println("Modular Exponentiation result for 23^25 (mod 30) : " + 
							applyFastModularExponentiation(new BigInteger("23"), 
		                   	  new BigInteger("25"), new BigInteger("30")));
		
		System.out.println("Modular Exponentiation result for 77^39 (mod 30) : " + 
				applyFastModularExponentiation(new BigInteger("77"), 
               	  new BigInteger("39"), new BigInteger("30")));
		
		System.out.println("Modular Exponentiation result for 11^13 (mod 21) : " + 
				applyFastModularExponentiation(new BigInteger("11"), 
               	  new BigInteger("13"), new BigInteger("21")));
		
	}
	
	public static void main(String args[]) throws NoSuchAlgorithmException {
		System.out.println("Modular Exponentiation result for 6^3 (mod 23) : " + 
				new ModularExponentiation().applyFastModularExponentiation(new BigInteger("6"), 
               	  new BigInteger("3"), new BigInteger("23")));
	}
	

}
