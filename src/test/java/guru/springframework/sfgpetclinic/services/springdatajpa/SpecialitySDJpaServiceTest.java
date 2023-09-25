package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {
    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;
    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void testDeleteByObject(){
        //given
        Speciality speciality = new Speciality();
        //when
        service.delete(speciality);
        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
        //verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
        Speciality foundSpeciality = service.findById(1L);
        assertThat(foundSpeciality).isNotNull();
        verify(specialtyRepository).findById(anyLong());
        then(specialtyRepository).should().findById(anyLong());
    }
    @Test
    void deleteByIdBddTest(){
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));
        //when
        Speciality foundSpeciality = service.findById(1L);
        //then
        assertThat(foundSpeciality).isNotNull();
        then(specialtyRepository).should(timeout(100).times(1)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }
    @Test
    void deleteById() {
        //given none
        //when
        service.deleteById(1L);
        service.deleteById(1L);
        //then
        //verify(specialtyRepository, times(2)).deleteById(1L);
        then(specialtyRepository).should(times(2)).deleteById(1L);
    }
    @Test
    void deleteByIdAtLeast(){
        //given none
        //when
        service.deleteById(1L);
        service.deleteById(1L);
        //then
        //verify(specialtyRepository, atLeastOnce()).deleteById(1l);
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
    }
    @Test
    void deleteByIdAtMost(){
        //given none
        //when
        service.deleteById(1L);
        service.deleteById(1L);
        //then
        //verify(specialtyRepository, atMost()).deleteById(1l);
        then(specialtyRepository).should(atMost(2)).deleteById(1L);
    }
    @Test
    void deleteByIdAtNever(){
        //given none
        //when
        service.deleteById(1L);
        service.deleteById(1L);
        //then
        //verify(specialtyRepository, atLeastOnce()).deleteById(1l);
        //verify(specialtyRepository, never()).deleteById(2L);
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
        then(specialtyRepository).should(never()).deleteById(2L);
    }
    @Test
    void testDelete() {
        //given nono
        //when
        service.delete(new Speciality());
        //then
        then(specialtyRepository).should().delete(any());
    }

    @Test
    void testDoThrow(){
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        verify(specialtyRepository).delete(any());
    }
    @Test
    void testFindByIdthrows(){
        given(specialtyRepository.findById(1l)).willThrow(new RuntimeException("boom"));

        assertThrows(RuntimeException.class, () -> service.findById(1l));

        then(specialtyRepository).should().findById(1l);
    }
    @Test
    void testDeleteBBD(){
        //given
        //when
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());
        //then
        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
        then(specialtyRepository).should().delete(any());
    }
    @Test
    void testSaveLambda(){
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality(MATCH_ME);
        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)) )).willReturn(savedSpeciality);

        //when
        Speciality returnedSpeciality = service.save(speciality);

        //then
        assertThat(returnedSpeciality.getId()).isEqualTo(1l);
    }
    @Test
    void testSaveLambdaNoMatch(){
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality("Not a match");
        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)) )).willReturn(savedSpeciality);

        //when
        Speciality returnedSpeciality = service.save(speciality);

        //then
        assertNull(returnedSpeciality);
    }

}