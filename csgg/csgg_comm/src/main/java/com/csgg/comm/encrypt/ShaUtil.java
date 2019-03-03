
package com.csgg.comm.encrypt;

public class ShaUtil {

	private static final ShaUtil shaUtil = new ShaUtil();

	private static final int HASH_INTERATIONS = 1024;

	private static  final String salt = "4636afc31e6cc891";

	public static ShaUtil newInstance() {
		return shaUtil;
	}
	
	/**
	 * 加密
	 * @param plain
	 * @return
	 */
	public String encrypt(String plain) {
		return Encodes.encodeHex(
			Digests.sha1(plain.getBytes(), Encodes.decodeHex(new String(salt)), HASH_INTERATIONS));
	}
}
