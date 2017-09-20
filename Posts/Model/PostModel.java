package StackOverflow.XMLParser.Posts.Model;


import java.util.LinkedHashMap;
import java.util.Map;

public class PostModel {	
	private String Id;
	private String PostTypeId;
	private String ParentId;
	private String AcceptedAnswerId;
	private String CreationDate;
	private String Score;
	private String ViewCount;
	private String Body;
	private String OwnerUserId;
	private String OwnerDisplayName;
	private String LastEditorUserId;
	private String LastEditorDisplayName;
	private String LastEditDate;
	private String LastActivityDate;
	private String Title;
	private String Tags;
	private String AnswerCount;
	private String CommentCount;
	private String FavoriteCount;
	private String CommunityOwnedDate;
	
	public Map<String, String> getPost() {
		Map<String, String> post = new LinkedHashMap<>();
		
		if(Id!=null) {
			post.put(PostConstant.ID, Id);
		}
		
		if(PostTypeId != null) {
			post.put(PostConstant.POSTTYPEID, PostTypeId);
		}
		
		if(ParentId != null) {
			post.put(PostConstant.PARENTID, ParentId);
		}
		
		if(AcceptedAnswerId != null) {
			post.put(PostConstant.ACCEPTEDANSWERID, AcceptedAnswerId);
		}
		
		if(CreationDate != null) {
			post.put(PostConstant.CREATIONDATE, CreationDate);
		}
		
		if(Score != null) {
			post.put(PostConstant.SCORE, Score);
		}
		
		if(ViewCount != null) {
			post.put(PostConstant.VIEWCOUNT, ViewCount);
		}
		
		if(Body != null) {
			post.put(PostConstant.BODY, Body);
		}
		
		if(OwnerUserId != null) {
			post.put(PostConstant.OWNERUSERID, OwnerUserId);
		}
		
		if(OwnerDisplayName != null) {
			post.put(PostConstant.OWNERDISPLAYNAME, OwnerDisplayName);
		}
		
		if(LastEditorUserId != null) {
			post.put(PostConstant.LASTEDITORUSERID, LastEditorUserId);
		}
		
		if(LastEditorDisplayName != null) {
			post.put(PostConstant.LASTEDITORDISPLAYNAME, LastEditorDisplayName);
		}
		
		if(LastEditDate != null) {
			post.put(PostConstant.LASTEDITDATE, LastEditDate);
		}
		
		if(LastActivityDate != null) {
			post.put(PostConstant.LASTACTIVITYDATE, LastActivityDate);
		}
		
		if(Title != null) {
			post.put(PostConstant.TITLE, Title);
		}
		
		if(Tags != null) {
			post.put(PostConstant.TAGS, Tags);
		}
		
		if(AnswerCount != null) {
			post.put(PostConstant.ANSWERCOUNT, AnswerCount);
		}
		
		if(CommentCount != null) {
			post.put(PostConstant.COMMENTCOUNT, CommentCount);
		}
		
		if(FavoriteCount != null) {
			post.put(PostConstant.FAVORITECOUNT, FavoriteCount);
		}
		
		if(CommunityOwnedDate != null) {
			post.put(PostConstant.COMMUNITYOWNEDDATE, CommunityOwnedDate);			
		}
		
		return post;
	}
	
	public String getId() {
		return Id;
	}
	
	public void setId(String Id) {
		this.Id = Id;
	}
	
	public String getPostTypeId() {
		return PostTypeId;
	}
	
	public void setPostTypeId(String PostTypeId) {
		this.PostTypeId = PostTypeId;
	}
	
	public String getParentId() {
		return ParentId;
	}
	
	public void setParentId(String ParentId) {
		this.ParentId = ParentId;
	}
	
	public String getAcceptedAnswerId() {
		return AcceptedAnswerId;
	}
	
	public void setAcceptedAnswerId(String AcceptedAnswerId) {
		this.AcceptedAnswerId = AcceptedAnswerId;
	}
	
	public String getCreationDate() {
		return CreationDate;
	}
	
	public void setCreationDate(String CreationDate) {
		this.CreationDate = CreationDate;
	}
	
	public String getScore() {
		return Score;
	}
	
	public void setScore(String Score) {
		this.Score = Score;
	}
	
	public String getViewCount() {
		return ViewCount;
	}
	
	public void setViewCount(String ViewCount) {
		this.ViewCount = ViewCount;
	}
	
	public String getBody() {
		return Body;
	}
	
	public void setBody(String Body) {
		this.Body = Body;
	}

	public String getOwnerUserId() {
		return OwnerUserId;
	}
	
	public void setOwnerUserId(String OwnerUserId) {
		this.OwnerUserId = OwnerUserId;
	}
	
	public String getOwnerDisplayName() {
		return OwnerDisplayName;
	}
	
	public void setOwnerDisplayName(String OwnerDisplayName) {
		this.OwnerDisplayName = OwnerDisplayName;
	}
	
	public String getLastEditorUserId() {
		return LastEditorUserId;
	}
	
	public void setLastEditorUserId(String LastEditorUserId) {
		this.LastEditorUserId = LastEditorUserId;
	}
	
	public String getLastEditorDisplayName() {
		return LastEditorDisplayName;
	}
	
	public void setLastEditorDisplayName(String LastEditorDisplayName) {
		this.LastEditorDisplayName = LastEditorDisplayName;
	}
	
	public String getLastEditDate() {
		return LastEditDate;
	}
	
	public void setLastEditDate(String LastEditDate) {
		this.LastEditDate = LastEditDate;
	}
	
	public String getLastActivityDate() {
		return LastActivityDate;
	}
	
	public void setLastActivityDate(String LastActivityDate) {
		this.LastActivityDate = LastActivityDate;
	}
	
	public String getTitle() {
		return Title;
	}
	
	public void setTitle(String Title) {
		this.Title = Title;
	}
	
	public String getTags() {
		return Tags;
	}
	
	public void setTags(String Tags) {
		this.Tags = Tags;
	}
	
	public String getAnswerCount() {
		return AnswerCount;
	}
	
	public void setAnswerCount(String AnswerCount) {
		this.AnswerCount = AnswerCount;
	}
	
	public String getCommentCount() {
		return CommentCount;
	}
	
	public void setCommentCount(String CommentCount) {
		this.CommentCount = CommentCount;
	}
	
	public String getFavoriteCount() {
		return FavoriteCount;
	}
	
	public void setFavoriteCount(String FavoriteCount) {
		this.FavoriteCount = FavoriteCount;
	}
	
	public String getCommunityOwnedDate() {
		return CommunityOwnedDate;
	}
	
	public void setCommunityOwnedDate(String CommunityOwnedDate) {
		this.CommunityOwnedDate = CommunityOwnedDate;
	}
	
	public String toString() {
		return "Id " + Id + "\tCreationDate " + CreationDate + "\tScore " + Score;
	}
}