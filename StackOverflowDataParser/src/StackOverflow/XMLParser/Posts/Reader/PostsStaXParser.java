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

public class PostsStaXParser {
	static final String ROW = "row";
	
	public List<PostModel> readXML(String XML) throws XMLStreamException {
		List<PostModel> posts = new ArrayList<PostModel>();
		try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();		
            // Setup a new eventReader
            InputStream in = new FileInputStream(XML);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            PostModel post = null;          
            
            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();          
             
                if(event.isStartElement()) {
                	StartElement startElement = event.asStartElement();
                	// If we have an row element, we create a row item
                	if(startElement.getName().getLocalPart().equals(ROW)) {
                		post = new PostModel();
                        // We read the attributes from this tag and add attribute to our object
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while(attributes.hasNext()) {
                        	Attribute attribute = attributes.next();
                        	String attributeName = attribute.getName().toString();
                        	String attributeValue = attribute.getValue();
                        	if(attributeName.equals(PostConstant.ID)) {
                        		post.setId(Integer.parseInt(attributeValue));
                        	}
                        	else if(attributeName.equals(PostConstant.POSTTYPEID)) {
                        		post.setPostTypeId(Integer.parseInt(attributeValue));
                        	}
                        	else if(attributeName.equals(PostConstant.PARENTID)) {
                        		post.setParentId(Integer.parseInt(attributeValue));
                        	}
                            else if(attributeName.equals(PostConstant.ACCEPTEDANSWERID)) {
                            	post.setAcceptedAnswerId(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.CREATIONDATE)) {
                            	post.setCreationDate(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.SCORE)) {
                            	post.setScore(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.VIEWCOUNT)) {
                            	post.setViewCount(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.BODY)) {
                            	post.setBody(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.OWNERUSERID)) {
                            	post.setOwnerUserId(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.OWNERDISPLAYNAME)) {
                            	post.setOwnerDisplayName(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.LASTEDITORUSERID)) {
                            	post.setLastEditorUserId(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.LASTEDITORDISPLAYNAME)) {
                            	post.setLastEditorDisplayName(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.LASTEDITDATE)) {
                            	post.setLastEditDate(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.LASTACTIVITYDATE)) {
                            	post.setLastActivityDate(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.TITLE)) {
                            	post.setTitle(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.TAGS)) {
                            	post.setTags(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.ANSWERCOUNT)) {          
                            	post.setAnswerCount(Integer.parseInt(attributeValue));
                            }
                            else if(attributeName.equals(PostConstant.COMMENTCOUNT)) {
                            	post.setCommentCount(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.FAVORITECOUNT)) {
                            	post.setFavoriteCount(attributeValue);
                            }
                            else if(attributeName.equals(PostConstant.COMMUNITYOWNEDDATE)) {
                            	post.setCommunityOwnedDate(attributeValue);
                            }
                        }
                		
                	}

                }
                
                if(event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(ROW)) {
                        posts.add(post);                   
                    }
                }
            }
            
            eventReader.close();
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		return posts;
	}
	
}
