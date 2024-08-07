<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gtn.ejbjboss.core.Student"  %>
<%@ page import="java.util.*"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student</title>
</head>
<body>
<h2>Student Search</h2>
<form action="student" method="get">
 Enter Student Id: 
 <select name="id">
   <option>1</option>
   <option>2</option>
   <option>3</option>
   <option>4</option>
   <option>5</option>
 </select>
 
 <BR/>
 <input type="submit" value="Get Student">
</form>
<%
 if(request.getAttribute("student") != null){
	 Student student =  (Student) request.getAttribute("student");
	%>
	<div> 
	<%= student.getId()%>:<%=student.getFirstName() %> <%=student.getSurname() %> 
	</div>
	<div>
	Department: <%=student.getDept() %>
	</div>
	<div>
	Fees: $<%=student.getFees() %> 
	</div>
	<%
 }
%>

<hr/>  
<form method="get" action="student">
<input type="submit" value="Get All Students"/>
</form>
<%
 if(request.getAttribute("students") != null){
	 %><table width="50%" border="1"><%
	 Collection<Student> students = (Collection<Student>)request.getAttribute("students");
	 Iterator<Student> iterator = students.iterator();
	 while(iterator.hasNext()){
		 Student student = iterator.next();
		 %><tr>
		     <td><%=student.getId() %></td>
		     <td><%=student.getFirstName()%></td>
		     <td><%=student.getSurname()%></td>
		     <td><%=student.getDept()%></td>
		     <td>$<%=student.getFees()%></td>
		 </tr><%
	 }%>
	 </table><%
 }
 %>
</body>
<hr/>
<h2>Add New Student To Admissions WaitList</h2>
<form action="waitList" method="post">
<table width="30%" >
  <tr><td>FirstName:</td><td><input type="text" name="firstName"/></td><tr>
  <tr><td>LastName:</td><td><input type="text" name="surname"/></td><tr>
  <tr><td>Department:</td><td><input type="text" name="dept"/></td><tr>
</table>
<input type="submit" value="Add">
</form>
 <%
 if(request.getAttribute("waitList") != null){
	 %><table width="30%" border="1"><%
	 Collection<Student> students = (Collection<Student>)request.getAttribute("waitList");
	 Iterator<Student> iterator = students.iterator();
	 while(iterator.hasNext()){
		 Student student = iterator.next();
		 %><tr>
	       <td><%=student.getFirstName()%></td>
	       <td><%=student.getSurname()%></td>
	       <td><%=student.getDept()%></td>
	     </tr><%
	 }%></table><%
 }
 %>

 
<a href="waitList">Empty WaitList</a>
</html>