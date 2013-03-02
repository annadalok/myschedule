# Project

MySchedule is a web application for managing Quartz Scheduler.

For more information, see project home at http://code.google.com/p/myschedule


# Getting the source code

	bash> hg clone https://code.google.com/p/myschedule
	bash> cd myschedule

The default branch is the latest development of 3.x release work and it may not be stable yet!

If you want an extract release source code, then simply switch to the tag name like this:

	bash> hg update myschedule-2.4.4

Or if you want the 2.4 maintence branch source code, then switch to the branch name like this:

	bash> hg update myschedule-2.4.x

Or to get back to latest development branch, then switch like this:

	bash> hg update default
	

# How to build this project (generate latest jar and war files etc)

Ensure you have JDK6+ and Maven3 installed, then run

	bash> mvn install


# How to run the application

You may deploy the `myschedule/myschedule-web/target/myschedule.war` into any Java Servlet container server.

Or if you want to run it directly from this project source, then try

	bash> mvn install
	bash> cd myschedule-web
	bash> mvn tomcat7:run

Then visit http://localhost:8080/myschedule-web


# Versioning and Maintence Branches

This project is using Mercurial source control and we are using the following workflow branches

* default => The latest development of MySchedule 3.0.0_q21 for quartz 2.1.x
* myschedule-2.4 => Use to maintain the web app for Quartz 2.1.x library using JQuery and table layout.
* myschedule-1.x => Use to maintain the web app for Quartz 1.8.x library using JQuery and table layout.