package vyatsu.fib.ovchinnikov.EntrantsApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.EntrantExamMapProvider;
import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.EntrantProvider;
import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.ExamProvider;
import vyatsu.fib.ovchinnikov.EntrantsApp.models.Exam;

import java.lang.reflect.Array;
import java.util.Arrays;

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

    @GetMapping("/addExam")
    public String addingExam(Model model) {
        model.addAttribute("title", "Добавление экзамена.");

        var exam = new Exam();

        model.addAttribute("exam", exam);

        return "addExam";
    }

    @PostMapping("/addExam")
    public String addingExam(@ModelAttribute Exam exam
            , Model model) {
        try {
            if (examProvider.getBySubject(exam.getSubject()).isPresent()) {
                throw new Exception("Экзамен с таким именем уже существует.");
            }

            var savingSuccess = examProvider.save(exam);

            if (!savingSuccess) {
                throw new Exception("Возникли ошибки при добавлении экзамена в базу данных.");
            }
        }
        catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        return "redirect:/exams";
    }
}
