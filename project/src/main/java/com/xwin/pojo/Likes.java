package com.xwin.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "likes")
public class Likes  implements Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	private Long count;

	@Column(name = "like_id")
	private String likeId;

	@Column(name = "createTime")
	private java.util.Date createtime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getLikeid() {
		return likeId;
	}

	public void setLikeid(String likeid) {
		this.likeId = likeid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "Likes{" +
				"id='" + id + '\'' +
				", count=" + count +
				", likeid='" + likeId + '\'' +
				", createtime=" + createtime +
				'}';
	}
}
