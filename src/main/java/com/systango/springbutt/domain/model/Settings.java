package com.systango.springbutt.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

/**
 * @author Arpit Khandelwal
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class Settings extends BaseDomainObject {

    private String settingKey;
    private String settingValue;

}