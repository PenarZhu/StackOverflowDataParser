package StackOverflow.XMLParser.Posts.Writer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import StackOverflow.XMLParser.Posts.Model.*;

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
		try {
			
			int postType = postModel.getPostTypeId().intValue();
			// new question will be added to in memory table
			if(postType == PostConstant.QUESTION) {
				slot--;
				
				QAModel qAModel = new QAModel();
				qAModel.addPost(postModel);
				
				// add to question table
				questionTableInMem.put(postModel.getId(), qAModel);
	
			} // deal with answers
			else if(postType == PostConstant.ANSWER) {
	
				Integer questionId = postModel.getParentId();
				
				// check if related question is in memory. Append the answer to related question.			
				if(questionTableInMem.containsKey(questionId)) {
					QAModel qAModel = questionTableInMem.get(questionId);
					qAModel.addPost(postModel);

				}
				else {
					// check if related question is written to disk. Flush the answer to related question
					if(openQuestionOnDisk.containsKey(questionId)) {
						Integer[] answerParams = openQuestionOnDisk.get(questionId);
						Integer availableAnswer = answerParams[0];
						Integer savedAnswers = answerParams[1];
	
						
						QAModel qAModel = new QAModel(false, availableAnswer, savedAnswers);
						qAModel.addPost(postModel);
						questionTableInMem.put(questionId, qAModel);
						
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
			// deal with all other identified types. Will go back to this again if there is value to improve data handling for these types
			else if(postType == PostConstant.ORPHANEDWIKI || postType == PostConstant.TAGWIKIEXCERPT || postType == PostConstant.TAGWIKI || postType == PostConstant.NORMINATION || postType == PostConstant.WIKIPLACEHOLDER || postType == PostConstant.PRIVILEGEWIKI ) {
				slot--;
				
				QAModel qAModel = new QAModel();
				qAModel.addPost(postModel);
				
				// add to question table
				questionTableInMem.put(postModel.getId(), qAModel);				
			}
			// In case there is other type of post, throw an exception here 
			else {
				
				throw new Exception("ID " + postModel.getId() + " has unknown type id");
			}
			
			// when the slot is full. flush all in memory questions to disk, no matter whether a question get all identified answers. 
			if(slot == 0) {
				flush();			
			}
		} catch(NullPointerException e) {
			System.out.println(postModel.toString());
			throw e;
		} 
	}
	
	public void close() throws Exception {
		flush();
	}
	
	private void flush() throws Exception {
		
		for(Map.Entry<Integer,QAModel> entry : questionTableInMem.entrySet()) {
	
			Integer questionId = entry.getKey();
			QAModel qAModel = entry.getValue();
			
			if(questionId == 5205343) {
				System.out.println(qAModel.toString());				
			}
			
			try {
				// Write tag directly to file. It does not have relationship with other posts. So it is not needed to buffer related Id in memory
				if(qAModel.isTag() == true) {
					QAStaXWriterForSolr.createAndWriteToSolrXML(outputDir + questionId.toString(), qAModel);							
				}
				// Write questions and answers to file.
				else {
					if(qAModel.getHasQuestion() == true) {
						QAStaXWriterForSolr.createAndWriteToSolrXML(outputDir + questionId.toString(), qAModel);			
					}
					else {
						QAStaXWriterForSolr.appendToSolrXML(outputDir + questionId.toString(), qAModel);
					}
					
					// In memory post is not complete and it is not recorded in open question table
					if(qAModel.isComplete() == false) {
						// add or replace question table recorded for disk storage.
						Integer answerParams[] = new Integer[2];
						answerParams[0] = qAModel.getAvailableAnswer();
						answerParams[1] = qAModel.getIdentifiedAnswer();
						openQuestionOnDisk.put(questionId, answerParams);
					}	
					else {
						openQuestionOnDisk.remove(questionId);
					}
				}
			} catch(FileNotFoundException e) {
				System.out.println(qAModel.toString());
				throw e;
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
