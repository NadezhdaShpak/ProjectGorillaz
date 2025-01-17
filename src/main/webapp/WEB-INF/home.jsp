<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<body>
<div class="container">
    <h1 class="text-center">Выберите квест</h1>
    <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
        <c:forEach var="quest" items="${requestScope.quests}">
            <div class="text-center">
                <h3 class="fs-2">${quest.name}</h3>
                <img id="image"
                     src="images/${quest.image}"
                     class="img-fluid w-100"
                     alt="${quest.image}">
                <br>
                <p class="text-muted mb-0">${quest.description}</p>
                <a href="play-game?questId=${quest.id}" class="icon-link d-inline-flex align-items-center">
                    Играть
                    <svg class="bi" width="1em" height="1em">
                        <use xlink:href="#chevron-right"></use>
                    </svg>
                </a>
                <c:if test='${sessionScope.user.role=="ADMIN"}'>
                    <a href="edit-quest?id=${quest.id}" class="icon-link d-inline-flex align-items-center">
                        Редактировать
                        <svg class="bi" width="1em" height="1em">
                            <use xlink:href="#chevron-right"></use>
                        </svg>
                    </a>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>
</body>

<%@include file="parts/footer.jsp" %>