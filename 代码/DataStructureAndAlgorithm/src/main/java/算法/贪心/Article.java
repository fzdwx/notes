package 算法.贪心;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author like
 * @date 2021-01-16 11:39
 * @contactMe 980650920@qq.com
 * @description
 */
public class Article {
    public int weight;
    public int value;
    public double valueDensity;

    public Article(int weight, int value) {
        this.weight = weight;
        this.value = value;
        valueDensity = value * 1.0 / weight;
    }

    @Override
    public String toString() {
        return "Article{" +
                "weight=" + weight +
                ", value=" + value +
                ", valueDensity=" + valueDensity +
                '}';
    }
}

class test {
    public static void main(String[] args) {
        Article[] articles = new Article[]{
                new Article(35, 10),
                new Article(30, 40),
                new Article(60, 30),
                new Article(35, 40),
                new Article(25, 30),
                new Article(50, 50),
                new Article(10, 40),
        };
        Arrays.sort(articles, test::compare);
        int capacity = 150, weight = 0, value = 0;
        List<Article> select = new ArrayList<>();
        for (int i = 0; i < articles.length && weight < capacity; i++) {
            int newWeight = weight + articles[i].weight;
            if (newWeight <= capacity) {
                weight = newWeight;
                value += articles[i].value;
                select.add(articles[i]);
            }
        }
        System.out.println("总价值" + value);
        System.out.println("总重量" + weight);
        for (Article article : select) {
            System.out.println(article);
        }
    }

    private static int compare(Article a1, Article a2) {
        return a1.value - a2.value;
    }
}