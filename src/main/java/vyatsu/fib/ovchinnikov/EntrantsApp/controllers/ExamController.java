package vyatsu.fib.ovchinnikov.EntrantsApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.EntrantExamMapProvider;
import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.EntrantProvider;
import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.ExamProvider;

/**
 * Контроллер для экзаменов.
 */
@Controller
public class ExamController {

    /**
     * Провайдер для связей абитурентов и экзаменов.
     */
    private final EntrantExamMapProvider entrantExamMapProvider = new EntrantExamMapProvider();

    /**
     * Провайдер для абитуриентов.
     */
    private final EntrantProvider entrantProvider = new EntrantProvider();

    /**
     * Провайдер для экзаменов.
     */
    private final ExamProvider examProvider = new ExamProvider();

    /**
     * Получение всех экзаменов.
     * @param model модель.
     * @return html страница.
     */
    @GetMapping("/exams")
    public String getAllExams(Model model) {
        var exams = examProvider.getAll();

        model.addAttribute("title", "Все экзамены");

        model.addAttribute("exams", exams);

        return "exams";
    }


}
