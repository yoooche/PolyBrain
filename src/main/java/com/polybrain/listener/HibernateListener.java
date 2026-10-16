package com.polybrain.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static com.polybrain.util.HibernateUtil.*;

@WebListener
public class HibernateListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		getSessionFactory();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		shutdown();
	}
}
