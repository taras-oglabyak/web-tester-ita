# web-tester

Project by SoftServe academy group Java IF-063

To startup project you need:

1. Download JDK 1.8 from http://www.oracle.com/technetwork/java/javase/downloads/index.html and install it using following instructions http://docs.oracle.com/javase/7/docs/webnotes/install/windows/jdk-installation-windows.html;
2. Download Maven from https://maven.apache.org/ and install it using following instructions https://maven.apache.org/install.html;
3. Download MySQL server (at least v5) from http://dev.mysql.com/downloads/mysql/ and install it;
4. Create database using scripts from <code>src\main\resources\sql-scripts</code> and execute command <code>mysql -u {username} -p{password} {dbName} < {script-file-name}</code>. Next scripts are available:
       - 'Web-tester-schema_only-date.sql - includes only database schema;
       - 'Web-tester-test_data-date.sql' - includes database schema with test data;
5. User has ability to log in as QA or Manager. You have to add new users to 'User' table in database - use role <code>ROLE_QA</code> for QA tester user and <code>ROLE_MANAGER</code> for Manager user. In case of using test data use following credentials:
       - QA: email - 'qa@mail.com'; password - '1234';
       - Manager: email - 'manager@mail.com'; password - '1234';
6. Set properties value for database connection in <code>src\main\resources\datasource.properties</code>;
7. Run application using Maven Tomcat plugin. For this execute <code>mvn tomcat7:run</code> in project root folder.
