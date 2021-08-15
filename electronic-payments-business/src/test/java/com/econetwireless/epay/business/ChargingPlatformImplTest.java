package com.econetwireless.epay.business;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.integrations.impl.ChargingPlatformImpl;
import com.econetwireless.in.soap.messages.CreditRequest;
import com.econetwireless.in.soap.service.IntelligentNetworkService;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.pojo.INBalanceResponse;

public class ChargingPlatformImplTest {

    private ChargingPlatform platform;

    @Mock
    private IntelligentNetworkService intelligentNetworkService;
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(intelligentNetworkService.enquireBalance(anyString(), anyString())).then(TestBase.BALANCE_RESPONSE_ANSWER);
        when(intelligentNetworkService.creditSubscriberAccount(any(CreditRequest.class))).then(TestBase.CREDIT_RESPONSE_ANSWER);
        platform = new ChargingPlatformImpl(intelligentNetworkService);
    }
    @Test
    public void testMissingPartnerCode() throws NullPointerException {
        INBalanceResponse expected = new INBalanceResponse();
        expected.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
        expected.setNarrative("Invalid request, missing partner code");
		INBalanceResponse actual = platform.enquireBalance(null,null);
        TestBase.shouldBeSame(expected, actual);
    }

    @Test
    public void testMissingMobileNumber()  {
        INBalanceResponse expected = new INBalanceResponse();
        expected.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
        expected.setNarrative("Invalid request, missing mobile number");
        INBalanceResponse actual = platform.enquireBalance("hello", null);
        TestBase.shouldBeSame(expected, actual);
    }
}