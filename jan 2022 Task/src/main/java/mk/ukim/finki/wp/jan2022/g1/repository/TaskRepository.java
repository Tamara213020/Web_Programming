package mk.ukim.finki.wp.jan2022.g1.repository;

import mk.ukim.finki.wp.jan2022.g1.model.Task;
import mk.ukim.finki.wp.jan2022.g1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

//Потребно е да овозможите пребарување на задачи според assignee и
//        преостанати денови (dueDate < now + filtering days) преку
//        формата со id="filter-form" во темплејтот list.html.
public interface TaskRepository extends JpaRepository<Task, Long>
{
    List<Task> findAllByAssigneesContainingAndDueDateBefore(User user, LocalDate localDate);
    List<Task> findAllByAssigneesContaining(User user);
    List<Task> findAllByDueDateBefore(LocalDate localDate);


}
