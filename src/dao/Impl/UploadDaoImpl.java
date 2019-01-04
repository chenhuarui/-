package dao.Impl;

import dao.UploadDao;
import entity.FileEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UploadDaoImpl implements UploadDao {

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


    @Override
    public void uploadFile(FileEntity file) {
        getSession().save(file);
    }

}
