package fieldarithmetic;
import java.math.BigInteger;
import java.util.Random;

public class RandomGenerator {
	
	/**
	 * This method for creating random BigInteger between given lower and upper limit.
	 * I wrote this method, because in primality test, it is needed creating random number
	 * for big integer numbers.
	 */
	public static BigInteger generateRandomNumber(BigInteger lowerLimit, BigInteger upperLimit) {
		
		Random randomSource = new Random();
		
		BigInteger interval = upperLimit.subtract(lowerLimit);
		
		int lenght = interval.bitLength();
		
		BigInteger generatedRandomNumber = new BigInteger(lenght, randomSource);
		
		BigInteger result = generatedRandomNumber.add(lowerLimit);
		
		// Because of generatedRandomNumber according to bit count, it can be over the upperLimit
		// Following ternary operation provides the number in wanted range
		return (result.compareTo(lowerLimit) < 0 ? 
				result.add(lowerLimit) : result.mod(interval).add(lowerLimit));
	}
	
}
