package generics;

/**
 * @author Yuicon
 */
public class Generics <T> {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static void main(String[] args) {
        Generics<String> generics = new Generics<>();
        generics.setValue("ss");
        System.out.println(generics.getValue());
    }

}
