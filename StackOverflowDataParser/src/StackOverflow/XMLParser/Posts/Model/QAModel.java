package StackOverflow.XMLParser.Posts.Model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QAModel {
	private Map<String, String> questionAnswers = null;
	private boolean hasQuestion = false;
	private Integer availableAnswer = 0;
	private Integer identifiedAnswer = 0;
	private boolean hasSolution = false;
	
	public QAModel() {
		questionAnswers = new LinkedHashMap();
	}
	
	public QAModel(boolean hasQuestion, Integer availableAnswer, Integer identifiedAnswer) {
		questionAnswers = new LinkedHashMap();
		this.hasQuestion = hasQuestion;
		this.availableAnswer = availableAnswer;
		this.identifiedAnswer = identifiedAnswer;
	}
	
	public Map<String, String> getQA() {
		return questionAnswers;
	}
	
	public void addPost(PostModel postModel) throws Exception {
		if(postModel.getPostTypeId() == 1) {
			addQuestion(postModel);
		} else {
			addAnswer(postModel);
		}
	}
	
	public boolean getHasQuestion() {
		return hasQuestion;
	}
	
	public Integer getAvailableAnswer() {
		return availableAnswer;
	}
	
	public Integer getIdentifiedAnswer() {
		return identifiedAnswer;
	}
	
	public boolean isComplete() {
	
		// if there is a question, and available answer reaches to answer count. it is a complete post.
		if(availableAnswer == identifiedAnswer) {
			return true;
		}
		
		return false;
	}
	
	private void addQuestion(PostModel postModel) throws Exception {
		if(hasQuestion == true) {
			throw new Exception("Question already exists in a QA");
		}
		hasQuestion = true;
		
		availableAnswer = postModel.getAnswerCount();
		
		Map<String, String> post = postModel.getPost();
		for(Map.Entry<String, String> entry : post.entrySet()) {
			questionAnswers.put(entry.getKey(), entry.getValue());
		}
		
		if(postModel.getAcceptedAnswerId() != null) {
			hasSolution = true;
		}
	}
	
	private void addAnswer(PostModel postModel) throws Exception {
		identifiedAnswer++;
		
		// add suffix to each answer
		String suffix = "ANSWER" + identifiedAnswer.toString();
		Map<String, String> post = postModel.getPost();
		for(Map.Entry<String, String> entry : post.entrySet()) {
			String name = entry.getKey();
			name = name + suffix;
			questionAnswers.put(name, entry.getValue());
		}
	}
/*	
	public Map<String, String> flushToDisk() {
		if(availableAnswer == null) {
			questionAnswers.put("APPENDABLE", "NO");			
			return questionAnswers;
		}
		
		if(identifiedAnswer == availableAnswer) {
			questionAnswers.put("APPENDABLE", "NO");
			return questionAnswers;
		}
		
		questionAnswers.put("APPENDABLE", "YES");
		return questionAnswers;
		
	}
	*/
}
