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

package org.entando.kubernetes.model.app;

import io.fabric8.kubernetes.client.CustomResource;
import org.entando.kubernetes.model.EntandoCustomResource;
import org.entando.kubernetes.model.EntandoCustomResourceStatus;

public abstract class EntandoBaseCustomResource extends CustomResource implements EntandoCustomResource {

    private EntandoCustomResourceStatus entandoStatus;

    protected EntandoBaseCustomResource() {
        super();
    }

    protected EntandoBaseCustomResource(EntandoCustomResourceStatus entandoStatus) {
        super();
        this.entandoStatus = entandoStatus;
        this.setApiVersion("v1alpha1");
    }

    @Override
    public EntandoCustomResourceStatus getStatus() {
        if (entandoStatus == null) {
            setStatus(new EntandoCustomResourceStatus());
        }
        return this.entandoStatus;
    }

    @Override
    public void setStatus(EntandoCustomResourceStatus status) {
        this.entandoStatus = status;
    }
}
