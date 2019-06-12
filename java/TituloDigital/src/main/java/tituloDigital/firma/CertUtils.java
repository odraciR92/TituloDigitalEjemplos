package tituloDigital.firma;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.ssl.PKCS8Key;

public class CertUtils {
	//CertUtils.java
	public static final String TEST_MSG = "test_message";
	public static final String UnstructuredName = "unstructuredName";
	
	
	public static PrivateKey getPrivateKey(byte[] encryptedKey, String passphrase){
	    try {
	    	PKCS8Key pkcs8 = new PKCS8Key(encryptedKey, passphrase.toCharArray());	        
	        return  pkcs8.getPrivateKey();
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Clave del archivo privado (archivo key) inv�lida");
	    }
	}

	public static X509Certificate getCertificate(File file){
	    InputStream is = null;
	    try {
	    	byte [] cerBytes = FileUtils.readFileToByteArray(file);
	    	StringBuilder sb = new StringBuilder();
	        for (byte b : cerBytes) {
	            sb.append(String.format("%02X", b));
	        }
	        System.out.println(sb.toString());
	        
	        is = new FileInputStream(file);
	        CertificateFactory cf = CertificateFactory.getInstance("X.509");
	        X509Certificate cert = (X509Certificate)cf.generateCertificate(is);
	        return cert;
	    } catch (Exception ex) {
	        throw new RuntimeException(ex);
	    } finally{
	        try {
	            is.close();
	        } catch (IOException e) { }
	    }
	}

	public static String getSerial(BigInteger big){
	    String hex = big.toString(16);
	    
	    if(hex.length() % 2 != 0){
	        return "";
	    }
	    
	    StringBuilder serial = new StringBuilder();
	    int pares = hex.length() / 2;
	    for(int i=0; i<pares; i++){
	        serial.append((char)Integer.parseInt(hex.substring(2*i, (2*i)+2), 16));
	    }
	    
	    return serial.toString();
	}

	public static String signSha1ConRsa(String datos, PrivateKey pkey) {
	    try{ return CertUtils.signSha1ConRsa(datos.getBytes("UTF8"), pkey); } catch(UnsupportedEncodingException ex){ ex.printStackTrace(); throw new RuntimeException(ex);}
	}

	public static String signSha1ConRsa(byte[] datos, PrivateKey pkey){
	    return CertUtils.sign("SHA1withRSA", datos, pkey);
	}

	public static String signSha256ConRsa(String datos, PrivateKey pkey) {
	    try{ return CertUtils.signSha256ConRsa(datos.getBytes("UTF8"), pkey); } catch(UnsupportedEncodingException ex){ ex.printStackTrace(); throw new RuntimeException(ex);}
	}

	public static String signSha256ConRsa(byte[] datos, PrivateKey pkey){
	    return CertUtils.sign("SHA256withRSA", datos, pkey);
	}

	/** Firma o sella digitalmente datos usando el algoritmo indicado con la llave privada proporcionada, retorna el resultado en Base64 */
	public static String sign(String algorithm, byte[] datos, PrivateKey pkey){
	    try {
	        Signature signer = Signature.getInstance(algorithm);
	        signer.initSign(pkey);
	        signer.update(datos);
	        return CertUtils.trimBase64(CertUtils.toBase64(signer.sign()));
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	private static String trimBase64(byte[] base64) {
		return new String(base64).trim();
	}

	private static byte[] toBase64(byte[] sign) {
		return Base64.getEncoder().encode(sign);
	}

	public static boolean verifySha1ConRsa(String plainMsg, String signatureBase64, Certificate certificate){
	    try{ 
	        return CertUtils.verifySha1ConRsa(plainMsg.getBytes("UTF8"), signatureBase64, certificate); 
	    } catch(UnsupportedEncodingException ex){ ex.printStackTrace(); throw new RuntimeException(ex);}
	}

	public static boolean verifySha1ConRsa(byte[] plainMsg, String signatureBase64, Certificate certificate){
	    return CertUtils.verify("SHA1withRSA", plainMsg, signatureBase64, certificate);
	}

	public static boolean verifySha256ConRsa(String plainMsg, String signatureBase64, Certificate certificate){
	    try{ 
	        return CertUtils.verifySha256ConRsa(plainMsg.getBytes("UTF8"), signatureBase64, certificate); 
	    } catch(UnsupportedEncodingException ex){ ex.printStackTrace(); throw new RuntimeException(ex);}
	}

	public static boolean verifySha256ConRsa(byte[] plainMsg, String signatureBase64, Certificate certificate){
	    return CertUtils.verify("SHA256withRSA", plainMsg, signatureBase64, certificate);
	}

	public static boolean verify(String algorithm, byte[] plainMsg, String signatureBase64, Certificate certificate){
	    return CertUtils.verify(algorithm, plainMsg, CertUtils.fromBase64(signatureBase64), certificate);
	}

	private static byte[] fromBase64(String signatureBase64) {		
		return Base64.getDecoder().decode(signatureBase64);
	}

	public static boolean verify(String algorithm, byte[] plainMsg, byte[] signature, Certificate certificate){
	    try {
	        Signature signer = Signature.getInstance(algorithm);
	        signer.initVerify(certificate);
	        signer.update(plainMsg);
	        return signer.verify(signature);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	public static String getAttribute(X500PrincipalHelper principal, String attrName){
	    List values = principal.getAllValues(attrName);
	    return values.size() > 0 ? values.get(0).toString() : null;
	}

}
