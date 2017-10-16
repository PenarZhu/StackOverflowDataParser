package StackOverflow.XMLParser.Posts.Model;

import java.util.LinkedHashMap;
import java.util.Map;

public class QAModel {
	private Map<String, String> questionAnswers = null;
	
	
	// For question and answer
	private boolean hasQuestion = false;
	private Integer availableAnswer = 0;
	private Integer identifiedAnswer = 0;
	private boolean hasSolution = false;
	
	// For tag
	private boolean isTag = false;
	
	
	public QAModel() {
		questionAnswers = new LinkedHashMap<String, String>();
	}
	
	public QAModel(boolean hasQuestion, Integer availableAnswer, Integer identifiedAnswer) {
		questionAnswers = new LinkedHashMap<String, String>();
		this.hasQuestion = hasQuestion;
		this.availableAnswer = availableAnswer;
		this.identifiedAnswer = identifiedAnswer;
	}
	
	public Map<String, String> getQA() {
		return questionAnswers;
	}
	
	public void addPost(PostModel postModel) throws Exception {
		int postType = postModel.getPostTypeId().intValue();
		
		if(postType == PostConstant.QUESTION) {
			addQuestion(postModel);
		} 
		else if(postType == PostConstant.ANSWER) {
			addAnswer(postModel);
		}
		else if(postType == PostConstant.ORPHANEDWIKI || postType == PostConstant.TAGWIKIEXCERPT || postType == PostConstant.TAGWIKI || postType == PostConstant.NORMINATION || postType == PostConstant.WIKIPLACEHOLDER || postType == PostConstant.PRIVILEGEWIKI ) {
			addTag(postModel);
		}
	}
	
	public boolean isTag() {
		return isTag;
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
		
		// In case that answer count field does not exist in a question, just give it a large number
		availableAnswer = postModel.getAnswerCount();
		if(availableAnswer == null) {
			availableAnswer = 1000;
		}
		
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
	
	private void addTag(PostModel postModel) throws Exception {
		
		isTag = true;
		
		Map<String, String> post = postModel.getPost();
		for(Map.Entry<String, String> entry : post.entrySet()) {
			questionAnswers.put(entry.getKey(), entry.getValue());
		}		
	}
	
	public String toString() {
		String content = "";

		for(Map.Entry<String, String> entry : questionAnswers.entrySet()) {
			content += entry.getKey();
			content += "\t";
			content += entry.getValue();
			content += "\n";
		}		
		
		return content;
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
