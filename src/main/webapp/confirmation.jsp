<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirmation Page</title>
	<script>
        function printPDF() {
            var pdfUrl = "Invoice.pdf"; 
            var win = window.open(pdfUrl, '_blank');
            win.onload = function() {
                win.print();
            };
        }
    </script>
    <link rel="stylesheet" href="style_home.css"> 
</head>
<body>
	<div class="content-container " style="max-width: 600px;">
	<h1>Invoice Generated and send to mail!!!</h1>
    <div>
    <p>
    ${invoiceBody}
    </p>
    <form>
    <button class="button-submit">
    <a href="Invoice.pdf" style="color: white;text-decoration:none;" download>Download Invoice</a>
    </button>
    <button class="button-submit" onclick="printPDF()">Print Invoice</button>
    </form>
    </div>
    </div>
</body>
</html>