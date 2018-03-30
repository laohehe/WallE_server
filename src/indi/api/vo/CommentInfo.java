package indi.api.vo;

import java.sql.Date;

/**
 * 
 * @author hyf
 *
 * @date2018年3月29日
 */
public class CommentInfo {
	private String openid;
	private String nickName;
	private String avatarUrl;
	private String comment;
	private int starnum;
	private String comntime;//评论时间
	//评论记录的标识
	private int comnId;
	//当前用户是否已点赞过该评论
	private boolean isStar;
	
	
	public int getComnId() {
		return comnId;
	}
	public void setComnId(int comnId) {
		this.comnId = comnId;
	}
	public boolean isStar() {
		return isStar;
	}
	public void setStar(boolean isStar) {
		this.isStar = isStar;
	}
	public String getComntime() {
		return comntime;
	}
	public void setComntime(String comntime) {
		this.comntime = comntime;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getStarnum() {
		return starnum;
	}
	public void setStarnum(int starnum) {
		this.starnum = starnum;
	}
	public CommentInfo(String openid, String nickName, String avatarUrl,
			String comment, int starnum,String comntime,int comnId,boolean isStar) {
		super();
		this.openid = openid;
		this.nickName = nickName;
		this.avatarUrl = avatarUrl;
		this.comment = comment;
		this.starnum = starnum;
		this.comntime = comntime;
		this.comnId = comnId;
		this.isStar = isStar;
	}
	
	
}
