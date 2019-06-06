package com.xwin.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
public class Comment  implements Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "post_id")
	private String postId;

	@Column(name = "abbreviation_id")
	private String abbreviationId;

	private String content;

	@Column(name = "data_status")
	private Long dataStatus;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "last_update_time")
	private java.util.Date lastUpdateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getAbbreviationId() {
		return abbreviationId;
	}

	public void setAbbreviationId(String abbreviationId) {
		this.abbreviationId = abbreviationId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Long dataStatus) {
		this.dataStatus = dataStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id='" + id + '\'' +
				", userId='" + userId + '\'' +
				", postId='" + postId + '\'' +
				", abbreviationId='" + abbreviationId + '\'' +
				", content='" + content + '\'' +
				", dataStatus=" + dataStatus +
				", createTime=" + createTime +
				", lastUpdateTime=" + lastUpdateTime +
				'}';
	}
}
