package br.com.uaubox.falemais.domain.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class TelephoneCharges {

    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(unique = true)
    @Id
    private String telephoneChargeId;
    @Column(nullable = false)
    private Integer origin;
    @Column(nullable = false)
    private Integer destination;
    @Digits(integer = 17, fraction = 2)
    @Column(nullable = false)
    private BigDecimal perMinuteRate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public String getTelephoneChargeId() {
        return telephoneChargeId;
    }

    public void setTelephoneChargeId(String telephoneChargeId) {
        this.telephoneChargeId = telephoneChargeId;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public BigDecimal getPerMinuteRate() {
        return perMinuteRate;
    }

    public void setPerMinuteRate(BigDecimal perMinuteRate) {
        this.perMinuteRate = perMinuteRate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
