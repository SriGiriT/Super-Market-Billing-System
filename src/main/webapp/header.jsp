<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="justify-content: center;display: flex;">
<c:if test="${user != null }">
<nav>
    <ul style="list-style-type:none; padding:0; display:flex;">
        <li style="margin-right:20px;">
            <a href="home.jsp">Home</a>
        </li>
        <li style="margin-right:20px;">
            <a href="products" >Products</a>
        </li>
        <c:if test="${user.role != null && user.role == 'ADMIN'}">
        	<li>
        		<a href="loadUsersAndRoles" >Assign Role</a>
        	</li>
        	<li>
        		<a href="loadUsersAndCredit" >Assign Credit</a>
        	</li>
        </c:if>
        <li style="margin-right:20px;">
            <a href="reports.jsp" >Reports</a>
        </li>
        <li style="margin-right:20px;">
            <form id="logoutForm" action="logout" method="post">
	    		<span><a href="javascript:;" onclick="return formSubmition();">Logout</a></span>
	    	</form>
        </li>
    </ul>
</nav>
</c:if>
</div>
<script>
	function formSubmition(){
		if(confirmLogout()){
			document.getElementById('logoutForm').submit();
		}
	}
    function confirmLogout() {
        return confirm("Are you sure you want to log out?");
    }
</script>