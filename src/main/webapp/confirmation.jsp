<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirmation Page</title>
	<script>
        function printPDF() {
        	console.log("${pdfPath}");
            var pdfUrl = "Invoice.pdf"; 
            var win = window.open(pdfUrl, '_blank');
            win.onload = function() {
                win.print();
            };
        }
    </script>
</head>
<body>
	
	<h1>Invoice Generated and send to mail!!!</h1>
	<p><a href="home.jsp">Go Home</a></p><br><br>
    <div>
    <span><button><a href="Invoice.pdf" style="color: black;text-decoration: none;" download>Download PDF</a></button></span>
    <span><button onclick="printPDF()">Print PDF</button></span>
    </div>
</body>
</html>