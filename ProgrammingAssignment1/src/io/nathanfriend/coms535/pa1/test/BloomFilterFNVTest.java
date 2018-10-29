package io.nathanfriend.coms535.pa1.test;

import org.junit.jupiter.api.BeforeEach;
import io.nathanfriend.coms535.pa1.BloomFilterFNV;

public class BloomFilterFNVTest extends BloomFilterTest {

	@Override
	@BeforeEach
	protected void setup() {
		bloomFilter = new BloomFilterFNV(50, 16);
	}

}
