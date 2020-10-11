



~~~
<link  type="text/css" rel="stylesheet" href="http://files.cnblogs.com/files/miangao/maodian.css">
<script src="http://files.cnblogs.com/files/miangao/maodian.js"></script>
<script src="http://files.cnblogs.com/files/miangao/bootstrap.min.js"></script>



<script type="text/javascript">
      //以下是锚点JS
      var a = $(document);
      a.ready(function() {
        var b = $('body'),
          d = 'sideToolbar',
          e = 'sideCatalog',
          f = 'sideCatalog-catalog',
          g = 'sideCatalogBtn',
          h = 'sideToolbar-up',
          i = '<div id="sideToolbar"style="display:none;">\<div class="sideCatalogBg"id="sideCatalog">\<div id="sideCatalog-sidebar">\<div class="sideCatalog-sidebar-top"></div>\<div class="sideCatalog-sidebar-bottom"></div>\</div>\<div id="sideCatalog-catalog">\<ul class="nav"style="width:225px;zoom:1">\</ul>\</div>\</div>\<a href="javascript:void(0);"id="sideCatalogBtn"class="sideCatalogBtnDisable"></a>\</div>',
          j = '',
          k = 200,
          l = 0,
          m = 0,
          n = 0,
          //限制存在个数，如数量过多，则只显示h2，不显示h3
          //o, p = 13,
          o, p = 100,
          q = true,
          r = true,
          s = b;
        if(s.length === 0) {
          return
        };
        b.append(i);
        //指定获取目录的范围-------------这一点非常重要，因为每个人指定的范围都不一样，所以这是要修改的地方
        //o = s.find(':header');
        o = $('#cnblogs_post_body').find(':header')
        if(o.length > p) {
          r = false;
          var t = s.find('h2');
          var u = s.find('h3');
          if(t.length + u.length > p) {
            q = false
          }
        };
        o.each(function(t) {
          var u = $(this),
            v = u[0];

          var title = u.text();
          var text = u.text();

          u.attr('id', 'autoid-' + l + '-' + m + '-' + n)
          //if (!u.attr('id')) {
          //    u.attr('id', 'autoid-' + l + '-' + m + '-' + n)
          //};
          if(v.localName === 'h2') {
            l++;
            m = 0;
            if(text.length > 14) text = text.substr(0, 20) + "...";
            j += '<li><span>' + l + '&nbsp&nbsp</span><a href="#' + u.attr('id') + '" title="' + title + '">' + text + '</a><span class="sideCatalog-dot"></span></li>';
          } else if(v.localName === 'h3') {
            m++;
            n = 0;
            if(q) {
              if(text.length > 12) text = text.substr(0, 16) + "...";
              j += '<li class="h2Offset"><span>' + l + '.' + m + '&nbsp&nbsp</span><a href="#' + u.attr('id') + '" title="' + title + '">' + text + '</a></li>';
            }
          } else if(v.localName === 'h4') {
            n++;
            if(r) {
              j += '<li class="h3Offset"><span>' + l + '.' + m + '.' + n + '&nbsp&nbsp</span><a href="#' + u.attr('id') + '" title="' + title + '">' + u.text() + '</a></li>';
            }
          }
        });
        $('#' + f + '>ul').html(j);
        b.data('spy', 'scroll');
        b.data('target', '.sideCatalogBg');
        $('body').scrollspy({
          target: '.sideCatalogBg'
        });
        $sideCatelog = $('#' + e);
        $('#' + g).on('click', function() {
          if($(this).hasClass('sideCatalogBtnDisable')) {
            $sideCatelog.css('visibility', 'hidden')
          } else {
            $sideCatelog.css('visibility', 'visible')
          };
          $(this).toggleClass('sideCatalogBtnDisable')
        });
        $('#' + h).on('click', function() {
          $("html,body").animate({
            scrollTop: 0
          }, 500)
        });
        $sideToolbar = $('#' + d);

        //通过判断评论框是否存在显示索引目录
        var commentDiv = $("#blog-comments-placeholder");

        a.on('scroll', function() {
          //评论框存在才调用方法
          if(commentDiv.length > 0) {
            var t = a.scrollTop();
            if(t > k) {
              $sideToolbar.css('display', 'block');
              $('#gotop').show()
            } else {
              $sideToolbar.css('display', 'none')
              $('#gotop').hide()
            }
          }
        })
      });
      //以上是锚点JS
      //以下是返回顶部JS
      $(function() {
        $('body').append('<div id="gotop" onclick="goTop();"></div>');
      });

      function goTop(u, t, r) {
        var scrollActivate = !0;
        if(scrollActivate) {
          u = u || 0.1;
          t = t || 16;
          var s = 0,
            q = 0,
            o = 0,
            p = 0,
            n = 0,
            j = 0;
          document.documentElement && (s = document.documentElement.scrollLeft || 0, q = document.documentElement.scrollTop || 0);
          document.body && (o = document.body.scrollLeft || 0, p = document.body.scrollTop || 0);
          n = window.scrollX || 0;
          j = window.scrollY || 0;
          s = Math.max(s, Math.max(o, n));
          q = Math.max(q, Math.max(p, j));
          p = 1 + u;
          window.scrollTo(Math.floor(s / p), Math.floor(q / p));
          0 < s || 0 < q ? window.setTimeout('goTop(' + u + ', ' + t + ')', t) : 'undefined' != typeof r && r()
        } else {
          scrollActivate = !0
        }
      }
      //以上是返回顶部JS
    </script>



