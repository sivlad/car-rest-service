package ua.com.foxmined.carrestservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "model")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name="maker_id", nullable=false)
    private CarMaker carMaker;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private CarCategory carCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarModel carModel = (CarModel) o;
        return Objects.equals(id, carModel.id)
                && Objects.equals(name, carModel.name)
                && Objects.equals(carMaker, carModel.carMaker)
                && Objects.equals(carCategory, carModel.carCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, carMaker, carCategory);
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", carMaker=" + carMaker +
                ", carCategory=" + carCategory +
                '}';
    }
}
