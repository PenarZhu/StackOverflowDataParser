package StackOverflow.XMLParser.Posts.Writer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.Attribute;

import StackOverflow.XMLParser.Posts.Model.*;

public class QAStaXWriterForSolr {
	static final String DOC = "doc";
	static final String ADD = "add";
	static final String FIELD = "field";
	static final String NAME = "name";
	/*
	private List<QAModel> qAsModel;
	
	public void setQAs(List<QAModel> qAsModel) {
		this.qAsModel = qAsModel;
	}
	*/
	
	// create a new document and write to the XML
	public static void createAndWriteToSolrXML(String fileName, QAModel qAModel) throws Exception {
		// create an XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		// create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(fileName), "utf-8");
		// create an EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		
		// create and write Start Tag
        StartDocument startDocument = eventFactory.createStartDocument("utf-8");
        eventWriter.add(startDocument);
        
        // create add open tag
        eventWriter.add(eventFactory.createStartElement("", "", ADD));
        eventWriter.add(end);
        
	    // create doc open tag
	    eventWriter.add(end);
	    eventWriter.add(eventFactory.createStartElement("", "", DOC));
	    eventWriter.add(end);
	            
	    Map<String, String> questionAnswers = qAModel.getQA();	                
	    for(Map.Entry<String, String> entry : questionAnswers.entrySet()) {
	    	Map<String, String> attribute = new LinkedHashMap<>();
	    	attribute.put(NAME, entry.getKey());
	    	String fieldValue = entry.getValue();            	
	    	createNode(eventWriter, FIELD, fieldValue, attribute);
	 	}
	
	    // create doc end tag
		eventWriter.add(eventFactory.createEndElement("", "", DOC));
		eventWriter.add(end);

	        
		// create add end tag
		eventWriter.add(eventFactory.createEndElement("", "", ADD));
	   	eventWriter.add(end);
	   	eventWriter.add(eventFactory.createEndDocument());       
	        
	   	eventWriter.flush();
	   	eventWriter.close();
	} 
        
	// append answers to a XML
	public static void appendToSolrXML(String fileName, QAModel qAModel) throws Exception {
//		System.out.println("append"+fileName);
		
        // First, create a new XMLInputFactory
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();		
        // Setup a new eventReader
        InputStream in = new FileInputStream(fileName);
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
        ArrayList<XMLEvent> eventList = new ArrayList<XMLEvent>();
		
		while(eventReader.hasNext()) {
		    XMLEvent event = (XMLEvent)eventReader.next();
		    eventList.add(event);

		    if(event.isEndElement()) {
		    	EndElement endElement = event.asEndElement();
		    	String name = endElement.getName().toString();
		    	if(name == DOC || name == ADD) {
		    		eventList.remove(event);
		    	}
		    }
		    
		    if(event.isEndDocument()) {
		    	eventList.remove(event);
		    }
		}
		
		eventReader.close();
		
	    // write events to file, and then append the data
		// create an XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		// create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(fileName), "utf-8");
		// create an EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		
		for(XMLEvent event : eventList) {
			eventWriter.add(event);
		}
	   	
		
	    Map<String, String> questionAnswers = qAModel.getQA();	                
	    for(Map.Entry<String, String> entry : questionAnswers.entrySet()) {
	    	Map<String, String> attribute = new LinkedHashMap<>();
	    	attribute.put(NAME, entry.getKey());
	    	String fieldValue = entry.getValue();            	
	    	createNode(eventWriter, FIELD, fieldValue, attribute);
	 	}
	    
		eventWriter.add(eventFactory.createEndElement("", "", DOC));
		eventWriter.add(end);
	        
		// create add end tag
		eventWriter.add(eventFactory.createEndElement("", "", ADD));
	   	eventWriter.add(end);
	   	eventWriter.add(eventFactory.createEndDocument());       
	       
	   	eventWriter.flush();
	   	eventWriter.close();	    
	}
	
	public void merge() throws Exception {
		
	}
	
	public static void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		
		// create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		
		// create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		
		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}
	
	public static void createNode(XMLEventWriter eventWriter, String name, String value, Map<String, String>attributes) throws XMLStreamException {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		XMLEvent space = eventFactory.createDTD(" ");
		
		// create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		

		//create attributes
		for(Map.Entry<String, String> entry : attributes.entrySet()) {
			Attribute attribute = eventFactory.createAttribute(entry.getKey(), entry.getValue());
			eventWriter.add(attribute);
		}
		
		// create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		
		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);		
	}

}


