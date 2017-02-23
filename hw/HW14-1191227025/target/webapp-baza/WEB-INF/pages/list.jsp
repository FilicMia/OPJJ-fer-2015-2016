<%@page import="hr.fer.zemris.java.tecaj_13.model.VotingOption"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%
  List<VotingOption> options = (List<VotingOption>)request.getAttribute("options");
%>
<html>
  <body>

  <b>Pronađeni su sljedeći unosi:</b><br>

  <% if(options.isEmpty()) { %>
    Nema unosa.
  <% } else { %>
    <ul>
    <% for(VotingOption u : options) { %>
    <li>[ID=<%= u.getId() %>] </li>  
    <% } %>  
    </ul>
  <% } %>

  </body>
</html>