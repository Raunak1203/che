/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.multiuser.keycloak.server;

import static com.jayway.restassured.RestAssured.given;
import static org.eclipse.che.multiuser.keycloak.server.UnavailableResourceInMultiUserFilter.ERROR_RESPONSE_JSON_MESSAGE;
import static org.everrest.assured.JettyHttpServer.ADMIN_USER_NAME;
import static org.everrest.assured.JettyHttpServer.ADMIN_USER_PASSWORD;
import static org.testng.Assert.assertEquals;

import com.jayway.restassured.response.Response;
import org.everrest.assured.EverrestJetty;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(value = {EverrestJetty.class, MockitoTestNGListener.class})
public class UnavailableReourceInMultiUserFilterTest {
  @SuppressWarnings("unused")
  private static final UnavailableResourceInMultiUserFilter FILTER = new UnavailableResourceInMultiUserFilter();

  @Test
  public void shouldReturnForbiddenResponseForUserCreation() {

    final Response response =
        given()
            .auth()
            .basic(ADMIN_USER_NAME, ADMIN_USER_PASSWORD)
            .when()
            .post("/user/");

    assertEquals(response.getStatusCode(), 403);
    assertEquals(response.getBody().print().trim(), ERROR_RESPONSE_JSON_MESSAGE );
  }

  @Test
  public void shouldReturnForbiddenResponseForUserDeletion() {

    final Response response =
        given()
            .auth()
            .basic(ADMIN_USER_NAME, ADMIN_USER_PASSWORD)
            .when()
            .delete("/user/123");

    assertEquals(response.getStatusCode(), 403);
    assertEquals(response.getBody().print().trim(), ERROR_RESPONSE_JSON_MESSAGE );
  }

  @Test
  public void shouldReturnForbiddenResponseForUserPasswordUpdate() {

    final Response response =
        given()
            .auth()
            .basic(ADMIN_USER_NAME, ADMIN_USER_PASSWORD)
            .when()
            .post("/user/password");

    assertEquals(response.getStatusCode(), 403);
    assertEquals(response.getBody().print().trim(), ERROR_RESPONSE_JSON_MESSAGE );
  }

  @Test
  public void shouldReturnForbiddenResponseForCurrentUserProfileUpdate() {

    final Response response =
        given()
            .auth()
            .basic(ADMIN_USER_NAME, ADMIN_USER_PASSWORD)
            .when()
            .post("/profile/attributes");

    assertEquals(response.getStatusCode(), 403);
    assertEquals(response.getBody().print().trim(), ERROR_RESPONSE_JSON_MESSAGE );
  }

  @Test
  public void shouldReturnForbiddenResponseFortUserProfileUpdate() {

    final Response response =
        given()
            .auth()
            .basic(ADMIN_USER_NAME, ADMIN_USER_PASSWORD)
            .when()
            .post("/profile/profile123/attributes");

    assertEquals(response.getStatusCode(), 403);
    assertEquals(response.getBody().print().trim(), ERROR_RESPONSE_JSON_MESSAGE );
  }

  @Test
  public void shouldReturnForbiddenResponseForCurrentUserProfileDelete() {

    final Response response =
        given()
            .auth()
            .basic(ADMIN_USER_NAME, ADMIN_USER_PASSWORD)
            .when()
            .delete("/profile/attributes");

    assertEquals(response.getStatusCode(), 403);
    assertEquals(response.getBody().print().trim(), ERROR_RESPONSE_JSON_MESSAGE );
  }
}
