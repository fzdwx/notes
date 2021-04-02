package 设计模式.行为模式.解释器模式;

import 数据结构和算法.数据结构.hash.HashMap;

/**
 * @author like
 * @date 2021-01-04 15:42
 * @contactMe 980650920@qq.com
 * @description
 */
public class Context {

    private HashMap<Var, Integer> map = new HashMap<>();

    public void assign(Var var, Integer value) {
        map.put(var,value);
    }

    protected int getValue(Var var) {
        return map.get(var);
    }
}
