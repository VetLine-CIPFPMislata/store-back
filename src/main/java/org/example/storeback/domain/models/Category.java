package org.example.storeback.domain.models;

public class Category {
    private final Long id;
    private final String name;
    private final String description;

    public Category(Long id, String name, String description) {

        if (name != null && name.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }


        if (name != null && !name.isEmpty() && name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede contener solo espacios en blanco");
        }


        if (name != null && name.contains("\t")) {
            throw new IllegalArgumentException("El nombre de la categoría no puede contener tabulaciones");
        }


        if (description != null && description.isEmpty()) {
            throw new IllegalArgumentException("La descripción de la categoría no puede estar vacía");
        }


        if (description != null && description.contains("\t")) {
            throw new IllegalArgumentException("La descripción de la categoría no puede contener tabulaciones");
        }

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
