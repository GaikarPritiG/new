import org.testng.annotations.Test;

import com.eppm.common.LoginPage;

public class LoginTest {
	
	LoginPage l = new LoginPage();
	
	
	@Test
	public void Login(){
		l.login();
		
	}

}
