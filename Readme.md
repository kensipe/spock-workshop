# Spock Testing Workshop
## purpose
This is a series of comparisons of junit, groovy test cases, and spock presented by Ken Sipe

## getting started
The code is provided using gradle.  So after you clone the repo, just type:

	> ./gradlew test
or

	> gradlw.bat test

This will download the internet... I mean download and bootstrap gradle + all the dependencies for the project including spock.

#### setting up your IDE
The gradle build file is configured to setup either an eclipse or IDEA project.  just type:

	> ./gradlew idea
or 

	> ./gradlew eclipse	

**I have only tested this on IDEA**

#### reviewing failures at the cmd-line
If a gradle build has a failed test, there isn't much information provided on the cmd-line for what is going on.  The information can be view from stdio if you use the -i switch with gradle such as:

	> ./gradlew test -i

## Labs

### Spock Basics Labs
#### Lab 1: Getting Started

1. Clone workshop
2. Execute the Getting Started
3. Review the dependencies for spock in the `build.gradle` file
4. Create your workspace environment
5. Run same test `gradle test -Dtest.single=FirstSpec`
6. Test it in your editor of choice

#### Lab 2: When/Then Tests

Create your first test using the hashmap example

        setup:
        def stack = new Stack()
        def elem = "Push me"

        when:   // stimulus
        stack.push(elem)

        then:   // response
        !stack.empty
        stack.size() == 1
        stack.peek().toString().toLowerCase() == elem.toLowerCase()

Lets check for if a null is thrown

		setup:
        def map = new HashMap()

        when:
        map.put(null, "elem")

        then:
        notThrown(NullPointerException)

Let's introduce `old()` with collections.

	 def "looking at old lists"() {
        def list = [1, 2, 3]

        when:
        list << 4

        then:
        list.size() == 4
        old(list.size()) == 3
    }
Using the above as an example, create another test which has a hashmap with a key of `example` and a value of `first`.  In the when body, assign the key to the value of `second`.  Then confirm that the old value is not equal to the current value.

Let's introduce the `with()`.  The project has an Account class.  Lets create a test on this class and assert expectations on multiple properties using the `with()`.

	given:
	def account = new Account(accountNo: "123", balance: 50.0)
	
	expect:
	with(account) {
		// code here
	}

#### Lab Expect
	
Create a simple test that assets with an expect that `2==Math.max(1, 2)`

#### Lab Where

Convert the following test to use a table in the where body

	 def "computing the maximum of two numbers"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [5, 3]
        b << [1, 9]
        c << [5, 9]
    }
    
Creating a custom data provider for where.   In the session we looked a sql statement which returned a tupple of data for testing which looked like `[year, interest, amt] << sql.rows("""select year, interest, amt from calcdata""")`

It is possible to create your own data provider (which is help for things like csv files, or ???).  Providing a customer data provider is accomplished by simply create a class that implements `Iterator`.   Create a specification with information below.  Then make the test functional by providing the implementation details to the the `CustomDataProvider` class.

	def "custom data provider version"() {
        expect:
        a + b == c

        where:
        [a, b, c] << new CustomDataProvider()
    }
    
#### Lab: SimpleInterestCalculator

SimpleInterestCalculator is a Java class.  Lets provide tests to assert the following.

When the rate is 0.5 the following is true:        
with an amount of 10000 for 2 years the interest is 1000.0
with an amount of 100 for 5 years the interest is 25.0

#### Ham Crest
When values are close but not exactly an asserted value, you can use HamCrest to help.  For spock there are 2 useful methods to statically import.

	import static spock.util.matcher.HamcrestMatchers.closeTo
	import static spock.util.matcher.HamcrestSupport.that

Hamcrest is use with the following notation in spock.

	that <value>, closeTo(<target value>, 
		<distanced from target allowed>) 
	// example
	that .6999999, closeTo(0.7, 0.01)
In this example as long as the tested value is .01 from 0.7 the test passes.

The SimpleInterestCalculator has a subtract method on it.  Tests `calc.subtract(2.0, 1.1)`

### Spock Mock Labs

#### Lab: Mocking 

In this lab you will test the provided Publisher class.  The publisher can take any number of `Subscriber`s.   When a `fire()` event is fired on a publisher, all subscribers should receive the event.

Mock 2 Subscribers and make sure that those to mocks have 1 and only 1 `receieve()` method called when `publisher.fire("event")` is invoked.

#### Lab: Order Control of Mocks
Using the same PublisherSpec, lets check to see if 1 mock throws an exception, if the other subscriber still receives notification.

