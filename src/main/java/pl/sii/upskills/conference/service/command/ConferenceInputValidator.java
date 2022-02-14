package pl.sii.upskills.conference.service.command;

import pl.sii.upskills.conference.service.model.ConferenceInput;

import java.time.LocalDateTime;

class ConferenceInputValidator {

      boolean validate(ConferenceInput conferenceInput) {

         ConferenceValidationException conferenceValidationException = new ConferenceValidationException();

         if (isEmpty(conferenceInput.getName())) {
             conferenceValidationException.addError("Name is required");
         }
         if (isEmpty(conferenceInput.getTitle())) {
             conferenceValidationException.addError("Title is required");
         }
         if(conferenceInput.getNumberOfPlaces()<1) {
             conferenceValidationException.addError("Number of places must be positive");
         }

         if (conferenceInput.getTimeSlot().getStartDate().isAfter(LocalDateTime.now().plusDays(7))) {
              conferenceValidationException.addError("Start date must be 7 days in the future");
         }

         if (conferenceInput.getTimeSlot().getEndDate().isAfter(conferenceInput.getTimeSlot().getStartDate())) {
             conferenceValidationException.addError("The end date cannot be faster than start date");
         }
          if (!conferenceValidationException.getErrors().isEmpty()) {
              throw conferenceValidationException;
          }
          return true;
     }

     private boolean isEmpty(String string) {
         return (string == null || string.trim().isEmpty());
     }
 }

