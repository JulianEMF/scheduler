Scheduling Application
This application allow users to create and edit appointments that are stored in a database. It also allows the creation of customers that can be used to complement the information that each appointment stores.

Author: Julian Montoya Franco
Contact: jmon301@wgu.edu
Version: 1.01
Date: 9/10/2021

IDE: IntelliJ Community 2021.1
Java SDK 11.0.10
JavaFX SDK 11.0.2

Directions:
- Make sure you have the right username and password to login.
- Fill out the username and password fields, then click login.
- You can add customers by clicking on "Add" in the "Customers" area. The application will open a form that collects customer information. Clicking on save will store the newly created customer in a database.
- Previously created customers can be edited by clicking on "Update" in the "Customers" area.
- Clicking on "Delete" in the "Customers" area deletes a customer.
- To add appointments click on "Add" in the "Appointments" area. This will open a window where the appointment information can be filled out. Clicking on save will store the appointment in a database.
- All the appointments can be edited. In order to do so, click on "Update" in the "Appointments" area. The same form used to create the appointment will allow for editing. Clicking on update saves the changes.
- To delete an appointment click on "Delete" in the "Appointments area.
- The application generates three different kind of reports. To explore them, click on each one of them in the "Reports" area. Then select the desired filter for the reports.

The additional report is "Reports by Location". This report filters the appointments based on each appointment's location and shows them in a table view, next to the appointment ID, title, Customer Name, Start, and End.

MYSQL Connector Driver: mysql-connector-java-8.1.22