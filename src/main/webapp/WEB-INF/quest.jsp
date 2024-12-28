<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="parts/header.jsp" %>
<body>
<div class="container">
    <form class="form-horizontal" method="post" enctype="multipart/form-data">
        <fieldset>
            <c:set var="quest" value="${requestScope.quest}"/>
            <c:set var="currentQuest" value="${sessionScope.quest}"/>
            <c:set var="currentUser" value="${sessionScope.user}"/>

            <h1 class="text-center">${currentQuest.name}</h1>
            <c:if test="${currentUser.role != 'ADMIN' && currentUser.id != currentQuest.author.id}">
                <img id="quests-image" src="images/${currentQuest.image}" width="100" alt="${currentQuest.image}">
                <div class="form-group">
                    <label>Описание квеста</label>
                    <p class="text-muted mb-0">${currentQuest.description}</p>
                </div>
            </c:if>

            <c:if test="${currentUser.role == 'ADMIN' || currentUser.id == currentQuest.author.id}">
                <div class="form-group">
                    <label for="quest-name">Название квеста</label>
                    <input id="quest-name" name="quest-name" type="text" value="${currentQuest.name}"
                           class="form-control" required
                           size="50"/>
                </div>
                <div class="form-group">
                    <label for="image">Картинка</label>
                    <img id="previewId" src="images/${currentQuest.getImage()}" width="150"
                         alt="${currentQuest.getImage()}"/>
                    <input id="image" name="image" class="input-file" type="file"/>
                </div>
                <div class="form-group">
                    <label for="quest-description">Описание квеста</label>
                    <input id="quest-description" name="quest-description" type="text"
                           value="${currentQuest.description}" class="form-control" required/>
                </div>


            <h2>Вопросы и ответы:</h2>
            <form method="post" action="updateQuestions">
                <ul>
                    <c:forEach var="question" items="${currentQuest.questions}">
                        <li>
                            <strong>Редактировать вопрос:</strong>
                            <input type="text" name="questions[${question.id}].text" value="${question.text}" class="form-control"/>

                            <ul>
                                <c:forEach var="answer" items="${question.answers}">
                                    <li>
                                        <strong>Редактировать ответ:</strong>
                                        <input type="text" name="questions[${question.id}].answers[${answer.id}].text" value="${answer.text}" class="form-control"/>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>

                <div class="form-group">
                    <label class="col-md-8 control-label" for="winMessage">Текст после выигрыша</label>
                    <div class="col-md-8">
                        <input id="winMessage"
                               name="winMessage"
                               type="text"
                               value="${currentQuest.winMessage}"
                               class="form-control input-md"
                               required=""
                               size="50"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-8 control-label" for="looseMessage">Текст после проигрыша</label>
                    <div class="col-md-8">
                        <input id="looseMessage"
                               name="looseMessage"
                               type="text"
                               value="${currentQuest.looseMessage}"
                               class="form-control input-md"
                               required=""
                               size="50"/>
                    </div>
                </div>
                <div class="col-md-8">
                    <button id="update" name="update" class="btn btn-primary">Update</button>
                </div>

            </c:if>
        </fieldset>
    </form>
</div>
</body>
<%@include file="parts/footer.jsp" %>