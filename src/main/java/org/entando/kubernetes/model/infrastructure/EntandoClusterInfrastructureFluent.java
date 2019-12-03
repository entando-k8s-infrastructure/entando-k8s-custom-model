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

package org.entando.kubernetes.model.infrastructure;

import io.fabric8.kubernetes.api.builder.Fluent;
import io.fabric8.kubernetes.api.builder.Nested;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import org.entando.kubernetes.model.EntandoBaseFluent;

public class EntandoClusterInfrastructureFluent<A extends EntandoClusterInfrastructureFluent<A>> extends EntandoBaseFluent<A> implements
        Fluent<A> {

    protected EntandoClusterInfrastructureSpecBuilder spec;

    protected EntandoClusterInfrastructureFluent() {
        this(new ObjectMetaBuilder(), new EntandoClusterInfrastructureSpecBuilder());
    }

    protected EntandoClusterInfrastructureFluent(EntandoClusterInfrastructureSpec spec, ObjectMeta objectMeta) {
        this(new ObjectMetaBuilder(objectMeta), new EntandoClusterInfrastructureSpecBuilder(spec));
    }

    private EntandoClusterInfrastructureFluent(ObjectMetaBuilder metadata, EntandoClusterInfrastructureSpecBuilder spec) {
        super(metadata);
        this.spec = spec;
    }

    @SuppressWarnings("unchecked")
    public SpecNestedImplCluster<A> editSpec() {
        return new SpecNestedImplCluster<>((A) this, this.spec.build());
    }

    @SuppressWarnings("unchecked")
    public SpecNestedImplCluster<A> withNewSpec() {
        return new SpecNestedImplCluster<>((A) this);
    }

    @SuppressWarnings("unchecked")
    public A withSpec(EntandoClusterInfrastructureSpec spec) {
        this.spec = new EntandoClusterInfrastructureSpecBuilder(spec);
        return (A) this;
    }

    public static class SpecNestedImplCluster<N extends EntandoClusterInfrastructureFluent> extends
            EntandoClusterInfrastructureSpecFluent<SpecNestedImplCluster<N>> implements
            Nested<N> {

        private final N parentBuilder;

        SpecNestedImplCluster(N parentBuilder, EntandoClusterInfrastructureSpec item) {
            super(item);
            this.parentBuilder = parentBuilder;
        }

        public SpecNestedImplCluster(N parentBuilder) {
            super();
            this.parentBuilder = parentBuilder;
        }

        @SuppressWarnings("unchecked")
        @Override
        public N and() {
            return (N) parentBuilder.withSpec(this.build());
        }

        public N endSpec() {
            return this.and();
        }
    }

}
