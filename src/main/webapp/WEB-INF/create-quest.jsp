<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="parts/header.jsp"/>

<body>
<div class="container">
    <form class="form-horizontal" method="post" enctype="multipart/form-data">

        <div class="mb-3">
            <label for="quest-name" class="form-label">Название квеста</label>
            <input name="name" type="text" class="form-control" id="quest-name" placeholder="Укажите имя квеста">
        </div>


        <!-- Image -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="image">
                <img id="previewId"
                     src="images/${requestScope.quest.getImage()}"
                     width="150"
                     alt="${requestScope.quest.getImage()}">
                <input id="image"
                       name="image"
                       class="input-file"
                       type="file"/>
            </label>
        </div>


        <div class="mb-3">
            <label for="quest-description" class="form-label">Описание квеста</label>
            <input name="description" type="text" class="form-control" id="quest-description"
                   placeholder="Укажите описание квеста">
        </div>
<%--        <h2>Сколько вопросов в квесте?</h2>--%>

        <div class="mb-3">
            <label for="winMessage" class="form-label">Текст после выигрыша</label>
            <input name="winMessage" type="text" class="form-control" id="winMessage"
                   placeholder="Укажите текст после выигрыша">
        </div>

        <div class="mb-3">
            <label for="looseMessage" class="form-label">Текст после проигрыша</label>
            <input name="looseMessage" type="text" class="form-control" id="looseMessage"
                   placeholder="Укажите текст проигрыша">
        </div>
        <h2>Введите вопрос и ответы</h2>
        <div id="questions">
            <c:if test="${not empty sessionScope.questsQuestions}">
                <c:forEach var="question"
                           items="${sessionScope.questsQuestions}" varStatus="loop">
                    Вопрос: <input type="text" name="question${loop.index}" value="${question}"/><br/>
                    Правильный ответ: <input type="text" name="answerWin${loop.index}"/><br/>
                    Неправильный ответ: <input type="text" name="answerLoose${loop.index}"/><br/><br/>
                </c:forEach>
            </c:if>
            <c:if test="${empty sessionScope.questsQuestions}">
                Вопрос: <input type="text" name="question0" /> <br/>
                Правильный ответ: <input type="text" name="answerWin0" /> <br/>
                Неправильный ответ: <input type="text" name="answerLoose0" /> <br/><br/>
            </c:if>
        </div>

        <button class="btn btn-primary d-block w-20" onclick="addQuestion()">Добавить еще один вопрос</button>

        <%
            int id = 0;
            if (session.getAttribute("questsQuestions") != null) {
                id = ((List<String>) session.getAttribute("questsQuestions")).size();
            }
        %>

        <script>
            let id = <%= id %>;
            function addQuestion() {
                var questionDiv = document.createElement('div');
                questionDiv.innerHTML = 'Вопрос №' + (id + 1) + ': <input type="text" name="question' + id + '" /> <br/>' +
                    'Правильный ответ: <input type="text" name="answerWin' + id + '" /> <br/>' +
                    'Неправильный ответ: <input type="text" name="answerLoose' + id + '" /> <br/>';
                document.getElementById('questions').appendChild(questionDiv);
                id++;
            }
        </script>



        <div class=" form-group">
            <label class="col-md-4 control-label" for="submit"></label>
            <div class="col-md-4">
                <button id="submit" name="create"
                        class="btn btn-success">Создать квест
                </button>

            </div>
        </div>
    </form>
</div>
</body>
<c:import url="parts/footer.jsp"/>

