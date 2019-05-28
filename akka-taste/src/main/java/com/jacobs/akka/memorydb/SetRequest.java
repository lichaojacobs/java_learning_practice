package com.jacobs.akka.memorydb;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @author lichao
 * Created on 2019-05-18
 */
@Data
@Builder
public class SetRequest implements Serializable {

    private final String key;
    private final Object value;
}
