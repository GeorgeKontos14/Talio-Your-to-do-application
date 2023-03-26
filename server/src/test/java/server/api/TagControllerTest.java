package server.api;

import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class TagControllerTest {
    private TestTagRepository repo;
    private TestBoardRepository boardRepo;
    private TagController controller;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        repo = new TestTagRepository();
        controller = new TagController(repo, boardRepo);
    }

    /**
     * Test for add
     */
    @Test
    public void addNullTagTest() {
        var actual = controller.add(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    /**
     * Test for add
     */
    @Test
    public void addNullOrEmptyNameTest() {
        var actual1 = controller.add(new Tag(null));
        var actual2 = controller.add(new Tag(""));
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
    }

    /**
     * Test for add
     */
    @Test
    public void addActualTagTest() {
        Tag tag = new Tag("tag");
        var actual = controller.add(tag);
        assertEquals(actual.getBody(), tag);
    }

    /**
     * Test for add
     */
    @Test
    public void databaseIsUsed() {
        controller.add(new Tag("tag"));
        assertTrue(repo.calledMethods.contains("save"));
    }

    /**
     * Test for getAll
     */
    @Test
    public void getAllTest() {
        Tag tag1 = new Tag("tag 1");
        Tag tag2 = new Tag("tag 2");
        Tag tag3 = new Tag("tag 3");
        controller.add(tag1);
        controller.add(tag2);
        controller.add(tag3);
        List<Tag> tags = controller.getAll();
        assertEquals(tags.size(), 3);
        assertTrue(tags.contains(tag1));
        assertTrue(tags.contains(tag2));
        assertTrue(tags.contains(tag3));
        assertTrue(repo.calledMethods.contains("findAll"));
    }

    /**
     * Test for getByID
     */
    @Test
    public void getByNegativeIdTest() {
        var actual = controller.getById(-2);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    /**
     * Test for getByID
     */
    @Test
    public void getByTooLargeIdTest() {
        Tag tag1 = new Tag("tag 1");
        Tag tag2 = new Tag("tag 2");
        Tag tag3 = new Tag("tag 3");
        controller.add(tag1);
        controller.add(tag2);
        controller.add(tag3);
        var actual = controller.getById(212);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    /**
     * Test for getByID
     */
    @Test
    public void getByIdTest() {
        Tag tag1 = new Tag("tag 1");
        Tag tag2 = new Tag("tag 2");
        Tag tag3 = new Tag("tag 3");
        controller.add(tag1);
        controller.add(tag2);
        controller.add(tag3);
        var actual1 = controller.getById(0);
        var actual2 = controller.getById(1);
        var actual3 = controller.getById(2);
        assertEquals(tag1, actual1.getBody());
        assertEquals(tag2, actual2.getBody());
        assertEquals(tag3, actual3.getBody());
        assertTrue(repo.calledMethods.contains("findById"));
    }

    /**
     * Test for modifyTag
     */
    @Test
    public void modifyTagTest() {
        Tag tag1 = new Tag("tag 1");
        Tag tag2 = new Tag("tag 2");
        Tag tag3 = new Tag("tag 3");
        controller.add(tag1);
        controller.add(tag2);
        controller.add(tag3);
        var actual = controller.modifyTag(2, new Tag("tag"));
        assertEquals(actual.getBody().getName(), "tag");
        assertTrue(repo.calledMethods.contains("save"));
    }

    /**
     * Test for removeTag
     */
    @Test
    public void getByUsernameTest() {
        Tag tag1 = new Tag("tag 1");
        Tag tag2 = new Tag("tag 2");
        Tag tag3 = new Tag("tag 3");
        controller.add(tag1);
        controller.add(tag2);
        controller.add(tag3);
        controller.removeTag(1);
        var actual = controller.getById(1);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
}