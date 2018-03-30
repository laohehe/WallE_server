package indi.api.model;

import java.sql.Date;

/**
 * @author hyf
 *
 * @date2018年2月27日
 */
public class Collection {
	private Integer id;
	private String openid;
	private short type;
	private int item_id;
	private Date date;
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
	public Collection(String openid, short type, int item_id, Date date) {
		super();
		this.openid = openid;
		this.type = type;
		this.item_id = item_id;
		this.date = date;
	}
	
	
}
