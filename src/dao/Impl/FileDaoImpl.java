package dao.Impl;

import dao.FileDao;
import entity.FileEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class FileDaoImpl implements FileDao {

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
     * 删除指定文件夹下的文件
     * @param currentDirId
     */
    @Override
    public void deleteFile(String currentDirId) {
        String hql = "delete from FileEntity where currentDirId = :id";
        getSession().createQuery(hql).setParameter("id" ,Integer.valueOf(currentDirId)).executeUpdate();
    }

    /**
     * 删除选中的文件
     * @param fileId
     */
    @Override
    public void deleteFile(Integer fileId) {
        String hql = "delete from FileEntity where id = :id";
        getSession().createQuery(hql).setParameter("id" ,Integer.valueOf(fileId)).executeUpdate();
    }

    /**
     * 新增文件-->新增文件表记录
     * @param file
     */
    @Override
    public void addFile(FileEntity file) {
        file.setId(selectNewIdOfFile() + 1);
        getSession().save(file);

    }

    @Override
    public Integer selectNewIdOfFile() {
        String hql = "select max(id) from FileEntity ";
        int newId = (int) getSession().createQuery(hql).uniqueResult();
        return newId;
    }

    @Override
    public FileEntity selectFileOfId(Integer id) {
        String hql = "from FileEntity where id =:id";
        Query query = getSession().createQuery(hql).setParameter("id" , id);
        return (FileEntity) query.uniqueResult();
    }

    @Override
    public void updateName(Integer id, String name) {
        String hql = "update FileEntity f set f.fileName = :fileName where f.id =:id";
        Query query = getSession().createQuery(hql);
        query.setParameter("fileName" , name);
        query.setParameter("id" , id);
        query.executeUpdate();

    }

    @Override
    public List<FileEntity> queryFiles(FileEntity file, String endTime) {

        String hql = "";
        Query query = null;

        if(file.getUpdatePerson()!=null&&!file.getCreateTime().equals("")&&!endTime.equals("")) {

            //根据文件名、时间范围、创建人
            hql = "from FileEntity as f where f.fileName like :name " +
                    "and f.createTime >= :createTime and f.createTime <= :endTime " +
                    "and f.updatePerson =:updatePerson";
            query = getSession().createQuery(hql);
            query.setParameter("updatePerson" , file.getUpdatePerson());
            query.setParameter("createTime" , file.getCreateTime());
            query.setParameter("endTime" , endTime);


        }else if(file.getUpdatePerson()==null&&!file.getCreateTime().equals("")&&!endTime.equals("")){

            //根据文件名、时间范围
            hql = "from FileEntity as f where f.fileName like :name " +
                    "and f.createTime >= :createTime and f.createTime <= :endTime";
            query = getSession().createQuery(hql);
            query.setParameter("createTime" , file.getCreateTime());
            query.setParameter("endTime" , endTime);

        }else {

            //只根据文件名
            hql = "from FileEntity as f where f.fileName like :name ";
            query = getSession().createQuery(hql);
        }

        query.setParameter("name" , '%'+file.getFileName()+'%');

        return  query.list();

    }

    @Override
    public void updateTimeAndPerson(FileEntity file) {
        String hql = "update FileEntity f set f.updateTime = :updateTime, f.updatePerson =:updatePerson where f.id =:id";
        Query query = getSession().createQuery(hql);
        query.setParameter("updateTime", file.getUpdateTime());
        query.setParameter( "updatePerson", file.getUpdatePerson());
        query.setParameter("id" , file.getId());
        query.executeUpdate();
    }

}
