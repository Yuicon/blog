import ioc.Container;
import ioc.OrderService;

/**
 * @author Yuicon
 * @Date 2018/7/4
 */
public class Main {

    public static void main(String[] args) {
        Container container = new Container(Main.class);
        OrderService service = container.getBean(OrderService.class);
        service.test();
    }

}
