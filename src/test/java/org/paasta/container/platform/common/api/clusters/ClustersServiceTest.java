package org.paasta.container.platform.common.api.clusters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Cluster Service Test 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.17
 **/
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.yml")
public class ClustersServiceTest {
    private static final String CLUSTER = "cp-cluster";
    private static final String CLUSTER_API_URL = "111.111.111.111:6443";
    private static final String CLUSTER_ADMIN_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IktNWmgxVXB3ajgwS0NxZjFWaVZJVGVvTXJoWnZ5dG0tMGExdzNGZjBKX00ifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJwYWFzLWYxMGU3ZTg4LTQ4YTUtNGUyYy04Yjk5LTZhYmIzY2ZjN2Y2Zi1jYWFzIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InN1cGVyLWFkbWluLXRva2VuLWtzbXo1Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InN1cGVyLWFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMjMwZWQ1OGQtNzc0MC00MDI4LTk0MTEtYTM1MzVhMWM0NjU4Iiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OnBhYXMtZjEwZTdlODgtNDhhNS00ZTJjLThiOTktNmFiYjNjZmM3ZjZmLWNhYXM6c3VwZXItYWRtaW4ifQ.nxnIJCOH_XVMK71s0gF8bgzSxA7g6_y7hGdboLvSqIAGf9J9AgG1DouP29uShK19fMsl9IdbGODPvtuiBz4QyGLPARZldmlzEyFG3k08UMNay1xX_oK-Fe7atMlYgvoGzyM_5-Zp5dyvnxE2skk524htMGHqW1ZwnHLVxtBg8AuGfMwLW1xahmktsNZDG7pRMasPsj73E85lfavMobBlcs4hwVcZU82gAg0SK1QVe7-Uc2ip_9doNo6_9rGW3FwHdVgUNAeCvPRGV0W1dKJv0IX5e_7fIPIznj2xXcZoHf3BnKfDayDIKJOCdsEsy_2NGi1tiD3UvzDDzZpz02T2sg";
    private static final String NAMESPACE = "cp-namespace";
    private static final String ALL_NAMESPACES = "all";
    private static final String USER_ID = "paasta";
    private static final String ROLE = "paas-ta-container-platform-init-role";
    private static final String ADMIN_ROLE = "paas-ta-container-platform-admin-role";
    private static final String SECRET_NAME = "paasta-token-jqrx4";
    private static final String TOKEN_NAME = "cp_admin";

    private static Clusters createdCluster = null;
    private static List<Clusters> clustersList = null;

    @Mock
    ClustersRepository clustersRepository;

    @InjectMocks
    ClustersService clustersService;

    @Before
    public void setUp() {
        createdCluster = new Clusters();
        createdCluster.setId(1);
        createdCluster.setClusterName(CLUSTER);
        createdCluster.setClusterApiUrl(CLUSTER_API_URL);
        createdCluster.setClusterToken(CLUSTER_ADMIN_TOKEN);
        createdCluster.setCreated("2020-11-05 13:26:24");
        createdCluster.setLastModified("2020-11-05 13:26:24");

        clustersList = new ArrayList<>();
        clustersList.add(createdCluster);
    }

    @Test
    public void createClusters() {
        Clusters clusters = new Clusters();
        clusters.setClusterName(CLUSTER);
        clusters.setClusterApiUrl(CLUSTER_API_URL);
        clusters.setClusterToken(CLUSTER_ADMIN_TOKEN);

        when(clustersRepository.save(clusters)).thenReturn(createdCluster);

        Clusters finalClusters = clustersService.createClusters(clusters);
        assertNotNull(finalClusters);
    }

    @Test
    public void getClusters() {
        when(clustersRepository.findAllByClusterName(CLUSTER)).thenReturn(clustersList);

        Clusters finalCluster = clustersService.getClusters(CLUSTER);
        assertEquals(finalCluster, createdCluster);
    }
}