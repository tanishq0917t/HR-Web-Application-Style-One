package com.tanishq.hr.dl;
import java.sql.*;
import java.util.*;
import java.math.*;
import com.tanishq.hr.dl.*;
public class EmployeeDAO
{
public List<EmployeeDTO> getAll() throws DAOException
{
List<EmployeeDTO>employees=new LinkedList<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet rSet;
rSet=statement.executeQuery("Select employee.employee_id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.basic_salary,employee.gender,employee.is_indian,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code order by employee.name");
EmployeeDTO employee;
int id;
String name;
int designationCode;
String title;
java.util.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
while(rSet.next())
{
id=rSet.getInt("employee_id");
name=rSet.getString("name").trim();
designationCode=rSet.getInt("designation_code");
title=rSet.getString("title").trim();
dateOfBirth=rSet.getDate("date_of_birth");
gender=rSet.getString("gender").trim();
isIndian=rSet.getBoolean("is_indian");
basicSalary=rSet.getBigDecimal("basic_salary");
System.out.println("Salary: "+basicSalary);
panNumber=rSet.getString("pan_number").trim();
aadharCardNumber=rSet.getString("aadhar_card_number").trim();
employee=new EmployeeDTO();
employee.setEmployeeId("A"+(100000+id));
employee.setName(name);
employee.setDesignationCode(designationCode);
employee.setDesignation(title);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(gender.charAt(0));
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
employee.setBasicSalary(basicSalary);
employees.add(employee);
}
rSet.close();
statement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return employees;
}
public void add(EmployeeDTO employeeDTO) throws DAOException
{
System.out.println("Checkpoint 1");


String employeeId=employeeDTO.getEmployeeId();
String name=employeeDTO.getName();
System.out.println("dl name-: "+name);
int designationCode=employeeDTO.getDesignationCode();
System.out.println(designationCode);
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
String panNumber=employeeDTO.getPANNumber();
String aadharNumber=employeeDTO.getAadharCardNumber();
DesignationDAO designationDAO=new DesignationDAO();
System.out.println("Checkpoint 2");
Connection connection=null;
ResultSet resultSet;
PreparedStatement preparedStatement;
try
{
boolean panNumberExists;
boolean aadharNumberExists;
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
preparedStatement.close();
resultSet.close();
System.out.println("Checkpoint 3");
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharNumber);
resultSet=preparedStatement.executeQuery();
aadharNumberExists=resultSet.next();
if(aadharNumberExists && panNumberExists) 
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("aadhar Number ("+aadharNumber+") exists and PAN Number ("+panNumber+") exists");
}
else if(aadharNumberExists) 
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("aadhar number exists:"+aadharNumber);
}
else if(panNumberExists) 
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("pan Number exists: "+panNumber);
}
preparedStatement.close();
resultSet.close();
System.out.println("Checkpoint 4");
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException(" Invalid designation code "+designationCode);
}
preparedStatement.close();
resultSet.close();
System.out.println("Checkpoint 5");
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,basic_salary,gender,is_indian,aadhar_card_number,pan_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,aadharNumber);
preparedStatement.setString(8,panNumber);
preparedStatement.executeUpdate();
System.out.println("Checkpoint 6");
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeId=resultSet.getInt(1);
employeeDTO.setEmployeeId("A"+(100000+generatedEmployeeId));
connection.close();
preparedStatement.close();
resultSet.close();
System.out.println("Checkpoint 7");
}catch(SQLException sqlException)
{
System.out.println("Checkpoint 8");
System.out.println(sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
boolean exists=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return exists;
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
boolean exists=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return exists;
}
public EmployeeDTO getByEmployeeId(String employeeId) throws DAOException
{
System.out.println("10000999");
String name;
int designationCode;
java.util.Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String aadharCardNumber;
String panNumber;
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
System.out.println(employeeId);
int actualEmployeeId=0;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-100000;
}catch(Exception nfe)
{
System.out.println("2");
}
System.out.println("22");
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
System.out.println("3");
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
System.out.println("in catch");
throw new DAOException("Invalid employeeId "+ employeeId);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
System.out.println("4");
EmployeeDTO employeeDTO=null;
while(resultSet.next())
{
employeeId="A"+String.valueOf((resultSet.getInt("employee_id")+100000));
name=resultSet.getString("name");
designationCode=resultSet.getInt("designation_code");
gender=(resultSet.getString("gender")).charAt(0);
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
java.sql.Date sqlDate=resultSet.getDate("date_of_birth");
dateOfBirth=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
aadharCardNumber=resultSet.getString("aadhar_card_number");
panNumber=resultSet.getString("pan_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setPANNumber(panNumber);
}
resultSet.close();
connection.close();
preparedStatement.close();
return employeeDTO;
}catch(SQLException sqlException)
{
System.out.println("5");
throw new DAOException(sqlException.getMessage());
}


}


