package ioc;

/**
 * @author Yuicon
 * @Date 2018/7/4
 */
@Component
public class OrderDao implements Dao {

    @Override
    public void doSomeThing() {
        System.out.println("test");
    }

}
