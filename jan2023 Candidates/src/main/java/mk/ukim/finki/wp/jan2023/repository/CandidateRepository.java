package mk.ukim.finki.wp.jan2023.repository;

import mk.ukim.finki.wp.jan2023.model.Candidate;
import mk.ukim.finki.wp.jan2023.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByGenderAndDateOfBirthBefore(Gender gender, LocalDate birth);
    List<Candidate> findByDateOfBirthBefore(LocalDate birth);
    List<Candidate> findByGender(Gender gender);
}
//    Потребно е да овозможите пребарување на кандидати кои се
//        постари од одредена вредност на возраст и се од одреден пол
//        преку формата со id="filter-form" во темплејтот list.html.
//        Резултатите од пребарувањето треба да се прикажат на истата страница,
//        при што ќе се прикажат само кандидатите кои се постари од возраста која
//        е внесена и се од селектираниот пол. Филтрирањето се извршува само според
//        внесените полиња (ако се празни, се игнорира филтрирањето по тој
//        критериум).
