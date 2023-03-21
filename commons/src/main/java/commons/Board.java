package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {

    /**
     * The boards are identified by their name,
     * so the name is the primary key is their name
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    /**
     * Each Board has a collection of users that have joined the board
     */
    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<User> users;
    /**
     * Each board has multiple lists of cards
     */
    @OneToMany(targetEntity = CardList.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "board_id")
    private List<CardList> list;

    /**
     * Constructor for the Board class
     * @param creator the creator of the board
     * @param name the name of the board
     * @param list a CardList
     */
    @SuppressWarnings("unused")
    public Board(User creator, List<CardList> list, String name) {
        this.users = new ArrayList<>();
        users.add(creator);
        this.list = list;
        this.name = name;
    }
    /**
    *empty constructor was necessary since post requests do not work for some reasons
    *also when creating a post request, the first name and last name of the person are set to null
     */
    @SuppressWarnings("unused")
    public Board() {
        // for object mappers
    }

    /**
     * Constructor for the Board class without a given list
     * @param creator the creator of the board
     * @param name the name of the board
     */
    @Inject
    public Board(User creator, String name) {
        this.users = new ArrayList<>();
        this.users.add(creator);
        this.name = name;
        this.list = new ArrayList<>();
    }

    /**
     * Getter for the id of the board
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for the id(Used for server tests)
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Checks whether the board has a user with a
     * specific id
     * @param id the id in search
     * @return true if the board has a user with this id,
     * false otherwise
     */
    public boolean hasUser(long id) {
        for (User u: this.getUsers()) {
            if (u.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for the list of users
     * @return the list of users that have joined the board
     */
    @SuppressWarnings("unused")
    public List<User> getUsers() {
        return users;
    }

    /**
     * Adds a user to the collection of users related to the board
     * @param user the user to be added
     */
    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
            users.add(user);
        }
        if (this.users.contains(user)) {
            return;
        }
        users.add(user);
        user.getBoardList().add(this);
    }

    /**
     * Removes a user from a board
     * @param user the user to be removed
     */
    @SuppressWarnings("unused")
    public void removeUser(User user) {
        this.users.remove(user);
    }

    /**
     * Getter for the name
     * @return the name of the board
     */
    @SuppressWarnings("unused")
    public String getName() {
        return this.name;
    }

    /**
     * Setter for the name
     * @param name the new name of the board
     */
    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the list of CardLists
     * @return the CardLists
     */
    public List<CardList> getList() {
        return list;
    }

    /**
     * Adds a CardList to the board(used for drag and drop feature)
     * @param cardList the card to be added
     */
    @SuppressWarnings("unused")
    public void addList(CardList cardList) {
        list.add(cardList);
    }

    /**
     * Adds a new, empty CardList to the board
     */
    @SuppressWarnings("unused")
    public void addEmptyList() {
        list.add(new CardList());
    }

    /**
     * Equals method for the Board class
     * @param obj the object whose equality we test
     * @return true if-f obj == this
     */
    @Override
    public boolean equals(Object obj) { return EqualsBuilder.reflectionEquals(this, obj); }

    /**
     * Hash Code method for Board
     * @return hash code
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
