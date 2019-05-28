package com.jacobs.akka.memorydb;

import java.io.Serializable;

/**
 * @author lichao
 * Created on 2019-05-18
 */
public class GetRequest implements Serializable {
    public final String key;

    public GetRequest(String key) {
        this.key = key;
    }
}
