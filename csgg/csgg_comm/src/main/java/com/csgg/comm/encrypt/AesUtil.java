
package com.csgg.comm.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class AesUtil {
    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";
    /**
     * 字符集
     */
    public static final String CHARSET = "UTF-8";
    /**
     * 加密、解密算法 / 工作模式 / 填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 转换密钥
     * @param key 二进制密钥
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private static Key toKey(byte[] key) throws Exception{
        //实例化密钥材料
        SecretKey secretKey = new SecretKeySpec(key,KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 转换密钥
     * @param key 二进制密钥
     * @param keyAlgorithm 密钥算法
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key,String keyAlgorithm) throws Exception{

        keyAlgorithm = Strings.isNotBlank(keyAlgorithm)?keyAlgorithm:KEY_ALGORITHM;
        //实例化密钥材料
        SecretKey secretKey = new SecretKeySpec(key,keyAlgorithm);
        return secretKey;
    }

    /**
     * 加密
     * @param data 待加密数据
     * @param key 密钥
     * @param cipherAlgorithm 加密、解密算法 / 工作模式 / 填充方式
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key ,String keyAlgorithm,String cipherAlgorithm) throws Exception {

        keyAlgorithm = Strings.isNotBlank(keyAlgorithm)?keyAlgorithm:KEY_ALGORITHM;

        cipherAlgorithm = Strings.isNotBlank(cipherAlgorithm)?cipherAlgorithm:CIPHER_ALGORITHM;
        //还原密钥
        Key k = toKey(key,keyAlgorithm);
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //初始化，设置为解密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行解密操作
        return cipher.doFinal(data);
    }
    /**
     * 加密
     * @param data 待加密数据
     * @param key 密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        return encrypt(data , key, KEY_ALGORITHM ,CIPHER_ALGORITHM);
    }
    /**
     * 解密
     * @param data 待解密数据
     * @param key 密钥
     * @param cipherAlgorithm 加密、解密算法 / 工作模式 / 填充方式
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,byte[] key , String keyAlgorithm, String cipherAlgorithm) throws Exception{

        keyAlgorithm = Strings.isNotBlank(keyAlgorithm)?keyAlgorithm:KEY_ALGORITHM;

        cipherAlgorithm = Strings.isNotBlank(cipherAlgorithm)?cipherAlgorithm:CIPHER_ALGORITHM;
        //还原密钥
        Key k = toKey(key,keyAlgorithm);
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行解密操作
        return cipher.doFinal(data);
    }
    /**
     * 解密
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        return decrypt(data , key,KEY_ALGORITHM ,CIPHER_ALGORITHM);
    }

    /**
     * 生成密钥
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws Exception{
        //实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES 要求密钥长度为128位、192位或256位
        kg.init(128);
        //生成秘密密钥
        SecretKey secretKey = kg.generateKey();
        //获取密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    /**
     * 密钥
     * @return 返回16进制字符串的KEY
     * @throws Exception
     */
    public static String getKey() throws Exception {
        return ByteUtil.byteArr2HexStr(initKey());
    }


    /**
     * AES加密
     * @param sourceDatas
     * @param keyText
     * @throws Exception
     */
    public static String enCodeByAES(String sourceDatas, String keyText) throws Exception {
        return enCodeByAES(sourceDatas.getBytes(CHARSET),ByteUtil.hexStr2ByteArr(keyText),KEY_ALGORITHM,CIPHER_ALGORITHM);
    }

    /**
     * AES加密
     * @param sourceDatas
     * @param keyText
     */
    public static String enCodeByAES(byte[] sourceDatas, byte[] keyText) throws Exception{
        return enCodeByAES(sourceDatas,keyText,KEY_ALGORITHM,CIPHER_ALGORITHM);
    }

    /**
     * AES加密
     * @param sourceDatas
     * @param keyText
     * @param cipherAlgorithm 加密、解密算法 / 工作模式 / 填充方式
     */
    public static String enCodeByAES(byte[] sourceDatas, byte[] keyText , String keyAlgorithm,String cipherAlgorithm ) throws Exception{
        String enCode = "";
        byte[] encryptData = encrypt(sourceDatas, keyText,keyAlgorithm,cipherAlgorithm);
        enCode = ByteUtil.byteArr2HexStr(encryptData);
        return enCode;
    }

    /**
     * AES解密
     * @param secertDatas
     * @param keyText
     */
    public static String deCodeByAES(byte[] secertDatas, byte[] keyText) throws Exception{
        return deCodeByAES(secertDatas,keyText,KEY_ALGORITHM,CIPHER_ALGORITHM);
    }

    /**
     * AES解密
     * @param secertDatas
     * @param keyText
     */
    public static String deCodeByAES(String secertDatas, String keyText) throws Exception {
        return deCodeByAES(ByteUtil.hexStr2ByteArr(secertDatas),ByteUtil.hexStr2ByteArr(keyText),KEY_ALGORITHM,CIPHER_ALGORITHM);
    }

    /**
     * AES解密
     * @param secretDatas
     * @param keyText
     * @param cipherAlgorithm 加密、解密算法 / 工作模式 / 填充方式
     */
    public static String deCodeByAES(byte[] secretDatas, byte[] keyText , String keyAlgorithm,String cipherAlgorithm ) throws Exception{
        String deCode;
        byte[] decryptData = decrypt(secretDatas, keyText,keyAlgorithm,cipherAlgorithm);
        deCode =new String(decryptData,CHARSET);
        return deCode;
    }

    public static  void main(String[] args) throws Exception {
        System.out.println(getKey());
    }
}
