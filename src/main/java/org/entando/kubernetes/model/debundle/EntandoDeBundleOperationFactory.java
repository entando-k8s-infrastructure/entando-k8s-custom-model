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

package org.entando.kubernetes.model.debundle;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.internal.CustomResourceOperationsImpl;
import org.entando.kubernetes.model.EntandoCustomResourceResolver;

public final class EntandoDeBundleOperationFactory {

    private static EntandoCustomResourceResolver<EntandoDeBundle, EntandoDeBundleList, DoneableEntandoDeBundle> resolver =
            new EntandoCustomResourceResolver<>(EntandoDeBundle.class, EntandoDeBundleList.class, DoneableEntandoDeBundle.class);

    private EntandoDeBundleOperationFactory() {
    }

    public static CustomResourceOperationsImpl<EntandoDeBundle, EntandoDeBundleList, DoneableEntandoDeBundle> produceAllEntandoDeBundles(
            KubernetesClient client) {
        return resolver.resolveOperation(client);
    }
}
