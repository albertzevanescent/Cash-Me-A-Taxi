package cashmeataxi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	EdgeTest.class, 
	TripTest.class, 
	NodeTest.class, 
	SortedListOfEdgesTest.class
})

public class AllTests {

}
