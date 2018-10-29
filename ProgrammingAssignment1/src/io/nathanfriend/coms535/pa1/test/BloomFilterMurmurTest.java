package io.nathanfriend.coms535.pa1.test;

import org.junit.jupiter.api.BeforeEach;
import io.nathanfriend.coms535.pa1.BloomFilterMurmur;

public class BloomFilterMurmurTest extends BloomFilterTest {

	@Override
	@BeforeEach
	protected void setup() {
		bloomFilter = new BloomFilterMurmur(50, 16);
	}
}
