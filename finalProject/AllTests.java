package finalProject;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
	UserTest.class, MessageTest.class, ClientTest.class, 
	LogTest.class, UserTest.class, ChatTest.class
})
public class AllTests {
}

