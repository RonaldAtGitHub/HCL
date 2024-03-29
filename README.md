# HCL

HCL

## AuditCase

We develop in IntelliJ using this gradle project it builds the osgi bundles and the update site.
In this project we call the gradle task buildAuditCase to update the manifest.

The update site is saved in nl.c2c.ac.bootstrap\build\distributions and is installed in the updatesite.nsf.

## development

We develop with a pde.launch.ini in the domino\workspace folder to redirect the development to the class files.
The pde.launch.ini file is generated with the Xpage SDK plugin in eclipse (https://www.openntf.org/main.nsf/project.xsp?r=project/XPages%20SDK%20for%20Eclipse%20RCP/releases/FFD60C3A085D3553862585E700563AE6):

## build

We use gradle buildAuditCase to build an update site.
The update site is saved in nl.c2c.ac.bootstrap\build\distributions and is installed in the updatesite.nsf.

To make it work on a clean windows machine we needed to set ECLIPSE_HOME in the gradle.properties file.

## install

After installing the bundle there is an /ac/api/test end-point available displaying the text "test"

## Showcase

In this project we showcase how we work with plugin.xml, HttpAcServlet to run a Jersey server to provide a REST API.

## Issues

We have two issues at this moment since we upgraded to Domino 14 and using java 17

1. The bundles are loaded very slow.
2. We cannot upgrade to the new Jersey server, despite we use java 17 because the HttpAcServlet is called by code that needs javax.servlet.
   We are using com.ibm.pvc.webcontainer.application here.


HCL Domino is calling the servlet with the following code:

This works with
* import javax.servlet.ServletRequest;
* import javax.servlet.ServletResponse;
But we need
* import jakarta.servlet.ServletRequest;
* import jakarta.servlet.ServletResponse;


```service:28, HttpAcServlet (nl.c2c.ac.api2.application.servlet.ac)
service:1661, ServletWrapper (com.ibm.ws.webcontainer.servlet)
handleRequest:937, ServletWrapper (com.ibm.ws.webcontainer.servlet)
handleRequest:85, ServletWrapper (com.ibm.pvc.internal.webcontainer.servlet)
handleRequest:500, ServletWrapper (com.ibm.ws.webcontainer.servlet)
handleRequest:91, CacheServletWrapper (com.ibm.ws.webcontainer.servlet)
handleRequest:864, WebContainer (com.ibm.ws.webcontainer)
handleRequest:25, WebContainerBridge (com.ibm.pvc.internal.webcontainer)
doService:141, WebApplicationsTracker (com.ibm.domino.osgi.core.webContainer)
invoke0:-1, NativeMethodAccessorImpl (jdk.internal.reflect)
invoke:77, NativeMethodAccessorImpl (jdk.internal.reflect)
invoke:43, DelegatingMethodAccessorImpl (jdk.internal.reflect)
invoke:568, Method (java.lang.reflect)
invokeWebAppContainerService:207, OSGIWebContainerModule (com.ibm.domino.xsp.adapter.osgi.webContainer)
doService:178, OSGIWebContainerModule (com.ibm.domino.xsp.adapter.osgi.webContainer)
doService:418, OSGIService (com.ibm.domino.xsp.adapter.osgi)
doService:371, LCDEnvironment (com.ibm.designer.runtime.domino.adapter)
service:327, LCDEnvironment (com.ibm.designer.runtime.domino.adapter)
service:302, XspCmdManager (com.ibm.domino.xsp.bridge.http.engine)
