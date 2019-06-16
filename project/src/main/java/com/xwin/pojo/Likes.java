package com.xwin.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "likes")
public class Likes  implements Serializable {

	@Id
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "like_id")
	private Long likeId;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "last_update_time")
	private java.util.Date lastUpdateTime;

	@Column(name = "data_status")
	private Long dataStatus;

	@Override
	public String toString() {
		return "Likes{" +
				"id=" + id +
				", userId=" + userId +
				", likeId=" + likeId +
				", createTime=" + createTime +
				", lastUpdateTime=" + lastUpdateTime +
				", dataStatus=" + dataStatus +
				'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLikeId() {
		return likeId;
	}

	public void setLikeId(Long likeId) {
		this.likeId = likeId;
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

	public Long getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Long dataStatus) {
		this.dataStatus = dataStatus;
	}
}
