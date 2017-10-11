<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Todo App</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<style>
body, html {
    height: 100%;
    color: #777;
    line-height: 1.8;


    background-attachment: fixed;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
    min-height: 100%;
}
table{
    padding: 10px;
    border: 1px solid black;
    outline: 1;
    font-size: 100%;
    border-spacing: 10px;
    border-collapse: separate;
}
h1{
	font-family: "italic", sans-serif
	}
</style>
<body onload="mySearch()" background="https://s-media-cache-ak0.pinimg.com/originals/c6/41/7b/c6417b2836b34b8108719c46cecee170.jpg">
	<center><h1> List your Todo's</h1>
	<h3> welcome ${loggedInUser.name} </h3>
	<p align="right"><a class = "btn btn-deafult logout" href="logout">LOGOUT</a></p>	
	<form action="addTodo" method="post">
		<input type="text" name="todoName"> 
		<input type="date" name="todoDate">
		<input type="submit" style="display: none"/>
	</form>
	<input type="search" id="myInput" onkeyup="mySearch()" placeholder="Search for names..">
	</center>
	<table cellspacing="5" cellpadding="5" align="center" style="text-align: center;">
		<c:forEach var="todo" items="${todos}" varStatus="loop">
			<tr>
				<c:if test="${todo.completed == true}">
					<td><a><del>${todo.name }</del></a></td>
				</c:if>
				<c:if test="${todo.completed == false}">
				<td><a href="complete?id=${todo.id }">${todo.name }</a></td>
				</c:if>
				<td><a href="#" onclick="showDetails(${todo.id })" style="color: orange">details</a></td>
				<td><a href="#" onclick="showEdit(${todo.id })"style="color:green" >edit</a></td>
				<td><a href="removeTodo?id=${todo.id }" style="color: red">X</a></td>
			</tr>
		</c:forEach>
		<tr>
			<td><a href="showCompleted"><button>Completed</button></a></td>
			<td><a href="showNotCompleted"><button>Not Completed</button></a></td>
			<td><a href="todo"><button>All</button></a></td>
			<td><a href="removeCompleted"><button>Remove Completed</button></a></td>
		</tr>
	</table>

<!-- Modal -->
  <div class="modal fade" id="details" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Task Details</h4>
        </div>
        <div class="modal-body">
          <p id="toName"></p>
          <p id="toDate"></p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
<!-- Modal -->
  <div class="modal fade" id="edit" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Edit Task</h4>
        </div>
        <div class="modal-body">
        	<form action="editTodo" method="post">
				<input type="text" id="id" name="id" style="display: none"> 
				<input type="text" id="name" name="name"> 
				<input type="date" id="date" name="date">
				<input type="submit" style="display: none"/>
			</form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>

<script type="text/javascript">
	document.getElementById("myInput").value = getSavedValue("myInput"); 
	
	function saveValue(e){
	    var id = e.id; 
	    var val = e.value;
	    sessionStorage.setItem(id, val); 
	}
	 
	function getSavedValue  (v){
	    if (sessionStorage.getItem(v) === null) {
	        return ""; 
	    }
	    return sessionStorage.getItem(v);
	}
	
	function showDetails(id){
		<c:forEach var="todo" items="${todos }" varStatus="loop">
		var todoId = "${todo.id}";
		if(todoId == id){
			document.getElementById("toName").innerHTML="Task: ${todo.name}";
			document.getElementById("toDate").innerHTML="Date: ${todo.date}";
			$(document).ready(function(){
				$('#details').modal('show');
			});
		}
		</c:forEach>
	}
	
	function showEdit(id){
		<c:forEach var="todo" items="${todos }" varStatus="loop">
		var todoId = "${todo.id}";
		if(todoId == id){
			document.getElementById("id").value="${todo.id}";
			document.getElementById("name").value="${todo.name}";
			document.getElementById("date").value="${todo.date}";
			$(document).ready(function(){
				$('#edit').modal('show');
			});
		}
		</c:forEach>
		
	}
	
	function mySearch() {
		    var input, filter, table, a, i, tr, td;
		    input = document.getElementById('myInput');
		    filter = input.value.toUpperCase();
		    table = document.getElementsByTagName('table');
		    tr = table[0].getElementsByTagName('tr');
		    
		    for (i = 0; i < tr.length-1; i++) {
		    	td = tr[i].getElementsByTagName('td')[0];
		        a = td.getElementsByTagName('a')[0];

		        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
		            tr[i].style.display = "";
		            tr[tr.length-1].style.display = "none";
		        } else {
		            tr[i].style.display = "none";
		            tr[tr.length-1].style.display = "none";
		        }
		        if(!filter){
		        	tr[tr.length-1].style.display = "";
		        }
		    }
	        saveValue(input);
	    }
	
</script>				
</body>
</html>