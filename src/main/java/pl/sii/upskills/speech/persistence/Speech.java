package pl.sii.upskills.speech.persistence;

import pl.sii.upskills.conference.persistence.Conference;
import pl.sii.upskills.conference.persistence.TimeSlotVO;
import pl.sii.upskills.speaker.persistence.Speaker;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;

@Entity
public class Speech {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    @Size(max = 100, message = "Speech title must not be longer than 100 characters")
    private String title;
    @Embedded
    private TimeSlotVO timeSlotVO;
    @ManyToOne
    @JoinColumn(name = "conference_id")
    Conference conference;
    @ManyToMany
    private Set<Speaker> speakerSet;

    public Speech() {
    }

    public Speech(Long id, String title, TimeSlotVO timeSlotVO, Conference conference, Set<Speaker> speakerSet) {
        this.id = id;
        this.title = title;
        this.timeSlotVO = timeSlotVO;
        this.conference = conference;
        this.speakerSet = speakerSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TimeSlotVO getTimeSlotVO() {
        return timeSlotVO;
    }

    public void setTimeSlotVO(TimeSlotVO timeSlotVO) {
        this.timeSlotVO = timeSlotVO;
    }

    public Set<Speaker> getSpeakerSet() {
        if (speakerSet == null || speakerSet.isEmpty()) {
            return Collections.emptySet();
        } else {
            return speakerSet;
        }
    }

    public void setSpeakerSet(Set<Speaker> speakerSet) {
        this.speakerSet = speakerSet;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }
}
