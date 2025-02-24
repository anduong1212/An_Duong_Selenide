package leapfrog.dataobjects;

import java.util.Objects;

public record Product(String title, String age, String price) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(title, product.title) && Objects.equals(age, product.age) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, age, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", age='" + age + '\'' +
                ", price=" + price +
                '}';
    }
}
