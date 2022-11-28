package vyatsu.fib.ovchinnikov.EntrantsApp.controllers;

import org.javatuples.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.EntrantExamMapProvider;
import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.EntrantProvider;
import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.ExamProvider;
import vyatsu.fib.ovchinnikov.EntrantsApp.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Контроллер для работы с абитуриетами.
 */
@Controller
public class EntrantController {

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
     * Получение всех абитуриетов.
     * @param model модель.
     * @return название html страницы.
     */
    @GetMapping("/entrants")
    public String entrants(Model model){
        model.addAttribute("title","Список абитуриентов");

        var entrants = entrantProvider.getAll();

        model.addAttribute("entrants", entrants);

        return "entrants";
    }

    /**
     * Получение абитуриента по id.
     * @param id id абитуриента.
     * @param model модель.
     * @return название html страницы.
     */
    @GetMapping("/entrant/{id}")
    public String entrant(@PathVariable UUID id, Model model) {
        model.addAttribute("title","Абитуриент");

        var entrant = entrantProvider.get(id);

        model.addAttribute("entrant"
                , entrant.orElse(null));

        ArrayList<Exam> exams = null;

        try {
            exams = entrantExamMapProvider.getExamsByEntrant(entrant.orElse(null));
        }
        catch (NullPointerException ex) {
            //Логирование ошибки (в данном случае в консоль).
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
        catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }

        model.addAttribute("exams", exams);

        return "entrant";
    }

    /**
     * Добавление нового абитуриента.
     * @param model модель.
     * @return форма для заполнения.
     */
    @GetMapping("/addEntrant")
    public String addingEntrant(Model model) {
        model.addAttribute("title","Добавление абитуриента");

        var entrant = new Entrant();

        var exams = new ArrayList<Exam>();

        exams.add(new Exam());
        exams.add(new Exam());
        exams.add(new Exam());

        var scores = new ArrayList<Score>();

        scores.add(new Score());
        scores.add(new Score());
        scores.add(new Score());

        model.addAttribute("entrant", entrant);

        model.addAttribute("exams", new ExamCreationDto(exams));

        model.addAttribute("scores", new ScoreCreationDto(scores));

        return "addEntrant";
    }

    /**
     * Добавление нового абитуриента.
     * @param entrant абитуриент.
     * @param model модель.
     * @return название html страницы.
     */
    @PostMapping("/addEntrant")
    public String addingEntrant(@ModelAttribute Entrant entrant
            , @ModelAttribute ExamCreationDto exams
            , @ModelAttribute ScoreCreationDto scores
            , Model model) {
        model.addAttribute("entrant", entrant);

        try {
            var savingStatus = entrantProvider.save(entrant);

            if (!savingStatus) {
                throw new Exception("Возникли ошибки при добавлении абитуриента в базу данных.");
            }

            var examFounded = examProvider.getBySubject(exams.getExams().get(0).getSubject().toLowerCase());

            if (examFounded.isEmpty()) {
                examProvider.save(exams.getExams().get(0));
            }
            else {
                exams.getExams().set(0, examFounded.get());
            }

            examFounded = examProvider.getBySubject(exams.getExams().get(1).getSubject().toLowerCase());

            if (examFounded.isEmpty()) {
                examProvider.save(exams.getExams().get(1));
            }
            else {
                exams.getExams().set(1, examFounded.get());
            }

            examFounded = examProvider.getBySubject(exams.getExams().get(2).getSubject().toLowerCase());

            if (examFounded.isEmpty()) {
                examProvider.save(exams.getExams().get(2));
            }
            else {
                exams.getExams().set(2, examFounded.get());
            }

            entrantExamMapProvider.save(
                    new EntrantExamMap(UUID.randomUUID()
                        , entrant
                        , exams.getExams().get(0)
                        , scores.getScores().get(0).getScoreInt()));

            entrantExamMapProvider.save(
                    new EntrantExamMap(UUID.randomUUID()
                            , entrant
                            , exams.getExams().get(1)
                            , scores.getScores().get(1).getScoreInt()));

            entrantExamMapProvider.save(
                    new EntrantExamMap(UUID.randomUUID()
                            , entrant
                            , exams.getExams().get(2)
                            , scores.getScores().get(2).getScoreInt()));

        }
        catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        return "redirect:/entrants";
    }

}
