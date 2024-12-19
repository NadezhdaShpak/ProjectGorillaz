<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="parts/header.jsp" %>
<body>
<c:set var="user" value="${requestScope.user}" />
<c:set var="currentUser" value="${sessionScope.user}"/>


<div class="container">
    <form class="form-horizontal" method="post" enctype="multipart/form-data"> <!--форма с бинарной информацией-->
        <fieldset>
            <!-- Form Name -->
            <legend>Edit user:</legend>

            <!-- File Button -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="image">
                    <img id="previewId"
                    src="images/${requestScope.user.getImage()}"
                    width="150"
                    alt="${requestScope.user.getImage()}">
                    <input id="image"
                    name="image"
                    class="input-file"
                    type="file"/>
                </label>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="login">Login</label>
                <div class="col-md-4">
                    <input
                            id="login"
                            name="login"
                            type="text"
                            value="${requestScope.user.login}"
                            class="form-control input-md"
                            required="">
                    <span class="help-block">min 3 symbols</span>
                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Password</label>
                <div class="col-md-4">
                    <input id="password"
                           name="password"
                           type="password"
                           value="${requestScope.user.password}"
                           placeholder="your password"
                           class="form-control input-md"
                           required="">
                    <span class="help-block">min 8 symb</span>
                </div>
            </div>


<c:if test="${currentUser.role == 'ADMIN'}">
    <div class="form-group">
                <label class="col-md-4 control-label" for="role">Role</label>
                <div class="col-md-4">
                    <select id="roleAdmin" name="role" class="form-control">
                        <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                        <option value="GUEST" ${user.role == 'GUEST' ? 'selected' : ''}>Guest</option>
                        <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>User</option>
                    </select>
                </div>
            </div>
</c:if>

            <c:if test="${currentUser.role != 'ADMIN'}">
                <div class="form-group">
                    <label class="col-md-4 control-label" for="role">Role</label>
                    <div class="col-md-4">
                        <select id="role" name="role" class="form-control" disabled>
                            <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                            <option value="GUEST" ${user.role == 'GUEST' ? 'selected' : ''}>Guest</option>
                            <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>User</option>
                        </select>
                    </div>
                </div>
            </c:if>

            <!-- Button (Double) -->
            <div class="form-group">
                <div class="col-md-8">
                        <button id="update" name="update" class="btn btn-primary">Update</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>
</body>
<%@include file="parts/footer.jsp" %>
