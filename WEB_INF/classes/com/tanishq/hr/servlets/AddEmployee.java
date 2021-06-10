package com.tanishq.hr.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.tanishq.hr.dl.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class AddEmployee extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
PrintWriter pw=response.getWriter();
response.setContentType("text/html");
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
String name=request.getParameter("name");
System.out.println("Name=>>>>>"+name);
int designationCode=Integer.parseInt(request.getParameter("designationCode"));
Date dob=sdf.parse(request.getParameter("dateOfBirth"));
char gender=request.getParameter("gender").charAt(0);
String isIndian=request.getParameter("isIndian");
if(isIndian==null)isIndian="N";
BigDecimal basicSalary=new BigDecimal(request.getParameter("basicSalary"));
String panNumber=request.getParameter("panNumber");
String aadharCardNumber=request.getParameter("aadharCardNumber");
EmployeeDTO employee=new EmployeeDTO();
employee.setName(name);
employee.setDesignationCode(designationCode);
employee.setDateOfBirth(dob);
employee.setGender(gender);
employee.setIsIndian(isIndian.equals("Y"));
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
DesignationDAO designationDAO=new DesignationDAO();
EmployeeDAO employeeDAO=new EmployeeDAO();
try
{
boolean designationCodeExists=designationDAO.codeExists(designationCode);
boolean panNumberExists=employeeDAO.panNumberExists(panNumber);
boolean aadharCardNumberExists=employeeDAO.aadharCardNumberExists(aadharCardNumber);


if(panNumberExists==true || aadharCardNumberExists==true || designationCodeExists==false)
{




response.setContentType("text/html");
pw.println("<!DOCTYPE html>");
pw.println("<html lang='en'>");
pw.println("<head>");
pw.println("<title>HR Application</title>");
pw.println("<script>");
pw.println("function validate(frm)");
pw.println("{");
pw.println("var valid=true;");
pw.println("var firstErrorSection=null;");
pw.println("var name=frm.name.value.trim();");
pw.println("var nameErrorSection=document.getElementById('nameErrorSection');");
pw.println("nameErrorSection.innerHTML='';");
pw.println("if(name.length==0)");
pw.println("{");
pw.println("nameErrorSection.innerHTML='Name Required';");
pw.println("if(firstErrorSection==null)firstErrorSection=frm.name;");
pw.println("valid=false;");
pw.println("}");
pw.println("");
pw.println("var designationCode=frm.designationCode.value;");
pw.println("var designationCodeErrorSection=document.getElementById('designationCodeErrorSection');");
pw.println("designationCodeErrorSection.innerHTML='';");
pw.println("if(designationCode==-1)");
pw.println("{");
pw.println("designationCodeErrorSection.innerHTML='Select Designation';");
pw.println("if(firstErrorSection==null)firstErrorSection=frm.designationCode;");
pw.println("valid=false;");
pw.println("}");
pw.println("");
pw.println("var genderErrorSection=document.getElementById('genderErrorSection');");
pw.println("genderErrorSection.innerHTML='';");
pw.println("if(frm.gender[0].checked==false && frm.gender[1].checked==false)");
pw.println("{");
pw.println("genderErrorSection.innerHTML='Select Gender';");
pw.println("if(firstErrorSection==null)firstErrorSection=frm.gender;");
pw.println("valid=false;");
pw.println("}");
pw.println("");
pw.println("var dateOfBirth=frm.dateOfBirth.value;");
pw.println("var dateOfBirthErrorSection=document.getElementById('dateOfBirthErrorSection');");
pw.println("dobErrorSection.innerHTML='';");
pw.println("if(dateOfBirth.length==0)");
pw.println("{");
pw.println("dobErrorSection.innerHTML='Select Date';");
pw.println("if(firstErrorSection==null)firstErrorSection=frm.dateOfBirth;");
pw.println("valid=false;");
pw.println("}");
pw.println("");
pw.println("var basicSalary=frm.basicSalary.value.trim();");
pw.println("var basicSalaryErrorSection=document.getElementById('basicSalaryErrorSection');");
pw.println("basicSalaryErrorSection.innerHTML='';");
pw.println("var isBasicValid=true;");
pw.println("if(basicSalary.length==0)");
pw.println("{");
pw.println("basicSalaryErrorSection.innerHTML='Basic Salary Required';");
pw.println("if(firstErrorSection==null)firstErrorSection=frm.basicSalary;");
pw.println("valid=false;");
pw.println("}");
pw.println("else");
pw.println("{");
pw.println("var v='0123456789.';");
pw.println("var e=0");
pw.println("while(e<basicSalary.length)");
pw.println("{");
pw.println("if(v.indexOf(basicSalary.charAt(e))==-1)");
pw.println("{");
pw.println("basicSalaryErrorSection.innerHTML='Invalid Basic Salary';");
pw.println("if(firstErrorSection==null)firstErrorSection=frm.basicSalary;");
pw.println("valid=false;");
pw.println("isBasicValid=false;");
pw.println("}");
pw.println("e++;");
pw.println("}");
pw.println("if(isBasicValid)");
pw.println("{");
pw.println("var dot=basicSalary.indexOf('.');");
pw.println("if(dot!=-1)");
pw.println("{");
pw.println("var fractions=basicSalary.length-(dot+1);");
pw.println("if(fractions>2)");
pw.println("{");
pw.println("if(firstErrorSection==null)firstErrorSection=frm.basicSalary;");
pw.println("valid=false;");
pw.println("basicSalaryErrorSection.innerHTML='Invalid Basic Salary';");
pw.println("}");
pw.println("}");
pw.println("}");
pw.println("}");
pw.println("");
pw.println("var panNumber=frm.panNumber.value.trim();");
pw.println("var panNumberErrorSection=document.getElementById('panNumberErrorSection');");
pw.println("panNumberErrorSection.innerHTML='';");
pw.println("if(panNumber.length==0)");
pw.println("{");
pw.println("panNumberErrorSection.innerHTML='PAN Number Required';");
pw.println("if(firstErrorSection==null)firstErrorSection=frm.panNumber;");
pw.println("valid=false;");
pw.println("}");
pw.println("");
pw.println("var aadharCardNumber=frm.aadharCardNumber.value.trim();");
pw.println("var aadharCardNumberErrorSection=document.getElementById('aadharCardNumberErrorSection');");
pw.println("aadharCardNumberErrorSection.innerHTML='';");
pw.println("if(aadharCardNumber.length==0)");
pw.println("{");
pw.println("aadharCardNumberErrorSection.innerHTML='Aadhar Card Number Required';");
pw.println("if(firstErrorSection==null)firstErrorSection=frm.aadharCardNumber;");
pw.println("valid=false;");
pw.println("}");
pw.println("");
pw.println("if(!valid)firstErrorSection.focus();");
pw.println("return valid;");
pw.println("}");
pw.println("");
pw.println("</script>");
pw.println("</head>");
pw.println("<body>");
pw.println("<!-- Main Container starts here-->");
pw.println("<div style='width:90hw;height:auto;border:1px solid black'>");
pw.println("<!-- Header Starts here-->");
pw.println("<div style='margin:5px;width:90hw;height:auto;border:1px solid black'>");
pw.println("<div style='margin-top:9px;margin-bottom:9px;font-size:20pt'>Tanishq Rawat</div>");
pw.println("</div><!-- Header ends here-->");
pw.println("<!-- Content Section Starts here-->");
pw.println("<div style='width:90hw;height:70vh;margin:5px;border:1px solid white'>");
pw.println("<!-- Left Panel Starts here -->");
pw.println("<div style='height:68vh;margin:5px;float:left;padding:5px;border:1px solid black'>");
pw.println("<a href='/styleOne/index.html'>Home</a><br><br>");
pw.println("<a href='/styleOne/designationsView'>Designations</a><br>");
pw.println("<b>Employees</b>");
pw.println("</div>");
pw.println("<!-- Left Panel Ends here -->");
pw.println("<!-- Right Panel Starts here -->");
pw.println("<div style='height:68vh;margin-left:105px;margin-right:5px;padding:5px;border:1px solid black'>");
pw.println("<h4>Employee Add Module</h1>");
pw.println("<form action='/styleOne/addEmployee' onsubmit='return validate(this)'>");
pw.println("<table>");
pw.println("<tr>");
pw.println("<td>Name</td>");
pw.println("<td><input type='name' id='name' name='name' maxLength='35' size='36' value='"+name+"'>");
pw.println("<span id='nameErrorSection' style='color:red'></span></td>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td>Designation</td>");
pw.println("<td><select id='designationCode' name='designationCode'>");
pw.println("<option value='-1'>&lt;Select Designation&gt;</option>");
Set<DesignationDTO> designations=designationDAO.getAll();
for(DesignationDTO designation:designations)
{
int code=designation.getCode();
String title=designation.getTitle();
if(code==designationCode) pw.println("<option selected value='"+code+"'>"+title+"</option>");
else pw.println("<option value='"+code+"'>"+title+"</option>");
}
pw.println("</select>");
if(designationCodeExists==false)
{
pw.println("<span id='designationCodeErrorSection' style='color:red'>Invalid Designation</span>");
}
else
{
pw.println("<span id='designationCodeErrorSection' style='color:red'></span>");
}

pw.println("</td>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td>Date of Birth</td>");
pw.println("<td><input type='date' id='dateOfBirth' name='dateOfBirth' value='"+sdf.format(dob)+"'>");
pw.println("<span id='dobErrorSection' style='color:red'></span>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td>Gender</td>");
pw.println("<td>");
if(gender!='M')
{
pw.println("<input type='radio' id='male' name='gender' value='M'>Male");
}
else
{
pw.println("<input checked type='radio' id='male' name='gender' value='M'>Male");
}
pw.println("&nbsp;&nbsp;&nbsp;&nbsp;");
if(gender!='F')
{
pw.println("<input type='radio' id='female' name='gender' value='F'>Female");
}
else
{
pw.println("<input checked type='radio' id='female' name='gender' value='F'>Female");
}
pw.println("<span id='genderErrorSection' style='color:red'></span>");
pw.println("</td>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td>Indian ?</td>");
pw.println("<td>");
if(isIndian.equals("Y"))
{
pw.println("<input checked type='checkbox' name='isIndian' id='isIndian' value='Y'></td> ");
}
else
{
pw.println("<input type='checkbox' name='isIndian' id='isIndian' value='N'></td> ");
}
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td>Basic Salary</td>");
pw.println("<td><input type='text' style='text-align:right' id='basicSalary' name='basicSalary' value='"+basicSalary.toPlainString()+"'>");
pw.println("<span id='basicSalaryErrorSection' style='color:red'></span>");
pw.println("</td>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td>PAN Number</td>");
pw.println("<td><input type='text' id='panNumber' name='panNumber' maxLength='15' size='16' value='"+panNumber+"'>");
if(panNumberExists)
{
pw.println("<span id='panNumberErrorSection' style='color:red'>Pan Number Exists</span>");
}
else
{
pw.println("<span id='panNumberErrorSection' style='color:red'></span>");
}
pw.println("</td>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<td>Aadhar Card Number</td>");
pw.println("<td><input type='text' id='aadharCardNumber' name='aadharCardNumber' maxLength='15' size='16' value='"+aadharCardNumber+"'>");
if(aadharCardNumberExists)
{
pw.println("<span id='aadharCardNumberErrorSection' style='color:red'>Aadhar Card Number Exists</span>");
}
else
{
pw.println("<span id='aadharCardNumberErrorSection' style='color:red'></span>");
}
pw.println("</td>");
pw.println("</tr>");
pw.println("");
pw.println("<tr>");
pw.println("<td>");
pw.println("<button type='submit'>Add</button>&nbsp;&nbsp;</td>");
pw.println("<td><button type='button' onclick='cancelAddition()'>Cancel</button></td>");
pw.println("</tr>");
pw.println("</table>");
pw.println("</form>");
pw.println("</div>");
pw.println("<!-- Right Panel Ends here -->");
pw.println("</div> <!-- Content Section Ends here -->");
pw.println("<!-- Footer Starts here -->");
pw.println("<div style='width:90hw;height:auto;margin:5px;text-align:center;border:1px solid white'>");
pw.println("&copy;Tanishq Rawat 2021");
pw.println("</div>");
pw.println("<!-- Footer Ends here -->");
pw.println("</div><!-- Main Container Ends here -->");
pw.println("<form id='cancelAdditionForm' action='/styleOne/designationsView'>");
pw.println("</form>");
pw.println("</body>");
pw.println("</html>");





}

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
try
{
employeeDAO.add(employee);

pw.println("<!DOCTYPE html>");
pw.println("<html lang='en'>");
pw.println("<head>");
pw.println("<title>HR Application</title>");
pw.println("</head>");
pw.println("<body>");
pw.println("<!-- Main Container starts here-->");
pw.println("<div style='width:90hw;height:auto;border:1px solid black'>");
pw.println("<!-- Header Starts here-->");
pw.println("<div style='margin:5px;width:90hw;height:auto;border:1px solid black'>");
pw.println("<div style='margin-top:9px;margin-bottom:9px;font-size:20pt'>Tanishq Rawat</div>");
pw.println("</div><!-- Header ends here-->");
pw.println("<!-- Content Section Starts here-->");
pw.println("<div style='width:90hw;height:70vh;margin:5px;border:1px solid white'>");
pw.println("<!-- Left Panel Starts here -->");
pw.println("<div style='height:68vh;margin:5px;float:left;padding:5px;border:1px solid black'>");
pw.println("<a href='/styleOne/index.html'>Home</a><br><br>");
pw.println("<a href='/styleOne/designationsView'>Designations</a><br>");
pw.println("<a href='/styleOne/employeesView'>Employees</a>");
pw.println("</div>");
pw.println("<!-- Left Panel Ends here -->");
pw.println("<!-- Right Panel Starts here -->");
pw.println("<div style='height:68vh;margin-left:105px;margin-right:5px;padding:5px;border:1px solid black'>");
pw.println("<h3>Notification</h3>");
pw.println("Employee Added<br><br>");
pw.println("Add more employees?<br>");
pw.println("<table>");
pw.println("<tr>");
pw.println("<td>");
pw.println("<form action='/styleOne/getEmployeeAddForm'>");
pw.println("<button type='submit'>Yes</button>");
pw.println("</form>");
pw.println("</td>");
pw.println("<td>");
pw.println("<form action='/styleOne/employeesView'>");
pw.println("<button type='submit'>No</button>");
pw.println("</form>");
pw.println("</td>");
pw.println("</tr>");
pw.println("</table>");
pw.println("</div>");
pw.println("<!-- Right Panel Ends here -->");
pw.println("</div> <!-- Content Section Ends here -->");
pw.println("<!-- Footer Starts here -->");
pw.println("<div style='width:90hw;height:auto;margin:5px;text-align:center;border:1px solid white'>");
pw.println("&copy;Tanishq Rawat 2021");
pw.println("</div>");
pw.println("<!-- Footer Ends here -->");
pw.println("</div><!-- Main Container Ends here -->");
pw.println("</body>");
pw.println("</html>");

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}catch(Exception e)
{
System.out.println(e);
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
doGet(request,response);
}
}