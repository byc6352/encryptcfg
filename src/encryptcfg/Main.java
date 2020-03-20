package encryptcfg;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Adler32;

import encrypt.DES;

public class Main {

	private static String WORK_PATH="";//工作目录
	private static String DEX_CFG_FIILE="dex.cfg";//参数配置文件
	
	private static String KEY="9ba45bfd500642328ec03ad8ef1b6e75";
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
			EncryptDexCfgFile();
		}catch(Exception e){
			System.out.println("cfg.jar error:"+e.toString());
		}
	}
	private static void EncryptDexCfgFile(){
		try{
			String rawDir="m/res/raw";//
			File rawDirFile=new File(rawDir);
			if(!rawDirFile.exists())rawDirFile.mkdir();
			String rawFilename="m/res/raw/b.pcm";//
			File rawFile=new File(rawFilename);
			if(rawFile.exists())rawFile.delete();
			String dexCfgFilename=WORK_PATH+DEX_CFG_FIILE;//参数配置文件
			DES des = DES.getDes( KEY);
			des.encryptfile(dexCfgFilename,rawFilename , true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
