package com.backend.social.repository.systemquery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;



public class SystemQueryManager {
    private SessionFactory sessionFactory;

    public SystemQueryManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void grantSelectOnTableToUser(String tableName, String userName) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        String sql = "GRANT SELECT ON " + tableName + " TO " + userName;
        session.createNativeQuery(sql).executeUpdate();

        tx.commit();
        session.close();
    }

    public void grantRoleToUser(String role,String userName){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        String sql = "GRANT " + role + " TO " + userName;
        session.createNativeQuery(sql).executeUpdate();

        tx.commit();
        session.close();
    }
}
