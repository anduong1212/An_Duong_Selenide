package leapfrog.dataobjects;

import java.util.Objects;

public record Product(int page, String title, String age, String price) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return page == product.page && Objects.equals(title, product.title) && Objects.equals(age, product.age) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, title, age, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "page=" + page +
                ", title='" + title + '\'' +
                ", age='" + age + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
