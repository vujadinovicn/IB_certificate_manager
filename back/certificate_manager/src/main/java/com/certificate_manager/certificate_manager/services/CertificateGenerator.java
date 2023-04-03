package com.certificate_manager.certificate_manager.services;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.certificate_manager.certificate_manager.entities.Certificate;
import com.certificate_manager.certificate_manager.entities.CertificateRequest;
import com.certificate_manager.certificate_manager.repositories.CertificateFileRepository;
import com.certificate_manager.certificate_manager.repositories.CertificateRepository;
import com.certificate_manager.certificate_manager.services.interfaces.ICertificateGenerator;

public class CertificateGenerator implements ICertificateGenerator {
	
	@Autowired
	private CertificateFileRepository fileRepository;
	
	@Autowired
	private CertificateRepository allCertificates;
	
	public CertificateGenerator() {
		
	}
	
	public X509Certificate generateCertificate(CertificateRequest request) {
		try {
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			builder = builder.setProvider("BC");
			
			PrivateKey issuerPrivateKey = fileRepository.readPrivateKey(Long.toString(request.getIssuerSerialNumber()));
			
			ContentSigner contentSigner = builder.build(issuerPrivateKey);
			
			//TODO: OVDE TREBA NEKI PAMETNIJI NACIN KAO STO JE REKAO - NEKI API ZA TACNO VREME, ILI JE OVO OK?
			LocalDateTime validFrom = LocalDateTime.now().toLocalDate().atStartOfDay();
			
			KeyPair keys = generateKeyPair();
			
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
                    getIssuerData(request),
                    // stavila sam uuid zasad da bude serial num
                    new BigInteger(UUID.randomUUID().toString()),
                    toDate(validFrom),
                    toDate(request.getValidTo()),
                    getSubjectData(request),
                    keys.getPublic()
					);
			
			X509CertificateHolder certHolder = certGen.build(contentSigner);
			
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");
            
            //TODO: sacuvati u bazu    
            //TODO: sacuvati na disk .cer
            //TODO: sacuvati na disk .key (keys.getPrivate())
            
            return certConverter.getCertificate(certHolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO: izmestiti ovo da bude clean negde drugde
	private Date toDate(LocalDateTime localDateTime) {
		ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
		Date output = Date.from(zdt.toInstant());
		
		return output;
	}
	
	private KeyPair generateKeyPair() {
		try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	
	private X500Name getIssuerData(CertificateRequest request) {
		//TODO: DODATI EXCEPTION ako ne valja
		Certificate issuer = allCertificates.findBySerialNumber(request.getIssuerSerialNumber()).orElse(null);
		
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, issuer.getIssuedTo().getName() + issuer.getIssuedTo().getLastname());
        builder.addRDN(BCStyle.SURNAME, issuer.getIssuedTo().getLastname() );
        builder.addRDN(BCStyle.GIVENNAME, issuer.getIssuedTo().getName());
        builder.addRDN(BCStyle.E, issuer.getIssuedTo().getEmail());

        //TODO: kaze "UID (USER ID) je ID korisnika", je l to serial number? - pocek kaze id korisnika
        builder.addRDN(BCStyle.UID, Long.toString(issuer.getIssuedTo().getId()));
		
        return builder.build();
	}
	
	private X500Name getSubjectData(CertificateRequest request) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, request.getRequester().getName() + request.getRequester().getLastname());
        builder.addRDN(BCStyle.SURNAME, request.getRequester().getLastname());
        builder.addRDN(BCStyle.GIVENNAME, request.getRequester().getName());
        builder.addRDN(BCStyle.E, request.getRequester().getEmail());

        // UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, Long.toString(request.getRequester().getId()));
       
        return builder.build();
	}

}
