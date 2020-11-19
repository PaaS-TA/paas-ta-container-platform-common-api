package org.paasta.container.platform.common.api.adminToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.paasta.container.platform.common.api.common.CommonService;
import org.paasta.container.platform.common.api.common.Constants;
import org.paasta.container.platform.common.api.exception.CommonErrCode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

/**
 * Admin Token Service Test 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.17
 **/
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.yml")
public class AdminTokenServiceTest {
    private static final String TOKEN_NAME = "cp_admin";

    private static AdminToken adminToken = null;
    private static AdminToken nullAdminToken = null;

    @Mock
    CommonService commonService;

    @Mock
    AdminTokenRepository adminTokenRepository;

    @InjectMocks
    AdminTokenService adminTokenService;

    @Before
    public void setUp() {
        adminToken = new AdminToken();
        adminToken.setTokenName(TOKEN_NAME);
        adminToken.setTokenValue("eyJhbGciOiJSUzI1NiIsImtpZCI6IktNWmgxVXB3ajgwS0NxZjFWaVZJVGVvTXJoWnZ5dG0tMGExdzNGZjBKX00ifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJwYWFzLWYxMGU3ZTg4LTQ4YTUtNGUyYy04Yjk5LTZhYmIzY2ZjN2Y2Zi1jYWFzIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InN1cGVyLWFkbWluLXRva2VuLWtzbXo1Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InN1cGVyLWFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMjMwZWQ1OGQtNzc0MC00MDI4LTk0MTEtYTM1MzVhMWM0NjU4Iiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OnBhYXMtZjEwZTdlODgtNDhhNS00ZTJjLThiOTktNmFiYjNjZmM3ZjZmLWNhYXM6c3VwZXItYWRtaW4ifQ.nxnIJCOH_XVMK71s0gF8bgzSxA7g6_y7hGdboLvSqIAGf9J9AgG1DouP29uShK19fMsl9IdbGODPvtuiBz4QyGLPARZldmlzEyFG3k08UMNay1xX_oK-Fe7atMlYgvoGzyM_5-Zp5dyvnxE2skk524htMGHqW1ZwnHLVxtBg8AuGfMwLW1xahmktsNZDG7pRMasPsj73E85lfavMobBlcs4hwVcZU82gAg0SK1QVe7-Uc2ip_9doNo6_9rGW3FwHdVgUNAeCvPRGV0W1dKJv0IX5e_7fIPIznj2xXcZoHf3BnKfDayDIKJOCdsEsy_2NGi1tiD3UvzDDzZpz02T2sg");

        nullAdminToken = new AdminToken();
        nullAdminToken.setTokenName(TOKEN_NAME);
        nullAdminToken.setTokenValue(null);
    }

    @Test
    public void getTokenValue_Valid_Token() {
        when(adminTokenRepository.findByTokenName(TOKEN_NAME)).thenReturn(adminToken);

        AdminToken gFinalAdminToken = adminToken;
        gFinalAdminToken.setResultCode(Constants.RESULT_STATUS_SUCCESS);

        when(commonService.setResultModel(adminToken, Constants.RESULT_STATUS_SUCCESS)).thenReturn(gFinalAdminToken);
        adminTokenService.getTokenValue(TOKEN_NAME);
    }

    @Test
    public void getTokenValue_InValid_Token() {
        when(adminTokenRepository.findByTokenName(TOKEN_NAME)).thenReturn(nullAdminToken);

        AdminToken nullAdminToken = new AdminToken();
        nullAdminToken.setTokenName(TOKEN_NAME);
        nullAdminToken.setResultMessage(CommonErrCode.NOT_FOUND.getMsg(TOKEN_NAME));
        nullAdminToken.setStatusCode(CommonErrCode.NOT_FOUND.getErrCode());

        AdminToken gFinalAdminToken = nullAdminToken;
        gFinalAdminToken.setResultCode(Constants.RESULT_STATUS_FAIL);

        when(commonService.setResultModel(nullAdminToken, Constants.RESULT_STATUS_FAIL)).thenReturn(gFinalAdminToken);
        adminTokenService.getTokenValue(TOKEN_NAME);
    }

    @Test
    public void createAdminToken_Valid() {
        when(commonService.procValidator(adminToken)).thenReturn(Constants.RESULT_STATUS_SUCCESS);
        when(adminTokenRepository.save(adminToken)).thenReturn(adminToken);

        adminTokenService.createAdminToken(adminToken);
    }

    @Test
    public void createAdminToken_InValid() {
        when(commonService.procValidator(adminToken)).thenReturn(Constants.RESULT_STATUS_FAIL);

        adminToken.setResultCode(Constants.RESULT_STATUS_FAIL);
        when(commonService.setResultModel(AdminToken.class, Constants.RESULT_STATUS_FAIL)).thenReturn(adminToken);

        adminTokenService.createAdminToken(adminToken);
    }
}
