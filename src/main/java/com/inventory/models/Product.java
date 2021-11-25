package com.inventory.models;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product extends BaseEntity implements java.io.Serializable {

    @Column(name = "product_id")
    private String productId;

    @Column(name = "name")
    private String name;

    @Column(name = "meta")
    private String meta;

    @Column(name = "active")
    private Boolean active;
}
