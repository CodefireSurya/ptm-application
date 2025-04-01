package com.eurokids.ptm_application.Config;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class SsmConfig {

    private static final SsmClient ssmClient = SsmClient.create();

    public static String getParameterValue(String parameterPath) {
        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(parameterPath)
                    .withDecryption(true) // Decrypt the parameter if it's a SecureString
                    .build();

            GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
            return parameterResponse.parameter().value();
        } catch (SsmException e) {
            throw new RuntimeException("Error fetching parameter value: " + e.getMessage(), e);
        }
    }


}