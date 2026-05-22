package lk.ijse.mental_health_therapy_center.dao.custom.impl;

import com.mysql.cj.Session;
import lk.ijse.mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center.dao.custom.LoginDAO;
import lk.ijse.mental_health_therapy_center.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public boolean save(Object entity) throws Exception {
        return false;
    }

    @Override
    public boolean add(Object entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Object entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public Object search(String id) throws Exception {
        return null;
    }

    @Override
    public List getAll() throws Exception {
        return List.of();
    }
//    @Override
//    public boolean save(User entity) throws Exception {
//        return false;
//    }
//
//    @Override
//    public boolean add(User entity) throws Exception {
//        return false;
//    }
//
//    @Override
//    public boolean update(User entity) throws Exception {
//        return false;
//    }
//
//    @Override
//    public boolean delete(String id) throws Exception {
//        return false;
//    }
//
//    @Override
//    public User search(String id) throws Exception {
//        return null;
//    }
//
//    @Override
//    public List<User> getAll() throws Exception {
//        return List.of();
}
//
//    private static String role;
//    private static int userId;
//    private boolean wrongPsw = false;
//
//    @Override
//    public boolean authenticate(String username, String password) {
//        try {
//            Session session = FactoryConfiguration.getInstance().getSession();
//
//            Query<User> query = session.createQuery("from Users u where u.username=:username", User.class);
//            query.setParameter("username", username);
//            /*query.setParameter("password", BCrypt.withDefaults().hashToString(12, PswField.getText().toCharArray()));*/
//            List<User> list = query.list();
//
//            if (!list.isEmpty()) {
//                /*System.out.println(list.get(0).getRole());*/
//
//                /*System.out.println(list.get(0).getPassword());*/
//                if (!list.isEmpty() && BCrypt.verifyer().verify(password.toCharArray(), list.get(0).getPassword()).verified) {
//                    System.out.println("Login successful!");
//                    role = list.get(0).getRole();
//                    userId = list.get(0).getId();
//                    return true;
//                } else {
//                    wrongPsw = true;
//                    System.out.println("Login failed password incorrect");
//                    return false;
//                }
//
//            }
//            System.out.println("Login failed Username and Password Incorrect");
//
//            session.close();
//        } catch (HibernateException e) {
//            System.out.println("Login failed Username and Password Incorrect");
//            return false;
//        }
//        return false;
//    }
//
//    @Override
//    public String getRole() {
//        return role;
//    }
//
//    @Override
//    public boolean isWrongPsw() {
//        return wrongPsw;
//    }
//
//    @Override
//    public User getUser() {
//        try {
//            Session session = FactoryConfiguration.getInstance().getSession();
//
//            Query<User> query = session.createQuery("from Users u where u.id=:id", User.class);
//            query.setParameter("id", userId);
//            List<User> list = query.list();
//
//            if (!list.isEmpty()) {
//                return list.get(0);
//            }
//
//            session.close();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return null;
//    }
//
//    @Override
//    public List<User> getAll() throws Exception {
//        return List.of();
//    }
//
//    @Override
//    public boolean save(User users) throws SQLException {
//        return false;
//    }
//
//    @Override
//    public boolean add(User entity) throws Exception {
//        return false;
//    }
//
//    @Override
//    public boolean update(User users) throws SQLException {
//        return false;
//    }
//
//    @Override
//    public boolean delete(String id) throws Exception {
//        return false;
//    }
//
//    @Override
//    public User search(String id) throws Exception {
//        return null;
//    }
//
//    @Override
//    public boolean deleteByPK(String pk) throws SQLException {
//        return false;
//    }

