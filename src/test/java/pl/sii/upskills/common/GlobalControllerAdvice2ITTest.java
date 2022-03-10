package pl.sii.upskills.common;


import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.sii.upskills.conference.controller.ConferenceController;
import pl.sii.upskills.conference.service.command.ConferenceBadRequestException;
import pl.sii.upskills.conference.service.query.ConferenceQueryService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConferenceController.class)
class GlobalControllerAdvice2ITTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    //@InjectMocks
    ConferenceQueryService conferenceQueryService;

//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(conferenceQueryService).build();

//    }

    @Test
    @DisplayName("shouldReturn4xxWhenBadStatusConferencesRequest")
    void handleConferenceBadRequestException() throws Exception{

        //given
        Mockito.when(conferenceQueryService.findByStatus(any())).thenThrow(new ConferenceBadRequestException("Error"));
        var request = MockMvcRequestBuilders.get("/api/v1/conferences")
                .param("status", "draf");
        // when:

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]", CoreMatchers.equalTo("Error")));
//        assertTrue(response.getStatusCode().is4xxClientError());
//        assertEquals(response.getBody(),
//                "[\"You have provided wrong status!Please use one of the following statuses : "
//                        + Arrays.toString(ConferenceStatus.values()) + "\"]");


    }
}
