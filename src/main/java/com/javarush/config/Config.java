package com.javarush.config;

import com.javarush.entity.*;
import com.javarush.service.QuestService;
import com.javarush.service.UserService;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class Config {

    private final UserService userService;
    private final QuestService questService;

    public void fillEmptyRepository() {
        if (userService.get(1L).isEmpty()) {
            User admin = biuldUser("Carl", "admin", Role.ADMIN);
            userService.create(admin);
            User alisa = biuldUser("Alisa", "qwerty", Role.USER);
            userService.create(alisa);
            User bob = biuldUser("Bob", "123", Role.GUEST);
            userService.create(bob);

            addDemoQuests(admin);
        }
    }

    private static User biuldUser(String name, String password, Role role) {
        return User.builder()
                .login(name)
                .password(password)
                .role(role)
                .build();
    }
    private static Quest biuldQuest(String questsName, String description,
                                    User author, ArrayList<Question> questsQuestions,
                                    String winMessage, String looseMessage) {
        return Quest.builder()
                .name(questsName)
                .description(description)
                .author(author)
                .questions(questsQuestions)
                .winMessage(winMessage)
                .looseMessage(looseMessage)
                .build();
    }

    private void addDemoQuests(User author) {
        HPQuiz(author);
        storyTales(author);
        history(author);

    }
    private void HPQuiz(User author) {
        String name = "КВИЗ по вселенной Гарри Поттера";
        String description = "Присоединяйтесь к увлекательному путешествию " +
                "через страницы знаменитой серии книг Дж.К. Роулинг о Гарри Поттере. " +
                "Этот квиз предназначен для настоящих фанов волшебного мира, где магия, " +
                "дружба и приключения становятся частью вашей реальности.";

        String winMessage = "Мои поздравления, Великий Волшебник!<br/>" +
                "Вы продемонстрировали невероятные знания о мире Гарри Поттера " +
                "и заслужили звание настоящего фаната этой магической вселенной. " +
                "Ваше глубокое понимание сюжетных линий, персонажей, заклинаний " +
                "и магических мест сделало вас непобедимыми в этом квизе.";
        String looseMessage = "Не расстраивайся, Молодой Волшебник.<br/>" +
                "Хотя вы не одержали победу в этом квизе, " +
                "вы все же показали свою любовь и интерес к магическому миру Гарри Поттера. " +
                "Каждый вопрос, на который вы ответили, ведет вас все ближе к пониманию этой увлекательной вселенной";

        ArrayList<Question> questions = new ArrayList<>();
        Collection<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("Сириус Блэк", true, 1L));
        answers1.add(new Answer("Том Реддл", false, 2L));
        questions.add(new Question("Как звали крестного папу Гарри Поттера?", answers1, 1L));

        Collection<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("Альбус Дамблдор", true, 1L));
        answers2.add(new Answer("Джеймс Поттер", false, 2L));
        questions.add(new Question("Кто подарил Гарри мантию-невидимку?", answers2, 2L));

        Collection<Answer> answers3 = new ArrayList<>();
        answers3.add(new Answer("Клык", true, 1L));
        answers3.add(new Answer("Клок", false, 2L));
        questions.add(new Question("Как зовут собаку Хагрида?", answers3, 3L));

        Collection<Answer> answers4 = new ArrayList<>();
        answers4.add(new Answer("Беллатриса Лестрейндж", true, 1L));
        answers4.add(new Answer("Люциус Малфой", false, 2L));
        questions.add(new Question("Кто убивает Добби?", answers4, 4L));

        Collection<Answer> answers5 = new ArrayList<>();
        answers5.add(new Answer("В розовый зонтик", true, 1L));
        answers5.add(new Answer("Во внутрений карман", false, 2L));
        questions.add(new Question("Куда Хагрид прятал свою волшебную палочку?", answers5, 5L));

       questService.create(biuldQuest(name, description, author, questions, winMessage, looseMessage));
    }

    private void storyTales(User author) {
        String name = "По страницам волшебных сказок";
        String description = "Добро пожаловать в Мир Русских Народных Сказок!<br/>" +
                "Представляем вам увлекательную викторину, посвященную русским народным сказкам. " +
                "Это ваш шанс проверить свои знания о волшебных мирах, любимых персонажах и захватывающих историях.";

        String winMessage = "Мои поздравления, Молодой Сказочник!<br/>" +
                "Вы продемонстрировали глубокие знания о русских народных сказках " +
                "и их волшебном мире. Ваше понимание языка, персонажей и " +
                "композиционного построения этих сказок действительно впечатляет.";
        String looseMessage = "Не расстраивайся, Молодой Сказочник.<br/>" +
                "Хотя вы не одержали победу в этой викторине, " +
                "вы все же показали свою заинтересованность в русских народных сказках. " +
                "Каждый вопрос, на который вы ответили, ведет вас все ближе к пониманию этого увлекательного мира.";

        ArrayList<Question> questions = new ArrayList<>();
        Collection<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("В тридевятом царстве, в тридесятом государстве", true, 1L));
        answers1.add(new Answer("В сказочном", false, 2L));
        questions.add(new Question("В каком государстве жили герои многих русских народных сказок?", answers1, 1L));

        Collection<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("Василиса Премудрая", true, 1L));
        answers2.add(new Answer("Елена Прекрасная", false, 2L));
        questions.add(new Question("Какое настоящее имя Царевны- лягушки?", answers2, 2L));

        Collection<Answer> answers3 = new ArrayList<>();
        answers3.add(new Answer("Лиса", true, 1L));
        answers3.add(new Answer("Баба-Яга", false, 2L));
        questions.add(new Question("Кого величают по отчеству — Патрикеевна?", answers3, 3L));

        Collection<Answer> answers4 = new ArrayList<>();
        answers4.add(new Answer("Свист", true, 1L));
        answers4.add(new Answer("Булава", false, 2L));
        questions.add(new Question("Назовите грозное оружие Соловья Разбойника", answers4, 4L));

        Collection<Answer> answers5 = new ArrayList<>();
        answers5.add(new Answer("Гуси-лебеди", true, 1L));
        answers5.add(new Answer("Сестрица Алёнушка и братец Иванушка", false, 2L));
        questions.add(new Question("В какой сказке есть молочная река с кисельными берегами?", answers5, 5L));

        questService.create(biuldQuest(name, description, author, questions, winMessage, looseMessage));
    }
    private void history(User author) {
        String name = "История Руси";
        String description = "Представляем вам увлекательную викторину, посвященную истории Руси.<br/>" +
                "Это ваш шанс проверить свои знания о ключевых событиях, личностях и " +
                "культурных достижениях, которые сформировали нашу великую страну.";

        String winMessage = "Поздравления, Мастер Русской Истории!<br/>" +
                "Вы продемонстрировали глубокие знания о ключевых событиях и личностях в истории Руси. " +
                "Ваше понимание различных периодов и культурных достижений действительно впечатляет.";
        String looseMessage = "Не расстраивайся, Молодой Историк.<br/>" +
                "Не опускайте руки Читайте книги, смотрите документальные фильмы " +
                "и продолжайте окунатсься в глубины русской истории. " +
                "В следующий раз вы будете готовы к новым вызовам.";

        ArrayList<Question> questions = new ArrayList<>();
        Collection<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("Вещий Олег", true, 1L));
        answers1.add(new Answer("Игорь Рюрикович", false, 2L));
        questions.add(new Question("Кто был первым князем Киевской Руси?", answers1, 1L));

        Collection<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("Русская Правда", true, 1L));
        answers2.add(new Answer("Правда Ярослава", false, 2L));
        questions.add(new Question("Как называлась первая письменная конституция Древней Руси?", answers2, 2L));

        Collection<Answer> answers3 = new ArrayList<>();
        answers3.add(new Answer("Стояние на Угре", true, 1L));
        answers3.add(new Answer("Ледовое побоище", false, 2L));
        questions.add(new Question("Какое событие ознаменовало начало конца Золотой Орды?", answers3, 3L));

        Collection<Answer> answers4 = new ArrayList<>();
        answers4.add(new Answer("С Золотой Ордой", true, 1L));
        answers4.add(new Answer("С Хазарами", false, 2L));
        questions.add(new Question("С кем сражались в Куликовской битве?", answers4, 4L));

        Collection<Answer> answers5 = new ArrayList<>();
        answers5.add(new Answer("Суворов", true, 1L));
        answers5.add(new Answer("Кутузов", false, 2L));
        questions.add(new Question("Кто взял Измаил?", answers5, 5L));

        questService.create(biuldQuest(name, description, author, questions, winMessage, looseMessage));
    }

}
