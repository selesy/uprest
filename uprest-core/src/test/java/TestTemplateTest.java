import org.apache.http.HttpRequest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.selesy.testing.uprest.UpRest;

@RunWith(JUnitPlatform.class)
public class TestTemplateTest {
	
	@UpRest
	void test(HttpRequest request) {
		
	}
}
