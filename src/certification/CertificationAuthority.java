package certification;

import java.math.BigInteger;

import agent.Authority;
import fieldarithmetic.ModularExponentiation;
import fieldarithmetic.RandomGenerator;
import file.FileHandler;
import signature.ElgamalSignature;

public class CertificationAuthority implements Authority{
	private String name;
	private BigInteger publicKey;
	private BigInteger privateKey;
	private BigInteger moduloPrime;
	private BigInteger generator;
	private String certificateFileName;
	
	private ElgamalSignature elgamal;
	
	public CertificationAuthority(String name, BigInteger moduloPrime, BigInteger generator) {
		this.name = name;
		this.moduloPrime = moduloPrime;
		this.generator = generator;
		initializeKeys();
		this.elgamal = new ElgamalSignature();
		this.certificateFileName = selfSign();
	}

	private void initializeKeys() {
		this.privateKey = RandomGenerator.generateRandomNumber(BigInteger.TWO, moduloPrime.subtract(BigInteger.ONE));
		this.publicKey = new ModularExponentiation().
								applyFastModularExponentiation(generator, privateKey, moduloPrime);
	}
	
	private String selfSign() {
		BigInteger[] domainParameters = new BigInteger[]{moduloPrime, generator};
		CertificateRequest cr = new CertificateRequest(name, publicKey, domainParameters);
		Certificate certWithoutSign = new Certificate(cr, name);
		BigInteger[] signature = elgamal.signMessage(certWithoutSign.toStringWithoutSignature(), moduloPrime, generator, privateKey);
		Certificate selfSigned = new Certificate(cr, name, signature);
		String fileName = "Certificate_"+name+".txt";
		FileHandler.writeToFile(fileName, selfSigned);
		return fileName;
	}
	
	public String giveCertificate(String requestFileName) {
		CertificateRequest cr = (CertificateRequest) FileHandler.readFromFile(requestFileName);
		Certificate certWithoutSignature = new Certificate(cr, name);
		BigInteger[] signature = elgamal.signMessage(certWithoutSignature.toStringWithoutSignature(), moduloPrime, generator, 
														privateKey);
		Certificate signed = new Certificate(cr, name, signature);
		String fileName = "Certificate_"+cr.getSubjectName()+".txt";
		FileHandler.writeToFile(fileName, signed);
		return fileName;
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
	
}

