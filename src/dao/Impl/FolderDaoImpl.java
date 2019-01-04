package dao.Impl;

import dao.FolderDao;
import entity.FileEntity;
import entity.FolderEntity;
import entity.RelationshipEntity;
import entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class FolderDaoImpl implements FolderDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 查询到所有文件夹
     * @return
     */
    @Override
    public List<FolderEntity> select() {
        String hql = "from FolderEntity";
        List<FolderEntity> list = (List<FolderEntity>) getSession().createQuery(hql).list();
        return list;
    }

    /**
     * 查询指定文件夹中-->包含的文件夹
     * @param id
     * @return
     */
    @Override
    public List<RelationshipEntity> selectFoldersFromRela(String id) {
        String hql = "from RelationshipEntity where folderId = :id";
        List<RelationshipEntity> list = getSession().createQuery(hql).setParameter("id", Integer.valueOf(id)).list();
        return list;
    }

    /**
     * 查询指定文件夹中-->上级文件夹（返回上级文件夹的id）
     * @param subordinateFolder
     * @return
     */
    @Override
    public String selectSuperFolderIdFromRela(String subordinateFolder) {
        String hql = "from RelationshipEntity where subordinateFolder = :id";
        RelationshipEntity entity = (RelationshipEntity) getSession().createQuery(hql).setParameter("id" , Integer.valueOf(subordinateFolder)).uniqueResult();
        return String.valueOf(entity.getFolderId());
    }

    /**
     * 根据文件夹id返回文件夹对象
     * @param id
     * @return
     */
    @Override
    public FolderEntity selectContainFoldersById(String id) {
        String hql = "from FolderEntity where id = :id";
        Query query = getSession().createQuery(hql).setParameter("id", Integer.valueOf(id));
        return (FolderEntity) query.uniqueResult();
    }


    /**
     * 查询指定文件夹中-->包含的文件
     * @param id
     * @return
     */
    @Override
    public List<FileEntity> selectContainFiles(String id) {
        String hql = "from FileEntity where currentDirId = :id";
        List<FileEntity> list = getSession().createQuery(hql).setParameter("id", Integer.valueOf(id)).list();
        return list;
    }

    @Override
    public UserEntity selectUserById(String id) {
        String hql = "from UserEntity where id = :id";
        Query query = getSession().createQuery(hql).setParameter("id", Integer.valueOf(id));
        return (UserEntity) query.uniqueResult();
    }

    /**
     * 删除文件表中的记录
     * @param folderId
     */
    @Override
    public void deleteFromFolder(String folderId) {
        String hql = "delete from FolderEntity where id = :id";
        getSession().createQuery(hql).setParameter("id" , Integer.valueOf(folderId)).executeUpdate();
    }

    /**
     * 删除文件夹关联表中:作为下级文件夹的记录
     * @param subordinateId
     */
    @Override
    public void deleteFromRelaBySubordId(String subordinateId) {
        String hql = "delete from RelationshipEntity where subordinateFolder = :id";
        getSession().createQuery(hql).setParameter("id" ,Integer.valueOf(subordinateId)).executeUpdate();

    }

    /**
     * 新增文件夹-->文件夹表新增记录
     * @param folder
     * @return
     */
    @Override
    public Integer addFolder(FolderEntity folder) {
        folder.setId(selectNewIdOfFolder() + 1);
        getSession().save(folder);

        return selectNewIdOfFolder();
    }

    @Override
    public Integer selectNewIdOfFolder() {
        String hql = "select max(id) from FolderEntity";
        int newId = (int) getSession().createQuery(hql).uniqueResult();

        return newId;
    }

    /**
     * 将新增的文件夹-->与父文件夹关联起来
     * @param rela
     */
    @Override
    public void addFolderToRela(RelationshipEntity rela) {
        rela.setId(selectNewIdOfRela() + 1);
        getSession().save(rela);
    }

    @Override
    public Integer selectNewIdOfRela() {
        String hql = "select max(id) from RelationshipEntity ";
        int newId = (int) getSession().createQuery(hql).uniqueResult();
        return newId;
    }

    @Override
    public void updateName(Integer id, String name) {
        String hql = "update FolderEntity f set f.folderName = :folderName where f.id =:id";
        Query query = getSession().createQuery(hql);
        query.setParameter("folderName" , name);
        query.setParameter("id" , id);
        query.executeUpdate();
    }

    @Override
    public void updateTime(FolderEntity folder) {
        String hql = "update FolderEntity f set f.updateTime = :updateTime where f.id =:id";
        Query query = getSession().createQuery(hql);
        query.setParameter("updateTime" , folder.getUpdateTime());
        query.setParameter("id" , folder.getId());
        query.executeUpdate();
    }

}
