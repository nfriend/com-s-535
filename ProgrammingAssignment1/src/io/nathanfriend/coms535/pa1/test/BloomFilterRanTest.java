package io.nathanfriend.coms535.pa1.test;

import org.junit.jupiter.api.BeforeEach;
import io.nathanfriend.coms535.pa1.BloomFilterRan;

public class BloomFilterRanTest extends BloomFilterTest {

	@Override
	@BeforeEach
	protected void setup() {
		bloomFilter = new BloomFilterRan(50, 16);
	}
}
