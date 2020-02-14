package fieldarithmetic;
import java.math.BigInteger;

import exceptions.NoMultiplicationInverseException;

public class Euclidean {
	
	/**
	 * This method finds the greatest common divisor of given two numbers by applying extended euclidean
	 * algorithm.
	 * 
	 */
	public BigInteger[] applyExtendedEuclideanAlgorithm(BigInteger a, BigInteger b) {
		//Finding min and max values of given two numbers to perform division
		if(a.compareTo(BigInteger.ZERO) == 0 || b.compareTo(BigInteger.ZERO) == 0) {
			return new BigInteger[] {b.max(a), BigInteger.ONE, BigInteger.ZERO};
		}
		BigInteger smallValue = a.min(b), highValue = b.max(a);
		BigInteger x = BigInteger.ZERO, y = BigInteger.ZERO, x1 = BigInteger.ONE, 
				x2 = BigInteger.ZERO, y1 = BigInteger.ZERO, y2 = BigInteger.ONE; 

		while(true) {
			//If the current remainder is zero, then terminates the algorithm
			BigInteger tempRemainder = highValue.mod(smallValue);
			
			if(tempRemainder == BigInteger.ZERO) {
				break;
			}
			BigInteger quotient = highValue.divide(smallValue);
			x = x1.subtract(x2.multiply(quotient));
			y = y1.subtract(y2.multiply(quotient));
			highValue = smallValue;
			smallValue = tempRemainder;
			//remainder = tempRemainder;
			x1 = x2;
			x2 = x;
			y1 = y2;
			y2 = y;
		}
		BigInteger[] result = new BigInteger[] {smallValue, x, y};
		return result;
	}
	
	public BigInteger findGcd(BigInteger a, BigInteger b) {
		BigInteger[] result = applyExtendedEuclideanAlgorithm(a, b);
		return result[0];
	}
	
	/**
	 * 
	 * @param a One of the values to be tested relatively prime or not.
	 * @param b One of the values to be tested relatively prime or not.
	 * @return
	 */
	public boolean isRelativelyPrime(BigInteger a, BigInteger b) {
		BigInteger[] result = applyExtendedEuclideanAlgorithm(a, b);
		
		if(result[0].compareTo(BigInteger.ONE) == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param a This is the parameter that we want to calculate multiplicative inverse according to given mod.
	 * @param mod This is the parameter that we want to calculate multiplicative inverse in which modular field.
	 * @return Function returns the multiplicative inverse of a in given mod N.
	 */
	public BigInteger findMultiplicativeInverse(BigInteger a, BigInteger modN) {
		BigInteger[] result = applyExtendedEuclideanAlgorithm(a, modN);
		if(result[0].compareTo(BigInteger.ONE) != 0) {
			throw new NoMultiplicationInverseException();
		}
		
		BigInteger multiplicativeInverse = BigInteger.ZERO;
		
		// Because of changing the values of x and y in ax + by = gcd(a,b) according to orders of parameters a and b 
		// of extended euclidean algorithm, if we give the value to b, higher than a, then x and y values are swapped
		// That is why I wrote the following if branches
		if(a.compareTo(modN) > 0) {
			multiplicativeInverse = result[1];
		}
		else {
			multiplicativeInverse = result[2];
		}
		multiplicativeInverse = makeTheValueInModularRange(multiplicativeInverse, modN);
		return multiplicativeInverse;
	}
	
	public BigInteger makeTheValueInModularRange(BigInteger value, BigInteger modN) {
		if(value.compareTo(BigInteger.ZERO) != -1 && value.compareTo(modN) < 0) {
			return value;
		}
		else {
			return makeTheValueInModularRange((value.add(modN)).mod(modN), modN);
		}
	}
	
	public void runTheScenario(){
		
		System.out.println("GCD of 26 and 0: " 
				+ findGcd(new BigInteger("26"), new BigInteger("0")));
		
		System.out.println("GCD of 398 and 1223: " 
							+ findGcd(new BigInteger("398"), new BigInteger("1223")));
		
		System.out.println("Multiplicative Inverse of 38 for mod 93: " + 
							findMultiplicativeInverse(new BigInteger("38"), new BigInteger("93")));
		
		System.out.println("Are 398 and 1223 relatively prime?: " 
							+ isRelativelyPrime(new BigInteger("398"), new BigInteger("1223")));
		
		System.out.println("Multiplicative Inverse of 17 for mod 1032: " 
				+ findMultiplicativeInverse(new BigInteger("17"), new BigInteger("1032")));
		
		System.out.println("GCD of 569 and 39: " 
				+ findGcd(new BigInteger("569"), new BigInteger("39")));
		
		System.out.println("Are 77 and 11 relatively prime?: " 
				+ isRelativelyPrime(new BigInteger("77"), new BigInteger("11")));
	
	}
	
}
