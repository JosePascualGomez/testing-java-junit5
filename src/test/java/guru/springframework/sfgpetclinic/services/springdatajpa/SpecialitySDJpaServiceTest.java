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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {
    @Mock
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
        then(specialtyRepository).should(times(1)).findById(anyLong());
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
}