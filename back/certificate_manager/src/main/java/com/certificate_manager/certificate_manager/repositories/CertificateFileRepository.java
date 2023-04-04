package com.certificate_manager.certificate_manager.repositories;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Repository;

import com.certificate_manager.certificate_manager.repositories.interfaces.ICertificateFileRepository;

@Repository
public class CertificateFileRepository implements ICertificateFileRepository {
	
	private static String CERTS_DIR = "data\\certs\\";
	private static String KEY_DIR = "data\\keys\\";
	
	@Override
	public void saveCertificateAsPEMFile(Object x509Certificate) throws IOException {
		  File directory = new File(CERTS_DIR);
		  String serialNumberStr = ((X509Certificate)x509Certificate).getSerialNumber().toString();
//		  File pemFile = File.createTempFile(serialNumberStr, ".crt", directory);
		  File pemFile = new File(directory, serialNumberStr + ".crt");
		  try (FileWriter pemfileWriter = new FileWriter(pemFile)) {
		    try (JcaPEMWriter jcaPEMWriter = new JcaPEMWriter(pemfileWriter)) {
		      jcaPEMWriter.writeObject(x509Certificate);
		    }
		  }
	}
	
	@Override
	public void savePrivateKeyAsPEMFile(Object privateKey, String serialNumber) throws IOException {
		  File directory = new File(KEY_DIR);
//		  File pemFile = File.createTempFile(serialNumber, ".key", directory);
		  File pemFile = new File(directory, serialNumber + ".key");

		  try (FileWriter pemfileWriter = new FileWriter(pemFile)) {
		    try (JcaPEMWriter jcaPEMWriter = new JcaPEMWriter(pemfileWriter)) {
		      jcaPEMWriter.writeObject(privateKey);
		    }
		  }
	}
	
	
	@Override
	public PrivateKey readPrivateKey(String serialNumber) throws Exception {
//		try (FileReader keyReader = new FileReader(KEY_DIR + serialNumber + ".key")) {
//
//	        PEMParser pemParser = new PEMParser(keyReader);
//	        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//	        System.out.println(pem);
//	        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());
//
//	        return (PrivateKey) converter.getPrivateKey(privateKeyInfo);
//	    }
		
//		byte[] key = Files.readAllBytes(Paths.get(KEY_DIR + serialNumber + ".key"));
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
//        return (PrivateKey)keyFactory.generatePrivate(keySpec);
		File directory = new File(CERTS_DIR);
		File file = new File(directory, serialNumber + ".crt");

		KeyFactory factory = KeyFactory.getInstance("RSA");

	    try (FileReader keyReader = new FileReader(file);
	      PemReader pemReader = new PemReader(keyReader)) {

	        PemObject pemObject = pemReader.readPemObject();
	        byte[] content = pemObject.getContent();
	        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
	        return (PrivateKey) factory.generatePrivate(privKeySpec);
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
