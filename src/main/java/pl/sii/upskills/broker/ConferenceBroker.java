package pl.sii.upskills.broker;

import pl.sii.upskills.conference.service.model.ConferenceOutput;

/**
 Interface for sending conference output to broker aplication.
 */
public interface ConferenceBroker {
    ConferenceOutput send(ConferenceOutput conferenceOutput);
}