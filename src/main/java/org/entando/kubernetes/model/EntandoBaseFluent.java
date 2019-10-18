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

import io.fabric8.kubernetes.api.builder.BaseFluent;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;

public abstract class EntandoBaseFluent<F extends EntandoBaseFluent<F>> extends BaseFluent<F> {

    protected ObjectMetaBuilder metadata;

    public final MetadataNestedImpl<F> editMetadata() {
        return new MetadataNestedImpl<>(thisAsF(), this.metadata.build());
    }

    public final MetadataNestedImpl<F> withNewMetadata() {
        return new MetadataNestedImpl<>(thisAsF());
    }

    public final F withMetadata(ObjectMeta metadata) {
        this.metadata = new ObjectMetaBuilder(metadata);
        return thisAsF();
    }

    private F thisAsF() {
        return (F) this;
    }

}
