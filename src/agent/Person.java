package agent;
import java.math.BigInteger;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import certification.Certificate;
import certification.CertificateRequest;
import certification.CertificationAuthority;
import exceptions.InvalidCertificateException;
import exceptions.InvalidSignatureException;
import exchange.DiffieHellman;
import fieldarithmetic.ModularExponentiation;
import fieldarithmetic.RandomGenerator;
import file.FileHandler;
import hash.HashGenerator;
import signature.ElgamalSignature;
import symmetric.AES;

public class Person {
	private String name;
	private BigInteger publicKey;
	private BigInteger privateKey;
	private SecretKey secretKey;
	private BigInteger moduloPrime;
	private BigInteger generator;
	
	private String certificateFileName;
	private Certificate certificateOfOther;
	private Certificate certificateOfAuthority;
	
	
	private ElgamalSignature es = new ElgamalSignature();
	private DiffieHellman dh = new DiffieHellman();
	
	public Person(String name, BigInteger moduloPrime, BigInteger generator){
		this.name = name;
		this.moduloPrime = moduloPrime;
		this.generator = generator;
		initializeKeys();
	}
	
	private void initializeKeys() {
		this.privateKey = RandomGenerator.generateRandomNumber(BigInteger.TWO, moduloPrime.subtract(BigInteger.ONE));
		this.publicKey = new ModularExponentiation().
								applyFastModularExponentiation(generator, privateKey, moduloPrime);
	}
	
	public BigInteger[] getDomainParameters() {
		return new BigInteger[] {moduloPrime, generator};
	}
	
	
	public String getName() {
		return name;
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public BigInteger getModuloPrime() {
		return moduloPrime;
	}

	public BigInteger getGenerator() {
		return generator;
	}

	public String getCertificateFileName() {
		return certificateFileName;
	}

	public String requestCertificate(CertificationAuthority ca) {
		CertificateRequest cr = new CertificateRequest(name, publicKey, getDomainParameters());
		String requestFileName = "CertificateRequest_"+name+".txt";
		FileHandler.writeToFile(requestFileName, cr);
		String fileName = ca.giveCertificate(requestFileName);
		this.certificateFileName = fileName;
		return fileName;
	}
	
	public boolean learnAuthority(String certificateOfAuthority) {
		Certificate ca = (Certificate) FileHandler.readFromFile(certificateOfAuthority);
		BigInteger[] caDomain = ca.getDomainParameters();
		if(es.verifySignature(ca.toStringWithoutSignature(), ca.getSignature(), 
				caDomain[0], caDomain[1], ca.getSubjectPublicKey()) == false) {
			throw new InvalidCertificateException("Invalid certificaton authority certificate!");
		}
		this.certificateOfAuthority = ca;
		return true;
	}
	
	public boolean exchangeCertificates(String certificateOfOther) {
		Certificate co = (Certificate) FileHandler.readFromFile(certificateOfOther);
		//certificate verification of other person that we want to communicate
		BigInteger[] caDomain = certificateOfAuthority.getDomainParameters();

		if(es.verifySignature(co.toStringWithoutSignature(), co.getSignature(), 
				caDomain[0], caDomain[1], certificateOfAuthority.getSubjectPublicKey()) == false) {
			throw new InvalidCertificateException("Invalid person certificate!");
		}
		this.certificateOfOther = co;
		return true;
	}
	
	public void createCommonSecret() {
		if(certificateOfAuthority == null || certificateOfOther == null) {
			throw new InvalidCertificateException("Invalid certificate!");
		}
		BigInteger[] coDomain = certificateOfOther.getDomainParameters();
		BigInteger commonValue = dh.exchangeKeys(coDomain[0], coDomain[1], certificateOfOther.getSubjectPublicKey(), privateKey);
		byte[] hashedCommon = new HashGenerator().hashTheMessage(commonValue.toString());
		this.secretKey = new SecretKeySpec(hashedCommon, "AES");
	}
	
	private String concatanateMessageAndSign(String message, BigInteger[] signature) {
		return message + "ElgamalSignature:" + signature[0] + "," + signature[1];
	}
	
	private BigInteger[] getSignatureFromString(String message) {
		String[] splitted = message.split("ElgamalSignature:");
		String[] signature = splitted[1].split(",");
		return new BigInteger[] {new BigInteger(signature[0]), new BigInteger(signature[1])};
	}
	
	private String getMessageFromString(String message) {
		String[] splitted = message.split("ElgamalSignature:");
		return splitted[0];
	}
	
	public String sendMessage(String message) {
		BigInteger[] signatureOfMessage = es.signMessage(message, moduloPrime, generator, privateKey);
		String finalMessage = concatanateMessageAndSign(message, signatureOfMessage);
		return new AES().encryptTheMessage(finalMessage, secretKey);
	}
	
	public String takeMessage(String message) {
		//dividing taken message as message and signature part
		String takenMessage = new AES().decryptTheMessage(message, secretKey);
		String messagePart = getMessageFromString(takenMessage);
		BigInteger[] signature = getSignatureFromString(takenMessage);
		//signature verification phase
		BigInteger[] coDomain = certificateOfOther.getDomainParameters();
		if(es.verifySignature(messagePart, signature, coDomain[0], coDomain[1], 
					certificateOfOther.getSubjectPublicKey()) == false) {
			throw new InvalidSignatureException();
		}
		
		return messagePart;
	}

}
