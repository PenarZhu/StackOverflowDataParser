package StackOverflow.XMLParser.Posts.Reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import StackOverflow.XMLParser.Posts.Model.*;

public class CommentsStaXParser {
	static final String ROW = "row";
	
	public List<CommentModel> readXML(String XML) {
		List<CommentModel> comments = new ArrayList<CommentModel>();
		try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();		
            // Setup a new eventReader
            InputStream in = new FileInputStream(XML);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            CommentModel comment = null;          
            
            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();          
             
                if(event.isStartElement()) {
                	StartElement startElement = event.asStartElement();
                	// If we have an row element, we create a row item
                	if(startElement.getName().getLocalPart().equals(ROW)) {
                		comment = new CommentModel();
                        // We read the attributes from this tag and add attribute to our object
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while(attributes.hasNext()) {
                        	Attribute attribute = attributes.next();
                        	String attributeName = attribute.getName().toString();
                        	String attributeValue = attribute.getValue();
                        	if(attributeName.equals(CommentConstant.ID)) {
                        		comment.setId(attributeValue);
                        	}
                        	else if(attributeName.equals(CommentConstant.POSTID)) {
                        		comment.setPostId(attributeValue);
                        	}
                            else if(attributeName.equals(CommentConstant.SCORE)) {
                            	comment.setScore(attributeValue);
                            }                      	
                        	else if(attributeName.equals(CommentConstant.TEXT)) {
                        		comment.setText(attributeValue);
                        	}
                            else if(attributeName.equals(CommentConstant.CREATIONDATE)) {
                            	comment.setCreationDate(attributeValue);
                            }
                            else if(attributeName.equals(CommentConstant.USERID)) {
                            	comment.setUserId(attributeValue);
                            }                        	
                        }                		
                	}
                }
                
                if(event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(ROW)) {
                        comments.add(comment);                   
                    }
                }
            }
            eventReader.close();
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
		return comments;
	}
}
