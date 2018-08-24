FNCP Project setup

Download IntelliJ 2018-2 CE
Download Git
Download GitHub Desktop 1.3.1
Download Java 8, 1.8.0_161
Download Launch 4J, 3.9

Clone FNCP repo from GitHub Desktop
	File -> Clone Repository
		Select inertBear/FNCP

Open FNCP in IntelliJ
    Setup Project Structure
        File -> Project Structure -> Project
	    Project SDK = “1.8”
	    Project language level = “8”
	    For “Project compiler output,” create a “/out” folder 		        at the root level of the project

	File -> Project Structure -> Libraries
	    Add the following libraries from the FNCP/libs folder
		JDatePicker_1.3.4 
		JTimeChooser_0.1.0
		mysql-connector-java-5.1.42-bin

	File -> Project Structure -> Modules -> Sources tab
	    Click on the “src” folder at the root of the FNCP 				project
	    Mark it as “Sources”, the folder should turn blue
			
	File -> Project Structure -> Modules -> Dependencies tab
	    click the checkboxes next to all the dependencies

	“Apply” and “OK”

	Create a “Run Configuration”
	    Run/Debug Configurations
		click the “+”
		select “Application”
		Configuration
		    Main class = “com.devhunter.fncp.FieldNotesInit"
			
	    “Apply” and “OK”
	
Build and Run
		

Export Executable Jar from IntelliJ

	File -> Project Structure -> Artifacts
		(+) add a new artifact -> Add JAR from module with dependencies
		Select FieldNotesInit.java
		Select the Directory to save it in
		Apply/OK
	Build -> Build Artifacts
		Select Artifact -> build (to out/artifacts/FNCP_jar/FNCP_%date%

Create Executable with Launch 4J

	Open Launch4J	
		Specifiy output file location (must end with .exe)
		Select the location of the JAR that we jsut built
		Select the icon
		Go to JRE tab
			put "Min JRE version" at 1.5.0_01
		Click the "gear" icon for "Build Wrapper"
			Specify location of .XML config file
			Upload the new .exe file to dropbox for client