package org.biojava3.structure.align.symm.benchmark.folds;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.biojava.bio.structure.scop.Astral;
import org.biojava.bio.structure.scop.ScopDatabase;
import org.biojava.bio.structure.scop.ScopDomain;
import org.biojava.bio.structure.scop.ScopInstallation;

/**
 * Generates the list of domains specified in the SymD paper (Kim et. al.) as the intersection of ASTRAL40 1.73 and SCOP 1.73.
 * Outputs the files to {@code src/main/resources/Guerler_folds}.
 * @author dmyerstu
 */
public class FoldNames {

	private static final Logger logger = LogManager.getLogger(FoldNames.class.getName());

	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("http://scop.berkeley.edu/downloads/scopseq-1.73/astral-scopdom-seqres-gd-sel-gs-bib-95-1.73.fa");
		Set<String> names = new Astral("1.73_40", url).getNames();
		ScopInstallation scop = new ScopInstallation();
		scop.setScopVersion("1.73");
		print(args, scop, names);
	}
	
	public static void print(String[] folds, ScopDatabase scop, Set<String> names) {
		for (String fold : folds) {
			Set<String> in = new HashSet<String>();
			for (String name : names) {
				ScopDomain domain = scop.getDomainByScopID(name);
				if (domain == null) continue;
				String classification = domain.getClassificationId();
				String[] parts = classification.split("\\.");
				String theFold = parts[0] + "." + parts[1];
				if (fold.equals(theFold)) in.add(name);
			}
			System.out.print(fold + "\t" + in.size());
			for (String i : in) System.out.print("\t" + i);
			System.out.println();
			File output = new File("src/main/resources/Guerler_folds/" + fold + "_names.list");
			PrintWriter pw;
			try {
				pw = new PrintWriter(new BufferedWriter(new FileWriter(output)));
			} catch (IOException e) {
				logger.error(e);
				continue;
			}
			for (String i : in) {
				pw.println(i);
			}
			pw.close();
		}
	}

}
