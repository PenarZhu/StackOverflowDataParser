package StackOverflow.XMLParser.Posts.Reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import StackOverflow.XMLParser.Posts.Reader.XMLMeta;

// remove all line breakers within row elements
// Split big file into small ones
public class XMLPreprocess {
	// tag
	static final String ENDOFELEMENT = "/>";

	// file name and file dir
	String raWXML;
	String processedXMLDir;
	
	// identified tags
	String startTag;
	String endTag;
	
	// configurations
	int splitSize = 20;
	int rowCounter = 0;
	int fileCounter = 0;
	
	// buffer 
	String rowBuffer = "";
	
	public XMLPreprocess(String raWXML, String processedXMLDir) throws IOException {
		this.raWXML = raWXML;

		//check if processedPostsXMLDir is empty
		DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(processedXMLDir));
	    if(dirStream.iterator().hasNext()) {
	    	throw new IOException("Output Dir is not empty");
	 	}		

		this.processedXMLDir = processedXMLDir;
	}
	
	public XMLPreprocess(String raWXML, String processedXMLDir, int splitSize) throws IOException {
		this.raWXML = raWXML;
		
		//check if processedPostsXMLDir is empty
		DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(processedXMLDir));
	    if(dirStream.iterator().hasNext()) {
	    	throw new IOException("Output Dir is not empty");
	 	}	
	    
		this.processedXMLDir = processedXMLDir;
		this.splitSize = splitSize;			
	}
	
	
	public void preprocess() throws IOException {
	
	    identifyRawXML();
		
	    // Open reader
		Reader reader = new InputStreamReader(new FileInputStream(raWXML),"UTF-8");		
		BufferedReader bufferedReader = new BufferedReader(reader);
	    		
		// Increase fileCounter and create output file
		fileCounter++;		
        Writer writer = new OutputStreamWriter(new FileOutputStream(processedXMLDir + fileCounter + ".xml"), "UTF-8");		
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
		// Increase rowCounter and read line
		rowCounter++;
		String line = bufferedReader.readLine();
	
		while(line != null) {			

			if(line.contains((ENDOFELEMENT))) {
				rowBuffer += line;
				bufferedWriter.write(rowBuffer);
				bufferedWriter.write("\n");
				
				// reset rowBuffer
				rowBuffer = "";
				rowCounter++;				
			}
			else {
//	 			Seems that read line already get rid of line breaker
//				line.replace("\n", "").replace("\r", "");	
				rowBuffer += line;
			}

			// reach split size, close file. and new another one
			if(rowCounter >= splitSize) {
				// reset rowCounter
				rowCounter = 0;
				
				// close file
				if(rowBuffer!="") {
					bufferedWriter.write(rowBuffer);
					rowBuffer = "";
				}
				bufferedWriter.write(endTag);
				bufferedWriter.close();
				
				// Increase fileCounter and create output file
				fileCounter++;		
				if(fileCounter%1000 == 0) {
					System.out.println(fileCounter);				
				}

		        writer = new OutputStreamWriter(new FileOutputStream(processedXMLDir + fileCounter + ".xml"), "UTF-8");		
				bufferedWriter = new BufferedWriter(writer);		
				bufferedWriter.write(XMLMeta.XMLHEADER);
				bufferedWriter.write("\n");
				bufferedWriter.write(startTag);
			}
			
			// read a new line
			line = bufferedReader.readLine();
		}
		
		bufferedReader.close();
		
		bufferedWriter.write(rowBuffer);
		bufferedWriter.close();
	}
	
	public void identifyRawXML() throws IOException {
	    // Open a temp reader to identify file type
		Reader reader = new InputStreamReader(new FileInputStream(raWXML),"UTF-8");		
		BufferedReader tempBufferedReader = new BufferedReader(reader);
		
		// read top three lines. It should contain tags that can identify file type.
		String header = tempBufferedReader.readLine();
		header += tempBufferedReader.readLine();
		header += tempBufferedReader.readLine();
		
//		System.out.println(header);
		
		// close reader
		tempBufferedReader.close();
		
		// Identify raw XML type and assign tags
		XMLMeta rawXMLMeta = new XMLMeta();
		XMLMeta.Type xmlType = rawXMLMeta.getXMLTypeFromTags(header);
	    startTag = rawXMLMeta.getStartTag(xmlType);
	    endTag = rawXMLMeta.getEndTag(xmlType);
	}
	
	// This is for debug purpose
	public void readHeaderRawXML(String fileIn, String fileOut, int lines) throws IOException {
	    // Open a temp reader to identify file type
		Reader reader = new InputStreamReader(new FileInputStream(fileIn),"UTF-8");		
		BufferedReader tempBufferedReader = new BufferedReader(reader);
        Writer writer = new OutputStreamWriter(new FileOutputStream(fileOut), "UTF-8");		
		BufferedWriter tempBufferedWriter = new BufferedWriter(writer);
		
		String header = tempBufferedReader.readLine();
		for(int i=0; i<lines; i++) {
			tempBufferedWriter.write(header);
			tempBufferedWriter.write("\n");
			// read top lines
			header = tempBufferedReader.readLine();
		}
		
		tempBufferedReader.close();
		tempBufferedWriter.close();
	}
	
}
