package HFJ;

/**
 * Created by Tigrenok on 20.08.2017.
 * Изучаем Java, стр.598
 */

import java.util.*;

public class TestGenerics1 {
    public static void main(String[] args) {
        new TestGenerics1().go();
    }

    public void go() {
        ArrayList<Animal> animals = new ArrayList<Animal>();
        animals.add(new Dog());
        animals.add(new Cat());
        animals.add(new Dog());
        takeAnimals(animals);

        ArrayList<Dog> dogs = new ArrayList<Dog>();
        dogs.add(new Dog());
        dogs.add(new Dog());
        takeAnimals(dogs);

        //ArrayList<Dog> dogs1 = new ArrayList<Animal>();
        //ArrayList<Animal> animals1 = new ArrayList<Dog>();
        List<Animal> list = new ArrayList<Animal>();
        //ArrayList<Dog> dogs = new ArrayList<Dog>();
    }

    public void takeAnimals(ArrayList<? extends Animal> animals) {
        for (Animal a : animals) {
            a.eat();
        }
    }
}

abstract class Animal {
    void eat() {
        System.out.println("Животное ест..");
    }
}

class Dog extends Animal {


}

class Cat extends Animal {
}
