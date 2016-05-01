import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CscoresToVCF {

	/**
	 * @param args
	 */

	public static void AddCscoresToVCF() throws IOException {
		String line1;
		String line2;
		String filename;
		Set<String> listaFiles = new TreeSet<String>();
		Set<String> CScore = new TreeSet<String>();
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(
						"D:\\Google Drive\\aSEU\\AllSamples\\AllSamples(Cscores).txt"));
		BufferedReader brListaFiles = new BufferedReader(
				new FileReader(
						"D:\\Google Drive\\Illumina vcf files\\CADD annotations\\listaFiles.txt"));
		while ((filename = brListaFiles.readLine()) != null) {
			listaFiles.add(filename);
			BufferedReader brVCF = new BufferedReader(
					new FileReader(
							"D:\\Google Drive\\aSEU\\AllSamples\\AllSamples.txt"));
			String primaRiga = brVCF.readLine();
			bw.write("Cscore"+"\t"+primaRiga+ "\r\n");
			while ((line1 = brVCF.readLine()) != null) {
				String[] field1 = line1.split("\t");
				if ((field1[0] + ".tsv").equalsIgnoreCase(filename)) {
					BufferedReader brCscores = new BufferedReader(
							new FileReader(
									"D:\\Google Drive\\Illumina vcf files\\CADD annotations\\"
											+ filename));
					brCscores.readLine();
					brCscores.readLine();
					while ((line2 = brCscores.readLine()) != null) {
						String[] field2 = line2.split("\t");
						if ((field1[3]).equalsIgnoreCase(field2[0])
								&& field1[4].equalsIgnoreCase(field2[1])) {
							bw.write(field2[89]+"\t"+line1+"\r\n");
							bw.flush();

						}
					}
					brCscores.close();

				}
			}
			
			brVCF.close();
			
		}brListaFiles.close();
bw.close();
	}
	
	public static void FinalWithNoDuplicates() throws IOException {
		String line;
		String line4;
		Set<String> VCFpulito = new TreeSet<String>();
		BufferedReader brCscoresDuplicati = new BufferedReader(
				new FileReader(
						"D:\\Google Drive\\aSEU\\AllSamples\\AllSamples(Cscores).txt"));
		while ((line = brCscoresDuplicati.readLine()) != null) {
			VCFpulito.add(line);
			
		}	brCscoresDuplicati.close();
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(
						"D:\\Google Drive\\aSEU\\AllSamples\\AllSamples(Cscores)pulito.txt"));
		Iterator<String> it1 = VCFpulito.iterator();
		while (it1.hasNext()) {
			line4 = it1.next();
			bw.write(line4+"\r\n");
			bw.flush();
		}bw.close();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CscoresToVCF.AddCscoresToVCF();
		CscoresToVCF.FinalWithNoDuplicates();
	}

}
