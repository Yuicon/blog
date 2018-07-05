package ioc;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Yuicon
 * @Date 2018/7/4
 */
public class Container {

    private List<String> classPaths = new ArrayList<>();

    private String separator;

    private Map<Class, Object> components = new HashMap<>();

    public Container(Class cls) {
        File file = new File(cls.getResource("").getFile());
        separator = file.getName();
        renderClassPaths(new File(this.getClass().getResource("").getFile()));
        make();
        di();
    }

    private void make() {
        classPaths.forEach(classPath -> {
            try {
                Class c = Class.forName(classPath);
                // 找到有 @ioc.Component 注解的类并实例化
                if (c.isAnnotationPresent(Component.class)) {
                    components.put(c, c.newInstance());
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 注入依赖
     */
    private void di() {
        components.forEach((aClass, o) -> Arrays.stream(aClass.getDeclaredFields()).forEach(field -> {
            if (field.isAnnotationPresent(Autowired.class)) {
                try {
                    String methodName = "set" + field.getType().getName().substring(field.getType().getName().lastIndexOf(".") + 1);
                    Method method = aClass.getMethod(methodName, field.getType());
                    if (field.getType().isInterface()) {
                        components.keySet().forEach(aClass1 -> {
                            if (Arrays.stream(aClass1.getInterfaces()).anyMatch(aClass2 -> aClass2.equals(field.getType()))) {
                                try {
                                    method.invoke(o, components.get(aClass1));
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        method.invoke(o, components.get(field.getType()));
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    /**
     * 该方法会得到所有的类，将类的绝对路径写入到classPaths中
     *
     * @param file 包
     */
    private void renderClassPaths(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            Arrays.stream(Objects.requireNonNull(files)).forEach(this::renderClassPaths);
        } else {
            if (file.getName().endsWith(".class")) {
                String classPath = file.getPath()
                        .substring(file.getPath().lastIndexOf(separator) + separator.length() + 1)
                        .replace('\\', '.')
                        .replace(".class", "");
                classPaths.add(classPath);
            }
        }
    }

    public <T> T getBean(Class c) {
        return (T) components.get(c);
    }

}
