<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<div style="justify-content: center;display: flex;">
<nav>
    <ul style="list-style-type:none; padding:0; display:flex;">
        <li style="margin-right:20px;">
            <a href="home.jsp">Home</a>
        </li>
        <li style="margin-right:20px;">
            <a href="products">Products</a>
        </li>
        <li style="margin-right:20px;">
            <a href="reports.jsp">Reports</a>
        </li>
        <li style="margin-right:20px;">
            <a href="signup.jsp">Login/Signup</a>
        </li>
        <li style="margin-right:20px;">
            <form id="logoutForm" action="logout" method="post">
	    		<span><a href="javascript:;" onclick="return formSubmition();">Logout</a></span>
	    	</form>
        </li>
    </ul>
</nav>
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