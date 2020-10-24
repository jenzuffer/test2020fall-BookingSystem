create table Employees (
       ID int not null auto_increment,
       firstname varchar(255) not null,
       lastname varchar(255) not null,
       birthdate date,
       job_description varchar (255),
       PRIMARY KEY (ID)
 )
