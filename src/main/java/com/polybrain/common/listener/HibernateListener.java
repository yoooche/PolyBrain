package com.polybrain.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static com.polybrain.common.util.HibernateUtil.*;

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