To throw an exception `1 * subscriber1.receive("event") >> { throw new Exception() }`

Multiple `Then:`s are needed to handle this situation.

If the test breaks, fix the application, such that the test passes.

#### Lab: AccountService
The `com.acme.account` package contains more of a real-world example.  It contains an `Account` domain object.  An `AccountService` which interacts with an `AccountDao`.  In this lab you want to test the interaction of the service implemention and it's interaction with the DAO layer.

Modify the `AccountServiceSpec` such that the test passes.

** Unroll **
The `AccountServiceSpec` has a feature now with a `where:` body.  Modify the feature to have an `@Unroll`.  In the unroll, report the values of the variables being used in the test.

#### Lab: Spies
The provided class `WebResourceSpec` has missing the missing implementation in order to test.   Uncomment the expected block and make the test work.
	

### Spock Extension Labs
In this lab you are going to create an extension to spock. Extensions to be used to setup preconditions for testing (like injecting objects from spring), providing special reporting, or anything you can imagine as a pre or post condition of a specation feature.   This lab is inspired by Zan Thrash who is the original author of this extension.   The extension will cause on the Mac OSX the computer to `say` something upon failure.

** Steps to creating an extension **

1. Create an annotation which is annotated with an `ExtensionAnnotation`
2. Create the Extension Annotation which is an implementation of `AbstractAnnotationDrivenExtension`
3. Create a specific interceptor, in this case an implementation of `IMethodInterceptor`
4. Add the annotation from step 1 on any test you want this feature applied to.

#### Create Annotation

	@Retention(RetentionPolicy.RUNTIME)
	@Target([ElementType.TYPE, ElementType.METHOD])
	@ExtensionAnnotation(SayOnFailExtension)

	public @interface SayOnFail {
    	String value() default 'Failure is not an option'
	    String voice() default "Alex"
	}
The annotation must have runtime retention and for this example the target will be `Type` and `Method`.

#### Create the Extension Annotation

	class SayOnFailExtension extends AbstractAnnotationDrivenExtension<SayOnFail> {

    	@Override
	    void visitSpecAnnotation(SayOnFail sayOnError, SpecInfo spec) {
    	    spec.features.each { FeatureInfo feature ->
        	    if (!feature.featureMethod.reflection.isAnnotationPresent(SayOnFail)) {
            	    visitFeatureAnnotation(sayOnError, feature)
            	}
        	}
    	}

	    @Override
    	void visitFeatureAnnotation(SayOnFail sayOnError, FeatureInfo feature) {
        	def interceptor = new SayOnFailInterceptor(sayOnError, feature)
	        feature.getFeatureMethod().addInterceptor(interceptor)
    	}
	}

This is the main extension class.  There are 2 methods, 1 that is applied to Specifications, the other is applied to features.   The code for Specification just iterators over the specifications features.  So all the work is in the `visitFeatureAnnotation` method.  It creates the interceptor and adds it to the feature method.

#### Create the Interceptor

	class SayOnFailInterceptor implements IMethodInterceptor {

    	SayOnFail sayOnError
	    FeatureInfo featureInfo

    	SayOnFailInterceptor(SayOnFail sayOnError, FeatureInfo featureInfo) {
        	this.sayOnError = sayOnError
	        this.featureInfo = featureInfo
    	}

	    @Override
    	void intercept(IMethodInvocation invocation) throws Throwable {
        	try {
        		// invocation of the test (or other interceptors)
            	invocation.proceed()        
	        } catch (Throwable t) {
	        
	        	/** our intented added behavior! **/
    	        def methodName = featureInfo.getFeatureMethod().name
        	    def voiceName = sayOnError.voice()
            	def sayText = sayOnError.value() ?: "Danger! Failure for: $methodName"
	            try {
    	            "say -v $voiceName $sayText".execute()
        	    } catch (IOException ex) {}
            	throw t
	        }
    	}
	}

The intented behavior is MacOSX specific `"say -v $voiceName $sayText".execute()`.  It is the invocation of the `say` command.  Developers on other platforms should find a replacement behavior.

#### Apply The Extension Annotation

Change the class `SayExtensionExampleSpec` to use the new extension.  Cause an assertion and test the class.

	@SayOnFail
	class SayExtensionExampleSpec extends Specification {

## sharing
you are free to use this as a reference and to share with others... remember where you got it from and share the love!  that includes the awesome guys working spock... namely Peter Niederwieser (@pniederw)

