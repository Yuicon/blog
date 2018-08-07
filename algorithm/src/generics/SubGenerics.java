package generics;

/**
 * @author Yuicon
 */
public class SubGenerics extends Generics<String> {

    @Override
    public void setValue(String value) {
        System.out.println(value);
    }

    public static void main(String[] args) {
        Generics<String> generics = new SubGenerics();
        generics.setValue("ss");
    }

}
