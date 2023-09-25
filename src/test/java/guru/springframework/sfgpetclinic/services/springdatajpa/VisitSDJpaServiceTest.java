package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @DisplayName("Test find All")
    @Test
    void findAll() {
        //given
        Visit visit = new Visit();
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);
        visits.add(new Visit(1l));

        given(visitRepository.findAll()).willReturn(visits);
        //when(visitRepository.findAll()).thenReturn(visits);

        //when
        Set<Visit> foundVisits = service.findAll();

        //then
        then(visitRepository).should().findAll();
        //verify(visitRepository).findAll();
        then(visitRepository).shouldHaveNoMoreInteractions();
        assertThat(foundVisits).hasSize(2);

    }

    @Test
    void findById() {
        //given
        Long id = 1l;
        Visit visit = new Visit();
        visit.setId(id);

        given(visitRepository.findById(id)).willReturn(Optional.of(visit));
        //when(visitRepository.findById(id)).thenReturn(Optional.of(visit));

        //when
        Visit foundVisit = service.findById(id);

        //then
        then(visitRepository).should().findById(anyLong());
        //verify(visitRepository).findById(anyLong());
        assertThat(foundVisit).isNotNull();
        assert foundVisit.getId() == id;
    }

    @Test
    void save() {
        //given
        Long id = 1l;
        Visit visit = new Visit(id);
        given(visitRepository.save(visit)).willReturn(visit);
        //given(visitRepository.save(any(Visit.class))).willReturn(visit);
        //when(visitRepository.save(visit)).thenReturn(visit);

        //when
        Visit savedVisit = service.save(visit);

        //then
        then(visitRepository).should().save(any(Visit.class));
        //verify(visitRepository).save(any(Visit.class));

        assertThat(savedVisit).isNotNull();
        assert savedVisit.getId() == id;
    }

    @Test
    void delete() {
        //given
        Long id= 1l;
        Visit visit = new Visit(id);

        //when
        service.delete(visit);

        //then
        then(visitRepository).should().delete(any(Visit.class));
        //verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        //given
        Long id = 1l;

        //when
        service.deleteById(id);
        //then
        then(visitRepository).should().deleteById(anyLong());
        //verify(visitRepository).deleteById(anyLong());
    }
}