~~~



链表 

# 一、单向链表

## 1.核心Api

根据索引返回节点

```java
private Node<T> getNodeOfIndex(int index) {
    Node<T> temp = first;

    //遍历节点
    for (int i = 0; i < index; i++) {
        temp = temp.next;
    }

    return temp;
}
```

## 2.T remove(int index)

-   找到需要删除的节点的前一个
-   使删除节点的下一个节点指向需要删除节点的下一个节点

```java
@Override
public T remove(int index) {
    checkIndex(index);

    Node<T> remove = first;
    if (index == 0) {
        //首节点后移
        first = first.next;
    } else {
        //删除节点的前一个
        Node<T> pre = getNodeOfIndex(index - 1);
        //删除的节点
        remove = pre.next;

        //删除节点
        pre.next = remove.next;
    }

    size--;
    return remove.o;
}
```



## 3.void add(int index, T o) 

-   找到需要添加节点的前一个
-   需要添加节点的next指向前节点的next
-   前节点的next指向添加的节点

```java
/**
 * 添加元素到链表的指定位置
 *
 * @param index 指定位置
 * @param o     元素
 */
@Override
public void add(int index, T o) {
    checkIndexOfAdd(index);

    if (index == 0) {
        //添加到链表的第一个
        first = new Node<>(o, first);
    } else {
        //添加节点的前一个节点
        Node<T> pre = getNodeOfIndex(index - 1);
        //需要添加的节点
        Node<T> add = new Node<>();
        //添加节点的下一个
        Node<T> next = pre.next;
        //保存元素
        add.o = o;

        //连接节点
        pre.next = add;
        add.next = next;
    }
    size++;
}
```



## 4.详情

```java
package 数据结构和算法.数据结构.list.linked;

import 数据结构和算法.数据结构.list.AbstractList;
import 数据结构和算法.数据结构.list.List;
import 数据结构和算法.数据结构.list.array.ArrayList;

/**
 * @author likeLove
 * @since 2020-10-01  14:30
 */
public class LinkedList<T> extends AbstractList<T> implements List<T> {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        System.out.println(list.size());
        list.add(1);
        System.out.println(list.size());
        list.add(2);
        list.add(3);
        System.out.println(list);
    }

    /**
     * 链表的第一个节点
     */
    private Node<T> first;

    /**
     * 查找元素在链表中的位置
     *
     * @param o 需要查找的元素
     *
     * @return 元素在链表中的索引
     */
    @Override
    public int indexOf(T o) {
        Node<T> temp = this.first;

        //遍历链表
        for (int i = 0; i < size; i++) {
            if (temp.o == o) {
                return i;
            }
            //后移
            temp = temp.next;
        }

        return 0;
    }

    /**
     * 添加元素到链表
     *
     * @param o 需要添加的元素
     */
    @Override
    public void add(T o) {
        add(size, o);
    }

    /**
     * 添加元素到链表的指定位置
     *
     * @param index 指定位置
     * @param o     元素
     */
    @Override
    public void add(int index, T o) {
        checkIndexOfAdd(index);

        if (index == 0) {
            //添加到链表的第一个
            first = new Node<>(o, first);
        } else {
            //添加节点的前一个节点
            Node<T> pre = getNodeOfIndex(index - 1);
            //需要添加的节点
            Node<T> add = new Node<>();
            //添加节点的下一个
            Node<T> next = pre.next;
            //保存元素
            add.o = o;

            //连接节点
            pre.next = add;
            add.next = next;
        }
        size++;
    }

    /**
     * 移除指定位置的元素
     *
     * @param index 指定位置
     *
     * @return 被移除的元素
     */
    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> remove = first;
        if (index == 0) {
            //首节点后移
            first = first.next;
        } else {
            //删除节点的前一个
            Node<T> pre = getNodeOfIndex(index - 1);
            //删除的节点
            remove = pre.next;

            //删除节点
            pre.next = remove.next;
        }

        size--;
        return remove.o;
    }

    /**
     * 移除指定元素
     *
     * @param o o
     *
     * @return 被移除的元素
     */
    @Override
    public T remove(T o) {
        return remove(indexOf(o));
    }

    /**
     * 根据索引返回元素(从0开始）
     *
     * @param index 元素的索引
     *
     * @return 元素
     */
    @Override
    public T get(int index) {
        checkIndex(index);

        return getNodeOfIndex(index).o;
    }

    /**
     * 为索引位置设置新的元素
     *
     * @param index 索引
     * @param o     新元素
     *
     * @return 旧元素
     */
    @Override
    public T set(int index, T o) {
        checkIndex(index);

        //找到相关节点
        Node<T> temp = getNodeOfIndex(index);
        T oldElement = temp.o;

        //替换元素
        temp.o = o;

        return oldElement;
    }

    /**
     * 清空链表
     */
    @Override
    public void clear() {
        size = 0;

        first = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> temp = this.first;
        for (int i = 0; i < size; i++) {
            sb.append(temp.o);
            if (i < size - 1) {
                sb.append(",").append(" ");
            }
            temp = temp.next;
        }
        sb.append("]");
        return String.valueOf(sb);
    }

    private Node<T> getNodeOfIndex(int index) {
        Node<T> temp = first;

        //遍历节点
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }

        return temp;
    }

    /**
     * 链表节点类
     *
     * @param <T>
     */
    private static class Node<T> {
        /**
         * 保存在节点中的元素
         */
        T o;
        /**
         * 当前节点的下一个节点
         */
        Node<T> next;

        public Node(T o, Node<T> next) {
            this.o = o;
            this.next = next;
        }

        public Node() { }
    }
}
```







