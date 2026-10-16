package com.polybrain.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    private static void buildSessionFactory() {
        try {
            registry = new StandardServiceRegistryBuilder().configure().build();
            MetadataSources metadataSource = new MetadataSources(registry);
            Metadata metadata = metadataSource.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }
        return sessionFactory;
    }

    /**
     * 等這次請求的 transaction（由 HibernateFilter / LoginFilter 開的）成功 commit 後才執行；rollback 就不執行。
     * 給寄信這類慢、又不能在資料還沒存進去時就做的事用。
     */
    public static void runAfterCommit(Runnable task) {
        getSessionFactory().getCurrentSession().getTransaction().registerSynchronization(new AfterCommitTask(task));
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
