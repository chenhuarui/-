package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "file", schema = "document_management", catalog = "")
public class FileEntity {
    private int id;
    private Integer currentDirId;
    private String fileName;
    private String type;
    private Double size;
    private String clickCount;
    private String createTime;
    private String updateTime;
    private String updatePerson;
    private String keyword;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "current_dir_id", nullable = true)
    public Integer getCurrentDirId() {
        return currentDirId;
    }

    public void setCurrentDirId(Integer currentDirId) {
        this.currentDirId = currentDirId;
    }

    @Basic
    @Column(name = "file_name", nullable = true, length = 11)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 11)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "size", nullable = true, precision = 0)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Basic
    @Column(name = "click_count", nullable = true, length = 11)
    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "update_person", nullable = true, length = 11)
    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    @Basic
    @Column(name = "keyword", nullable = true, length = 225)
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileEntity that = (FileEntity) o;
        return id == that.id &&
                Objects.equals(currentDirId, that.currentDirId) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(type, that.type) &&
                Objects.equals(size, that.size) &&
                Objects.equals(clickCount, that.clickCount) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(updatePerson, that.updatePerson) &&
                Objects.equals(keyword, that.keyword);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, currentDirId, fileName, type, size, clickCount, createTime, updateTime, updatePerson, keyword);
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", currentDirId=" + currentDirId +
                ", fileName='" + fileName + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", clickCount='" + clickCount + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", updatePerson='" + updatePerson + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
