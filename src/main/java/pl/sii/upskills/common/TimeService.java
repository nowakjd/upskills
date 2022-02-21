package pl.sii.upskills.common;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 LocalDateTime supplier.
 */
@FunctionalInterface
public interface TimeService extends Supplier<LocalDateTime> {

    @Override
    LocalDateTime get();
}
