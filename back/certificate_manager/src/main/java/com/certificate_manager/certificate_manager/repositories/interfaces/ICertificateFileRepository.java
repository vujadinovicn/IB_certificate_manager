package com.certificate_manager.certificate_manager.repositories.interfaces;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface ICertificateFileRepository {

	public void saveCertificateAsPEMFile(Object x509Certificate) throws IOException;

	public void savePrivateKeyAsPEMFile(Object privateKey, String serialNumber) throws IOException;

	public PrivateKey readPrivateKey(String serialNumber) throws Exception;

	public X509Certificate readX509Certificate(String serialNumber) throws Exception;

}
