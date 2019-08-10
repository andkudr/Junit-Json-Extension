package andkudr.extensions;

import andkudr.annotations.JsonParameter;
import andkudr.annotations.JsonResourceParameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Parameter;
import java.util.Optional;

public class JsonParameterExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return parameter.isAnnotationPresent(JsonParameter.class) || parameter.isAnnotationPresent(JsonResourceParameter.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Optional<JsonParameter> jsonParameterOptional = parameterContext.findAnnotation(JsonParameter.class);

        if (jsonParameterOptional.isPresent()) {
            Class<?> type = parameterContext.getParameter().getType();
            return jsonParameterHandler(jsonParameterOptional.get().json(), type);
        }

        Optional<JsonResourceParameter> jsonSourceParameterOptional = parameterContext.findAnnotation(JsonResourceParameter.class);

        if (jsonSourceParameterOptional.isPresent()) {
            Class<?> type = parameterContext.getParameter().getType();
            return jsonResourceParameterHandler(jsonSourceParameterOptional.get().path(), type);
        }

        throw new ParameterResolutionException("Annotation not found");
    }

    private Object jsonParameterHandler(String json, Class<?> type) throws ParameterResolutionException {
        try {
            return new ObjectMapper().readValue(json, type);
        } catch (IOException e) {
            throw new ParameterResolutionException("Json parsing exception");
        }
    }

    private Object jsonResourceParameterHandler(String path, Class<?> type) throws ParameterResolutionException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        try {
            return new ObjectMapper().readValue(is, type);
        } catch (IOException e) {
            throw new ParameterResolutionException("Json parsing exception");
        }
    }

}
