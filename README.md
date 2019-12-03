# C195 Software II Performance Assessment
#### Author: Maxwell Brown

## Requirements

 - [x] Login Form Requirements
	 - [x] Can determine users location and translate all form controls into two languages (English, French, and Spanish)
 - [x] Customer CRUD Requirements
	 - [x] Add
	 - [x] Update
	 - [x] Delete
	 - [x] Includes address and phone number updates
 - [x] Appointment CRUD Requirements
	 - [x] Add
	 - [x] Update
	 - [x] Delete
	 - [x] Links to the appropriate customer 
 - [x] Appointment Time Requirements
	 - [x] Auto-adjust to time zone and daylight savings time
 - [x] Calendar View 
	 - [x] Weekly View
	 - [x] Monthly View
 - [x] Exception Control Requirements
	 - [x] Scheduling an appointment outside business hours
	 - [x] Scheduling overlapping appointments
	 - [x] Entering nonexistent or invalid customer data
	 - [x] Entering an incorrect username and password
         - [x] Two different types: form data validation, restriction of appointment time scheduling to the hours of 8AM to 5PM (business hours)
 - [x] Lambda Expression Requirements
	 - [x] First lambda expression justified in detail (see controls.calendar.Calendar#upcomingAppointments)
	 - [x] Second lambda expression justified in detail (see reports.appointment.AppointmentReport#refreshData)
 - [x] Alert Requirements
	 - [x] Alerts user on login of upcoming appointments
 - [x] Report Requirements (must include 3)
	 - [x] Number of appointments by month (bar chart)
	 - [x] Schedule for each consultant (table views)
	 - [x] Number of customers per city (bar chart)
 - [x] Tracking Requirements
	 - [x] Record timestamps for user logins in a .txt file (see logins.txt)
