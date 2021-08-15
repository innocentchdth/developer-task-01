package com.econetwireless.epay.business;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.business.services.impl.CreditsServiceImpl;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.pojo.INCreditRequest;

public class CreditsServiceImplTest {

    @Mock
    private SubscriberRequestDao requestDao;
    @Mock
    private ChargingPlatform platform;

    private CreditsService creditsService;
    private String partnerCode;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(requestDao.save(any(SubscriberRequest.class))).then(TestBase.SUBSCRIBER_REQUEST);
        when(platform.enquireBalance(anyString(), anyString())).then(TestBase.SUCCESSFUL_BALANCE_ENQUIRY);
        when(platform.creditSubscriberAccount(any(INCreditRequest.class))).then(TestBase.SUCCESSFUL_CREDIT_REQUEST);
        partnerCode = "hot-recharge";
        this.creditsService = new CreditsServiceImpl(platform, requestDao);
    }

    @Test
    public void testCreditMethod() throws Exception {
        final AirtimeTopupRequest airtimeTopupRequest = new AirtimeTopupRequest();
        airtimeTopupRequest.setPartnerCode(partnerCode);
        airtimeTopupRequest.setReferenceNumber("TestReference");
        airtimeTopupRequest.setAmount(3);
        airtimeTopupRequest.setMsisdn("774440550");
        creditsService.credit(airtimeTopupRequest);

    }
}