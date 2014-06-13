/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtlmappingpipeline.ase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;
import umcg.genetica.io.trityper.EQTL;
import umcg.genetica.io.trityper.eQTLTextFile;

/**
 *
 * @author Patrick Deelen
 */
public class CompareAseToEqtl {

	private static final Pattern TAB_PATTERN = Pattern.compile("\\t");
	private static final Pattern COMMA_PATTERN = Pattern.compile(",");

	/**
	 * @param args the command line arguments
	 */
	@SuppressWarnings("ManualArrayToCollectionCopy")
	public static void main(String[] args) throws Exception {

		//eQTLTextFile eQTLsTextFile = new eQTLTextFile("D:\\UMCG\\Genetica\\Projects\\RnaSeqEqtl\\batch9_eQTLmapping\\result_non-geuvadis_maf0.05_call0.5_pcs100_normalizedPCA_meta\\notInGeuvadis.txt", false);
		eQTLTextFile eQTLsTextFile = new eQTLTextFile("D:\\UMCG\\Genetica\\Projects\\RnaSeqEqtl\\batch9_eQTLmapping\\result_all_maf0.05_call0.5_pcs100_normalizedPCA_meta_specialPermutation\\eQTLsFDR0.05-ProbeLevel.txt", false);
		//eQTLTextFile eQTLsTextFile = new eQTLTextFile("D:\\UMCG\\Genetica\\Projects\\RnaSeqEqtl\\batch9_eQTLmapping\\result_geuvadis_maf0.05_call0.5_pcs100_normalizedPCA_meta\\eQTLsFDR0.05-ProbeLevel.txt", false);

		BufferedReader aseReader = new BufferedReader(new FileReader("D:\\UMCG\\Genetica\\Projects\\RnaSeqEqtl\\Ase\\all_maskAll_r20_a10_p0_s5_rq0_gatkGeno\\ase_bh.txt"));

		HashMap<String, ArrayList<EQTL>> eQtls = new HashMap<String, ArrayList<EQTL>>();

		for (Iterator<EQTL> eQtlIt = eQTLsTextFile.getEQtlIterator(); eQtlIt.hasNext();) {
			EQTL eQtl = eQtlIt.next();
			String eQtlKey = eQtl.getRsChr() + ":" + eQtl.getRsChrPos();
			ArrayList<EQTL> posEqtls = eQtls.get(eQtlKey);
			if (posEqtls == null) {
				posEqtls = new ArrayList<EQTL>(1);
				eQtls.put(eQtlKey, posEqtls);
			}
			posEqtls.add(eQtl);
		}

		int aseTotal = 0;
		int aseWithEQtl = 0;
		int sameDirection = 0;
		int oppositeDirection = 0;
		
		HashSet<String> countedGenes = new HashSet<String>();

		aseReader.readLine();//header
		String line;
		String[] elements;
		while ((line = aseReader.readLine()) != null) {

			elements = TAB_PATTERN.split(line);


			HashSet<String> aseGenes = new HashSet<String>();
			for (String gene : COMMA_PATTERN.split(elements[9])) {
				aseGenes.add(gene);
			}

			++aseTotal;

			ArrayList<EQTL> posEqtls = eQtls.get(elements[2] + ":" + elements[3]);
			if (posEqtls != null) {
				for (EQTL eQtl : eQtls.get(elements[2] + ":" + elements[3])) {
					if (eQtl != null && aseGenes.contains(eQtl.getProbe())) {

						if(countedGenes.contains(eQtl.getProbe())){
							continue;
						}
						countedGenes.add(eQtl.getProbe());
						
						System.out.println(eQtl.getProbe());
						
						//if(eQtl.getRsChr() != 6 && eQtl.getRsChrPos() < 20000000 || eQtl.getRsChrPos() > 40000000) {

						++aseWithEQtl;


						double aseZ = Double.parseDouble(elements[1]);

						if (elements[7].equals(eQtl.getAlleleAssessed())) {

							if (aseZ * eQtl.getZscore() > 0) {
								++sameDirection;
							} else {
								++oppositeDirection;
//								System.out.println("Opposite: " + eQtl.getRsChr() + ":" + eQtl.getRsChrPos() + "\t" + elements[7] + "\t" + eQtl.getAlleleAssessed() + "\t" + aseZ + "\t" + eQtl.getZscore());
							}
						} else {
							if (aseZ * eQtl.getZscore() > 0) {
								++oppositeDirection;
								//System.out.println("Opposite: " + eQtl.getRsChr() + ":" + eQtl.getRsChrPos() + "\t" + elements[7] + "\t" + eQtl.getAlleleAssessed() + "\t" + aseZ + "\t" + eQtl.getZscore());
							} else {
								++sameDirection;
							}
						}

					}
				}
			}
		}

		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
		System.out.println("Ase total: " + aseTotal);
		System.out.println("Ase SNP with eQTL effect: " + aseWithEQtl + " (" + numberFormat.format(aseWithEQtl / (double) aseTotal) + ")");
		System.out.println(" - Same direction: " + sameDirection + " (" + numberFormat.format(sameDirection / (double) aseWithEQtl) + ")");
		System.out.println(" - Opposite direction: " + oppositeDirection + " (" + numberFormat.format(oppositeDirection / (double) aseWithEQtl) + ")");

	}
}
