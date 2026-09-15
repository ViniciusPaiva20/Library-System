package br.com.librarysystem.model.entities;

public class Renter {

    private String name;
    private int age;
    private String email;

    public Renter() {
    }

    public Renter(String name, int age, String email) {
        this.name = name;
        this.age = age;
        validateEmail(email);
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private void validateEmail(String email) {
        boolean valide = false;
        for (int i = 0; i < email.length(); i++) {
            char charEmail = email.charAt(i);

            if (charEmail == '@') {
                valide = true;
            }
        }
        if (valide != true) {
            throw new RuntimeException("Email Inválido!");
        }
    }

    @Override
    public String toString() {
        return "Renter: " + name + ", age: " + age + ", email: " + email;
    }
}
