package com.gowyn.invariant;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DataOutput {

    private String objectName;

    private Map<String, String> fields;
}
