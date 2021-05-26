package com.maximuscooke.lib.common;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public abstract class CEncryption
{
	private static String mSecretKeyFactory = "PBEWithMD5AndDES";

	private static Cipher ecipher;
	private static Cipher dcipher;
	private static byte[] mKeyByte = { (byte) 0x19, (byte) 0x7B, (byte) 0x68, (byte) 0x32, (byte) 0xA6, (byte) 0x85, (byte) 0xE4, (byte) 0x13 };
	private static final int mIterationCount = 24;

	/**
	 *
	 * @param secretKey Key used to encrypt data
	 * @param plainText Text input to be encrypted
	 * @return Returns encrypted text
	 * @throws Exception if encryption caused error
	 */
	public static String encrypt(String secretKey, String plainText) throws Exception
	{
		return encrypt(secretKey, plainText, mIterationCount);
	}

	public static String encrypt(String secretKey, String plainText, int iterationCount) throws Exception
	{
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), mKeyByte, iterationCount);

		SecretKey key = SecretKeyFactory.getInstance(mSecretKeyFactory).generateSecret(keySpec);

		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(mKeyByte, iterationCount);

		ecipher = Cipher.getInstance(key.getAlgorithm());

		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

		String charSet = "UTF-8";

		byte[] in = plainText.getBytes(charSet);

		byte[] out = ecipher.doFinal(in);

		String encStr = new String(Base64.getEncoder().encode(out));

		return encStr;
	}

	/**
	 * @param secretKey     Key used to decrypt data
	 * @param encryptedText encrypted text input to decrypt
	 * @return Returns plain text after decryption
	 * @throws Exception if encryption caused error
	 */
	public static String decrypt(String secretKey, String encryptedText) throws Exception
	{
		return decrypt(secretKey, encryptedText, mIterationCount);
	}

	public static String decrypt(String secretKey, String encryptedText, int iterationCount) throws Exception
	{
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), mKeyByte, iterationCount);

		SecretKey key = SecretKeyFactory.getInstance(mSecretKeyFactory).generateSecret(keySpec);

		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(mKeyByte, iterationCount);

		dcipher = Cipher.getInstance(key.getAlgorithm());

		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		byte[] enc = Base64.getDecoder().decode(encryptedText);

		byte[] utf8 = dcipher.doFinal(enc);

		String charSet = "UTF-8";

		String plainStr = new String(utf8, charSet);

		return plainStr;
	}
}
