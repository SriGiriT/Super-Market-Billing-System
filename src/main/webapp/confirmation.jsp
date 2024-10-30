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
        const pdfWindow = window.open('DownloadPdfServlet?action=print', '_blank');
        pdfWindow.onload = function () {
            pdfWindow.print();
        };
    }
    </script>
    <link rel="stylesheet" href="style_home.css"> 
</head>
<body style="background-image:url('https://images.pexels.com/photos/264636/pexels-photo-264636.jpeg');
   	background-size: cover;backdrop-filter: blur(5px);        
    background-position: center;color:white;   
    background-repeat: no-repeat;">
	<div class="content-container " style="max-width: 600px;background: rgba( 0, 0, 0, 0.8 );
box-shadow: 0 8px 32px 0 rgba( 31, 38, 135, 0.37 );
backdrop-filter: blur( 20px );
-webkit-backdrop-filter: blur( 20px );
border-radius: 10px;
border: 1px solid rgba( 255, 255, 255, 0.18 );">
	<h1>Invoice Generated and send to mail!!!</h1>
    <div>
    <p>
    ${invoiceBody}
    </p>
    <br/><br/>
      <form action="DownloadPdfServlet" method="post" style="display:inline;margin-right:20px;">
        <button type="submit">Download PDF</button>
    </form>
    <button onclick="printPDF()" style="    background-color: #4CAF50;
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;">Print PDF</button>
    </div>
    </div>
</body>
</html>