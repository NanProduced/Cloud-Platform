package tech.nan.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TraceService {

    @Autowired
    private Tracer tracer;

    public String getTraceId() {
        return tracer.currentSpan() != null ? Objects.requireNonNull(tracer.currentSpan()).context().traceId() : null;
    }
}
