package StackOverflow.XMLParser.Posts.Model;


import java.util.LinkedHashMap;
import java.util.Map;

public class CommentModel {
	private String Id;
	private String PostId;
	private String Score;
	private String Text;
	private String CreationDate;
	private String UserId;

	public Map<String, String> getComment() {
		Map<String, String> comment = new LinkedHashMap<>();
		
		if(Id!=null) {
			comment.put(CommentConstant.ID, Id);
		}
		
		if(PostId != null) {
			comment.put(CommentConstant.POSTID, PostId);
		}
		
		if(Score != null) {
			comment.put(CommentConstant.SCORE, Score);
		}
		
		if(Text != null) {
			comment.put(CommentConstant.TEXT, Text);
		}
		
		if(CreationDate != null) {
			comment.put(CommentConstant.CREATIONDATE, CreationDate);
		}
		
		if(UserId != null) {
			comment.put(CommentConstant.USERID, UserId);
		}

		
		return comment;
	}
	
	public String getId() {
		return Id;
	}
	
	public void setId(String Id) {
		this.Id = Id;
	}
	
	public String getPostId() {
		return PostId;
	}
	
	public void setPostId(String PostId) {
		this.PostId = PostId;
	}
	
	public String getScore() {
		return Score;
	}
	
	public void setScore(String Score) {
		this.Score = Score;
	}
	
	public String getText() {
		return Text;
	}
	
	public void setText(String Text) {
		this.Text = Text;
	}
	
	public String getCreationDate() {
		return CreationDate;
	}
	
	public void setCreationDate(String CreationDate) {
		this.CreationDate = CreationDate;
	}
	
	public String getUserId() {
		return UserId;
	}
	
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}
}
