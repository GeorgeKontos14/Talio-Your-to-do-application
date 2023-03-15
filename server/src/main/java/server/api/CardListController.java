package server.api;

import commons.CardList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.CardListService;

import java.util.List;

@RestController
@RequestMapping(path = "api/lists")
public class CardListController {
    private final CardListService cLService;

    /**
     * Constructor for the CardListController
     * @param cLService the service that is used
     */
    public CardListController(CardListService cLService){
        this.cLService = cLService;
    }


    /**
     * @return all the CardList objects on the server
     */
    @GetMapping(path = { "", "/" })
    @SuppressWarnings("unused")
    public List<CardList> getAll() {
        return cLService.getAll();
    }

    /**
     * @param id id of the CardList to be retrieved
     * @return a ResponseEntity with the status OK
     * and the value of the CardList
     * if the CardList with the searched id is found,
     * else a ResponseEntity with the BAD_REQUEST status
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unused")
    public ResponseEntity<CardList> getById(@PathVariable("id") long id) {
        CardList list = cLService.getById(id);
        if(list == null){
            System.out.println("this is null");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * @param list the list that is posted
     * @return a ResponseEntity with the status OK and the value of the CardList
     * if the addition is successful, else a ResponseEntity with the BAD_REQUEST status
     */
    @PostMapping(path = "/add")
    public ResponseEntity<CardList> add(@RequestBody CardList list) {
        CardList addedList = cLService.add(list);
//        if (list == null || isNullOrEmpty(list.getName())){
//            return ResponseEntity.badRequest().build();
//        }
//
//        Quote saved = repo.save(quote);
//        return ResponseEntity.ok(saved);
        if(addedList == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(addedList);
    }


    /**
     * @param id the id of the list that is deleted
     * @return a ResponseEntity with the status OK if the deletion is successful,
     * else a ResponseEntity with the BAD_REQUEST status
     */
    @DeleteMapping(path = "/delete/{id}")
    @SuppressWarnings("unused")
    public ResponseEntity<CardList> removeList(@PathVariable("id") long id){
        if(!cLService.delete(id)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Modifies the name of a cardList
     * @param id the id of the list to be renamed
     * @param name the new name
     * @return ok if the modification goes through, false otherwise
     */
    @PutMapping(path = "/{id}")
    @SuppressWarnings("unused")
    public ResponseEntity<CardList> modifyName(@PathVariable("id") long id,
                                               @RequestBody String name){
        if(!cLService.changeName(cLService.getById(id),name)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}

