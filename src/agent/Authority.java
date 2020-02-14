package agent;

import certification.CertificateRequest;

public interface Authority {
	public String giveCertificate(String requestFileName);
}
