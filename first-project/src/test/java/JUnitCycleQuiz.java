import org.junit.jupiter.api.*;

public class JUnitCycleQuiz {

    @BeforeEach
    public void beforEach(){
        System.out.println("Hello!");
    }
    @AfterAll
    static void afterAll(){
        System.out.println("Bye!");
    }

    @Test
    public void junitQuiz3(){
        System.out.println("This is first test");
    }

    @Test
    public void junitQuiz4() {
        System.out.println("This is second test");
    }
}
