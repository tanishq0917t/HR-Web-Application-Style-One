package xyz;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class aaa extends HttpServlet
{
public void doGet(HttpServletRequest rq,HttpServletResponse rs)
{
try
{
String nn=rq.getParameter("nm");
String cc=rq.getParameter("ct");
String gg=rq.getParameter("gdr");
System.out.println("Data Arrived\nName: "+nn+"\nCity: "+cc+"\nGender: "+gg);
PrintWriter pw;
pw=rs.getWriter();
rs.setContentType("text/html");
pw.println("<!DOCTYPE html>");
pw.println("<html lang=en>");
pw.println("<head>");
pw.println("<meta charset='utf-8'>");
pw.println("<title>My First Web Application</title>");
pw.println("</head>");
pw.println("<body>");
pw.println("<h3>Data Saved</h3>");
pw.println("<form action='/one/index.html'>");
pw.println("<button type='submit'>OK</button>");
pw.println("</form>");
pw.println("</body>");
pw.println("</html>");
}catch(Exception e)
{
System.out.println(e);
}
}
}
