# 需求

如何判断一堆不重复的字符串是否以某个前缀开头





# Trie

字典树，前缀树，单词查找树



![image-20201227165513677](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201227165513.png)



# 接口设计

int size();

boolean isEmpty();void clear();

boolean contains(String str);

v add(string str, v value);

v remove(string str);

boolean starswith(String prefix);