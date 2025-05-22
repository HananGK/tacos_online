package com.hanan.tacosonline.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date placedAt;
    @NotBlank(message = "El nombre es obligatorio")
    private String deliveryName;
    @NotBlank(message = "La calle es obligatoria")
    private String deliveryStreet;
    @NotBlank(message = "La ciudad es obligatoria")
    private String deliveryCity;
    @NotBlank(message = "El estado es obligatorio")
    private String deliveryState;
    @NotBlank(message = "El código postal es obligatorio")
    private String deliveryZip;
    @CreditCardNumber(message = "El número de tarjeta de crédito es inválido")
    private String ccNumber;
    @Pattern(regexp ="^(0[1-9]|1[0-2](\\/))([2-9][0-9])$" , message = "La fecha debe ser MM/AA")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "CVV inválido")
    private String ccCVV;
    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design) {
        tacos.add(design);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
