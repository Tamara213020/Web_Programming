package mk.ukim.finki.wp.kol2023.g2.repository;

import mk.ukim.finki.wp.kol2023.g2.model.Genre;
import mk.ukim.finki.wp.kol2023.g2.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
//    Потребно е да овозможите пребарување
//    на филмови кои имаат одреден жанр и помал рејтинг
//    од одредена вредност преку формата со id="filter-form" во темплејтот list.html.
//    Резултатите од пребарувањето треба да се прикажат на истата страница, при што ќе се
//    прикажат само филмовите кои имаат рејтинг помал од оној кој е внесен и се од селектираниот жанр.
//    Филтрирањето се извршува само според внесените полиња (ако се празни, се игнорира филтрирањето по тој критериум).

    List<Movie> findAllByGenreAndRatingLessThan(Genre genre, Double rating);
    List<Movie> findAllByGenre(Genre genre);
    List<Movie> findAllByRatingLessThan(Double rating);
}
