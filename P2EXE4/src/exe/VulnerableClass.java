package exe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VulnerableClass {

  /**
   *  Validate a path.
   */
  public boolean validatePath(String fileName) {
    if (fileName.contains("..")) {
      return false;
    }
    if (fileName.contains("WINDIR")) {
      return false;
    }
    if (fileName.contains("SYSDIR")) {
      return false;
    }
    if (fileName.contains("C:")) {
      return false;
    }
    return true;
  }
  
  /**
   *  Vulnerable method.
   */
  public void vulnerableMethod(String fileName) {
    if (!validatePath(fileName)) {
      System.out.print("Nome de arquivo nao valido.");
      return;
    }
    Scanner console = new Scanner(System.in);
    while (true) {
      if (!console.hasNextLine()) {
        break;
      }
      System.out.print(
          "Digite a operacao desejada para realizar no arquivo <R para ler um arquivo, "
          + "W para escrever em um arquivo>? ");
      String opr = console.nextLine();
      if (opr.equals("R")) {
        File f = new File(fileName);
        if (!f.exists()) {
          System.out.print("Arquivo nao existente.");
        } else {
          BufferedReader br = null;
          try {
            String currentLine;
            br = new BufferedReader(new FileReader(fileName));
            while ((currentLine = br.readLine()) != null) {
              System.out.println(currentLine);
            }
            br.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } else {
        File f = new File(fileName);
        if (f.exists()) { 
          System.out.println("Arquivo ja existente, escrita proibida.");
        } else {
          BufferedWriter buffWrite = null;
          try {
            buffWrite = new BufferedWriter(new FileWriter(fileName));
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
