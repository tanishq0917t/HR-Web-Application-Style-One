package com.tanishq.hr.servlets;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.tanishq.hr.dl.*;
public class ConfirmDeleteDesignation extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
int code=0;
try
{
code=Integer.parseInt(request.getParameter("code"));
}catch(NumberFormatException nfe)
{
sendBackView(response);
return;
}
DesignationDAO designationDAO=new DesignationDAO();
try
{
DesignationDTO designation=designationDAO.getByCode(code);
PrintWriter pw=response.getWriter();
response.setContentType("text/html");

pw.println("<!DOCTYPE html>");
pw.println("<html lang='en'>");
pw.println("<head>");
pw.println("<title>HR Application</title>");
pw.println("<script>");
pw.println("function cancelDeletion()");
pw.println("{");
pw.println("document.getElementById('cancelDeletionForm').submit();");
pw.println("}");
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
pw.println("<b>Designations</b><br>");
pw.println("<a href='/styleOne/employeesView'>Employees</a>");
pw.println("</div>");
pw.println("<!-- Left Panel Ends here -->");
pw.println("<!-- Right Panel Starts here -->");
pw.println("<div style='height:68vh;margin-left:105px;margin-right:5px;padding:5px;border:1px solid black'>");
pw.println("<h1>Designation Delete Module</h1>");
pw.println("<form method='post' action='/styleOne/deleteDesignation'>");
pw.println("<input type='hidden' id='code' name='code' value='"+code+"'>");
pw.println("Designation: ");
pw.println("<b>"+designation.getTitle()+"</b><br><br>");
pw.println("Are you sure you want to delete this designation?<br><br>");
pw.println("<button type='submit'>Delete</button>&nbsp;&nbsp;");
pw.println("<button type='button' onclick='cancelDeletion()'>Cancel</button>");
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
pw.println("<form id='cancelDeletionForm' action='/styleOne/designationsView'>");
pw.println("</form>");
pw.println("</body>");
pw.println("</html>");
}catch(DAOException daoException)
{
sendBackView(response);
}
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
doGet(request,response);
}
void sendBackView(HttpServletResponse response)
{
try
{
DesignationDAO designationDAO=new DesignationDAO();
Set<DesignationDTO>designations=designationDAO.getAll();
PrintWriter pw=response.getWriter();
response.setContentType("text/html");



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
pw.println("<b>Designations</b><br>");
pw.println("<a href='/styleOne/employeesView'>Employees</a>");
pw.println("</div>");
pw.println("<!-- Left Panel Ends here -->");
pw.println("<!-- Right Panel Starts here -->");
pw.println("<div style='height:68vh;margin-left:105px;margin-right:5px;padding:5px;overflow:scroll;border:1px solid black'>");
pw.println("<h1>Designations</h1>");
pw.println("<table border='1'>");
pw.println("<thead>");
pw.println("<tr>");
pw.println("<th colspan='4' style='text-align:right;padding:5px'>");
pw.println("<a href='/styleOne/AddDesignation.html'>Add New Designation</a>");
pw.println("</th>");
pw.println("</tr>");
pw.println("<tr>");
pw.println("<th style='width:60px;text-align:center'>S.No.</th>");
pw.println("<th style='width:200px;text-align:center'>Designation</th>");
pw.println("<th style='width:80px;text-align:center'>Edit</th>");
pw.println("<th style='width:80px;text-align:center'>Delete</th>");
pw.println("</tr>");
pw.println("</thead>");
pw.println("<tbody>");
int x=0;
//designations.forEach((designationDTO)->{
for(DesignationDTO designationDTO:designations)
{
int code=designationDTO.getCode();
String title=designationDTO.getTitle();
pw.println("<tr>");
pw.println("<td style='text-align:right'>"+(x+1)+".</td>");
pw.println("<td style='text-align:center'>"+title+"</td>");
pw.println("<td style='text-align:center'><a href='/styleOne/editDesignation?code="+code+"'>Edit</a></td>");
pw.println("<td style='text-align:center'><a href='/styleOne/confirmDeleteDesignation?code="+code+"'>Delete</a></td>");
pw.println("</tr>");
x++;
}
pw.println("</tbody>");
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
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}
}