package com.stori.util;

import com.stori.util.Exception.OptionalNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

public class AssertUtil {
    public static final Logger logger = LoggerFactory.getLogger(AssertUtil.class);

    public static <T> T getOptional(Optional<T> o) {
        try {
            return o.orElseThrow(OptionalNullException::new);
        } catch (OptionalNullException e) {
            logger.warn("Failed to get non-null object from: {}", o.getClass());
            return null;
        }
    }
}
