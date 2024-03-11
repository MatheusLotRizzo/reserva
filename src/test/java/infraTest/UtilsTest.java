package infraTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class UtilsTest {
	public static String convertJson(Object object) throws JsonProcessingException {
		return new ObjectMapper().findAndRegisterModules().writeValueAsString(object);
	}
}
