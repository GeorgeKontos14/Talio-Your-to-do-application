package server.api;

import commons.Card;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CardListControllerTest {

    private TestCardListRepository repo;
    private TestCardRepository cardRepository;
    private TestTaskRepository taskRepository;
    private CardListController sut;
    private SimpMessagingTemplate msg;
    private MessageChannel channel;

    private CardController cSut;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        channel = (message, timeout) -> true;
        msg = new SimpMessagingTemplate(channel);
        repo = new TestCardListRepository();
        cardRepository = new TestCardRepository();
        taskRepository = new TestTaskRepository();
        sut = new CardListController(repo, cardRepository, msg);
        cSut = new CardController(cardRepository, repo, msg, taskRepository);
    }

    /**
     * Test for add
     */
    @Test
    public void addNullCardListTest() {
        var actual = sut.add(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    /**
     * Test for add
     */
    @Test
    public void addNullOrEmptyNameBoardTest() {
        var actual1 = sut.add(new CardList(null));
        var actual2 = sut.add(new CardList(""));
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
    }

    /**
     * Test for add
     */
    @Test
    public void addActualCardList() {
        CardList cl = new CardList("a");
        cl.setId((long) -1);
        var actual = sut.add(cl);
        assertEquals(cl, actual.getBody());
    }

    /**
     * Test for add
     */
    @Test
    public void databaseIsUsed() {
        CardList c = new CardList("a");
        c.setId((long) -1);
        sut.add(c);
        assertTrue(repo.calledMethods.contains("save"));
    }

    /**
     * Test for getAll
     */
    @Test
    public void getAllTest() {
        CardList c1 = new CardList("a");
        CardList c2 = new CardList("b");
        CardList c3 = new CardList("c");
        c1.setId((long) -1);
        c2.setId((long) -2);
        c3.setId((long) -3);
        sut.add(c1);
        sut.add(c2);
        sut.add(c3);
        List<CardList> lists = sut.getAll();
        assertEquals(lists.size(), 3);
        assertTrue(lists.contains(c1));
        assertTrue(lists.contains(c2));
        assertTrue(lists.contains(c3));
    }

    /**
     * Test for getById
     */
    @Test
    public void getByNegativeIdTest() {
        var actual = sut.getById(-2);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    /**
     * Test for getById
     */
    @Test
    public void getByTooLargeTest() {
        CardList c1 = new CardList("a");
        CardList c2 = new CardList("b");
        CardList c3 = new CardList("c");
        c1.setId((long) -1);
        c2.setId((long) -2);
        c3.setId((long) -3);
        sut.add(c1);
        sut.add(c2);
        sut.add(c3);
        var actual = sut.getById(22);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    /**
     * Test for getById
     */
    @Test
    public void getByIdTest() {
        CardList c1 = new CardList("a");
        CardList c2 = new CardList("b");
        CardList c3 = new CardList("c");
        c1.setId((long) -1);
        c2.setId((long) -2);
        c3.setId((long) -3);
        sut.add(c1);
        sut.add(c2);
        sut.add(c3);
        var actual1 = sut.getById(0);
        var actual2 = sut.getById(1);
        var actual3 = sut.getById(2);
        assertEquals(actual1.getBody(), c1);
        assertEquals(actual2.getBody(), c2);
        assertEquals(actual3.getBody(), c3);
        assertTrue(repo.calledMethods.contains("findById"));
    }

    /**
     * Test for modifyName
     */
    @Test
    public void modifyNameNonExisting() {
        var actual = sut.modifyName(222, "Bad");
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    /**
     * Test for modifyName
     */
    @Test
    public void modifyNameTest() {
        CardList c1 = new CardList("a");
        CardList c2 = new CardList("b");
        CardList c3 = new CardList("c");
        c1.setId((long) -1);
        c2.setId((long) -2);
        c3.setId((long) -3);
        sut.add(c1);
        sut.add(c2);
        sut.add(c3);
        var actual = sut.modifyName(2, "cc");
        assertEquals(actual.getBody().getName(), "cc");
        //var actual2 = sut.getById(2);
        //assertEquals(actual2.getBody().getName(), "cc");
        assertTrue(repo.calledMethods.contains("save"));
    }

    /**
     * Test for removeList
     */
    @Test
    public void removeNonExistingList() {
        var actual = sut.removeList(22);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    /**
     * Test for removeList
     */
    @Test
    public void removeListTest() {
        CardList c1 = new CardList("a");
        CardList c2 = new CardList("b");
        CardList c3 = new CardList("c");
        c1.setId((long) -1);
        c2.setId((long) -2);
        c3.setId((long) -3);
        sut.add(c1);
        sut.add(c2);
        sut.add(c3);
        var actual = sut.removeList(2);
        assertEquals(actual.getBody(), c3);
        assertEquals(repo.lists.size(), 2);
        assertTrue(repo.calledMethods.contains("deleteById"));
    }

    /**
     * Test for addCardToList
     */
    @Test
    public void addBadCardToListTest() {
        CardList c1 = new CardList("a");
        c1.setId((long) -1);
        sut.add(c1);
        var actual1 = sut.addCardToList(0, null);
        var actual2 = sut.addCardToList(0, new Card(""));
        var actual3 = sut.addCardToList(0, new Card(null));
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
        assertEquals(BAD_REQUEST, actual3.getStatusCode());
    }

    /**
     * Test for addCardToList
     */
    @Test
    public void addCardToBadList() {
        CardList c1 = new CardList("a");
        c1.setId((long) -1);
        sut.add(c1);
        var actual = sut.addCardToList(22, new Card("a"));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    /**
     * Test for addCardToList
     */
    @Test
    public void addCardToList() {
        CardList c1 = new CardList("a");
        CardList c2 = new CardList("b");
        CardList c3 = new CardList("c");
        c1.setId((long) -1);
        c2.setId((long) -2);
        c3.setId((long) -3);
        sut.add(c1);
        sut.add(c2);
        sut.add(c3);
        Card c = new Card("a");
        var actual = sut.addCardToList(1, c);
        assertEquals(c, actual.getBody());
        var actual1 = sut.getById(1);
        assertTrue(actual1.getBody().getCards().contains(c));
    }

    /**
     * Test for moveCard
     */
    @Test
    public void moveCardTest() {
        CardList c1 = new CardList("a");
        CardList c2 = new CardList("b");
        CardList c3 = new CardList("c");
        c1.setId((long) -1);
        c2.setId((long) -2);
        c3.setId((long) -3);
        sut.add(c1);
        sut.add(c2);
        sut.add(c3);
        Card card1 = new Card("Card1");
        Card card2 = new Card("Card2");
        Card card3 = new Card("Card3");
        Card card4 = new Card("Card4");
        List<Card> list = new ArrayList<>();
        list.add(card1);
        list.add(card2);
        card1.setId(-1);
        card2.setId(-2);
        card3.setId(-3);
        card4.setId(-4);
        cSut.add(card1);
        cSut.add(card2);
        List<Card> broken1 = new ArrayList<>();
        broken1.add(card1);
        broken1.add(card3);
        List<Card> broken2 = new ArrayList<>();
        broken2.add(card4);
        broken2.add(card2);
        var actual1 = sut.moveCard(1, null);
        var actual2 = sut.moveCard(-1, list);
        var actual3 = sut.moveCard(1, broken1);
        var actual4 = sut.moveCard(1, broken2);
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
        assertEquals(BAD_REQUEST, actual3.getStatusCode());
        assertEquals(BAD_REQUEST, actual4.getStatusCode());
    }
}
