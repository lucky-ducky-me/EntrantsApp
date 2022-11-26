package vyatsu.fib.ovchinnikov.EntrantsApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vyatsu.fib.ovchinnikov.EntrantsApp.models.Entrant;
import vyatsu.fib.ovchinnikov.EntrantsApp.models.Exam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Контроллер для работы с абитуриетами.
 */
@Controller
public class EntrantController {

    /**
     * Получение всех абитуриетов.
     * @param model модель.
     * @return название html страницы.
     */
    @GetMapping("/entrants")
    public String entrants(Model model){
        model.addAttribute("title","Список абитуриентов");

        //todo: получение списка абитуриентов
        var entrants = new ArrayList<Entrant>();

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

        //todo: получение абитуриента по id.
        var entrant = new Entrant(UUID.randomUUID(), "test", "test", "test", LocalDate.now());

        model.addAttribute("entrant", entrant);

        var exams = new ArrayList<Exam>();

        model.addAttribute("exams", exams);

        return "entrant";
    }
}
