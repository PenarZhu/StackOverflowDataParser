package StackOverflow.XMLParser.Posts.Writer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import StackOverflow.XMLParser.Posts.Model.*;

public class PostsDocManager {

	static final String PREFIX = "PostDoc";
	// question Id and answer count
	Map<Integer,Integer> questionTable = null;
	// By default 100 Ids grouped in one file
	int slice = 100;
	// file buffer
	Map<Integer,String> fileBuffer = null;
	
	PostsDocManager() {
		questionTable = new LinkedHashMap<Integer,Integer>();
		fileBuffer = new HashMap<Integer,String>();
	}
	
	PostsDocManager(int slice) {
		this.slice = slice;
		questionTable = new LinkedHashMap<Integer,Integer>();
		fileBuffer = new HashMap<Integer,String>();		
	}
	
	private void writeQuestion(PostModel postModel) {
		Integer Id = postModel.getId();
		Integer fileId = Id/slice;
		// create a new buffer slot for qustion
		if(fileBuffer.get(fileId) == null) {
			fileBuffer.new
		}
	}
	
	private void appendAnswer(PostModel postModel) {
		
	}
	
	public void addQuestion(Integer Id) {
		questionTable.put(Id, 0);
	}
	
	public void addAnswer(Integer Id) {
		Integer answerCount = questionTable.get(Id);
		if(answerCount != null) {
			answerCount++;
			questionTable.put(Id,answerCount);
		}	
	}
	
	public String getDynamicFieldName(String fieldName, Integer parentId) {
		Integer answerCount = questionTable.get(parentId);
		if(answerCount == null) {
			return null;
		}
		else {
			return fieldName + answerCount.toString();
		}
	}
	
	private String getFileName(String Id) {
		return PREFIX + Id;
	}
}
