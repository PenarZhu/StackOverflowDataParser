package StackOverflow.XMLParser.Posts.Model;


import java.util.LinkedHashMap;
import java.util.Map;

public class PostModel {	
	private Integer Id = null;
	private Integer PostTypeId = null;
	private Integer ParentId = null;
	private String AcceptedAnswerId = null;
	private String CreationDate = null;
	private String Score = null;
	private String ViewCount = null;
	private String Body = null;
	private String OwnerUserId = null;
	private String OwnerDisplayName = null;
	private String LastEditorUserId = null;
	private String LastEditorDisplayName = null;
	private String LastEditDate = null;
	private String LastActivityDate = null;
	private String Title = null;
	private String Tags = null;
	private Integer AnswerCount = null;
	private String CommentCount = null;
	private String FavoriteCount = null;
	private String CommunityOwnedDate = null;
	
	private boolean fatalError = false;
	
	public Map<String, String> getPost() {
		Map<String, String> post = new LinkedHashMap<String, String>();
		
		if(Id!=null) {
			post.put(PostConstant.ID, Id.toString());
		}
		
		if(PostTypeId != null) {
			post.put(PostConstant.POSTTYPEID, PostTypeId.toString());
		}
		
		if(ParentId != null) {
			post.put(PostConstant.PARENTID, ParentId.toString());
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
			post.put(PostConstant.ANSWERCOUNT, AnswerCount.toString());
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
	
	// If Id or Post Type Id can not be parsed correctly, the post is useless. And it is recorded as fatal error.
	public boolean hasFatalError() {
		return fatalError;
	}
	
	public Integer getId() {
		return Id;
	}
	
	public void setId(Integer Id) {
		if(Id == null) {
			fatalError = true;
		}
		this.Id = Id;
	}
	
	public Integer getPostTypeId() {
		return PostTypeId;
	}
	
	public void setPostTypeId(Integer PostTypeId) {
		if(PostTypeId == null) {
			fatalError = true;
		}
		this.PostTypeId = PostTypeId;
	}
	
	public Integer getParentId() {
		return ParentId;
	}
	
	public void setParentId(Integer ParentId) {
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
	
	public Integer getAnswerCount() {
		return AnswerCount;
	}
	
	public void setAnswerCount(Integer AnswerCount) {
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
