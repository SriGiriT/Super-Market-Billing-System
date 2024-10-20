package com.billingsystem.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import java.io.IOException;

public class SortIconTag extends TagSupport {
    private static final long serialVersionUID = 1L;
	private String column;
    private String sortColumn;
    private String sortOrder;

    public void setColumn(String column) {
        this.column = column;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String icon = "&#9651;"; 
            
            if (column.equals(sortColumn)) {
                if ("asc".equals(sortOrder)) {
                    icon = "&#9650;";
                } else if ("desc".equals(sortOrder)) {
                    icon = "&#9660;"; 
                }
            }

            out.print("<a href='?sortColumn=" + column + "&sortOrder=" + 
                ("asc".equals(sortOrder) ? "desc" : "asc") + "'>" + icon + "</a>");

        } catch (IOException e) {
            throw new JspException("Error: " + e.getMessage());
        }
        return SKIP_BODY;
    }
}
