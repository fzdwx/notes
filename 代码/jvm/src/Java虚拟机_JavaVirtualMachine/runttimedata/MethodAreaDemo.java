package Java虚拟机_JavaVirtualMachine.runttimedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author likeLove
 * @since 2020-10-14  11:28
 * 方法区
 * -XX:MaxMetaspaceSize=100M -XX:MetaspaceSize=100M
 */
@SpringBootApplication
public class MethodAreaDemo {
    public static void main(String[] args) {
        SpringApplication.run(MethodAreaDemo.class, args);
    }
}
