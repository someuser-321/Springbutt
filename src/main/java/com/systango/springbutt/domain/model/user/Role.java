package com.systango.springbutt.domain.model.user;

import com.systango.springbutt.domain.model.BaseDomainObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class Role extends BaseDomainObject {

    private String name;
}
