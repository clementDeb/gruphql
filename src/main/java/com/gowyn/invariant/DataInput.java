package com.gowyn.invariant;

import lombok.Builder;

import java.util.List;

@lombok.Data
@Builder
public class DataInput {

    private String objectName;

    private List<String> fields;
}

