package andkudr;

import andkudr.annotations.JsonParameter;
import andkudr.annotations.JsonResourceParameter;
import andkudr.extensions.JsonParameterExtension;
import andkudr.pojo.SimplePojo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JsonParameterExtension.class)
public class JsonParameterExtensionTest {

    @Test
    public void testWithParameter(@JsonParameter(json = "{\"name\": \"TestWithParameter\"}") SimplePojo pojo) {
        assertEquals(pojo.getName(), "TestWithParameter");
    }

    @Test
    public void testWithResource(@JsonResourceParameter(path = "SimplePojo.json") SimplePojo pojo) {
        assertEquals(pojo.getName(), "TestWithResource");
    }

    @Test
    public void multiParamsTest(@JsonParameter(json = "{\"name\": \"TestWithParameter\"}") SimplePojo firstPojo,
                                @JsonResourceParameter(path = "SimplePojo.json") SimplePojo secondPojo) {
        assertEquals(firstPojo.getName(), "TestWithParameter");
        assertEquals(secondPojo.getName(), "TestWithResource");

    }

    @Test
    public void multiParamsTestReverse(@JsonResourceParameter(path = "SimplePojo.json") SimplePojo firstPojo,
                                       @JsonParameter(json = "{\"name\": \"TestWithParameter\"}") SimplePojo secondPojo) {
        assertEquals(firstPojo.getName(), "TestWithResource");
        assertEquals(secondPojo.getName(), "TestWithParameter");
    }

}
