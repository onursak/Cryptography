package fieldarithmetic;
import java.math.BigInteger;

public class Fermat {
	private Euclidean euclidean = new Euclidean();
	
	public boolean isPrimeNumber(BigInteger p, int numberOfIterations) {
		if( p.compareTo(BigInteger.ZERO) < 0) {
			return false;
		}
		else if(p.compareTo(BigInteger.valueOf(2)) == 0 || p.compareTo(BigInteger.valueOf(3)) == 0) {
			return true;
		}
		
		BigInteger randomNumber = null;
		for(int i = 0; i < numberOfIterations ; i++) {
			randomNumber = RandomGenerator.generateRandomNumber(new BigInteger("2"), p.subtract(BigInteger.ONE));
			if(euclidean.isRelativelyPrime(randomNumber, p) == false) {
				return false;
			}
			if( randomNumber.modPow(p.subtract(BigInteger.ONE), p).compareTo(BigInteger.ONE) != 0) {
				return false;
			}
		}
		return true;
	}
	
	public void runTheScenario() {
		
		System.out.println("15332 is prime?: " + isPrimeNumber(new BigInteger("15332"), 1000));
		
		System.out.println("1223 is prime?: " + isPrimeNumber(new BigInteger("1223"), 1000));
		
		System.out.println("3725376 is prime?: " + isPrimeNumber(new BigInteger("3725376"), 1000));
		
		System.out.println("736473 is prime?: " + isPrimeNumber(new BigInteger("736473"), 1000));
		
		System.out.println("128 is prime?: " + isPrimeNumber(new BigInteger("128"), 1000));
	
	}
	
}
