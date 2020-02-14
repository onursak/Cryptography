package certification;

import java.math.BigInteger;

import signature.ElgamalSignature;


public class Certificate extends CertificateRequest{
	
	private String caName;
	private BigInteger[] signature;
	
	public Certificate(CertificateRequest cr, String caName){
		super(cr.getSubjectName(), cr.getSubjectPublicKey(), cr.getDomainParameters());
		this.caName = caName;
	}
	
	public Certificate(CertificateRequest cr, String caName, BigInteger[] signature){
		super(cr.getSubjectName(), cr.getSubjectPublicKey(), cr.getDomainParameters());
		this.caName = caName;
		this.signature = signature;
	}
	

	public String getCaName() {
		return caName;
	}


	public void setCaName(String caName) {
		this.caName = caName;
	}


	public BigInteger[] getSignature() {
		return signature;
	}
	
	// this method for signing the document and verifying the signature
	public String toStringWithoutSignature() {
		return caName + "\n" +
			   super.toString();
	}
	
	public String toString() {
		return caName + "\n" +
			   super.toString() +
			   signature[0] + " " + signature[1];
	}
	
	
}
