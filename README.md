# HCL Showcase

In this project we call the gradle task buildAuditCase to update the manifest.

The update site is saved on `..\in nl.c2c.ac.bootstrap\build\distributions` and then this `.zip` file is installed in the `updatesite.nsf` within Domino.

## Build & Use this project

The update site is saved in nl.c2c.ac.bootstrap\build\distributions and is installed in the updatesite.nsf.

- We use gradle buildAuditCase to build an update site.
  - The update site is saved in `..\nl.c2c.ac.bootstrap\build\distributions`
- To make it work on a clean windows machine we needed to set ECLIPSE_HOME in the gradle.properties file.
- In this project we showcase how we work with plugin.xml, HttpAcServlet to run a Jersey server to provide a REST API.

In order to build this project follow these steps:
1. Clone the repository
2. Open the project in IntelliJ
   1. Run the gradle task `assemble` to build the manifest files within `../META-INF/MANIFEST.MF`. Make sure these files exists before you continue.
3. Open the project in eclipse (Administrator Mode) (Make sure the input source has loaded the bundles (`ac` & `ac.api2` & `ac.bootstrap`)).
   1. Make sure you have the Xpage SDK plugin installed in eclipse.
      1. Download the plugin from [OpenNTF](https://www.openntf.org/main.nsf/project.xsp?r=project/XPages%20SDK%20for%20Eclipse%20RCP/releases/FFD60C3A085D3553862585E700563AE6)
   2. Within the `Target Platform` settings, add the `XPages Domino Plugin Target`, by pressing "Add" and then "Template > Domino Install target".
   3. Reload this target platform, and make sure there are plugins available inside the locations tab (At the bottom).
   4. Inside the `Run Configurations` make sure that the the 3 bundles are selected correctly.
   5. Run the configuration `AuditCase Development` then a `pde.launch.ini` file is generated in the domino\workspace folder to redirect the development to the class files.
4. Run the gradle task `buildAuditCase` this will also build an update site inside the `nl.c2c.ac.bootstrap\build\distributions` folder that we are using for development, but we do not need this for this project.
5. Start the Domino 14 server.
   1. Make sure the bundles are loaded correctly to ensure the next step will work without any issues.
   2. In order to manually check the loaded bundles, you can use the following command in the Domino console: `tell http osgi ss nl.c2c`.
   3. At the bottom of this page we have a list of issues we are facing at this moment. The first issue is that the bundles are loaded very slow, which you notice here.
6. Make a HTTP request to `/ac/api/test`.
   1. If this request has run successfully, you will see the text "test" and a 200 status code.
   2. If the request has failed, you will see a 404 status code or 500, and something went wrong during the installation of this project.


## How we use this internally
We develop using IntelliJ, and with this gradle project, we build the osgi bundles and the `.zip` file for the updatesite.

We create a `pde.launch.ini` in the domino\workspace folder to redirect the development to the class files.
The pde.launch.ini file is generated with the Xpage SDK plugin in eclipse (https://www.openntf.org/main.nsf/project.xsp?r=project/XPages%20SDK%20for%20Eclipse%20RCP/releases/FFD60C3A085D3553862585E700563AE6):


## Current Issues we are facing

We have two issues at this moment while upgrading to Domino 14 and using java 17 within the project.

1. **The bundles are loaded very slow.**
   1. Issue ticket: CS0493282
2. **We cannot upgrade to the new Jersey server, despite we use java 17 because the HttpAcServlet is called by code that needs javax.servlet.
   We are using com.ibm.pvc.webcontainer.application here.**
   1. Issue ticket: CS0491973


HCL Domino is calling the servlet with the following code:

This works with
* import javax.servlet.ServletRequest;
* import javax.servlet.ServletResponse;


But we need
* import jakarta.servlet.ServletRequest;
* import jakarta.servlet.ServletResponse;


If we change the code to use the new Jakarta servlet, we get the following error:
```java
service:28, HttpAcServlet (nl.c2c.ac.api2.application.servlet.ac)
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
```
