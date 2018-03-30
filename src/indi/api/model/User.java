package indi.api.model;
/**
 * 
 * @author hyf
 *
 * @date2018年3月27日
 */
public class User {
	private Integer id;
	private String openid;
	private String nickName;
	private String avatarUrl;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getAvataUrl() {
		return avatarUrl;
	}
	public void setAvataUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public User(String openid, String nickName, String avatarUrl){
		super();
		this.openid = openid;
		this.nickName = nickName;
		this.avatarUrl = avatarUrl;
	}
}
