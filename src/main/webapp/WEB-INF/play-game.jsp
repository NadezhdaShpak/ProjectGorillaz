<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="parts/header.jsp"/>

<body>
<div class="container">
    <c:set var="game" value="${requestScope.game}"/>
    <c:set var="quest" value="${requestScope.game.quest}"/>
    <c:set var="question" value="${sessionScope.question}"/>
    <form class="form-horizontal" method="post" enctype="multipart/form-data" action="play-game?id=${game.id}">
        <fieldset>


            <input type="hidden" name="questId" value="${requestScope.game.quest.id}"/>


            <h1>${quest.name}</h1>
            <div class="row gy-4 gy-md-0">
                <div class="col-md-6">
                    <div class="p-xl-6 m-xl-9">
                        <br>
                        <img id="image" src="images/${quest.image}" width="150" alt="${quest.image}">
                        <br>
                    </div>
                </div>
                <br>


                <c:choose>
                    <c:when test="${game.gameState == 'LOSE'}">
                        <br>
                        <div class="alert alert-danger" style="margin: 10px; border-radius: 5px;">${quest.looseMessage}</div>
                        <br>
                        <button type="button" style="width:200px" class="btn btn-warning" onclick="newGame()">Сыграем еще?</button>
                        <script>
                            function newGame() {
                                <%
                                    request.getSession().removeAttribute("game");
                                %>
                                window.location='play-game?questId=${quest.id}';
                            }
                        </script>
                    </c:when>

                    <c:when test="${game.gameState == 'WIN'}">
                        <br>
                        <div class="alert alert-success" style="margin: 10px; border-radius: 5px;">${quest.winMessage}</div>
                        <br>
                        <button type="button" class="btn btn-warning" onclick="window.location='home'">Сыграем во что-нибудь еще?
                        </button>
                    </c:when>

                    <c:otherwise>
                        <div class="col-sm-6">
                            <h2 class="fw-bold">${question.text}</h2>

                            <p class="my-3">Выберите вариант ответа</p>

                            <ul>
                                <c:forEach var="answer" items="${question.answers}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="answer" value="${answer.id}"
                                               id="answer${answer.id}">
                                        <label class="form-check-label" for="answer${answer.id}">
                                                ${answer.text}
                                        </label>
                                    </div>
                                </c:forEach>
                            </ul>

                            <input type="hidden" name="questId" value="${game.questId}">


                            <c:choose>
                                <c:when test="${not empty question.answers}">
                                    <button type="submit" name="game" class="btn btn-primary btn-lg me-2">Отправить
                                    </button>
                                </c:when>
                            </c:choose>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </fieldset>
    </form>
</div>

<c:import url="parts/footer.jsp"/>
