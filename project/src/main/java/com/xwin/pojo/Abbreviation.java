package com.xwin.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "abbreviation")
public class Abbreviation  implements Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "abbr_name")
	private String abbrName;

	@Column(name = "full_name")
	private String fullName;

	private String content;

	@Column(name = "image_id")
	private String imageId;

	@Column(name = "data_status")
	private Long dataStatus;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "last_update_time")
	private java.util.Date lastUpdateTime;

	private Long type;

	@Transient
	private Image image;

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

	public String getAbbrName() {
		return abbrName;
	}

	public void setAbbrName(String abbrName) {
		this.abbrName = abbrName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Abbreviation{" +
				"id='" + id + '\'' +
				", userId='" + userId + '\'' +
				", abbrName='" + abbrName + '\'' +
				", fullName='" + fullName + '\'' +
				", content='" + content + '\'' +
				", imageId='" + imageId + '\'' +
				", dataStatus=" + dataStatus +
				", createTime=" + createTime +
				", createBy='" + createBy + '\'' +
				", lastUpdateTime=" + lastUpdateTime +
				", type=" + type +
				", image=" + image +
				'}';
	}
}
