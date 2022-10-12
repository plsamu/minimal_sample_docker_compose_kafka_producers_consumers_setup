package com.example.backend;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

import javax.annotation.ManagedBean;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.net.InetAddress;

@ManagedBean
public final class ExecutorListener implements ServletContextInitializer, ApplicationListener<WebServerInitializedEvent> {
    private int port;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("onStartup");
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
    }

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        System.out.println("onApplicationReadyEvent");
        try {
            /*System.out.println(env.getProperty("java.rmi.server.hostname"));
            System.out.println(env.getProperty("local.server.port"));*/
            System.out.println(InetAddress.getLocalHost().getHostAddress() + ":" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
