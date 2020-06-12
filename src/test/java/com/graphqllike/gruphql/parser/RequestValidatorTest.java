package com.graphqllike.gruphql.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
public class RequestValidatorTest {

    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingGoodRequest() throws Exception {

        String objectName = "User";
        List<String> fields = Collections.singletonList("lastname");

        RequestValidator.validateRequest(objectName, fields);

    }

}