# 二、双向链表

含有一个头节点和尾节点的链表

-   头节点：链表的第一个元素
-   尾节点：链表的最后一个元素
-   节点类
    -   prev指针
    -   next指针
    -   o



## 1、clear

思路：

-   size归o
-   first清空
-   last清空



## 2、add

```
普通添加
添加前   prev  next
添加后   prev  newCurr next

添加到最后
添加前   last
添加后   last newLast
```

-   判断是否是添加到最后

    -   获取旧的last
    -   创建新的last，新last的prev指向旧last
    -   判断链表是否为空
        -   如果为空，则first=last
        -   不为空，旧last的next指向新last

-   添加到其他位置

    -   获取这个节点的前驱与后继
    -   创建需要添加的节点，指向前驱与后继
    -   后继的prev指向新创建的节点
    -   判断是否是添加到最前面
        -   只需要判断前驱是否为空
        -   不为空，前驱的next指向新添加的节点
        -   为空，就让first指向新添加的节点

    

## 3、remove

~~~
 prev remove next
 prev next
~~~

-   获取要删除的节点
-   获取该节点的前驱与后继
-   前驱和后继互连
    -   判断前驱是否为空
        -   说明要删除是first
        -   first直接指向next
    -   判断next是否为空
        -   说明要删除的是last
        -   last直接指向prev









## 代码

