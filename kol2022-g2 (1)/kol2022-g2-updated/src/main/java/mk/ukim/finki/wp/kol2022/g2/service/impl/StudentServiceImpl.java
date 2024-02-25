package mk.ukim.finki.wp.kol2022.g2.service.impl;

import mk.ukim.finki.wp.kol2022.g2.model.Course;
import mk.ukim.finki.wp.kol2022.g2.model.Student;
import mk.ukim.finki.wp.kol2022.g2.model.StudentType;
import mk.ukim.finki.wp.kol2022.g2.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.wp.kol2022.g2.model.exceptions.InvalidStudentIdException;
import mk.ukim.finki.wp.kol2022.g2.repository.CourseRepository;
import mk.ukim.finki.wp.kol2022.g2.repository.StudentRepository;
import mk.ukim.finki.wp.kol2022.g2.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
    }

    @Override
    public Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate enrollmentDate) {
        List<Course> courses = courseRepository.findAllById(courseId);
        String pass = passwordEncoder.encode(password);
        Student student = new Student(name, email, pass, type, courses, enrollmentDate);
        return studentRepository.save(student);
    }

    @Override
    public Student update(Long id, String name, String email, String password, StudentType type, List<Long> coursesId, LocalDate enrollmentDate) {
        Student student = findById(id);
        List<Course> courses = courseRepository.findAllById(coursesId);
        String pass = passwordEncoder.encode(password);
        student.setName(name);
        student.setEmail(email);
        student.setPassword(pass);
        student.setType(type);
        student.setCourses(courses);
        student.setEnrollmentDate(enrollmentDate);
        return studentRepository.save(student);
    }

    @Override
    public Student delete(Long id) {
        Student student = findById(id);
        studentRepository.delete(student);
        return student;
    }

    @Override
    public List<Student> filter(Long courseId, Integer yearsOfStudying) {
        Course course = courseId != null ? courseRepository.findById(courseId).orElseThrow(InvalidCourseIdException::new) : null;
        if (courseId != null && yearsOfStudying != null) {
            LocalDate localDate = LocalDate.now().minusYears(yearsOfStudying);

            return studentRepository.findAllByCoursesContainingAndEnrollmentDateBefore(course, localDate);
        }

        if (courseId != null) {
            return studentRepository.findAllByCoursesContaining(course);
        }

        if (yearsOfStudying != null) {
            LocalDate localDate = LocalDate.now().minusYears(yearsOfStudying);

            return studentRepository.findAllByEnrollmentDateBefore(localDate);
        }

        return listAll();
    }

}

