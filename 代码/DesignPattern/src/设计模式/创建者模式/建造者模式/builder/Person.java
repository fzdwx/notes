package 设计模式.创建者模式.建造者模式.builder;

import java.util.Date;

/**
 * @author like
 * @date 2020-12-16 16:06
 * @contactMe 980650920@qq.com
 * @description
 */
public class Person {

    private String name;
    private int age;
    private String address;
    private String mobilePhoneNumber;
    private Date birthDate;

    private Person() {
    }

    public  static PersonBuilder builder() {
        return new PersonBuilder();
    }
    public static class PersonBuilder {
        private String name;
        private int age;
        private String address;
        private String mobilePhoneNumber;
        private Date birthDate;

        public  Person build() {
            Person person = new Person();
            person.name = name;
            person.age = age;
            person.address = address;
            person.mobilePhoneNumber = mobilePhoneNumber;
            person.birthDate = birthDate;
            return person;
        }

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder age(int age) {
            this.age = age;
            return this;
        }

        public PersonBuilder address(String address) {
            this.address = address;
            return this;
        }

        public PersonBuilder mobilePhoneNumber(String mobilePhoneNumber) {
            this.mobilePhoneNumber = mobilePhoneNumber;
            return this;
        }

        public PersonBuilder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
