package ru.povarchuk.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.povarchuk.springcourse.dao.PersonDao;
import ru.povarchuk.springcourse.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;
    @Autowired
    public PeopleController(PersonDao personDao){
        this.personDao = personDao;
    }

    @GetMapping()// пустой потому что достаточно набрать /people
    public String index(Model model){ //получим всех людей из DAO и передадим на отображение в представление
        model.addAttribute("people", personDao.index());
            return "people/index";
    }
    @GetMapping("/{id}") //для того чтобы можно было в "/{id}" положить любое число и оно передалось аргументом в show
    public String show(@PathVariable("id") int id, Model model){ // получим одного человека по id из DAO и передадим на отображение в представление
        model.addAttribute("person", personDao.show(id));
        return "people/show";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") Person person){
        personDao.save(person);
        return "redirect:/people"; // redirect для возвращ-ия на страницу people после добавления нового человека
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) { // метод будет возвращать HTML страницу для редакт-ия человека
        //@PathVariable для извлечения id который передается в адресе запроса и положить в int id
        model.addAttribute("person", personDao.show(id)); //из personDao вернется человек с указанным id
        // в модель внедряем человека чтобы в форме на HTML странице поле не было пустым, а уже имело имя
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) { // с помощью @ModelAttribute мы должны принимать объект person из формы
        // @PathVariable чтобы принять id из адреса
        //находим человека из БД с таким id и поменять его значение на те значения что пришли из формы
        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDao.delete(id);
        return "redirect:/people";
    }

}
