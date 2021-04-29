package ru.leverx.dealerStatistics.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedbacks", schema = "public")
public class Feedback extends BaseEntity {

    @Column(name = "message")
    private String message;

    @Column(name = "rating")
    private double rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "approved")
    private boolean approved;
}
