package com.example.util.exampledemoutils.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public class BaseEntity implements Serializable {
    
    private static final long serialVersionUID = -5693162489720182075L;
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
}
