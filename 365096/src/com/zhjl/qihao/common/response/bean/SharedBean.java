package com.zhjl.qihao.common.response.bean;

import java.io.Serializable;
import java.util.List;

public class SharedBean implements Serializable{

	private String userId;
	private String forumNoteId;
	private String currentPosition;
	private String isShow;
	/**发帖的平台0 app端 1 后台*/
	private String noteSource;
	private String createTime;
	private String nickName;
	private String userPicturePath;
	private String labelName;
	private String howLong;
	private String content;
	private List<DiscussBean> discussList;
	private List<PraiseBean> praiseList;
	private List<PraiseBean> notePictureList;
	private String replyType = "";
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getForumNoteId() {
		return forumNoteId;
	}
	public void setForumNoteId(String forumNoteId) {
		this.forumNoteId = forumNoteId;
	}
	public String getCurrentPosition() {
		return currentPosition;
	}
	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getNoteSource() {
		return noteSource;
	}
	public void setNoteSource(String noteSource) {
		this.noteSource = noteSource;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserPicturePath() {
		return userPicturePath;
	}
	public void setUserPicturePath(String userPicturePath) {
		this.userPicturePath = userPicturePath;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getHowLong() {
		return howLong;
	}
	public void setHowLong(String howLong) {
		this.howLong = howLong;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<DiscussBean> getDiscussList() {
		return discussList;
	}
	public void setDiscussList(List<DiscussBean> discussList) {
		this.discussList = discussList;
	}
	public List<PraiseBean> getPraiseList() {
		return praiseList;
	}
	public void setPraiseList(List<PraiseBean> praiseList) {
		this.praiseList = praiseList;
	}
	public List<PraiseBean> getNotePictureList() {
		return notePictureList;
	}
	public void setNotePictureList(List<PraiseBean> notePictureList) {
		this.notePictureList = notePictureList;
	}
	public String getReplyType() {
		return replyType;
	}
	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		
		SharedBean shared = (SharedBean) o;
		if(shared != null){
			return forumNoteId.equals(shared.getForumNoteId());
		}
		return super.equals(o);
	}
	
	
}
