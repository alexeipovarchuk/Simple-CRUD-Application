package ru.povarchuk.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.povarchuk.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDao {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Nil"));
        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT,"Andrew"));
        people.add(new Person(++PEOPLE_COUNT, "Mike"));

    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatedPerson){ // принимает id человека и нового человека
        Person personToBeUpdated = show(id); // находим человека по его id и помещаем в форму на странице для update
        personToBeUpdated.setName(updatedPerson.getName()); // меняем значение на новое
    }

    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }

}
