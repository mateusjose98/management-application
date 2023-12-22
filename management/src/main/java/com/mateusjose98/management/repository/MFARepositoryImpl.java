package com.mateusjose98.management.repository;

import com.mateusjose98.management.api.UserDTO;
import com.mateusjose98.management.query.UserQuery;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.mateusjose98.management.query.UserQuery.DELETE_VERIFICATION_CODE_BY_USER_ID;
import static com.mateusjose98.management.query.UserQuery.INSERT_VERIFICATION_CODE_QUERY;
import static com.twilio.rest.api.v2010.account.Message.creator;
import static java.util.Map.of;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.time.DateFormatUtils.format;
import static org.apache.commons.lang3.time.DateUtils.addDays;

@Repository
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MFARepositoryImpl implements MFARepository {

    @Value("${fromNumber}")
    private String FROM_NUMBER;
    @Value("${sidKey}")
    private String SID_KEY ;
    @Value("${tokenKey}")
    private String TOKEN_KEY;
    private final NamedParameterJdbcTemplate jdbc;
    @Override

    public void sendVerificationCodeToUser(UserDTO user) {

        String expirationDate = format(addDays(new Date(), 1), "yyyy-MM-dd hh:mm:ss");
        String verificationCode = randomAlphabetic(8).toUpperCase();
        try {
            jdbc.update(DELETE_VERIFICATION_CODE_BY_USER_ID, of("id", user.getId()));
            jdbc.update(INSERT_VERIFICATION_CODE_QUERY, of("userId", user.getId(), "code", verificationCode, "expirationDate", expirationDate));
            sendSMS(user.getPhone(), "From: Aplicação bolada \nVerification code\n" + verificationCode);
            log.info("Verification Code: {}", verificationCode);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException("An error occurred. Please try again.");
        }

    }


    public void sendSMS(String to, String messageBody) {
        Twilio.init(SID_KEY, TOKEN_KEY);
        Message message = creator(new PhoneNumber("+" + to), new PhoneNumber(FROM_NUMBER), messageBody).create();
        System.out.println(message);
    }


}
