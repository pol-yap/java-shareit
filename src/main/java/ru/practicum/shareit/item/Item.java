package ru.practicum.shareit.item;

import lombok.*;
import ru.practicum.shareit.booking.Booking;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
//    @SequenceGenerator(name = "item_seq", sequenceName = "item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "owner_id")
    private Long ownerId;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private Booking nextBooking;

    @Transient
    private Booking lastBooking;
}
