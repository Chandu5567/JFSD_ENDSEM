package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Inserting records
        insertProjects(session);

        // Aggregate functions using Criteria
        performAggregateFunctions(session);

        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    private static void insertProjects(Session session) {
        Project p1 = new Project();
        p1.setProjectName("AI System");
        p1.setDuration(12);
        p1.setBudget(50000);
        p1.setTeamLead("Alice");

        Project p2 = new Project();
        p2.setProjectName("Web Development");
        p2.setDuration(8);
        p2.setBudget(30000);
        p2.setTeamLead("Bob");

        Project p3 = new Project();
        p3.setProjectName("Mobile App");
        p3.setDuration(6);
        p3.setBudget(20000);
        p3.setTeamLead("Charlie");

        session.save(p1);
        session.save(p2);
        session.save(p3);
    }

    private static void performAggregateFunctions(Session session) {
        Criteria criteria = session.createCriteria(Project.class);

        // Count
        criteria.setProjection(Projections.rowCount());
        System.out.println("Count: " + criteria.uniqueResult());

        // Max Budget
        criteria.setProjection(Projections.max("budget"));
        System.out.println("Max Budget: " + criteria.uniqueResult());

        criteria.setProjection(Projections.min("budget"));
        System.out.println("Min Budget: " + criteria.uniqueResult());

        // Sum Budget
        criteria.setProjection(Projections.sum("budget"));
        System.out.println("Total Budget: " + criteria.uniqueResult());

        // Average Budget
        criteria.setProjection(Projections.avg("budget"));
        System.out.println("Average Budget: " + criteria.uniqueResult());
    }
}
