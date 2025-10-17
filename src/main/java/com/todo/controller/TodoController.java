package com.todo.controller;

import com.todo.model.Todo;
import com.todo.repo.TodoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Todo> create(@RequestBody Map<String, Object> payload) {
        // Handle both 'text' and 'title' field names
        String title = payload.containsKey("text") 
            ? (String) payload.get("text") 
            : (String) payload.get("title");
        
        Boolean completed = payload.containsKey("completed") 
            ? (Boolean) payload.get("completed") 
            : false;

        Todo todo = new Todo(title);
        todo.setCompleted(completed);
        Todo saved = repo.save(todo);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH endpoint for partial updates (used by frontend)
    @PatchMapping("/{id}")
    public ResponseEntity<Todo> patch(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        Optional<Todo> existing = repo.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();

        Todo todo = existing.get();
        
        // Update only the fields that are present in the payload
        if (payload.containsKey("text")) {
            todo.setTitle((String) payload.get("text"));
        }
        if (payload.containsKey("title")) {
            todo.setTitle((String) payload.get("title"));
        }
        if (payload.containsKey("completed")) {
            todo.setCompleted((Boolean) payload.get("completed"));
        }

        Todo saved = repo.save(todo);
        return ResponseEntity.ok(saved);
    }

    // PUT endpoint for full updates
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        Optional<Todo> existing = repo.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();

        Todo todo = existing.get();
        
        // Handle both 'text' and 'title' field names
        String title = payload.containsKey("text") 
            ? (String) payload.get("text") 
            : (String) payload.get("title");
        
        Boolean completed = payload.containsKey("completed") 
            ? (Boolean) payload.get("completed") 
            : false;

        todo.setTitle(title);
        todo.setCompleted(completed);
        
        Todo saved = repo.save(todo);
        return ResponseEntity.ok(saved);
    }
}
