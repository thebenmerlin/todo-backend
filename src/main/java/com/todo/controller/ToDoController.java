package com.todo.controller;

import com.todo.model.Todo;
import com.todo.repo.TodoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*") // allow all for now — lock this down for production
public class TodoController {

    private final TodoRepository repo;

    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Todo> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody Todo todo) {
        if (todo.getId() == null || todo.getId().isBlank()) {
            // id and createdAt are set by entity constructor
            todo = new Todo(todo.getTitle());
            todo.setCompleted(todo.isCompleted());
        }
        Todo saved = repo.save(todo);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Optional: update
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable String id, @RequestBody Todo payload) {
        Optional<Todo> existing = repo.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        Todo t = existing.get();
        t.setTitle(payload.getTitle());
        t.setCompleted(payload.isCompleted());
        repo.save(t);
        return ResponseEntity.ok(t);
    }

    // Health endpoint (simple)
    @GetMapping(path = "/../health") // not used; health below at root
    public ResponseEntity<String> healthProxy() {
        return ResponseEntity.ok("ok");
    }
}