package io.nathanfriend.coms535.pa1.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.nathanfriend.coms535.pa1.IBloomFilter;

@Disabled
public abstract class BloomFilterTest {

	protected IBloomFilter bloomFilter;

	@BeforeEach
	protected abstract void setup();

	@Test
	void testFilter() {
		String[] states = new String[] { "Tennessee", "Kentucky", "Nevada", "Florida", "South Carolina", "Ohio",
				"Illinois", "Wyoming", "New Hampshire", "Delaware", "West Virginia", "Kansas", "Iowa", "Idaho",
				"Louisiana", "Alabama", "Nebraska", "Minnesota", "Wisconsin", "North Carolina", "Maine", "New Jersey",
				"Arizona", "South Dakota", "Rhode Island" };
		for (String s : states) {
			bloomFilter.add(s);
		}

		// this tests that all of the states added to the filter
		// above return a value of "true" when queried. The .appears()
		// function must *always* return true in these cases.
		for (String s : states) {
			assertEquals(true, bloomFilter.appears(s));
		}

		// this tests that values *not* added to the filter
		// return false. The .appears() function will *usually*
		// return false, but it's possible it will return true
		// and fail this test.
		String[] notIncludedStates = new String[] { "Montana", "Colorado", "Alaska", "Georgia", "Arkansas",
				"Massachusetts", "Michigan", "Mississippi", "Utah", "New York", "Pennsylvania", "Indiana", "Oregon",
				"New Mexico", "Washington", "North Dakota", "Hawaii", "Vermont", "Connecticut", "Maryland", "Oklahoma",
				"Missouri", "Virginia", "Texas", "California" };
		for (String s : notIncludedStates) {
			assertEquals(false, bloomFilter.appears(s),
					"The string \"" + s + "\" was not added to the filter, but .appears() returned \"true\"");
		}

		System.out.println(bloomFilter.toString());
	}

	@Test
	void testProperties() {
		String[] states = new String[] { "Tennessee", "Kentucky", "Nevada", "Florida", "South Carolina" };
		for (String s : states) {
			bloomFilter.add(s);
		}

		assertEquals(5, bloomFilter.dataSize());
		assertEquals(12, bloomFilter.numHashes());
		assertEquals(50 * 16, bloomFilter.filterSize());
	}
}
