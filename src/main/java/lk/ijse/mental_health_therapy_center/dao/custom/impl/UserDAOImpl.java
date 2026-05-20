package lk.ijse.mental_health_therapy_center.dao.custom.impl;

import lk.ijse.mental_health_therapy_center.config.HibernateUtil;
import lk.ijse.mental_health_therapy_center.dao.custom.UserDAO;
import lk.ijse.mental_health_therapy_center.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User findByUsername(String username) throws Exception {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // HQL query — no native SQL, no JDBC
            String hql = "FROM User u WHERE u.username = :username";

            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);

            List<User> results = query.list();

            // Return the user or null if not found
            return results.isEmpty() ? null : results.get(0);
        }
    }
}