public void delete(String employeeId) throws DAOException
{

Connection connection=null;
ResultSet resultSet;
PreparedStatement preparedStatement;
employeeId=employeeId.trim();
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-100000;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException(" Invalid employee id "+employeeId);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
connection.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}

}

public boolean employeeIdExists(String employeeId) throws DAOException
{
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("employeeId can not be empty");

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean exists=false;
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-100000;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
preparedStatement.close();
resultSet.close();
connection.close();
return exists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public void update(EmployeeDTO employeeDTO) throws DAOException
{
System.out.println("Starting");
String employeeId=employeeDTO.getEmployeeId().trim();
String name=employeeDTO.getName();
int designationCode=employeeDTO.getDesignationCode();//
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
String panNumber=employeeDTO.getPANNumber();//
String aadharNumber=employeeDTO.getAadharCardNumber();//

Connection connection=null;
ResultSet resultSet;
PreparedStatement preparedStatement;
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-100000;
try
{
boolean panNumberExists;
boolean aadharNumberExists;
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=? and employee_id!=?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and employee_id!=?");
preparedStatement.setString(1,aadharNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
aadharNumberExists=resultSet.next();
if(aadharNumberExists && panNumberExists) 
{
System.out.println("1st");
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("aadhar Number ("+aadharNumber+") exists and PAN Number ("+panNumber+") exists");
}
if(aadharNumberExists && panNumberExists==false) 
{
System.out.println("2nd");
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("aadhar number exists:"+aadharNumber);
}
if(panNumberExists && aadharNumberExists==false) 
{
System.out.println("3rd");
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("pan Number exists: "+panNumber);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
System.out.println("4th");
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException(" Invalid employee id "+employeeId);
}
preparedStatement.close();
resultSet.close();

preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,aadhar_card_number=?,pan_number=?  where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,aadharNumber);
preparedStatement.setString(8,panNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
connection.close();
preparedStatement.close();
System.out.println("Done updation");
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}
}



public EmployeeDTO getByPANNumber(String panNumber) throws DAOException
{

if(panNumber==null) throw new DAOException("panNumber can not be null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("pan number can not be empty");
String employeeId;
String name;
int designationCode;
java.util.Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String aadharCardNumber;

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("Invalid PAN number "+ panNumber);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
EmployeeDTO employeeDTO=null;
while(resultSet.next())
{
employeeId="A"+String.valueOf((resultSet.getInt("employee_id")+100000));
name=resultSet.getString("name");
designationCode=resultSet.getInt("designation_code");
gender=(resultSet.getString("gender")).charAt(0);
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
java.sql.Date sqlDate=resultSet.getDate("date_of_birth");
dateOfBirth=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
aadharCardNumber=resultSet.getString("aadhar_card_number");
panNumber=resultSet.getString("pan_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setPANNumber(panNumber);
}
resultSet.close();
connection.close();
preparedStatement.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}

//*****************************************************************************************

public EmployeeDTO getByAadharCardNumber(String aadharCardNumber) throws DAOException
{

if(aadharCardNumber==null) throw new DAOException("aadharNumber can not be null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("aadhar number can not be empty");
String employeeId;
String name;
int designationCode;
java.util.Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
preparedStatement.close();
resultSet.close();
connection.close();
throw new DAOException("Invalid aadhar number "+ aadharCardNumber);
}
preparedStatement.close();
resultSet.close();
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
EmployeeDTO employeeDTO=null;
while(resultSet.next())
{
employeeId="A"+String.valueOf((resultSet.getInt("employee_id")+100000));
name=resultSet.getString("name");
designationCode=resultSet.getInt("designation_code");
gender=(resultSet.getString("gender")).charAt(0);
isIndian=resultSet.getBoolean("is_indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
java.sql.Date sqlDate=resultSet.getDate("date_of_birth");
dateOfBirth=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
aadharCardNumber=resultSet.getString("aadhar_card_number");
panNumber=resultSet.getString("pan_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setPANNumber(panNumber);
}
resultSet.close();
connection.close();
preparedStatement.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}


}



}