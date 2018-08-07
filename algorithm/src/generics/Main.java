package generics;

import java.util.Arrays;

/**
 * @author Yuicon
 */
public class Main<U extends Manager> {

    Manager manager = new Manager();
    Employee employee = new Manager();
    Object object = new Object();
    Boss boss = new Boss();

//    public static void main(String[] args) {
//
//        Generics<Employee> generics = new Generics<>();
//
//    }
//
//    private void test1(Generics<? super Manager> generics) {
//        Employee employee = (Employee) generics.getValue();
//        employee.say();
//    }

}
