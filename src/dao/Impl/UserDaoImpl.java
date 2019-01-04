package dao.Impl;

import dao.UserDao;
import entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao {

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
     * 判断是否存在该用户，存在则返回用户id
     * @param userName
     * @param password
     * @return
     */
    @Override
    public UserEntity isExistsUser(String userName, String password) {

        String hql = "from UserEntity where userName =:userName and userPassword = :password";
        Query query = getSession().createQuery(hql);
        query.setParameter("userName" , userName);
        query.setParameter("password" , password);

        return (UserEntity) query.uniqueResult();
    }

    @Override
    public List<UserEntity> getPerson(String department) {

        String hql = "from UserEntity where department =:department";
        List<UserEntity> users = getSession().createQuery(hql).setParameter("department" , department).list();
        return users;
    }

    @Override
    public UserEntity selectUser(String name) {

        String hql = "from UserEntity where userName =:name";
        UserEntity user = (UserEntity) getSession().createQuery(hql).setParameter("name" , name).uniqueResult();
        return user;
    }
}
