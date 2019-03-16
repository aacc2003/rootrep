
package com.csgg.comm.encrypt;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

public class ShaUtil {
	
	// sha256
	 public static String  sha256(String ori){
	        MessageDigest md;
	        try {
	            md = MessageDigest.getInstance("SHA-256");

	            md.update(ori.getBytes("UTF-8"));
	            String res=new String(Hex.encodeHex(md.digest()));
	            return res;
	        } catch (Exception e) {
	            throw new RuntimeException( "SHA256失败："+e.getMessage());
	        }
	    }

	public static void main(String[] args) {
		System.out.println("sha256:"+ShaUtil.sha256("kdfwposdf82nhng"));
	}
}
