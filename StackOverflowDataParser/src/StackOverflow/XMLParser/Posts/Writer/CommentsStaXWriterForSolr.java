package StackOverflow.XMLParser.Posts.Writer;

import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.Attribute;

import StackOverflow.XMLParser.Posts.Model.*;

public class CommentsStaXWriterForSolr {
	
	private List<CommentModel> commentsModel;
	private String fileName;
	
	public void setComments(List<CommentModel> commentsModel, String fileName) {
		this.commentsModel = commentsModel;
		this.fileName = fileName;
	}
	
	public void WriteToSolrXML() throws Exception {
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
        eventWriter.add(eventFactory.createStartElement("", "", SolrIndexConstant.ADD));
        eventWriter.add(end);
        
        //write nodes
        for(CommentModel commentModel : commentsModel) {
            // create doc open tag
        	eventWriter.add(end);
            eventWriter.add(eventFactory.createStartElement("", "", SolrIndexConstant.DOC));
            eventWriter.add(end);
            

            Map<String, String> comment = commentModel.getComment();
                
            for(Map.Entry<String, String> entry : comment.entrySet()) {
                Map<String, String> attribute = new LinkedHashMap<>();
                attribute.put(SolrIndexConstant.NAME, entry.getKey());
                String fieldValue = entry.getValue();            	
                createNode(eventWriter, SolrIndexConstant.FIELD, fieldValue, attribute);
            }

            // create doc end tag
            eventWriter.add(eventFactory.createEndElement("", "", SolrIndexConstant.DOC));
            eventWriter.add(end);
        }
        
        // create add end tag
        eventWriter.add(eventFactory.createEndElement("", "", SolrIndexConstant.ADD));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());       
        
        eventWriter.flush();
        eventWriter.close();
	}
	
	private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
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
	
	private void createNode(XMLEventWriter eventWriter, String name, String value, Map<String, String>attributes) throws XMLStreamException {
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
