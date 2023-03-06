package server.api;

import commons.Board;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardRepository repo;

    public BoardController(BoardRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = {"", "/"})
    public List<Board> getAll() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable("id") String id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @PostMapping(path = { "", "/"})
    public ResponseEntity<Board> add(@RequestBody Board board) {
        Board saved = repo.save(board);
        return ResponseEntity.ok(saved);
    }
}
