package com.xwin.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class Message  implements Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "user_id")
	private String userId;

	private String content;

	private Long type;

	@Column(name = "read_flag")
	private Long readFlag;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(Long readFlag) {
		this.readFlag = readFlag;
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
		return "Message{" +
				"id='" + id + '\'' +
				", userId='" + userId + '\'' +
				", content='" + content + '\'' +
				", type=" + type +
				", readFlag=" + readFlag +
				", dataStatus=" + dataStatus +
				", createTime=" + createTime +
				", lastUpdateTime=" + lastUpdateTime +
				'}';
	}
}
