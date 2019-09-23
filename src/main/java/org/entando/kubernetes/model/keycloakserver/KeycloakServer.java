package org.entando.kubernetes.model.keycloakserver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.CustomResource;
import java.util.Optional;
import org.entando.kubernetes.model.EntandoCustomResourceStatus;
import org.entando.kubernetes.model.HasIngress;

@JsonSerialize
@JsonDeserialize
public class KeycloakServer extends CustomResource implements HasIngress {

    public static final String CRD_NAME = "entandokeycloakservers.entando.org";
    @JsonProperty
    private KeycloakServerSpec spec;
    @JsonProperty
    private EntandoCustomResourceStatus entandoStatus;

    public KeycloakServer() {
        super("EntandoKeycloakServer");
        setApiVersion("entando.org/v1alpha1");
    }

    public KeycloakServer(KeycloakServerSpec spec) {
        this();
        this.spec = spec;
    }

    public KeycloakServer(KeycloakServerSpec spec, ObjectMeta metadata, EntandoCustomResourceStatus status) {
        this(metadata, spec);
        this.entandoStatus = status;
    }

    public KeycloakServer(ObjectMeta metadata, KeycloakServerSpec spec) {
        this(spec);
        super.setMetadata(metadata);
    }

    @JsonIgnore
    public KeycloakServerSpec getSpec() {
        return spec;
    }

    public void setSpec(KeycloakServerSpec spec) {
        this.spec = spec;
    }

    @JsonIgnore
    @Override
    public EntandoCustomResourceStatus getStatus() {
        return this.entandoStatus == null ? this.entandoStatus = new EntandoCustomResourceStatus() : this.entandoStatus;
    }

    @Override
    public void setStatus(EntandoCustomResourceStatus status) {
        this.entandoStatus = status;
    }

    @Override
    @JsonIgnore
    public Optional<String> getIngressHostName() {
        return getSpec().getIngressHostName();
    }

    @Override
    @JsonIgnore
    public Optional<Boolean> getTlsEnabled() {
        return getSpec().getTlsEnabled();
    }
}
