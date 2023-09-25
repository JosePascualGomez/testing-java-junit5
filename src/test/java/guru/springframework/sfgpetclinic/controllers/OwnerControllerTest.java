package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    public static final String REDIRECT_OWNERS_OKEY = "redirect:/owners/1";
    public static final String REDIRECT_OWNERS_ERROR = "owners/createOrUpdateOwnerForm";
    @Mock
    OwnerService ownerService;
    @Mock
    Model model;
    @InjectMocks
    OwnerController controller;
    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocationOnMock ->{
                    List<Owner> owners = new ArrayList<>();
                    String name = invocationOnMock.getArgument(0);
                    if(name.equals("%Gomez%")){
                        return owners;
                    }else if(name.equals("%Delgado%")){
                        owners.add(new Owner(1L, "Diana", "Delgado"));
                        return owners;
                    }else if(name.equals("%Perez%")){
                        owners.add(new Owner(1L, "Juan", "Perez"));
                        owners.add(new Owner(2L, "Jose", "Gomez"));
                        return owners;
                    }else{
                        return owners;
                    }
                });
    }

    @Test
    void processFindFormResultOne(){
        //given
        Owner owner = new Owner(1L, "Diana", "Delgado");
        //given(bindingResult.hasErrors()).willReturn(false);
        //List<Owner> owners = new ArrayList<>();
        //owners.add(owner);
        //given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(owners);

        //when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        //then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Delgado%");
        assertEquals("redirect:/owners/1", viewName);
        verifyNoMoreInteractions (model);
    }

    @Test
    void processFindFormResultMultiple(){
        //given
        //Owner owner = new Owner(1L, "Jose", "Gomez");
        Owner owner2 = new Owner(2L, "Juan", "Perez");
        //given(bindingResult.hasErrors()).willReturn(false);
        //List<Owner> owners = new ArrayList<>();
        //owners.add(owner2);
        //owners.add(owner);
        //(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(owners);
        //when
        String viewName = controller.processFindForm(owner2, bindingResult, model);
        InOrder inOrder = Mockito.inOrder(ownerService, model);

        //then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Perez%");
        assertEquals("owners/ownersList", viewName);
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1) ).addAttribute(anyString(), anyList());
    }

    @Test
    void processFindFormWildcardStringAnnotation(){
        //given
        Owner owner = new Owner(1L, "Jose", "Gomez");
        //given(bindingResult.hasErrors()).willReturn(false);
        //List<Owner> owners = new ArrayList<>();
       // given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(owners);

        //when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        //then
        verifyNoMoreInteractions(ownerService);
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Gomez%");
        assertEquals("owners/findOwners", viewName);
        verifyNoMoreInteractions (model);
    }

    @Test
    void processFindFormWildcardString(){
        //given
        Owner owner = new Owner(1L, "Jose", "Gomez");
        //List<Owner> owners = new ArrayList<>();
        //final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        //given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(owners);

        //when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        //then
        assertThat( stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Gomez%");
        assertEquals("owners/findOwners", viewName);
        verifyNoMoreInteractions (model);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void processCreationFormNoErrors() {
        //given
        Owner owner = new Owner(1L, "Jose", "Gomez");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(owner)).willReturn(owner);
        //when
        String viewName = controller.processCreationForm(owner, bindingResult);
        //then
        assertEquals(viewName, REDIRECT_OWNERS_OKEY);
    }

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void processCreationFormHasErrors() {
        //given
        Owner owner = new Owner(1L, "Jose", "Gomez");
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        String viewName = controller.processCreationForm(owner, bindingResult);
        //then
        assertEquals(viewName, REDIRECT_OWNERS_ERROR);
    }

}