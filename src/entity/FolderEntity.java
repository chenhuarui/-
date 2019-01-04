package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "folder", schema = "document_management", catalog = "")
public class FolderEntity {
    private int id;
    private String folderName;
    private String keyword;
    private String updateTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "folder_name", nullable = false, length = 11)
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
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
        FolderEntity that = (FolderEntity) o;
        return id == that.id &&
                Objects.equals(folderName, that.folderName) &&
                Objects.equals(keyword, that.keyword);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, folderName, keyword);
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
