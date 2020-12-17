package 设计模式.创建者模式.建造者模式.builder;

import java.util.Date;

/**
 * @author like
 * @date 2020-12-16 16:13
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) {
        Person person = Person.builder()
                .name("like")
                .birthDate(new Date())
                .mobilePhoneNumber("13789983260")
                .address("武汉")
                .build();
        System.out.println(person);
        Person person2 = Person.builder()
                .name("keke")
                .address("三亚")
                .build();
        System.out.println(person2);
    }
}
