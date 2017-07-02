package test;

import static org.junit.Assert.*;

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

import exe2.VulnerableClass;

public class VulnerableClassTest {

	VulnerableClass instance;
	
	@Before
	public void SetUp(){
		instance = new VulnerableClass();
	}
	
	@Test
	public void SuccessfulRead() {
		String FileName = "testRead.txt";
		File f = new File(FileName);
		if (f.exists()) f.delete();
		
		BufferedWriter buffWrite = null;
		try {
			buffWrite = new BufferedWriter(new FileWriter(FileName));
			buffWrite.append("test content");
			buffWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		String input = "R\n";
		InputStream stdin = System.in;
		OutputStream stdout = System.out;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		instance.vulnerableMethod(FileName);
		assertTrue(outContent.toString().contains("test content"));
		
		System.setIn(stdin);
		System.setOut(new PrintStream(stdout));
	}
	
	@Test
	public void SuccessfulWrite() {
		String FileName = "testWrite.txt";
		File f = new File(FileName);
		if (f.exists()) f.delete();
		
		String input = "W\ntest content";
		InputStream stdin = System.in;
		OutputStream stdout = System.out;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		instance.vulnerableMethod(FileName);
		
		System.setIn(stdin);
		System.setOut(new PrintStream(stdout));
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(FileName));
			assertTrue(br.readLine().contains("test content"));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void invalidFileName(){
		String input = "R";
		InputStream stdin = System.in;
		OutputStream stdout = System.out;

		String FileName = "../testInvalid.txt";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		instance.vulnerableMethod(FileName);
		assertTrue(outContent.toString().contains("Nome de arquivo não válido."));

		FileName = "C:/testInvalid.txt";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		instance.vulnerableMethod(FileName);
		assertTrue(outContent.toString().contains("Nome de arquivo não válido."));

		FileName = "WINDIR/testInvalid.txt";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		instance.vulnerableMethod(FileName);
		assertTrue(outContent.toString().contains("Nome de arquivo não válido."));

		FileName = "SYSDIR/testInvalid.txt";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		instance.vulnerableMethod(FileName);
		assertTrue(outContent.toString().contains("Nome de arquivo não válido."));
		
		System.setIn(stdin);
		System.setOut(new PrintStream(stdout));
	}
	
	@Test
	public void NonExistentFileRead() {
		String FileName = "testNonExistent.txt";
		File f = new File(FileName);
		if (f.exists()) f.delete();
		
		String input = "R\n";
		InputStream stdin = System.in;
		OutputStream stdout = System.out;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		instance.vulnerableMethod(FileName);
		assertTrue(outContent.toString().contains("Arquivo nao existente."));
		
		System.setIn(stdin);
		System.setOut(new PrintStream(stdout));
	}
	
	@Test
	public void ExistentFileWrite() {
		String FileName = "testExistent.txt";
		File f = new File(FileName);
		if (f.exists()) f.delete();
		
		BufferedWriter buffWrite = null;
		try {
			buffWrite = new BufferedWriter(new FileWriter(FileName));
			buffWrite.append("test content");
			buffWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		String input = "W\n";
		InputStream stdin = System.in;
		OutputStream stdout = System.out;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		instance.vulnerableMethod(FileName);
		assertTrue(outContent.toString().contains("Arquivo ja existente, escrita proibida."));
		
		System.setIn(stdin);
		System.setOut(new PrintStream(stdout));
	}

}
