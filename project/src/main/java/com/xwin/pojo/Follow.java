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
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "followed_user_id")
	private String followedUserId;

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

	public String getFollowedUserId() {
		return followedUserId;
	}

	public void setFollowedUserId(String followedUserId) {
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
