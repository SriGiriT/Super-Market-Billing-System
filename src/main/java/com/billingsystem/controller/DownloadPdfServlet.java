package com.billingsystem.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/DownloadPdfServlet")
public class DownloadPdfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        String pdfFilePath = (String) session.getAttribute("pdfPath");

        if (pdfFilePath == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File pdfFile = new File(pdfFilePath);

        String action = request.getParameter("action");
        boolean isPrint = "print".equals(action);

        response.setContentType("application/pdf");

        if (!isPrint) {
            response.setHeader("Content-Disposition", "attachment; filename=Invoice.pdf");
        }

        response.setContentLength((int) pdfFile.length());

        try (FileInputStream fis = new FileInputStream(pdfFile);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        } finally {
            if (!isPrint && pdfFile.exists()) {
            }
        }
	}
	

}
