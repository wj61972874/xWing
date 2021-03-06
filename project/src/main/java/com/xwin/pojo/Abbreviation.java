package com.xwin.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "abbreviation")
public class Abbreviation  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "abbr_name")
	private String abbrName;

	@Column(name = "full_name")
	private String fullName;

	private String content;

	@Column(name = "image_id")
	private Long imageId;

	@Column(name = "data_status")
	private Long dataStatus;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "create_by")
	private Long createBy;

	@OneToMany(mappedBy = "abbreviationId")
	private List<Image> imageList;

	@Column(name = "last_update_time")
	private java.util.Date lastUpdateTime;

	private Long type;


	@Column(name = "liked_count")
	private Long likedCount;

	@Column(name = "visited_count")
	private Long visitedCount;

	@Override
	public String toString() {
		return "Abbreviation{" +
				"id=" + id +
				", userId=" + userId +
				", abbrName='" + abbrName + '\'' +
				", fullName='" + fullName + '\'' +
				", content='" + content + '\'' +
				", imageId=" + imageId +
				", dataStatus=" + dataStatus +
				", createTime=" + createTime +
				", createBy=" + createBy +
				", lastUpdateTime=" + lastUpdateTime +
				", type=" + type +
				", likedCount=" + likedCount +
				", visitedCount=" + visitedCount +
				", image=" + image +
				'}';
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Image> getImageList() {
		return imageList;
	}

	public  void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}

	@Transient
	private Image image;

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

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
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

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
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

	public Long getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Long likedCount) {
		this.likedCount = likedCount;
	}

	public Long getVisitedCount() {
		return visitedCount;
	}

	public void setVisitedCount(Long visitedCount) {
		this.visitedCount = visitedCount;
	}
}
