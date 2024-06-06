package com.example.UserRegistration.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "favorite_user_id")
    private User favoriteUser;

    public Favorite() {
        // Default constructor
    }

    public Favorite(User user, User favoriteUser) {
        this.user = user;
        this.favoriteUser = favoriteUser;
    }

    // Getter and Setter methods...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFavoriteUser() {
        return favoriteUser;
    }

    public void setFavoriteUser(User favoriteUser) {
        this.favoriteUser = favoriteUser;
    }
}

