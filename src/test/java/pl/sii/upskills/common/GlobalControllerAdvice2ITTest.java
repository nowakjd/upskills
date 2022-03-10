package pl.sii.upskills.common;


import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.sii.upskills.conference.controller.ConferenceController;
import pl.sii.upskills.conference.service.command.ConferenceBadRequestException;
import pl.sii.upskills.conference.service.command.ConferenceCommandService;
import pl.sii.upskills.conference.service.query.ConferenceQueryService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConferenceController.class)
class GlobalControllerAdvice2ITTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ConferenceQueryService conferenceQueryService;
    @MockBean
    ConferenceCommandService conferenceCommandService;

    @Test
    @DisplayName("shouldReturn4xxWhenBadStatusConferencesRequest")
    void handleConferenceBadRequestException() throws Exception{

        //given
        Mockito.when(conferenceQueryService.findByStatus(any())).thenThrow(new ConferenceBadRequestException("Error"));
        var request = MockMvcRequestBuilders.get("/api/v1/conferences")
                .param("status", "DRAFT");
        // when:

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]", CoreMatchers.equalTo("Error")));
    }
}
