## Starting the System with Eclipse

-Convert project into MavenProject
-Change eMail properties in /BVIS/src/main/resources/application.properties
-Change eMail server in /BVIS/src/main/java/com/group5/BVIS/email/eMailController.java
-Input correct REST-Endpoint in:
	-> /BVIS/src/main/java/com/group5/BVIS/delegates/SendAndReceiveOffers.java
	-> /BVIS/src/main/java/com/group5/BVIS/delegates/SendContract.java
	-> /BVIS/src/main/java/com/group5/BVIS/delegates/SendDamageReport.java
	-> /BVIS/src/main/java/com/group5/BVIS/delegates/SendInsuranceFees.java
	-> /BVIS/src/main/java/com/group5/BVIS/delegates/SignalContractEndAndReceiveOverallDamageAssessment.java
-Run project as MavenInstall
-Execute .jar
-Open website at localhost:8080
-Open Camunda at localhost:8080/app/



## Structure

-Web Services:
-> com.group5.BVIS.Billings
-> com.group5.BVIS.Cars
-> com.group5.BVIS.Contracts
-> com.group5.BVIS.DamageReport
-> com.group5.BVIS.email

-Camunda Delegates:
-> com.group5.BVIS.delegates

-REST Models (structuring and bundling data that is sent via REST-call as defined by the interface definition):
-> com.group5.BVIS.RestModels

-Website:
-> com.group5.BVIS.webapp
-> com.group5.BVIS.webapp.formModels
-> /BVIS/src/main/resources/templates
-> /BVIS/src/main/resources/static

-User Forms:
-> /BVIS/src/main/resources/static/forms

-Procsess Models:
-> /BVIS/src/main/resources

