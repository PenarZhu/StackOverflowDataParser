
package StackOverflow.XMLParser.Posts.Test;

import StackOverflow.XMLParser.Posts.Model.PostModel;
import StackOverflow.XMLParser.Posts.Reader.PostsStaXParser;
import StackOverflow.XMLParser.Posts.Reader.XMLPreprocess;
import StackOverflow.XMLParser.Posts.Writer.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TestPostsReader {
	public static void main(String args[]) {
/*
		String rawPosts = "D:\\stackoverflow\\Posts.xml";
		String processedPath = "D:\\stackoverflow\\XMLParser\\src\\StackOverflow\\XMLParser\\Posts\\Test\\Processed\\";
		
		try {
		XMLPreprocess preprocess = new XMLPreprocess(rawPosts, processedPath,1000);
		preprocess.preprocess();
		//		preprocess.readHeaderRawXML(rawPosts, "D:\\stackoverflow\\XMLParser\\src\\StackOverflow\\XMLParser\\Posts\\Test\\PostsHeader.xml", 10000);
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
		
		String inputDir = "D:\\stackoverflow\\SplitXML\\PostsXML\\";
		String outputDir = "D:\\stackoverflow\\SolrXML\\PostsSolrXML\\";

		try {
			// get the list of input files
			File inputFolder = new File(inputDir);
			File[] listOfInputFiles = inputFolder.listFiles();

			
			//check if output folder is empty
			DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(outputDir));
		    if(dirStream.iterator().hasNext()) {
		    	throw new IOException("Output Dir is not empty");
		 	}	
		    
		    // create reader and writer
			PostsStaXParser reader = new PostsStaXParser();
			PostsDocManager docManager = new PostsDocManager(1000, outputDir);
			
			for(Integer i=1; i<listOfInputFiles.length+1; i++) {
				String fileName = inputDir+i.toString()+".xml";
				List<PostModel> postsModel = reader.readXML(fileName);
				for(PostModel postModel : postsModel) {
					if(!postModel.hasFatalError()) {
						docManager.write(postModel);				
					}
				}
			}
		
			docManager.close();
			
		} catch (XMLStreamException e) {
			e.printStackTrace();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
		
//		for(PostModel post : postsModel) {
//			System.out.println(post);
//		}
		
	}
	
}