```java
package 数据结构和算法.数据结构.list.linked;

import 数据结构和算法.数据结构.list.AbstractList;
import 数据结构和算法.数据结构.list.List;

import java.util.Objects;


/**
 * @author likeLove
 * @since 2020-10-03  19:58
 * 双向链表
 * - 含有头节点和尾节点
 * - 节点类含有prev 和 next 两个指针
 */
public class LinkedList<T> extends AbstractList<T> implements List<T> {
    /**
     * 链表的第一个节点 The First.
     */
    private Node<T> first;
    /**
     * 最后一个节点 The Last.
     */
    private Node<T> last;

    /**
     * 查找元素在数组中的位置
     *
     * @param o 需要查找的元素
     *
     * @return 元素在数组中的索引
     */
    @Override
    public int indexOf(T o) {
        Node<T> temp = this.first;
        int index = 0;

        while (temp != null) {
            if (temp.o == o) {
                break;
            }
            index++;
            temp = temp.next;
        }

        return index;
    }

    /**
     * 添加元素到数组
     *
     * @param o 需要添加的元素
     */
    @Override
    public void add(T o) {
        add(size, o);
    }

    /**
     * 添加元素到数组的指定位置
     * 普通添加
     * 添加前   prev  next
     * 添加后   prev  newCurr next
     * <p>
     * 添加到最后
     * 添加前   last
     * 添加后   last newLast
     *
     * @param index 指定位置
     * @param o     元素
     */
    @Override
    public void add(int index, T o) {
        // 检查索引是否越界
        checkIndexOfAdd(index);
        if (index == size) {  // 添加到最后面
            // 获取前驱，因为是最后一个节点所以没有后继
            Node<T> prev = last;

            // 新的last
            last = new Node<>(o, prev, null);

            // 判断是否是添加的第一个节点
            if (prev == null) {
                // 因为是第一个节点，所以first和last都指向同一个节点
                first = last;
            } else {
                // 旧last的next指向新last
                prev.next = last;
            }
        } else {   // 添加到中间和前面
            // 获取添加节点的前驱和后继
            Node<T> next = getNodeOfIndex(index);
            Node<T> prev = next.prev;

            // 添加节点
            Node<T> newCurr = new Node<>(o, prev, next);

            // 前驱和后继和新添加的节点连线
            next.prev = newCurr;

            // 判断是否是添加到第一个第一个节点
            if (prev != null) { //  不是
                prev.next = newCurr;
            } else {  // 是
                first = newCurr;
            }
        }

        size++;
    }

    /**
     * 移除指定位置的元素
     * prev remove next
     * prev next
     *
     * @param index 指定位置
     *
     * @return 被移除的元素
     */
    @Override
    public T remove(int index) {
        // 检查索引是否越界
        checkIndex(index);

        // 获取要删除的节点以及前驱和后继
        Node<T> remove = getNodeOfIndex(index);
        Node<T> prev = remove.prev;
        Node<T> next = remove.next;

        // 判断是否删除的是first
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        // 判断删除的是否是last
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }

        size--;
        return remove.o;
    }

    /**
     * 移除指定元素
     *
     * @param o o
     *
     * @return 被移除的元素
     */
    @Override
    public T remove(T o) {
        return remove(indexOf(o));
    }

    /**
     * 根据索引返回元素(从0开始）
     *
     * @param index 元素的索引
     *
     * @return 元素
     */
    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeOfIndex(index).o;
    }

    /**
     * 为索引位置设置新的元素
     *
     * @param index 索引
     * @param o     新元素
     *
     * @return 旧元素
     */
    @Override
    public T set(int index, T o) {
        // 检查index
        checkIndex(index);

        // 获取需要修改的节点
        Node<T> set = getNodeOfIndex(index);
        // 节点存放的旧元素
        T oldO = set.o;

        // 修改
        set.o = o;

        //返回旧元素
        return oldO;
    }

    /**
     * 清空数组
     */
    @Override
   public void clear() {
        size = 0;
        first = null;
        last = null;
        System.gc();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> temp = this.first;
        for (int i = 0; i < size; i++) {
            sb.append(temp.o);
            if (i < size - 1) {
                sb.append(",").append(" ");
            }
            temp = temp.next;
        }
        sb.append("]");
        return String.valueOf(sb);
    }

    /**
     * 根据索引返回node节点
     *
     * @param index 索引
     *
     * @return 根据节点返回的节点
     */
    private Node<T> getNodeOfIndex(int index) {
        Node<T> temp;

        // 寻找这个节点
        if (index < (size >> 1)) {
            // 1.index < 元素个数的一半，就从first开始向后寻找。
            //   从0遍历到index
            temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            // 2.index > 元素个数的一半，就从last开始向前寻找
            //   从最后一个元素遍历到index
            temp = last;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }

        return temp;
    }

    /**
     * 双向链表的节点类
     *
     * @param <T> the type parameter
     */
    public static class Node<T> {
        /**
         * 元素
         */
        T o;
        /**
         * 前驱
         */
        Node<T> prev;
        /**
         * 后继
         */
        Node<T> next;

        /**
         * 初始化 new Node.
         *
         * @param o    当前节点存放的元素
         * @param prev 当前节点的前驱
         * @param next 当前节点的后继
         */
        public Node(T o, Node<T> prev, Node<T> next) {
            this.o = o;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return o.toString();
        }
    }
}
```





## 测试：

~~~java
public static void main(String[] args) {
        int j = 100000;
        LinkedList<Integer> list = new LinkedList<>();
        java.util.LinkedList<Integer> sysList = new java.util.LinkedList<>();
        long listStart = System.currentTimeMillis();
        for (int i = 1; i <= j; i++) {
            list.add(i);
            if (i > 100) {
                list.add(i-10,i);
            }
        }
        long listEnd = System.currentTimeMillis();
        System.out.println(listEnd  - listStart);
        long sysStart = System.currentTimeMillis();
        for (int i = 1; i <= j; i++) {
            sysList.add(i);
            if (i > 100) {
                sysList.add(i - 10, i);
            }
        }
        long sysEnd = System.currentTimeMillis();
        System.out.println(sysEnd - sysStart);

        System.out.println(Objects.equals(list.toString(), sysList.toString()));
    }	
~~~

![image-20201006145807224](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006145807.png)







# 三、单向循环链表

最后一个节点指向第一个节点



## add

![image-20201006162732468](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006162732.png)



## remove![image-20201006161559527](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006161559.png)





# 四、双向循环链表

## add

![image-20201006182818571](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006182818.png)



## remove

![image-20201006190743462](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006190743.png)