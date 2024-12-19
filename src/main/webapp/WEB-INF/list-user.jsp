<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp"%>
<body>
<c:forEach var="user" items="${requestScope.users}">
    <img id="image"
         src="images/${user.image}"
         width="50"
         alt="${user.image}">
    <a href="users-profile?id=${user.id}">${user.login}</a>
    <br>
    <br>
</c:forEach>

</body>
<%@include file="parts/footer.jsp" %>
