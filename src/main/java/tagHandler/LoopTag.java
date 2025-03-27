/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/TagHandler.java to edit this template
 */
package tagHandler;

import jakarta.servlet.jsp.JspContext;
import java.util.Collection;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.JspFragment;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author anhkc
 */
public class LoopTag extends SimpleTagSupport {

    private String var;
    private Collection<?> items;
    private int begin = -1;
    private int end = -1;

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setItems(Collection<?> items) {
        this.items = items;
    }

    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException, IOException {
        if (var == null || var.isEmpty()) {
            throw new JspException("Attribute 'var' is required.");
        }
        if (items == null || items.isEmpty()) {
            throw new JspException("There is no data to show.");
        }

        PageContext pageContext = (PageContext) getJspContext();

        Iterator<?> iterator = items.iterator();
        int count = -1;

        if (begin > -1 || end > -1) {

            while (iterator.hasNext()) {
                count++;
                if (count > end) {
                    break;
                } else if (count >= begin) {
                    pageContext.setAttribute(var, iterator.next());
                    JspWriter out = pageContext.getOut();
                    getJspBody().invoke(out);
                }

            }

        }

    }

}
