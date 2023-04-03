package com.certificate_manager.certificate_manager.repositories;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import com.certificate_manager.certificate_manager.repositories.interfaces.ICertificateFileRepository;

public class CertificateFileRepository implements ICertificateFileRepository {
	
	private static String CERTS_DIR = "data/certs/";
	private static String KEY_DIR = "data/keys/";
	
	@Override
	public void saveCertificateAsPEMFile(Object x509Certificate, String serialNumber) throws IOException {
		  File pemFile = File.createTempFile(CERTS_DIR + serialNumber + ".pem", null);
		  try (FileWriter pemfileWriter = new FileWriter(pemFile)) {
		    try (JcaPEMWriter jcaPEMWriter = new JcaPEMWriter(pemfileWriter)) {
		      jcaPEMWriter.writeObject(x509Certificate);
		    }
		  }
	}
	
	@Override
	public void savePrivateKeyAsPEMFile(Object privateKey, String serialNumber) throws IOException {
		  File pemFile = File.createTempFile(KEY_DIR + serialNumber + ".pem", null);
		  try (FileWriter pemfileWriter = new FileWriter(pemFile)) {
		    try (JcaPEMWriter jcaPEMWriter = new JcaPEMWriter(pemfileWriter)) {
		      jcaPEMWriter.writeObject(privateKey);
		    }
		  }
	}
	
	
	@Override
	public PrivateKey readPrivateKey(String serialNumber) throws Exception {
		try (FileReader keyReader = new FileReader(KEY_DIR + serialNumber + ".pem")) {

	        PEMParser pemParser = new PEMParser(keyReader);
	        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
	        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());

	        return (PrivateKey) converter.getPrivateKey(privateKeyInfo);
	    }
	}
	
	private X509Certificate readX509Certificate(String serialNumber) throws Exception {
	    Security.addProvider(new BouncyCastleProvider());
	    PEMParser pemParser = new PEMParser(new FileReader(CERTS_DIR + serialNumber + ".pem"));

	    JcaX509CertificateConverter x509Converter = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider());

	    X509Certificate certificate =x509Converter.getCertificate((X509CertificateHolder) pemParser.readObject());
	    
	    return certificate;
	}

	
	
}
