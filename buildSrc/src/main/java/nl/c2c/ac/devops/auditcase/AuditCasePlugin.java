package nl.c2c.ac.devops.auditcase;

import lombok.extern.slf4j.Slf4j;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

import java.util.Arrays;
import java.util.List;

import nl.c2c.ac.devops.auditcase.buildac.BuildAuditCase;
import nl.c2c.ac.devops.auditcase.deploy.AcClearDomino;
import nl.c2c.ac.devops.auditcase.deploy.AcCopyDomino;
import nl.c2c.ac.devops.auditcase.deploy.AcDeploy;
import nl.c2c.ac.devops.auditcase.startstop.StartHttp;
import nl.c2c.ac.devops.auditcase.startstop.StopHttp;
import nl.c2c.ac.devops.auditcase.version.AcVersionUpdateService;

import static java.lang.Thread.sleep;
import static nl.c2c.ac.devops.auditcase.AcGradleTask.ACCLEAR;
import static nl.c2c.ac.devops.auditcase.AcGradleTask.ACCOPYDOMINO;
import static nl.c2c.ac.devops.auditcase.AcGradleTask.ACSTARTNHTTP;
import static nl.c2c.ac.devops.auditcase.AcGradleTask.ACSTOPNHTTP;
import static nl.c2c.ac.devops.auditcase.AcGradleTask.ASSEMBLE;
import static nl.c2c.ac.devops.auditcase.AcGradleTask.BUILD_AND_MINIMIZE_FRONTEND;
import static nl.c2c.ac.devops.auditcase.AcGradleTask.BUILD_AUDITCASE;
import static nl.c2c.ac.devops.auditcase.AcGradleTask.CLEAN;
import static nl.c2c.ac.devops.auditcase.AcGradleTask.DEPLOYAUDITCASE;

@Slf4j
public class AuditCasePlugin implements Plugin<Project> {

    private static final String GROUP_NAME = "AuditCase plugin steps";

    private static final String GROUP_NAME_MAIN = "AuditCase";

    private static final Action<? super Task> sleep = tasklocal -> {
        try {
            sleep(10 * 1000);
        } catch (InterruptedException e) {
            //ignore
        }
    };
    //For passing arguments from gradle script.

    // deprecation suppressed because there is no alternative in this version, so check gradle 7 how to build this
    @Override
    public void apply(final Project project) {

        //update property file

        final AuditCaseProp auditCaseProp = new AcVersionUpdateService().getProperties(project);
        project.getExtensions().add("auditCaseProp", auditCaseProp);

        final TaskContainer container = project.getTasks();
        // release tasks

        this.apply(container, GROUP_NAME_MAIN, BUILD_AUDITCASE, BuildAuditCase.class, "create production jars and compile and minimize front-end")
          .configure(
            tsk -> {
                tsk.dependsOn(Arrays.asList(
                  project.getTasksByName(CLEAN.toString(), true), // we call a clean here to keep old code from getting into the updateSite
                  project.getTasksByName(ASSEMBLE.toString(), true), // Use assemble here to not run any unit tests

                  // build  the auditcase.prod.js and auditcase.prod.css
                  // minify the auditcase.prod.js  -> auditcase.js
                  // minify the auditcase.prod.css -> auditcase.css
                  project.getTasksByName(BUILD_AND_MINIMIZE_FRONTEND.toString(), true)
                ));
                tsk.mustRunAfter(Arrays.asList(
                  project.getTasksByName(CLEAN.toString(), true),
                  project.getTasksByName(ASSEMBLE.toString(), true),
                  project.getTasksByName(BUILD_AND_MINIMIZE_FRONTEND.toString(), true)
                ));
            });
        // other tasks

        this.apply(container, GROUP_NAME, ACSTOPNHTTP, StopHttp.class, "stop http").configure(
          tsk -> tsk.doLast(sleep));

        this.apply(container, GROUP_NAME, ACSTARTNHTTP, StartHttp.class, "start http").configure(
          tsk -> {
              tsk.mustRunAfter(Arrays.asList(BUILD_AUDITCASE.toString(), ACSTOPNHTTP.toString(), ACCLEAR.toString(), ACCOPYDOMINO.toString()));
              // probably needs to dependOn BUILDAUDITCASE & ACCOPYDOMINO
              tsk.doLast(sleep);
          });

        // deployAuditCase
        this.apply(container, GROUP_NAME, ACCLEAR, AcClearDomino.class, "clear feature files and plugin files").configure(tsk -> {
            // We can only remove the old jars after the domino http task has stopped.
            // To give the server some more time to stop the task we wait until the new jars are ready before deleting the old ones
            tsk.mustRunAfter(ACSTOPNHTTP.toString(), BUILD_AUDITCASE.toString());
            tsk.dependsOn(ACSTOPNHTTP.toString());
        });

        this.apply(container, GROUP_NAME, ACCOPYDOMINO, AcCopyDomino.class, "copies feature files an plugin files").configure(tsk -> {
            // we want the build to be finished and the http task to be stopped before we start copying the new files
            final List<String> tasksInOrder = Arrays.asList(BUILD_AUDITCASE.toString(), ACSTOPNHTTP.toString(), ACCLEAR.toString());
            tsk.mustRunAfter(tasksInOrder);
            tsk.dependsOn(tasksInOrder);
        });

        this.apply(container, GROUP_NAME_MAIN, DEPLOYAUDITCASE, AcDeploy.class, "clears, copy files and start http").configure(tsk -> {
            final List<String> tasksInOrder = Arrays.asList(BUILD_AUDITCASE.toString(), ACSTOPNHTTP.toString(), ACCLEAR.toString(), ACCOPYDOMINO.toString(), ACSTARTNHTTP.toString());
            tsk.dependsOn(tasksInOrder);
            tsk.mustRunAfter(tasksInOrder);
        });

    }

    public <T extends Task> TaskProvider<T> apply(final TaskContainer container, final String groupName, final AcGradleTask name, final Class<T> clazz, final String description) {

        final TaskProvider<T> taskRegistered = container.register(name.toString(), clazz, task -> {
            task.setGroup(groupName);
            task.setDescription(description);
        });

        // https://github.com/gradle/gradle/issues/5510#issuecomment-416860213
        // Java lambdas cannot be used in the plugin only as task inputs (e.g. Task.doFirst, Task.doLast).
        // Whenever a receiver is not a task input (e.g. Project.afterEvaluate, Distribution.contents), it's fine to use Java lambdas.

        taskRegistered.configure(tsk -> tsk.doFirst(new Action<Task>() { // Do not use a lambda here as it disables Gradles ability to mark the task as up-to-date
            @Override
            public void execute(final Task task) {
                log.info(task.getName() + " START for project " + tsk.getProject());
            }
        }));
        taskRegistered.configure(tsk -> tsk.doLast(new Action<Task>() { // Do not use a lambda here as it disables Gradles ability to mark the task as up-to-date
            @Override
            public void execute(final Task task) {
                log.info(task.getName() + " DONE for project " + tsk.getProject());
            }
        }));

        return taskRegistered;
    }
}
