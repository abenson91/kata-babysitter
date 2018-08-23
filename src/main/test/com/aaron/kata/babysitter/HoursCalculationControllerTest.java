package com.aaron.kata.babysitter;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HoursCalculationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void givenHomePageUrl_getViewWithStartAndEndTime() throws Exception {
        mvc.perform(get("/").accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Start Time:")))
                .andExpect(content().string(containsString("End Time:")))
                .andDo(print());
    }

    @Test
    public void givenSuccessfulInput_redirectToResults_andDisplayCorrectSalaryEarned() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "5:30PM")
                .param("endTime", "3:30AM"))
                .andExpect(view().name("results"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Salary earned is: $120.00")))
                .andDo(print());

    }

    @Test
    public void givenNullInputMessages_expectValidationMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "")
                .param("endTime", ""))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "startTime",
                        "InputHoursConstraint"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "endTime",
                        "InputHoursConstraint"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenNonTimeUnitInput_expectInvalidInputErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "5")
                .param("endTime", "3"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "startTime",
                        "typeMismatch"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "endTime",
                        "typeMismatch"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenInputInMilitaryTimeInsteadOfAmPm_expectInvalidInputErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "23:00")
                .param("endTime", "00:00"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "startTime",
                        "typeMismatch"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "endTime",
                        "typeMismatch"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenInputHasSpaces_expectInvalidInputErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "5:00 pm")
                .param("endTime", "3:00 am"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "startTime",
                        "typeMismatch"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "endTime",
                        "typeMismatch"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenInputIsMissingAmPm_expectInvalidInputErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "5:00")
                .param("endTime", "3:00"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "startTime",
                        "typeMismatch"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "endTime",
                        "typeMismatch"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenStartTimeIsBefore5Pm_expectValidationErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "4:00PM")
                .param("endTime", "7:00PM"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "startTime",
                        "InputHoursConstraint"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenEndTimeIsBefore5Pm_expectValidationErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "5:00PM")
                .param("endTime", "4:00PM"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "endTime",
                        "InputHoursConstraint"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenStartTimeIsAfter4Am_expectValidationErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "5:00AM")
                .param("endTime", "7:00PM"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "startTime",
                        "InputHoursConstraint"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenEndTimeIsAfter4Am_expectValidationErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "5:00PM")
                .param("endTime", "5:00AM"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "endTime",
                        "InputHoursConstraint"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenEndTimeIsBeforeStartTime_expectValidationErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "7:00PM")
                .param("endTime", "5:00PM"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "endTime",
                        "InputHoursConstraint"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenEndTimeIsEqualToStartTime_expectValidationErrorMessage() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("startTime", "7:00PM")
                .param("endTime", "7:00PM"))
                .andExpect(model().attributeHasFieldErrorCode(
                        "hoursTrackingForm",
                        "endTime",
                        "InputHoursConstraint"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}