package tituloDigitalFirma;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tituloDigital.firma.CertUtils;


public class firmaElectronicaTest {
	
	private File llaveFile;
	private File cerFile;
	
	@Before
	public void init(){
		llaveFile = new File("C:\\Users\\ricgu\\Desktop\\FIEL_GUOR921127F46_20170627084440\\Claveprivada_FIEL_GUOR921127F46_20170627_084440.key");
		cerFile = new File("C:\\Users\\ricgu\\Desktop\\FIEL_GUOR921127F46_20170627084440\\guor921127f46.cer");
	}
	
	
	
	@Test
	public void firmarVerificarCadena() throws IOException {
		
		byte[]  llaveBytes = FileUtils.readFileToByteArray(llaveFile);				
		String pass = "";		
		String cadenPrueba= "test";
		
		PrivateKey pkey = CertUtils.getPrivateKey(llaveBytes, pass);
		String signatureBase64 = CertUtils.signSha256ConRsa(cadenPrueba, pkey);
		System.out.println("Cadena prueba \n"+ cadenPrueba);
		System.out.println("Cadena firmada \n"+ signatureBase64);
		
		X509Certificate cer = CertUtils.getCertificate(cerFile);
		boolean verify = CertUtils.verifySha256ConRsa(cadenPrueba, signatureBase64, cer);
		System.out.println("verify :"+verify);
		
		assertTrue(verify);		
	}
	
	

	@Test
	public void muestraCertificado(){
		
		X509Certificate cer = CertUtils.getCertificate(cerFile);
		
		System.out.println("Subject DN : " + cer.getSubjectDN().getName());
        System.out.println("Issuer : " + cer.getIssuerDN().getName());
        System.out.println("Not After: " + cer.getNotAfter());
        System.out.println("Not Before: " + cer.getNotBefore());
        System.out.println("version: " + cer.getVersion());
        System.out.println("serial number : " + cer.getSerialNumber());

        System.out.println("PublicKey : " );
		System.out.println(cer.getPublicKey());
		
		
		int pathLen = cer.getBasicConstraints();
		if(pathLen != -1){
		    throw new RuntimeException("El certificado no es un CSD, posee el atributo de Autoridad Certificadora (CA)");
		}

		if(CertUtils.getSerial(cer.getSerialNumber()).length() != 20){
		    throw new RuntimeException("El numero de serie del certificado debe contener 20 caracteres");
		}
		

		assertNotNull(cer);

	}
}
