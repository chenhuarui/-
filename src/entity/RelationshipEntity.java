package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "relationship", schema = "document_management", catalog = "")
public class RelationshipEntity {
    private int id;
    private Integer folderId;
    private Integer subordinateFolder;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "folder_id", nullable = true)
    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    @Basic
    @Column(name = "subordinate_folder", nullable = true)
    public Integer getSubordinateFolder() {
        return subordinateFolder;
    }

    public void setSubordinateFolder(Integer subordinateFolder) {
        this.subordinateFolder = subordinateFolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationshipEntity that = (RelationshipEntity) o;
        return id == that.id &&
                Objects.equals(folderId, that.folderId) &&
                Objects.equals(subordinateFolder, that.subordinateFolder);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, folderId, subordinateFolder);
    }

    @Override
    public String toString() {
        return "RelationshipEntity{" +
                "id=" + id +
                ", folderId=" + folderId +
                ", subordinateFolder=" + subordinateFolder +
                '}';
    }
}
