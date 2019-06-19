package com.xwin.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "image")
public class Image  implements Serializable {

	@Id
	private Long id;

	@Column(name = "post_id")
	private Long postId;

//	@Column(name = "abbreviation_id")
//	private Long abbreviationId;


    @ManyToOne
    @JoinColumn(name = "abbreviation_id")
    @JsonBackReference
    private Abbreviation abbreviationId;

	private String path;

	private String name;

	private String sequence;

	@Column(name = "data_status")
	private Long dataStatus;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "last_update_time")
	private java.util.Date lastUpdateTime;

	private Long type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Abbreviation getAbbreviationId() {
        return abbreviationId;
    }

    public void setAbbreviationId(Abbreviation abbreviationId) {
        this.abbreviationId = abbreviationId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
	public String toString() {
		return "Image{" +
				"id='" + id + '\'' +
				", postId='" + postId + '\'' +
				", abbreviationId='" + abbreviationId + '\'' +
				", path='" + path + '\'' +
				", name='" + name + '\'' +
				", sequence='" + sequence + '\'' +
				", dataStatus=" + dataStatus +
				", createTime=" + createTime +
				", lastUpdateTime=" + lastUpdateTime +
				", type=" + type +
				'}';
	}
}
