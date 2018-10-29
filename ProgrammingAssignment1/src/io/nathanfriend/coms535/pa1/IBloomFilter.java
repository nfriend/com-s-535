package io.nathanfriend.coms535.pa1;

/**
 * A Bloom filter
 * @author Nathan Friend
 */
public interface IBloomFilter {
	
	/**
	 * Adds a string to the filter
	 * @param s The string to add to the filter 
	 */
	void add(String s);
	
	/**
	 * Determines whether or not the given string
	 * has been added to the filter
	 * @param s The string to test
	 * @return false if the string has not been added
	 * to the filter.  true if the string has (probably)
	 * been added to the filter.
	 */
	boolean appears(String s);
	
	/**
	 * The size of the filter, in bits
	 * @return The size of the filter, in bits
	 */
	int filterSize();
	
	/**
	 * Gets the number of elements that have been added to this filter
	 * @return The number of elements added to this filter
	 */
	int dataSize();
	
	/**
	 * Gets the number of hash functions this filter uses
	 * @return The number of hash functions used by this filter
	 */
	int numHashes();
}
