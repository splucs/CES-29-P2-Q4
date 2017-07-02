package exe2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VulnerableClass {
	
	public boolean validatePath(String FILENAME){
		if (FILENAME.contains("..")) return false;
		if (FILENAME.contains("WINDIR")) return false;
		if (FILENAME.contains("SYSDIR")) return false;
		if (FILENAME.contains("C:")) return false;
		return true;
	}
	
	public void vulnerableMethod(String FILENAME) {
		
		if (!validatePath(FILENAME)){
			System.out.print("Nome de arquivo não válido.");
			return;
		}
		
		Scanner console = new Scanner(System.in);
		
		while (true) {
			if (!console.hasNextLine())
				break;
			
			System.out.print("Digite a operacao desejada para realizar no arquivo <R para ler um arquivo, "
					+ "W para escrever em um arquivo>? ");

			String opr = console.nextLine();

			if (opr.equals("R")) {
				File f = new File(FILENAME);
				if(!f.exists()) { 
					System.out.print("Arquivo nao existente.");
				}
				else{
				
					BufferedReader br = null;
	
					try {
						String sCurrentLine;
	
						br = new BufferedReader(new FileReader(FILENAME));
	
						while ((sCurrentLine = br.readLine()) != null) {
							System.out.println(sCurrentLine);
						}
						br.close();
	
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			else {

				File f = new File(FILENAME);
				if(f.exists()) { 
					System.out.println("Arquivo ja existente, escrita proibida.");
				}
				else{
					BufferedWriter buffWrite = null;
	
					try {
						buffWrite = new BufferedWriter(new FileWriter(FILENAME));
						String linha = "";
						System.out.println("Escreva algo: ");
						linha = console.nextLine();
						buffWrite.append(linha + "\n");
						buffWrite.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		console.close();
	}
}
