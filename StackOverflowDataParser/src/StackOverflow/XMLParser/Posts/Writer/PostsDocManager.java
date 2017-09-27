package StackOverflow.XMLParser.Posts.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import StackOverflow.XMLParser.Posts.Model.*;
import StackOverflow.XMLParser.Posts.Writer.*;

public class PostsDocManager {

	static final String PREFIX = "PostDoc";
	// question Id and answer count
	Map<Integer,QAModel> questionTableInMem = null;
	Map<Integer,Integer[]> openQuestionOnDisk = null;
	String outputDir;
	
	ArrayList<Integer> throwAwayPost = null;
	
	// By default 100 questions grouped in one file
	int slot = 1000;
	int max = 1000;
	
	public PostsDocManager(String outputDir) {
		this.outputDir = outputDir;
		questionTableInMem = new LinkedHashMap<Integer,QAModel>();
		openQuestionOnDisk = new LinkedHashMap<Integer,Integer[]>();
		
		throwAwayPost = new ArrayList<Integer>();
	}
	
	public PostsDocManager(int slot, String outputDir) {
		this.slot = slot;
		this.max = slot;
		this.outputDir = outputDir;
		questionTableInMem = new LinkedHashMap<Integer,QAModel>();
		openQuestionOnDisk = new LinkedHashMap<Integer,Integer[]>();
		
		throwAwayPost = new ArrayList<Integer>();
	}
	
	public void write(PostModel postModel) throws Exception {
		Integer Id = postModel.getId();
		
		// new question will be added to in memory table
		if(postModel.getPostTypeId() == 1) {
			slot--;
			
			QAModel qAModel = new QAModel();
			qAModel.addPost(postModel);
			
			// add to question table
			questionTableInMem.put(postModel.getId(), qAModel);

		} // deal with answers
		else {

			Integer questionId = postModel.getParentId();
			
			// check if related question is in memory. Append the answer to related question.			
			if(questionTableInMem.containsKey(questionId)) {
				QAModel qAModel = questionTableInMem.get(questionId);
				qAModel.addPost(postModel);
				if(questionId == 11) {
					System.out.println("AnswerFromInMem "+qAModel.getIdentifiedAnswer().toString());
				}
			}
			else {
				// check if related question is written to disk. Flush the answer to related question
				if(openQuestionOnDisk.containsKey(questionId)) {
					Integer[] answerParams = openQuestionOnDisk.get(questionId);
					Integer availableAnswer = answerParams[0];
					Integer savedAnswers = answerParams[1];
					// Store each question as a file with question id
					Integer fileId = questionId;
					
					QAModel qAModel = new QAModel(false, availableAnswer, savedAnswers);
					qAModel.addPost(postModel);
					questionTableInMem.put(questionId, qAModel);
					if(questionId == 11) {
						System.out.println("AnswerRetrieveFromDisk "+qAModel.getIdentifiedAnswer().toString());
					}
					
					savedAnswers++;
					// All answers are saved to disk. Remove it from open question list.
					if(savedAnswers >= availableAnswer) {
						openQuestionOnDisk.remove(questionId);
					} // Not all answers are saved to disk. Still need to have this info.
					else {
						answerParams[1] = savedAnswers;						
						openQuestionOnDisk.put(questionId, answerParams);								
					}					
				} // If related question is not in memory, and related question is not written to disk.  
				  // Has to write the question to memory with its question id.
				else {
					QAModel qAModel = new QAModel(false, 1, 0);
					qAModel.addPost(postModel);
					
					flushToDisk(questionId, qAModel);
				}
			}			
		}
		
		// when the slot is full. flush all in memory questions to disk, no matter whether a question get all identified answers. 
		if(slot == 0) {
			flush();			
		}
		
	}
	
	public void close() throws Exception {
		flush();
	}
	
	private void flush() throws Exception {
		for(Map.Entry<Integer,QAModel> entry : questionTableInMem.entrySet()) {

			Integer questionId = entry.getKey();
			QAModel qAModel = entry.getValue();
			
	//		System.out.println(questionId);
			
			if(qAModel.getHasQuestion() == true) {				
				QAStaXWriterForSolr.createAndWriteToSolrXML(outputDir + questionId.toString(), qAModel);			
			}
			else {
				QAStaXWriterForSolr.appendToSolrXML(outputDir + questionId.toString(), qAModel);
			}
			
			// In memory post is not complete and it is not recorded in open question table
			if(qAModel.isComplete() == false && !openQuestionOnDisk.containsKey(questionId)) {
				// add or replace question table recorded for disk storage.
				Integer answerParams[] = new Integer[2];
				answerParams[0] = qAModel.getAvailableAnswer();
				answerParams[1] = qAModel.getIdentifiedAnswer();
				openQuestionOnDisk.put(questionId, answerParams);
			}			
		}
		
		// reset parameters
		questionTableInMem.clear();
		slot = max;
		
	}
	
	// this is for a special case where we want to create a placeholder on disk, where question is not available, but answer appears in the list. 
	private void flushToDisk(Integer questionId, QAModel qAModel) throws Exception {
		QAStaXWriterForSolr.createAndWriteToSolrXML(outputDir + questionId.toString(), qAModel);	
		Integer answerParams[] = new Integer[2];
		answerParams[0] = 10000;
		answerParams[1] = 1;		
		openQuestionOnDisk.put(questionId, answerParams);
	}
	
		
	
/*	
	public String getDynamicFieldName(String fieldName, Integer parentId) {
		Integer answerCount = questionTable.get(parentId);
		if(answerCount == null) {
			return null;
		}
		else {
			return fieldName + answerCount.toString();
		}
	}
*/	
	private String getFileName(String Id) {
		return PREFIX + Id;
	}
}
