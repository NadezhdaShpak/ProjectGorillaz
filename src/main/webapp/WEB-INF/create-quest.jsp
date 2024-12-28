<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="parts/header.jsp"/>
<body>
<script>
    let id = 2;
</script>
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
        <h2>Введите вопрос и ответы</h2>
        <div id="questions">
            <c:if test="${empty sessionScope.questsQuestions}">
                Вопрос №1: <input type="text" name="question1" /> <br/>
                Правильный ответ: <input type="text" name="answerWin1" /> <br/>
                Неправильный ответ: <input type="text" name="answerLoose1" /> <br/><br/>
            </c:if>
        </div>
        <label for="questionsCount">Сколько вопросов будет в квесте?</label>
        <select id="questionsCount" onchange="addQuestion()">
            <option value=""></option>
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="30">30</option>
        </select>

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





        <script>
            function addQuestion() {
                let questionsCount = $("#questionsCount").val();
                if (questionsCount == null) {
                    questionsCount = 5;
                }
                while (id <= questionsCount) {
                    let questionDiv = document.createElement('div');
                    questionDiv.innerHTML = 'Вопрос №' + id + ': <input type="text" name="question' + id + '" /> <br/>' +
                        'Правильный ответ: <input type="text" name="answerWin' + id + '" /> <br/>' +
                        'Неправильный ответ: <input type="text" name="answerLoose' + id + '" /> <br/>';
                    document.getElementById('questions').appendChild(questionDiv);
                    id++;
                }
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

