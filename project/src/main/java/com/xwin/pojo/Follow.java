package com.xwin.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "follow")
public class Follow  implements Serializable {

	@Id
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "followed_user_id")
	private Long followedUserId;

	@Column(name = "data_status")
	private Long dataStatus;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "last_update_time")
	private java.util.Date lastUpdateTime;

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

	public Long getFollowedUserId() {
		return followedUserId;
	}

	public void setFollowedUserId(Long followedUserId) {
		this.followedUserId = followedUserId;
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
		return "Follow{" +
				"id='" + id + '\'' +
				", userId='" + userId + '\'' +
				", followedUserId='" + followedUserId + '\'' +
				", dataStatus=" + dataStatus +
				", createTime=" + createTime +
				", lastUpdateTime=" + lastUpdateTime +
				'}';
	}
}
