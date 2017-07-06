package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import exe.VulnerableClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;


public class VulnerableClassTest {

  VulnerableClass instance;
  
  /**
   *  Instantiation.
   */
  @Before
  public void setUp() {
    instance = new VulnerableClass();
  }
  
  /**
   *  Successful read test.
   */
  @Test
  public void successfulRead() {
    String fileName = "testRead.txt";
    File f = new File(fileName);
    if (f.exists()) {
      f.delete();
    }
    BufferedWriter buffWrite = null;
    try {
      buffWrite = new BufferedWriter(new FileWriter(fileName));
      buffWrite.append("test content");
      buffWrite.close();
    } catch (IOException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
    String input = "R\n";
    final InputStream stdin = System.in;
    final OutputStream stdout = System.out;
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    instance.vulnerableMethod(fileName);
    assertTrue(outContent.toString().contains("test content"));
    System.setIn(stdin);
    System.setOut(new PrintStream(stdout));
  }

  /**
   *  Successful write test.
   */
  @Test
  public void successfulWrite() {
    String fileName = "testWrite.txt";
    File f = new File(fileName);
    if (f.exists()) {
      f.delete();
    }
    String input = "W\ntest content";
    final InputStream stdin = System.in;
    final OutputStream stdout = System.out;
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    instance.vulnerableMethod(fileName);
    System.setIn(stdin);
    System.setOut(new PrintStream(stdout));
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(fileName));
      assertTrue(br.readLine().contains("test content"));
    } catch (IOException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }
  
  /**
   *  Invalid name test.
   */
  @Test
  public void invalidfileName() {
    String input = "R";
    final InputStream stdin = System.in;
    final OutputStream stdout = System.out;
    String fileName = "../testInvalid.txt";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    instance.vulnerableMethod(fileName);
    assertTrue(outContent.toString().contains("Nome de arquivo nao valido."));
    fileName = "C:/testInvalid.txt";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    instance.vulnerableMethod(fileName);
    assertTrue(outContent.toString().contains("Nome de arquivo nao valido."));
    fileName = "WINDIR/testInvalid.txt";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    instance.vulnerableMethod(fileName);
    assertTrue(outContent.toString().contains("Nome de arquivo nao valido."));
    fileName = "SYSDIR/testInvalid.txt";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    instance.vulnerableMethod(fileName);
    assertTrue(outContent.toString().contains("Nome de arquivo nao valido."));
    System.setIn(stdin);
    System.setOut(new PrintStream(stdout));
  }
  
  /**
   *  Non existent file test.
   */
  @Test
  public void nonExistentFileRead() {
    String fileName = "testNonExistent.txt";
    File f = new File(fileName);
    if (f.exists()) {
      f.delete();
    }
    String input = "R\n";
    final InputStream stdin = System.in;
    final OutputStream stdout = System.out;
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    instance.vulnerableMethod(fileName);
    assertTrue(outContent.toString().contains("Arquivo nao existente."));
    System.setIn(stdin);
    System.setOut(new PrintStream(stdout));
  }

  /**
   *  Existent file write test.
   */
  @Test
  public void existentFileWrite() {
    String fileName = "testExistent.txt";
    File f = new File(fileName);
    if (f.exists()) {
      f.delete();
    }
    BufferedWriter buffWrite = null;
    try {
      buffWrite = new BufferedWriter(new FileWriter(fileName));
      buffWrite.append("test content");
      buffWrite.close();
    } catch (IOException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
    String input = "W\n";
    final InputStream stdin = System.in;
    final OutputStream stdout = System.out;
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    instance.vulnerableMethod(fileName);
    assertTrue(outContent.toString().contains("Arquivo ja existente, escrita proibida."));
    System.setIn(stdin);
    System.setOut(new PrintStream(stdout));
  }
}