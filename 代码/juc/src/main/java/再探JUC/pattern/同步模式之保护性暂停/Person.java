package 再探JUC.pattern.同步模式之保护性暂停;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Person {
    private String name;
    private int age;
}