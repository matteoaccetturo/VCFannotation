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

public class VCFreannotationWithdbSNP {
	public static List<String> ListaVCF() throws IOException {
		String line;
		List<String> listaVarianti = new ArrayList<String>();
		
		BufferedReader brVCF = new BufferedReader(
				new FileReader(
				"C:\\Users\\Matteo\\Documents\\Ematologia\\624_VarMVF,KNOWN,EXON,FLANK,AAC.vcf"));

		for (int i = 0; i < 9; i++) {
			brVCF.readLine();
		}

		while ((line = brVCF.readLine()) != null) {
			String[] field = line.split("\t");
			listaVarianti.add(field[0] + "\t" + field[1]);
			// listaVarianti.add(line);
		}

		System.out.println(listaVarianti.size());
		// System.out.println(listaVarianti);

		return listaVarianti;

	}

	public static void AddNewRSToVCF() throws IOException {
		String filename;
		String line1;
		String line2;
		String line3;
		String line4;
		String pattern = "(.*)";
		Set<String> listaFiles = new TreeSet<String>();
		List<String> ListaVariantiDaAnnotare = ListaVCF();
		// System.out.println(ListaVariantiDaAnnotare);

		BufferedWriter bw = new BufferedWriter(
				new FileWriter(
						"C:\\Users\\Matteo\\Documents\\Ematologia\\dbSNP141reannotatedVCF\\624(dbSNP141).txt"));

		bw.write("#CHROM" + "\t" + "POS" + "\t" + "ID" + "\t" + "REF" + "\t"
				+ "ALT" + "\t" + "QUAL" + "\t" + "FILTER" + "\t" + "INFO"
				+ "\t" + "FORMAT" + "\t"
				+ "624_Var (MVF, KNOWN, EXON, FLANK, AAC)" + "\t"
				+ "dbSNP141 ID" + "\r\n");
		bw.flush();
		BufferedWriter bw_nonSNP = new BufferedWriter(
				new FileWriter(
						"C:\\Users\\Matteo\\Documents\\Ematologia\\dbSNP141reannotatedVCF\\624(nondbSNP141).txt"));

		bw_nonSNP.write("#CHROM" + "\t" + "POS" + "\t" + "ID" + "\t" + "REF" + "\t"
				+ "ALT" + "\t" + "QUAL" + "\t" + "FILTER" + "\t" + "INFO"
				+ "\t" + "FORMAT" + "\t"
				+ "624_Var (MVF, KNOWN, EXON, FLANK, AAC)" + "\t"
				+ "dbSNP141 ID" + "\r\n");
		bw_nonSNP.flush();
		
		BufferedReader brListaFiles = new BufferedReader(
		new FileReader(
		"C:\\Users\\Matteo\\Documents\\Ematologia\\chr_rpts\\listafiles.txt"));

		

		while ((filename = brListaFiles.readLine()) != null) {
			Set<String> listaSNP141 = new TreeSet<String>();
			listaFiles.add(filename);
			System.out.println(filename + " in corso");
			String[] nomeChrtmp = filename.split("_");
			String secondaParteNome = nomeChrtmp[1];

			System.out.println("seconda parte del nome: " + secondaParteNome);
			String[] nomeChr = secondaParteNome.split(".txt");
			String chr = nomeChr[0];

			//System.out.println("cromosoma numero: " + chr);
//			BufferedWriter bwtmp = new BufferedWriter(
//			new FileWriter("D:\\Ematologia\\tmp\\" + chr + "tmp.txt"));
			BufferedReader br_chr = new BufferedReader(
			new FileReader("C:\\Users\\Matteo\\Documents\\Ematologia\\chr_rpts\\" + filename));
			
			for (int i = 0; i < 7; i++) {
				br_chr.readLine();
			}

			System.out.println("dimensione lista varianti da annotare: "
					+ ListaVariantiDaAnnotare.size());
			System.out.println("comincia il controllo");
			
			while ((line1 = br_chr.readLine()) != null) {
				String[] field1 = line1.split("\t");
				// System.out.println("scorro chr");
				if (ListaVariantiDaAnnotare.contains(field1[6] + "\t"
						+ field1[11])) {
					// System.out.println("trovato");
					listaSNP141.add(field1[6] + "\t" + field1[11] + "\t"+ field1[0]);
					// bwtmp.write(field1[6]+"\t"+field1[11]+"\t"+field1[0]+"\r\n");
					// bwtmp.flush();
				}
				// }
			}

			br_chr.close();
			System.out.println("lista completa");
			System.out.println("scorro il vcf");
			System.out.println("cromosoma numero: " + chr);
			System.out.println("dimensione lista dbSNP141: "+ listaSNP141.size());

			BufferedReader brVCF = new BufferedReader(
					new FileReader(
					"C:\\Users\\Matteo\\Documents\\Ematologia\\624_VarMVF,KNOWN,EXON,FLANK,AAC.vcf"));

			for (int i = 0; i < 9; i++) {
				brVCF.readLine();
			}
			while ((line2 = brVCF.readLine()) != null) {
				// while ((line2 = br_chr.readLine()) != null) {
				String[] field = line2.split("\t");
				// System.out.println("comincio a scorrere la lista");
				
				//scrive le varianti trovate in dbSNP141
					Iterator<String> it1 = listaSNP141.iterator(); 
				while (it1.hasNext()) {
					line3 = it1.next();
					String[] field3 = line3.split("\t");
					// if (field3[0].equalsIgnoreCase(chr)) {
					if ((field3[0] + "\t" + field3[1]).equalsIgnoreCase((field[0] + "\t" + field[1]))) {
						bw.write(line2 + "\t" + "rs" + field3[2] + "\r\n");
						bw.flush();
					} 			
				}
				
				//scrive le varianti non trovate in dbSNP141 (probabili mutazioni)
				Iterator<String> it2 = listaSNP141.iterator();
				Set<String> Set_tmp = new TreeSet<String>();
				while (it2.hasNext()) {
					line4 = it2.next();
					String[] field4 = line4.split("\t");
					// if (field3[0].equalsIgnoreCase(chr)) {
					Set_tmp.add(field4[0] + "\t" + field4[1]);					
				}//System.out.println("dimensione Set_tmp: "+Set_tmp.size());
					
					if ((!Set_tmp.contains(field[0] + "\t" + field[1]))&& field[0].equalsIgnoreCase(chr)) {
						bw_nonSNP.write(line2 + "\r\n");
						bw_nonSNP.flush();
					} 
				
				
				
			}
			br_chr.close(); brVCF.close();
			}		
		// brListaFiles.close();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// VCFreannotationWithdbSNP.ListaVCF();
		VCFreannotationWithdbSNP.AddNewRSToVCF();
	}
}
