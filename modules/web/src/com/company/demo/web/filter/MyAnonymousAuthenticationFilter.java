/*
 * Copyright (c) 2008-2016 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.company.demo.web.filter;

import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.security.app.TrustedClientService;
import com.haulmont.cuba.security.global.LoginException;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.restapi.auth.CubaAnonymousAuthenticationToken;
import com.haulmont.restapi.config.RestApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;
import javax.servlet.*;
import java.io.IOException;

/**
 * This filter is used for anonymous access to custom REST API. Anonymous user session is set to the {@link
 * SecurityContext} and the request is authenticated.
 */
public class MyAnonymousAuthenticationFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(MyAnonymousAuthenticationFilter.class);

    @Inject
    protected RestApiConfig restApiConfig;

    @Inject
    protected TrustedClientService trustedClientService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        populateSecurityContextWithAnonymousSession();
        chain.doFilter(request, response);
    }

    protected void populateSecurityContextWithAnonymousSession() {
        UserSession anonymousSession;
        try {
            anonymousSession = trustedClientService.getAnonymousSession(restApiConfig.getTrustedClientPassword());
        } catch (LoginException e) {
            throw new RuntimeException("Unable to obtain anonymous session for REST", e);
        }

        CubaAnonymousAuthenticationToken anonymousAuthenticationToken =
                new CubaAnonymousAuthenticationToken("anonymous", AuthorityUtils.createAuthorityList("ROLE_CUBA_ANONYMOUS"));
        SecurityContextHolder.getContext().setAuthentication(anonymousAuthenticationToken);
        AppContext.setSecurityContext(new SecurityContext(anonymousSession));
    }

    @Override
    public void destroy() {
    }
}