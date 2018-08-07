FNCP Project setup

Download IntelliJ 2018-2 CE
Download Git
Download GitHub Desktop 1.3.1
Download Java 8, “1.8.0_161”

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
			

