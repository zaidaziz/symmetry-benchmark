package org.biojava3.benchmark;


import java.util.List;
import junit.framework.TestCase;
import org.biojava.bio.structure.align.client.PdbPair;


public class TestFisherBenchmark extends TestCase {


	public void testBenchmark(){

		Benchmark benchmark = new FisherBenchmark();

		List<PdbPair> pairs = benchmark.getPairs();

		assertEquals(68,pairs.size());
	}

	
}
