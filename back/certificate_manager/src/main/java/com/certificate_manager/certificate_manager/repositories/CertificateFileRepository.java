package com.certificate_manager.certificate_manager.repositories;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.stereotype.Repository;

import com.certificate_manager.certificate_manager.repositories.interfaces.ICertificateFileRepository;

import jakarta.persistence.criteria.Path;

@Repository
public class CertificateFileRepository implements ICertificateFileRepository {
	
	private static String CERTS_DIR = "data\\certs\\";
	private static String KEY_DIR = "data\\keys\\";
	
	@Override
	public void saveCertificateAsPEMFile(Object x509Certificate) throws IOException {
		  File directory = new File(CERTS_DIR);
		  String serialNumberStr = ((X509Certificate)x509Certificate).getSerialNumber().toString();
		  File pemFile = File.createTempFile(serialNumberStr, ".crt", directory);
		  try (FileWriter pemfileWriter = new FileWriter(pemFile)) {
		    try (JcaPEMWriter jcaPEMWriter = new JcaPEMWriter(pemfileWriter)) {
		      jcaPEMWriter.writeObject(x509Certificate);
		    }
		  }
	}
	
	@Override
	public void savePrivateKeyAsPEMFile(Object privateKey, String serialNumber) throws IOException {
		  File directory = new File(KEY_DIR);
		  File pemFile = File.createTempFile(serialNumber, ".key", directory);
		  try (FileWriter pemfileWriter = new FileWriter(pemFile)) {
		    try (JcaPEMWriter jcaPEMWriter = new JcaPEMWriter(pemfileWriter)) {
		      jcaPEMWriter.writeObject(privateKey);
		    }
		  }
	}
	
	
	@Override
	public PrivateKey readPrivateKey(String serialNumber) throws Exception {
		try (FileReader keyReader = new FileReader(KEY_DIR + serialNumber + ".key")) {

	        PEMParser pemParser = new PEMParser(keyReader);
	        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
	        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());

	        return (PrivateKey) converter.getPrivateKey(privateKeyInfo);
	    }
	}
	
	@Override
	public X509Certificate readX509Certificate(String serialNumber) throws Exception {
	    Security.addProvider(new BouncyCastleProvider());
	    try (FileReader certReader = new FileReader(CERTS_DIR + serialNumber + ".crt")) {
		    PEMParser pemParser = new PEMParser(certReader);
	
		    JcaX509CertificateConverter x509Converter = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider());
	
		    X509Certificate certificate = x509Converter.getCertificate((X509CertificateHolder) pemParser.readObject());
		    
		    return certificate;
	    }
	}

	
	
}
