package com.example.MedicCenter.domain.model;

public class Patient {
    private Long id;
    private String dni;
    private String name;
    private Integer age;
    private String phone;
    private String email;
    private String status; // ACTIVO, INACTIVO

    public Patient() {
    }

    public Patient(Long id, String dni, String name, Integer age, String phone, String email, String status) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
