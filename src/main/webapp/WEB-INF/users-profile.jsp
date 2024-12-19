<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="parts/header.jsp" %>
<body>
<div class="container">
    <form class="form-horizontal" method="post" enctype="multipart/form-data"> <!--форма с бинарной информацией-->
        <fieldset>
            <c:set var="user" value="${requestScope.user}"/>
            <c:set var="currentUser" value="${sessionScope.user}"/>

            <!-- Form Name -->
            <b>${user.login}</b>
            <br>(${user.role})
            <br>
            <img id="image"
                 src="images/${user.image}"
                 width="150"
                 alt="${user.image}">
            <br>
            <br>

            <ul class="nav col-md-3 text-end">
                <c:if test="${currentUser.role == 'ADMIN'}">
                    <script type="text/javascript">
                        function redirect() {
                            window.location.href = 'edit-user?id=${user.id}';
                        }
                    </script>
                    <button onclick="redirect()" type="button" class="btn btn-success">Edit user</button>
                </c:if>
            </ul>
        </fieldset>
    </form>
</div>
</body>
<%@include file="parts/footer.jsp" %>