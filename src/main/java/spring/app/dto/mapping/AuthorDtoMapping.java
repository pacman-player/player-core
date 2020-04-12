package spring.app.dto.mapping;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.AuthorDto;
import spring.app.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class AuthorDtoMapping {
    @PersistenceContext
    EntityManager entityManager;

    public List<AuthorDto> getAll() {
        List<AuthorDto> authorDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.AuthorDto(" +
                        "t.id, " +
                        "t.name " +
                        ") FROM "+ Author.class.getName()+" t",
                AuthorDto.class
        )
                .getResultList();

        List<Long> ids = taskDtoWithAnswers.stream().mapToLong(TaskDtoWithAnswersDto::getId).boxed().collect(Collectors.toList());

        List<AnswerDto> answers = entityManager.createQuery(
                "SELECT new com.javahelps.jpa.test.dto_mapping.dto.AnswerDto(a.id, a.answer, a.task.id)" +
                        " FROM Answer a WHERE a.task.id IN (:ids)",
                AnswerDto.class
        )
                .setParameter("ids", ids)
                .getResultList();

        taskDtoWithAnswers.forEach(tdwa -> tdwa.setAnswerDtos(
                answers.stream().filter(a -> a.getTaskId() == tdwa.getId()).collect(Collectors.toList())
        ));
    }
}
