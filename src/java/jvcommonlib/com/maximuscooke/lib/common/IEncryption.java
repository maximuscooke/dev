package com.maximuscooke.lib.common;

public interface IEncryption
{
	/**
	 * Encrypt object
	 * 
	 * @param key       encryption key string
	 * @param iteration encryption iteration count
	 */
	void encrypt(String key, int iteration);

	/**
	 * decrypt object
	 * 
	 * @param key       encryption key string
	 * @param iteration encryption iteration count
	 */
	void decrypt(String key, int iteration);
}
