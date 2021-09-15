package edu.strongsubgroup.agreement.service.impl;

import edu.strongsubgroup.agreement.service.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateTimeUtilsImpl implements DateTimeUtils {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

}
