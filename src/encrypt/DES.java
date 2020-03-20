package encrypt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
 
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;




/**
 * @author Administrator
 *
 */
public class DES {
	private final byte[] DESIV = new byte[] { 0x12, 0x34, 0x56, 120, (byte) 0x90, (byte) 0xab, (byte) 0xcd, (byte) 0xef };// ����
	private final static String CHARSET = "utf-8";
	private AlgorithmParameterSpec iv = null;// �����㷨�Ĳ����ӿ�
	private Key key = null;
	private String charset =CHARSET;
	
	private static DES current;
	/**
	 * ��ʼ��
	 * @param deSkey	��Կ
	 * @throws Exception
	 */
	public DES(String deSkey, String charset) throws Exception {
		this.charset = charset;
		DESKeySpec keySpec = new DESKeySpec(deSkey.getBytes(this.charset));// ������Կ����
		iv = new IvParameterSpec(DESIV);// ��������
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// �����Կ����
		key = keyFactory.generateSecret(keySpec);// �õ���Կ����
	}
	public DES(String deSkey) throws Exception {
		DESKeySpec keySpec = new DESKeySpec(deSkey.getBytes(this.charset));// ������Կ����
		iv = new IvParameterSpec(DESIV);// ��������
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// �����Կ����
		key = keyFactory.generateSecret(keySpec);// �õ���Կ����
	}
	public static synchronized DES getDes(String deSkey) {
        if(current == null) {
            current = getDes(deSkey,CHARSET);
        }
        return current;
	}
	public static synchronized DES getDes(String deSkey, String charset) {
        if(current == null) {
        	try {
        		current = new DES(deSkey,charset);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }
        return current;
	}
	/**
	 * ����
	 * @author ershuai
	 * @date 2017��4��19�� ����9:40:53
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encode(String data) throws Exception {
		Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// �õ����ܶ���Cipher
		enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// ���ù���ģʽΪ����ģʽ��������Կ������
		byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
		return Base64.Encode(pasByte);
	}
	
	/**
	 * ����
	 * @author ershuai
	 * @date 2017��4��19�� ����9:41:01
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String decode(String data) throws Exception {
		Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		deCipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] pasByte = deCipher.doFinal(Base64.Decode(data));
		return new String(pasByte, "UTF-8");
	}
	
	public boolean encryptfile(String fromfilename, String tofilename,boolean bEncrypt) {
	    	File fromfile=new File(fromfilename);
	    	if(!fromfile.exists())return false;
	    	File tofile=new File(tofilename);
	    	if(tofile.exists())tofile.delete();
	    	try{
	            InputStream fosfrom = new FileInputStream(fromfilename);  
	            OutputStream fosto = new FileOutputStream(tofilename); 

	            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	            int opmode=Cipher.ENCRYPT_MODE;
	            if(!bEncrypt)opmode=Cipher.DECRYPT_MODE;
	            cipher.init(opmode, key, iv);
	            int filesize=fosfrom.available();
	            if(filesize<=1024){
	            	byte buf[] = new byte[filesize];
	            	int readsize=fosfrom.read(buf);
	            	if(readsize!=filesize){fosfrom.close();fosto.close();return false;}
	            	byte buf2[]= cipher.doFinal(buf);// ����
	            	fosto.write(buf2, 0, buf2.length);
	            }else{
	            	byte buf[] = new byte[1024];  
	            	byte buf2[] = new byte[filesize];  
	            	int c=0; 
	            	int p=0;
	            	while ((c = fosfrom.read(buf)) > 0)  
	            	{  
	            		System.arraycopy(buf, 0, buf2, p, c);
	            		p=p+c;
	            	} 
            		byte buf3[]= cipher.doFinal(buf2);// ����
            		fosto.write(buf3, 0, buf3.length);	            	
	            }
	            fosfrom.close();  
	            fosto.close(); 
	            return true;
	    	}
	        catch(Exception e){
	        	e.printStackTrace();
	        	return false;
	        }
	    }
	

}