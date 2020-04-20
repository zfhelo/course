import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author zhf
 */
public class Main {
    public static void main(String[] args) throws IOException {
        LocalDateTime l1 = LocalDateTime.now();
        LocalDateTime l2 = LocalDateTime.now().plusYears(1);
        System.out.println(l1.compareTo(l2));


    }
}
