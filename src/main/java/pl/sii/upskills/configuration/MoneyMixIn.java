package pl.sii.upskills.configuration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.sii.upskills.conference.persistence.MoneyVO;

@JsonDeserialize(as = MoneyVO.class)
public class MoneyMixIn {
}
