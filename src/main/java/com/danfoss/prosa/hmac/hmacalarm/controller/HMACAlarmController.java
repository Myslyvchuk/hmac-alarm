package com.danfoss.prosa.hmac.hmacalarm.controller;

import com.danfoss.prosa.hmac.hmacalarm.util.HmacUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/alarms-hmac", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class HMACAlarmController {

    private final ObjectMapper mapper;
    private final HmacUtil hmacUtil;

    @Autowired
    public HMACAlarmController(ObjectMapper mapper) {
        this.mapper = mapper;
        this.hmacUtil = new HmacUtil("secretTest");
    }

    @PostMapping(value = "/coles")
    public void getAlarmsForColes(@RequestHeader("Header") String hmac, @RequestBody Object request)
        throws JsonProcessingException {
        final String data = mapper.writeValueAsString(request);
        final String debug = hmacUtil.calculateHmac(data);
        if (!hmacUtil.checkHmac(mapper.writeValueAsString(request), hmac)) {
            throw new IllegalArgumentException("Modified request");
        }
        log.info("HMAC alarm {}", request);
    }


}
