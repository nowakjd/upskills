package pl.sii.upskills.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.sii.upskills.conference.service.command.ConferencePublishingException;
import pl.sii.upskills.conference.service.model.ConferenceOutput;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;


@Component
public class SQSAdapter implements ConferenceBroker {
    ObjectMapper mapper;
    @Value("${aws.queue.url}")
    String url;

    public SQSAdapter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ConferenceOutput send(ConferenceOutput conferenceOutput) {
        String conferenceAsJSON;
        try {
            conferenceAsJSON = mapper.writeValueAsString(conferenceOutput);
        } catch (JsonProcessingException e) {
            throw new ConferencePublishingException(e.getMessage());
        }

        try {
            SqsClient.builder().build()
                    .sendMessage(SendMessageRequest.builder()
                            .queueUrl(url)
                            .messageBody(conferenceAsJSON)
                            .build());
        } catch (SqsException e) {
            throw new ConferencePublishingException("Error during publishing. Try again later");
        }
        return conferenceOutput;
    }
}
