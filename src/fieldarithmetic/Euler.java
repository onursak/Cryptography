package fieldarithmetic;
import java.math.BigInteger;

public class Euler {

	/**
	 * This method finds the the count of relatively 
	 * prime numbers between 2 and (a-1) by using Euler's toilent function.
	 */
	public long applyEulerPhiFunction(BigInteger a) {
		
		BigInteger initialPrime = BigInteger.TWO;
		
		double eulerPhi = a.doubleValue();
		
		while(a.compareTo(BigInteger.ONE) > 0) {
			
			if(a.mod(initialPrime).compareTo(BigInteger.ZERO) == 0) {
				
				while(a.mod(initialPrime).compareTo(BigInteger.ZERO) == 0) {
					a = a.divide(initialPrime);
				}
			
				eulerPhi = eulerPhi * (1.0 - (1.0/initialPrime.doubleValue()));
			
			}
			
			initialPrime = initialPrime.add(BigInteger.ONE);
		}
		return (long) eulerPhi;
	}

}
