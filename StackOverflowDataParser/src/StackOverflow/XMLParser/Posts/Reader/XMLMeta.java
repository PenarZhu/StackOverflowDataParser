package StackOverflow.XMLParser.Posts.Reader;

import java.io.IOException;

public class XMLMeta {
	public enum Type {
		POSTS,
		COMMENTS,
		BADGES,
		POSTLINKS,
		USERS,
		VOTES,
		UNKNOWN
	}
	
	public static final String XMLHEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	
	static final String XMLPOSTSTART = "<posts>";
	static final String XMLPOSTEND = "</posts>";
	
	static final String XMLCOMMENTSSTART = "<comments>";
	static final String XMLCOMMENTSEND = "</comments>";
	
	static final String XMLBAGESSTART = "<badges>";
	static final String XMLBADGESEND = "</badges>";
	
	static final String XMLPOSTLINKSSTART = "<postlinks>";
	static final String XMLPSOTLINKSEND = "</postlinks>";
	
	static final String XMLUSERSSTART = "<users>";
	static final String XMLUSERSEND = "</users>";
	
	static final String XMLVOTESSTART = "<votes>";
	static final String XMLVOTESEND = "</votes>";
			
	public Type getXMLTypeFromTags(String tag) {
		if(tag.contains(XMLPOSTSTART) || tag.contains(XMLPOSTEND)) {
			return Type.POSTS;
		}
		else if(tag.contains(XMLCOMMENTSSTART) || tag.contains(XMLCOMMENTSEND)) {
			return Type.COMMENTS;
		}
		else if(tag.contains(XMLBAGESSTART) || tag.contains(XMLBADGESEND)) {
			return Type.BADGES;
		}
		else if(tag.contains(XMLPOSTLINKSSTART) || tag.contains(XMLPSOTLINKSEND)) {
			return Type.POSTLINKS;
		}
		else if(tag.contains(XMLUSERSSTART) || tag.contains(XMLUSERSEND)) {
			return Type.USERS;
		}
		else if(tag.contains(XMLVOTESSTART) || tag.contains(XMLVOTESEND)) {
			return Type.VOTES;
		}
		
		return Type.UNKNOWN;
	}
	
	public String getStartTag(Type xmlType) throws IOException {
		String startTag = "UNKOWN";
		switch (xmlType) {
			case POSTS:
				startTag = XMLPOSTSTART;
				break;
			case COMMENTS:
				startTag = XMLCOMMENTSSTART;
				break;
			case BADGES:
				startTag = XMLBAGESSTART;
				break;
			case POSTLINKS:
				startTag = XMLPOSTLINKSSTART;
				break;
			case USERS:
				startTag = XMLUSERSSTART;
				break;
			case VOTES:
				startTag = XMLVOTESSTART;
				break;	
			case UNKNOWN:
				throw new IOException("Unkown Type");
			
		}
		
		return startTag;
	}
	
	public String getEndTag(Type xmlType) throws IOException {
		String endTag = "UNKOWN";
		switch (xmlType) {
			case POSTS:
				endTag = XMLPOSTEND;
				break;
			case COMMENTS:
				endTag = XMLCOMMENTSEND;
				break;
			case BADGES:
				endTag = XMLBADGESEND;
				break;
			case POSTLINKS:
				endTag = XMLPSOTLINKSEND;
				break;
			case USERS:
				endTag = XMLUSERSEND;
				break;
			case VOTES:
				endTag = XMLVOTESEND;
				break;	
			case UNKNOWN:
				throw new IOException("Unkown Type");	
		}
		
		return endTag;
	}

}
