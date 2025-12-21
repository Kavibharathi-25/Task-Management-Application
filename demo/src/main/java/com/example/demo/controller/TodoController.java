package com.example.demo.controller;

import com.example.demo.service.TodoService;
import com.example.demo.models.Todo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

@ApiResponses(value={
       @ApiResponse(responseCode = "200",description = "Todo Retrieved Successfully"),
      @ApiResponse(responseCode = "404",description = "Todo was not found!")
})

    ResponseEntity<Todo> getTODObyId(@PathVariable long id)
    {
        try{
            Todo createdTodo=todoService.getTodoById(id);
            return new ResponseEntity<>(createdTodo, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    ResponseEntity<List<Todo>> getTodos()
    {
        return new ResponseEntity<List<Todo>>(todoService.getTodos(),HttpStatus.OK);
    }
    @GetMapping("/page")
    ResponseEntity<Page<Todo>> getTodosPaged(@RequestParam int page,@RequestParam int size)
    {
        return new ResponseEntity<>(todoService.getAllTodosPages(page, size),HttpStatus.OK);

    }

    @PostMapping("/create")
    ResponseEntity<Todo> createUser(@RequestBody Todo todo)
    {
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<Todo> updateTodoById(@RequestBody Todo todo)
    {
        return new ResponseEntity<>(todoService.updateTodo(todo),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteTodoById(@PathVariable long id)
    {
        todoService.deleteTodoById(id);
    }

    @RestController
    public static class demoController {
        @GetMapping("/hello")
        String saydemo()
        {
            return "HelloWorld!";
        }

    }
}
