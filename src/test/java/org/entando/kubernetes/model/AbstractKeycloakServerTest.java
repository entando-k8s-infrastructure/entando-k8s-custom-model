/*
 *
 * Copyright 2015-Present Entando Inc. (http://www.entando.com) All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 */

package org.entando.kubernetes.model;

import static org.entando.kubernetes.model.keycloakserver.KeycloakServerOperationFactory.produceAllKeycloakServers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.fabric8.kubernetes.client.dsl.internal.CustomResourceOperationsImpl;
import org.entando.kubernetes.model.keycloakserver.DoneableKeycloakServer;
import org.entando.kubernetes.model.keycloakserver.KeycloakServer;
import org.entando.kubernetes.model.keycloakserver.KeycloakServerBuilder;
import org.entando.kubernetes.model.keycloakserver.KeycloakServerList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractKeycloakServerTest implements CustomResourceTestUtil {

    protected static final String MY_KEYCLOAK = "my-keycloak";
    protected static final String MY_NAMESPACE = TestConfig.calculateNameSpace("my-namespace");
    private static final String SNAPSHOT = "6.1.0-SNAPSHOT";
    private static final String ENTANDO_SOMEKEYCLOAK = "entando/somekeycloak";
    private static final String MYHOST_COM = "myhost.com";
    private static final String MY_TLS_SECRET = "my-tls-secret";
    private CustomResourceOperationsImpl<KeycloakServer, KeycloakServerList, DoneableKeycloakServer> operations;

    @BeforeEach
    public void deleteKeycloakServer() {
        prepareNamespace(keycloakServers(), MY_NAMESPACE);
    }

    @Test
    public void testCreateKeycloakServer() {
        //Given
        KeycloakServer keycloakServer = new KeycloakServerBuilder()
                .withNewMetadata().withName(MY_KEYCLOAK)
                .withNamespace(MY_NAMESPACE)
                .endMetadata()
                .withNewSpec()
                .withDbms(DbmsImageVendor.MYSQL)
                .withEntandoImageVersion(SNAPSHOT)
                .withImageName(ENTANDO_SOMEKEYCLOAK)
                .withReplicas(5)
                .withDefault(true)
                .withIngressHostName(MYHOST_COM)
                .withTlsSecretName(MY_TLS_SECRET)
                .endSpec()
                .build();
        keycloakServers().inNamespace(MY_NAMESPACE).createNew().withMetadata(keycloakServer.getMetadata())
                .withSpec(keycloakServer.getSpec()).done();
        //When
        KeycloakServer actual = keycloakServers().inNamespace(MY_NAMESPACE).withName(MY_KEYCLOAK).get();
        //Then
        assertThat(actual.getSpec().getDbms().get(), is(DbmsImageVendor.MYSQL));
        assertThat(actual.getSpec().getEntandoImageVersion().get(), is(SNAPSHOT));
        assertThat(actual.getSpec().getImageName().get(), is(ENTANDO_SOMEKEYCLOAK));
        assertThat(actual.getSpec().getIngressHostName().get(), is(MYHOST_COM));
        assertThat(actual.getSpec().getReplicas().get(), is(5));
        assertThat(actual.getSpec().getTlsSecretName().get(), is(MY_TLS_SECRET));
        assertThat(actual.getSpec().isDefault(), is(true));
        assertThat(actual.getMetadata().getName(), is(MY_KEYCLOAK));
    }

    @Test
    public void testEditKeycloakServer() {
        //Given
        KeycloakServer keycloakServer = new KeycloakServerBuilder()
                .withNewMetadata()
                .withName(MY_KEYCLOAK)
                .withNamespace(MY_NAMESPACE)
                .endMetadata()
                .withNewSpec()
                .withDbms(DbmsImageVendor.POSTGRESQL)
                .withEntandoImageVersion("6.2.0-SNAPSHOT")
                .withIngressHostName(MYHOST_COM)
                .withImageName("entando/anotherkeycloak")
                .withReplicas(3)
                .withTlsSecretName("some-othersecret")
                .withDefault(false)
                .endSpec()
                .build();
        //When
        keycloakServers().inNamespace(MY_NAMESPACE).create(keycloakServer);
        KeycloakServer actual = keycloakServers().inNamespace(MY_NAMESPACE).withName(MY_KEYCLOAK).edit()
                .editMetadata().addToLabels("my-label", "my-value")
                .endMetadata()
                .editSpec()
                .withDbms(DbmsImageVendor.MYSQL)
                .withEntandoImageVersion(SNAPSHOT)
                .withImageName(ENTANDO_SOMEKEYCLOAK)
                .withIngressHostName(MYHOST_COM)
                .withReplicas(5)
                .withDefault(true)
                .withTlsSecretName(MY_TLS_SECRET)
                .endSpec()
                .withStatus(new WebServerStatus("some-qualifier"))
                .withStatus(new WebServerStatus("some-other-qualifier"))
                .withStatus(new WebServerStatus("some-qualifier"))
                .withStatus(new DbServerStatus("another-qualifier"))
                .withPhase(EntandoDeploymentPhase.STARTED)
                .done();
        //Then
        assertThat(actual.getSpec().getDbms().get(), is(DbmsImageVendor.MYSQL));
        assertThat(actual.getSpec().getEntandoImageVersion().get(), is(SNAPSHOT));
        assertThat(actual.getSpec().getImageName().get(), is(ENTANDO_SOMEKEYCLOAK));
        assertThat(actual.getSpec().getIngressHostName().get(), is(MYHOST_COM));
        assertThat(actual.getSpec().getReplicas().get(), is(5));
        assertThat(actual.getSpec().getTlsSecretName().get(), is(MY_TLS_SECRET));
        assertThat(actual.getSpec().isDefault(), is(true));
        assertThat(actual.getMetadata().getName(), is(MY_KEYCLOAK));
        assertThat("the status reflects", actual.getStatus().forServerQualifiedBy("some-qualifier").isPresent());
        assertThat("the status reflects", actual.getStatus().forServerQualifiedBy("some-other-qualifier").isPresent());
        assertThat("the status reflects", actual.getStatus().forDbQualifiedBy("another-qualifier").isPresent());
    }

    protected CustomResourceOperationsImpl<KeycloakServer, KeycloakServerList, DoneableKeycloakServer> keycloakServers() {
        if (operations == null) {
            operations = produceAllKeycloakServers(getClient());
        }
        return operations;
    }
}
