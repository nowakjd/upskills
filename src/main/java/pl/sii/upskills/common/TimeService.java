package pl.sii.upskills.common;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@FunctionalInterface
public interface TimeService extends Supplier<LocalDateTime> {

    @Override
    LocalDateTime get();
}
