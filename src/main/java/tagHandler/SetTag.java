/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/TagHandler.java to edit this template
 */
package tagHandler;

import java.io.IOException;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author anhkc
 */
public class SetTag extends SimpleTagSupport {

    private String var; // The name of the variable
    private Object value; // The value to set
    private String scope; // The scope (page, request, session, application)

    // Setters for the attributes
    public void setVar(String var) {
        this.var = var;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public void doTag() throws JspException, IOException {
        if (var == null) {
            throw new JspException("Attribute 'var' is required.");
        }

        if (scope == null || scope.isEmpty()) {
            scope = "page";
        }

        if (value == null) {
            value = "No value!";
        }

        PageContext pageContext = (PageContext) getJspContext();

        // Determine the scope and set the variable accordingly
        if ("page".equalsIgnoreCase(scope)) {
            pageContext.setAttribute(var, value);
        } else if ("request".equalsIgnoreCase(scope)) {
            pageContext.getRequest().setAttribute(var, value);
        } else if ("session".equalsIgnoreCase(scope)) {
            pageContext.getSession().setAttribute(var, value);
        } else if ("application".equalsIgnoreCase(scope)) {
            pageContext.getServletContext().setAttribute(var, value);
        } else {
            throw new JspException("Invalid scope: " + scope);
        }
    }

}
