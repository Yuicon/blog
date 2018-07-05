package ioc;

/**
 * @author Yuicon
 * @Date 2018/7/4
 */
@Component
public class OrderService {

    @Autowired
    private Dao dao;

    public void test() {
        dao.doSomeThing();
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}
