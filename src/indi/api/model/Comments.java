package indi.api.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 
 * @author hyf
 *
 * @date2018年3月27日
 */
public class Comments {
	private Integer id;
	private String openid;
	private short type;
	private int item_id;
	private Date date;
	private String comment;
	private int starnum;
	private Timestamp comntime;//mysql  datetime
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
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	
	public Timestamp getComntime() {
		return comntime;
	}
	public void setComntime(Timestamp comntime) {
		this.comntime = comntime;
	}
	public Comments(String openid,short type,int item_id,Date date,String comment){
		this.openid = openid;
		this.type = type;
		this.item_id = item_id;
		this.date = date;
		this.comment = comment;
	}
	
	public Comments(int comnId,String openid,short type,int item_id,Date date,String comment,int starnum,Timestamp comntime){
		this.id = comnId;
		this.openid = openid;
		this.type = type;
		this.item_id = item_id;
		this.date = date;
		this.comment = comment;
		this.starnum = starnum;
		this.comntime = comntime;
	}
	
	
}
