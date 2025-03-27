<%-- 
    Document   : loop
    Created on : Oct 14, 2024, 10:49:13 PM
    Author     : anhkc
--%>

<!--Contruct-->
<%@ tag description="Custom forEach tag" pageEncoding="UTF-8" %>
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="var" required="true" type="java.lang.String" %>

<!--Functioning-->
<%
    java.util.Iterator iterator = items.iterator();
    while (iterator.hasNext()) {
        Object item = iterator.next();
        request.setAttribute(var, item);
%>
<jsp:doBody/>
<%
    }
%>