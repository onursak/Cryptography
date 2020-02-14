package certification;

import java.math.BigInteger;

public class CertificateRequest {
	
	private String subjectName;
	private BigInteger subjectPublicKey;
	private BigInteger[] domainParameters;
	
	public CertificateRequest(String subjectName, 
			BigInteger subjectPublicKey, BigInteger[] domainParameters){
		
		this.subjectName = subjectName;
		this.subjectPublicKey = subjectPublicKey;
		this.domainParameters = domainParameters;
	}

	public String getSubjectName() {
		return subjectName;
	}


	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}


	public BigInteger getSubjectPublicKey() {
		return subjectPublicKey;
	}

	public void setSubjectPublicKey(BigInteger subjectPublicKey) {
		this.subjectPublicKey = subjectPublicKey;
	}

	public BigInteger[] getDomainParameters() {
		return domainParameters;
	}

	public void setDomainParameters(BigInteger[] domainParameters) {
		this.domainParameters = domainParameters;
	}
	
	public String toString() {
		return  subjectName + "\n" + 
				subjectPublicKey.toString() + "\n"
				+ domainParameters[0] + " " + domainParameters[1] + "\n";
		
	}
	
	
}
